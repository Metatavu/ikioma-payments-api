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

import fi.metatavu.ikioma.email.payment.spec.model.Callbacks
import fi.metatavu.ikioma.email.payment.spec.model.RefundItem

import com.squareup.moshi.Json

/**
 * 
 * @param callbackUrls 
 * @param amount The amount to refund. Required for normal merchant accounts. SiS aggregate can refund the whole purchase by providing just the amount 
 * @param refundStamp Merchant specific unique stamp for the refund
 * @param refundReference Merchant reference for the refund
 * @param items Item level refund information for SiS refunds.
 */

data class Refund (
    @Json(name = "callbackUrls")
    val callbackUrls: Callbacks,
    /* The amount to refund. Required for normal merchant accounts. SiS aggregate can refund the whole purchase by providing just the amount  */
    @Json(name = "amount")
    val amount: kotlin.Long? = null,
    /* Merchant specific unique stamp for the refund */
    @Json(name = "refundStamp")
    val refundStamp: kotlin.String? = null,
    /* Merchant reference for the refund */
    @Json(name = "refundReference")
    val refundReference: kotlin.String? = null,
    /* Item level refund information for SiS refunds. */
    @Json(name = "items")
    val items: kotlin.collections.List<RefundItem>? = null
)
