package id.niteroomcreation.archcomponent.domain.data.remote.utils

/**
 * Created by Septian Adi Wijaya on 11/06/2021.
 * please be sure to add credential if you use people's code
 */
class ApiResponse<T>(val status: StatusResponse, val body: T?, val message: String?) {
    companion object {
        @JvmStatic
        fun <T> success(body: T): ApiResponse<T> {
            return ApiResponse(StatusResponse.SUCCESS, body, null)
        }

        @JvmStatic
        fun <T> empty(msg: String?, body: T?): ApiResponse<T> {
            return ApiResponse(StatusResponse.EMPTY, body, msg)
        }

        @JvmStatic
        fun <T> error(msg: String?, body: T?): ApiResponse<T> {
            return ApiResponse(StatusResponse.ERROR, body, msg)
        }
    }
}