package fi.metatavu.ikioma.rest

import fi.metatavu.ikioma.email.EmailController
import fi.metatavu.ikioma.email.api.api.spec.V1Api
import fi.metatavu.ikioma.email.api.spec.model.PaymentStatus
import fi.metatavu.ikioma.email.api.spec.model.PrescriptionRenewal
import fi.metatavu.ikioma.keycloak.KeycloakController
import fi.metatavu.ikioma.payments.PaymentController
import fi.metatavu.ikioma.persistence.models.Prescription
import fi.metatavu.ikioma.prescriptions.PrescriptionController
import fi.metatavu.ikioma.prescriptions.PrescriptionRenewalController
import fi.metatavu.ikioma.rest.translate.PrescriptionRenewalTranslator
import java.util.*
import javax.annotation.security.RolesAllowed
import javax.enterprise.context.RequestScoped
import javax.inject.Inject
import javax.transaction.Transactional
import javax.ws.rs.core.Response


/**
 * V1 API
 */
@RequestScoped
@Transactional
class V1ApiImpl : V1Api, AbstractApi() {

    @Inject
    private lateinit var emailController: EmailController

    @Inject
    private lateinit var prescriptionRenewalController: PrescriptionRenewalController

    @Inject
    private lateinit var paymentController: PaymentController

    @Inject
    private lateinit var prescriptionRenewalTranslator: PrescriptionRenewalTranslator

    @Inject
    private lateinit var keycloakController: KeycloakController

    @Inject
    private lateinit var prescriptionController: PrescriptionController

    override fun checkoutFinlandCancel(signature: String): Response? {
        val checkoutParameters = getCheckoutParameters()

        val checkoutReference = checkoutParameters["checkout-reference"] ?: return createBadRequest("No checkout reference")
        val checkoutStatus = checkoutParameters["checkout-status"] ?: return createBadRequest("No checkout status")
        val checkoutStamp = checkoutParameters["checkout-stamp"] ?: return createBadRequest("No checkout stamp")
        val checkoutTransactionId = checkoutParameters["checkout-transaction-id"] ?: return createBadRequest("No checkout transaction id")

        val refNo: UUID?
        try {
            refNo = UUID.fromString(checkoutReference)
        } catch (e: IllegalArgumentException) {
            return createBadRequest("Invalid reference no")
        }

        val prescriptionRenewal = prescriptionRenewalController.findPrescriptionRenewalByReference(reference = refNo)
        prescriptionRenewal ?: return createNotFound()
        if (prescriptionRenewal.stamp != UUID.fromString(checkoutStamp) || prescriptionRenewal.transactionId != checkoutTransactionId) {
            return createForbidden("Payment information does not match")
        }

        if (!paymentController.verifyPayment(signature, checkoutParameters)) {
            return createForbidden("Bad signature")
        }

        prescriptionRenewalController.deletePrescriptionRenewal(prescriptionRenewal)
        return createNoContent()
    }

    override fun checkoutFinlandSuccess(
        signature: String
    ): Response {
        val checkoutParameters = getCheckoutParameters()

        val checkoutReference = checkoutParameters["checkout-reference"] ?: return createBadRequest("No checkout reference")
        val checkoutStatus = checkoutParameters["checkout-status"] ?: return createBadRequest("No checkout status")
        val checkoutStamp = checkoutParameters["checkout-stamp"] ?: return createBadRequest("No checkout stamp")
        val checkoutTransactionId = checkoutParameters["checkout-transaction-id"] ?: return createBadRequest("No checkout transaction id")

        val refNo: UUID?
        try {
            refNo = UUID.fromString(checkoutReference)
        } catch (e: IllegalArgumentException) {
            return createBadRequest("Invalid reference no")
        }

        val prescriptionRenewal = prescriptionRenewalController.findPrescriptionRenewalByReference(reference = refNo)
        prescriptionRenewal ?: return createNotFound()
        val userId = prescriptionRenewal.creatorId

        val ssn = keycloakController.getUserSSN(userId) ?: return createNotFound("User with ID $userId could not be found!")
        val firstName = keycloakController.getFirstName(userId) ?: return createNotFound("User with ID $userId could not be found!")
        val lastName = keycloakController.getLastName(userId) ?: return createNotFound("User with ID $userId could not be found!")

        if (prescriptionRenewal.stamp != UUID.fromString(checkoutStamp) || prescriptionRenewal.transactionId != checkoutTransactionId) {
            return createForbidden("Payment information does not match")
        }

        if (!paymentController.verifyPayment(signature, checkoutParameters)) {
            return createForbidden("Bad signature")
        }

        val practitionerEmail = keycloakController.getUserEmail(prescriptionRenewal.practitionerUserId) ?: return createNotFound("No practitioner email found")
        when (checkoutStatus) {
            "ok" -> {
                prescriptionRenewalController.updatePrescriptionRenewalStatus(prescriptionRenewal, PaymentStatus.PAID)
                emailController.sendPrescriptionRenewalEmail(
                    prescriptionRenewal,
                    practitionerEmail,
                    ssn,
                    firstName,
                    lastName
                )
                prescriptionRenewalController.deletePrescriptionRenewal(prescriptionRenewal)
            }
            "pending", "delayed" -> return createAccepted()
            "fail" -> {
                return createBadRequest("Payment failed")
            }
        }

        return createOk()
    }

    @RolesAllowed(value = [UserRole.PATIENT.name])
    override fun createPrescriptionRenewal(prescriptionRenewal: PrescriptionRenewal): Response {
        val userId = loggedUserId ?: return createUnauthorized("Unauthorized")
        keycloakController.getUserEmail(prescriptionRenewal.practitionerUserId) ?: return createNotFound("No practitioner email found")

        prescriptionRenewal.successRedirectUrl ?: return createBadRequest("Success redirect URL is required")
        prescriptionRenewal.cancelRedirectUrl ?: return createBadRequest("Cancel Redirect URL is required")
        val paymentData = paymentController.initRenewPrescriptionPayment(prescriptionRenewal, userId)
        paymentData ?: return createInternalServerError("Failed to create payment")

        val prescriptions = mutableListOf<Prescription>()
        prescriptionRenewal.prescriptions.forEach { prescriptionName ->
            val prescription = prescriptionController.findByName(prescriptionName)
            if (prescription == null) {
                prescriptions.add(prescriptionController.createPrescription(prescriptionName))
            } else {
                prescriptions.add(prescription)
            }
        }

        val newPrescriptionRenewal = prescriptionRenewalController.createPrescriptionRenewal(
            id = paymentData.id,
            stamp = paymentData.stamp,
            price = paymentData.price,
            prescriptions = prescriptions,
            practitionerUserId = prescriptionRenewal.practitionerUserId,
            paymentStatus = prescriptionRenewal.status,
            paymentUrl = paymentData.paymentUrl,
            transactionId = paymentData.transactionId,
            checkoutAccount = paymentData.checkoutAccount,
            userId = userId
        )

        return createOk(prescriptionRenewalTranslator.translate(newPrescriptionRenewal))
    }

    @RolesAllowed(value = [UserRole.PATIENT.name])
    override fun getPrescriptionRenewal(id: UUID): Response {
        loggedUserId ?: return createUnauthorized("Unauthorized")
        val prescriptionRenewal = prescriptionRenewalController.findPrescriptionRenewal(id)

        prescriptionRenewal ?: return createNotFound()
        return createOk(prescriptionRenewalTranslator.translate(prescriptionRenewal))
    }

    override fun ping(): Response {
        return Response.ok("pong").build()
    }
}