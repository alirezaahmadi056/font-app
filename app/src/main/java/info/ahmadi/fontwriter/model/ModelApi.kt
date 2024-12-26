package info.ahmadi.fontwriter.model

import com.google.gson.annotations.SerializedName

data class LoginApiRequest(
    @SerializedName("phone")
    val phone: String
)

data class CheckCodeApiRequest(
    @SerializedName("phone")
    val phone: String,
    @SerializedName("code")
    val code: String
)

data class FontApiResponse(
    @SerializedName("fonts")
    val fonts: ArrayList<FontApiResponseData>
)

data class FontApiResponseData(
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String,
    @SerializedName("font")
    val font: String,
    @SerializedName("font_name")
    val fontName: String,
    @SerializedName("type")
    val type: String
)

data class AssetsApiResponse(
    @SerializedName("stickers")
    val stickers: ArrayList<AssetsApiResponseData>,
    @SerializedName("backgrounds")
    val backgrounds: ArrayList<AssetsApiResponseData>
)

data class AssetsApiResponseData(
    @SerializedName("name")
    val name: String,
    @SerializedName("link")
    val link: String,
    @SerializedName("is_download")
    val isDownload: Boolean,
    @SerializedName("asset_name")
    val assetsName: String
)

data class VideoApiResponseData(
    @SerializedName("video")
    val videoLink: String
)

