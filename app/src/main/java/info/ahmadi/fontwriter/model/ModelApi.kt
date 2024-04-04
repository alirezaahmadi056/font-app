package info.ahmadi.fontwriter.model

import com.google.gson.annotations.SerializedName

data class LoginApiRequest(
    @SerializedName("phone")
    val phone : String
)
data class CheckCodeApiRequest(
    @SerializedName("phone")
    val phone: String,
    @SerializedName("code")
    val code: String
)
data class FontApiResponse(
    @SerializedName("fonts")
    val fonts:ArrayList<FontApiResponseData>
)
data class FontApiResponseData(
    @SerializedName("name")
    val name:String,
    @SerializedName("image")
    val image:String,
    @SerializedName("font")
    val font:String
)
data class AssetsApiResponse(
    @SerializedName("assets")
    val assets:ArrayList<AssetsApiResponseData>
)
data class AssetsApiResponseData(
    @SerializedName("name")
    val name:String,
    @SerializedName("link")
    val link:String,
    @SerializedName("is_download")
    val isDownload:Boolean
)