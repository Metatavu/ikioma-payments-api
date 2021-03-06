/**
* Payment service API
* Payment processing API  Note: The API is currently under development. Some endpoints do not yet have all the features described here, and the responses of some do not match the description here. 
*
* The version of the OpenAPI document: 2.0.0
* 
*
* NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech).
* https://openapi-generator.tech
* Do not edit the class manually.
*/
package fi.metatavu.ikioma.email.payment.spec.model


import com.squareup.moshi.Json

/**
 * Response for a successful merchant initiated transaction payment request. 
 * @param transactionId Checkout assigned transaction ID for the payment.
 */

data class TokenMITPaymentResponse (
    /* Checkout assigned transaction ID for the payment. */
    @Json(name = "transactionId")
    val transactionId: java.util.UUID
)

