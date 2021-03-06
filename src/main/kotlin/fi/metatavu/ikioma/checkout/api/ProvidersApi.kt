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
package fi.metatavu.ikioma.email.payment.api.spec

import fi.metatavu.ikioma.email.payment.spec.model.BasePaymentMethodProvider
import fi.metatavu.ikioma.email.payment.spec.model.Error
import fi.metatavu.ikioma.email.payment.spec.model.GroupedPaymentProvidersResponse

import org.openapitools.client.infrastructure.ApiClient
import org.openapitools.client.infrastructure.ClientException
import org.openapitools.client.infrastructure.ClientError
import org.openapitools.client.infrastructure.ServerException
import org.openapitools.client.infrastructure.ServerError
import org.openapitools.client.infrastructure.MultiValueMap
import org.openapitools.client.infrastructure.RequestConfig
import org.openapitools.client.infrastructure.RequestMethod
import org.openapitools.client.infrastructure.ResponseType
import org.openapitools.client.infrastructure.Success
import org.openapitools.client.infrastructure.toMultiValue

class ProvidersApi(basePath: kotlin.String = defaultBasePath) : ApiClient(basePath) {
    companion object {
        @JvmStatic
        val defaultBasePath: String by lazy {
            System.getProperties().getProperty("org.openapitools.client.baseUrl", "https://api.checkout.fi")
        }
    }

    /**
    * List grouped merchant payment methods
    * Similar to the /merchants/payment-providers, but in addition of a flat list of providers, it returns payment group data containing localized names, icon URLs and grouped providers, and a localized text with a link to the terms of payment. 
    * @param checkoutAccount Merchant ID (optional)
    * @param checkoutAlgorithm HMAC algorithm (optional)
    * @param checkoutMethod HTTP method of the request (optional)
    * @param checkoutTimestamp Current timestamp in ISO 8601 format (optional)
    * @param checkoutNonce Unique random identifier (optional)
    * @param signature HMAC signature calculated over the request headers and payload (optional)
    * @param amount Optional amount in minor unit (eg. EUR cents) for the payment providers. Some providers have minimum or maximum amounts that can be purchased.  (optional)
    * @param groups Comma separated list of payment method groups to include in the reply. (optional)
    * @param language Language code of the language the terms of payment and the payment group names will be localized in. Defaults to FI if left undefined  (optional)
    * @return GroupedPaymentProvidersResponse
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getGroupedPaymentProviders(checkoutAccount: kotlin.Int?, checkoutAlgorithm: kotlin.String?, checkoutMethod: kotlin.String?, checkoutTimestamp: java.time.OffsetDateTime?, checkoutNonce: kotlin.String?, signature: kotlin.String?, amount: kotlin.Int?, groups: kotlin.collections.List<kotlin.String>?, language: kotlin.String?) : GroupedPaymentProvidersResponse {
        val localVariableConfig = getGroupedPaymentProvidersRequestConfig(checkoutAccount = checkoutAccount, checkoutAlgorithm = checkoutAlgorithm, checkoutMethod = checkoutMethod, checkoutTimestamp = checkoutTimestamp, checkoutNonce = checkoutNonce, signature = signature, amount = amount, groups = groups, language = language)

        val localVarResponse = request<GroupedPaymentProvidersResponse>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as GroupedPaymentProvidersResponse
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException("Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException("Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
        }
    }

    /**
    * To obtain the request config of the operation getGroupedPaymentProviders
    *
    * @param checkoutAccount Merchant ID (optional)
    * @param checkoutAlgorithm HMAC algorithm (optional)
    * @param checkoutMethod HTTP method of the request (optional)
    * @param checkoutTimestamp Current timestamp in ISO 8601 format (optional)
    * @param checkoutNonce Unique random identifier (optional)
    * @param signature HMAC signature calculated over the request headers and payload (optional)
    * @param amount Optional amount in minor unit (eg. EUR cents) for the payment providers. Some providers have minimum or maximum amounts that can be purchased.  (optional)
    * @param groups Comma separated list of payment method groups to include in the reply. (optional)
    * @param language Language code of the language the terms of payment and the payment group names will be localized in. Defaults to FI if left undefined  (optional)
    * @return RequestConfig
    */
    fun getGroupedPaymentProvidersRequestConfig(checkoutAccount: kotlin.Int?, checkoutAlgorithm: kotlin.String?, checkoutMethod: kotlin.String?, checkoutTimestamp: java.time.OffsetDateTime?, checkoutNonce: kotlin.String?, signature: kotlin.String?, amount: kotlin.Int?, groups: kotlin.collections.List<kotlin.String>?, language: kotlin.String?) : RequestConfig {
        val localVariableBody: kotlin.Any? = null
        val localVariableQuery: MultiValueMap = mutableMapOf<kotlin.String, List<kotlin.String>>()
            .apply {
                if (amount != null) {
                    put("amount", listOf(amount.toString()))
                }
                if (groups != null) {
                    put("groups", toMultiValue(groups.toList(), "csv"))
                }
                if (language != null) {
                    put("language", listOf(language.toString()))
                }
            }
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        checkoutAccount?.apply { localVariableHeaders["checkout-account"] = this.toString() }
        checkoutAlgorithm?.apply { localVariableHeaders["checkout-algorithm"] = this.toString() }
        checkoutMethod?.apply { localVariableHeaders["checkout-method"] = this.toString() }
        checkoutTimestamp?.apply { localVariableHeaders["checkout-timestamp"] = this.toString() }
        checkoutNonce?.apply { localVariableHeaders["checkout-nonce"] = this.toString() }
        signature?.apply { localVariableHeaders["signature"] = this.toString() }
        
        val localVariableConfig = RequestConfig(
            method = RequestMethod.GET,
            path = "/merchants/grouped-payment-providers",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )

        return localVariableConfig
    }

    /**
    * List merchant payment methods
    * Returns the payment methods available for merchant without creating a new payment to the system. Useful for displaying payment provider icons during different phases of checkout before finally actually creating the payment request when customer decides to pay 
    * @param checkoutAccount Merchant ID (optional)
    * @param checkoutAlgorithm HMAC algorithm (optional)
    * @param checkoutMethod HTTP method of the request (optional)
    * @param checkoutTimestamp Current timestamp in ISO 8601 format (optional)
    * @param checkoutNonce Unique random identifier (optional)
    * @param signature HMAC signature calculated over the request headers and payload (optional)
    * @param amount Optional amount in minor unit (eg. EUR cents) for the payment providers. Some providers have minimum or maximum amounts that can be purchased.  (optional)
    * @param groups Comma separated list of payment method groups to include in the reply. (optional)
    * @return kotlin.collections.List<BasePaymentMethodProvider>
    * @throws UnsupportedOperationException If the API returns an informational or redirection response
    * @throws ClientException If the API returns a client error response
    * @throws ServerException If the API returns a server error response
    */
    @Suppress("UNCHECKED_CAST")
    @Throws(UnsupportedOperationException::class, ClientException::class, ServerException::class)
    fun getPaymentProviders(checkoutAccount: kotlin.Int?, checkoutAlgorithm: kotlin.String?, checkoutMethod: kotlin.String?, checkoutTimestamp: java.time.OffsetDateTime?, checkoutNonce: kotlin.String?, signature: kotlin.String?, amount: kotlin.Int?, groups: kotlin.collections.List<kotlin.String>?) : kotlin.collections.List<BasePaymentMethodProvider> {
        val localVariableConfig = getPaymentProvidersRequestConfig(checkoutAccount = checkoutAccount, checkoutAlgorithm = checkoutAlgorithm, checkoutMethod = checkoutMethod, checkoutTimestamp = checkoutTimestamp, checkoutNonce = checkoutNonce, signature = signature, amount = amount, groups = groups)

        val localVarResponse = request<kotlin.collections.List<BasePaymentMethodProvider>>(
            localVariableConfig
        )

        return when (localVarResponse.responseType) {
            ResponseType.Success -> (localVarResponse as Success<*>).data as kotlin.collections.List<BasePaymentMethodProvider>
            ResponseType.Informational -> throw UnsupportedOperationException("Client does not support Informational responses.")
            ResponseType.Redirection -> throw UnsupportedOperationException("Client does not support Redirection responses.")
            ResponseType.ClientError -> {
                val localVarError = localVarResponse as ClientError<*>
                throw ClientException("Client error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
            ResponseType.ServerError -> {
                val localVarError = localVarResponse as ServerError<*>
                throw ServerException("Server error : ${localVarError.statusCode} ${localVarError.message.orEmpty()}", localVarError.statusCode, localVarResponse)
            }
        }
    }

    /**
    * To obtain the request config of the operation getPaymentProviders
    *
    * @param checkoutAccount Merchant ID (optional)
    * @param checkoutAlgorithm HMAC algorithm (optional)
    * @param checkoutMethod HTTP method of the request (optional)
    * @param checkoutTimestamp Current timestamp in ISO 8601 format (optional)
    * @param checkoutNonce Unique random identifier (optional)
    * @param signature HMAC signature calculated over the request headers and payload (optional)
    * @param amount Optional amount in minor unit (eg. EUR cents) for the payment providers. Some providers have minimum or maximum amounts that can be purchased.  (optional)
    * @param groups Comma separated list of payment method groups to include in the reply. (optional)
    * @return RequestConfig
    */
    fun getPaymentProvidersRequestConfig(checkoutAccount: kotlin.Int?, checkoutAlgorithm: kotlin.String?, checkoutMethod: kotlin.String?, checkoutTimestamp: java.time.OffsetDateTime?, checkoutNonce: kotlin.String?, signature: kotlin.String?, amount: kotlin.Int?, groups: kotlin.collections.List<kotlin.String>?) : RequestConfig {
        val localVariableBody: kotlin.Any? = null
        val localVariableQuery: MultiValueMap = mutableMapOf<kotlin.String, List<kotlin.String>>()
            .apply {
                if (amount != null) {
                    put("amount", listOf(amount.toString()))
                }
                if (groups != null) {
                    put("groups", toMultiValue(groups.toList(), "csv"))
                }
            }
        val localVariableHeaders: MutableMap<String, String> = mutableMapOf()
        checkoutAccount?.apply { localVariableHeaders["checkout-account"] = this.toString() }
        checkoutAlgorithm?.apply { localVariableHeaders["checkout-algorithm"] = this.toString() }
        checkoutMethod?.apply { localVariableHeaders["checkout-method"] = this.toString() }
        checkoutTimestamp?.apply { localVariableHeaders["checkout-timestamp"] = this.toString() }
        checkoutNonce?.apply { localVariableHeaders["checkout-nonce"] = this.toString() }
        signature?.apply { localVariableHeaders["signature"] = this.toString() }
        
        val localVariableConfig = RequestConfig(
            method = RequestMethod.GET,
            path = "/merchants/payment-providers",
            query = localVariableQuery,
            headers = localVariableHeaders,
            body = localVariableBody
        )

        return localVariableConfig
    }

}
