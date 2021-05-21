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
 * ID, name and icon URLs for a payment method group
 * @param id ID of the group
 * @param name Localized name of the group
 * @param icon URL to the group PNG icon
 * @param svg URL to the group SVG icon (recommended to be used instead if PNG)
 */

data class PaymentMethodGroupData (
    /* ID of the group */
    @Json(name = "id")
    val id: PaymentMethodGroupData.Id,
    /* Localized name of the group */
    @Json(name = "name")
    val name: kotlin.String,
    /* URL to the group PNG icon */
    @Json(name = "icon")
    val icon: kotlin.String,
    /* URL to the group SVG icon (recommended to be used instead if PNG) */
    @Json(name = "svg")
    val svg: kotlin.String
) {

    /**
     * ID of the group
     * Values: mobile,bank,creditcard,credit
     */
    enum class Id(val value: kotlin.String) {
        @Json(name = "mobile") mobile("mobile"),
        @Json(name = "bank") bank("bank"),
        @Json(name = "creditcard") creditcard("creditcard"),
        @Json(name = "credit") credit("credit");
    }
}
