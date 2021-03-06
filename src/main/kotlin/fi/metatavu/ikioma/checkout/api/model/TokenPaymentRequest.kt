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

import fi.metatavu.ikioma.email.payment.spec.model.Address
import fi.metatavu.ikioma.email.payment.spec.model.Callbacks
import fi.metatavu.ikioma.email.payment.spec.model.Customer
import fi.metatavu.ikioma.email.payment.spec.model.Item

import com.squareup.moshi.Json

/**
 * Payment request payload
 * @param stamp Merchant specific unique stamp
 * @param reference Merchant reference for the payment
 * @param amount Total amount of the payment (sum of items), VAT included
 * @param currency 
 * @param language Alpha-2 language code for the payment process
 * @param items 
 * @param customer 
 * @param redirectUrls 
 * @param token Payment card token received from request to /tokenization/{checkout-tokenization-id}
 * @param orderId Order ID. Used for eg. Collector payments order ID. If not given, merchant reference is used instead.
 * @param deliveryAddress 
 * @param invoicingAddress 
 * @param callbackUrls 
 * @param callbackDelay Callback delay in seconds. If callback URLs and delay are provided, callbacks will be called after the delay.
 */

data class TokenPaymentRequest (
    /* Merchant specific unique stamp */
    @Json(name = "stamp")
    val stamp: kotlin.String,
    /* Merchant reference for the payment */
    @Json(name = "reference")
    val reference: kotlin.String,
    /* Total amount of the payment (sum of items), VAT included */
    @Json(name = "amount")
    val amount: kotlin.Long,
    @Json(name = "currency")
    val currency: TokenPaymentRequest.Currency,
    /* Alpha-2 language code for the payment process */
    @Json(name = "language")
    val language: TokenPaymentRequest.Language,
    @Json(name = "items")
    val items: kotlin.collections.List<Item>,
    @Json(name = "customer")
    val customer: Customer,
    @Json(name = "redirectUrls")
    val redirectUrls: Callbacks,
    /* Payment card token received from request to /tokenization/{checkout-tokenization-id} */
    @Json(name = "token")
    val token: kotlin.String,
    /* Order ID. Used for eg. Collector payments order ID. If not given, merchant reference is used instead. */
    @Json(name = "orderId")
    val orderId: kotlin.String? = null,
    @Json(name = "deliveryAddress")
    val deliveryAddress: Address? = null,
    @Json(name = "invoicingAddress")
    val invoicingAddress: Address? = null,
    @Json(name = "callbackUrls")
    val callbackUrls: Callbacks? = null,
    /* Callback delay in seconds. If callback URLs and delay are provided, callbacks will be called after the delay. */
    @Json(name = "callbackDelay")
    val callbackDelay: kotlin.Int? = null
) {

    /**
     * 
     * Values: eUR
     */
    enum class Currency(val value: kotlin.String) {
        @Json(name = "EUR") eUR("EUR");
    }
    /**
     * Alpha-2 language code for the payment process
     * Values: fI,sV,eN
     */
    enum class Language(val value: kotlin.String) {
        @Json(name = "FI") fI("FI"),
        @Json(name = "SV") sV("SV"),
        @Json(name = "EN") eN("EN");
    }
}

