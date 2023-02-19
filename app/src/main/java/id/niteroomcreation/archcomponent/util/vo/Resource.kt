package id.niteroomcreation.archcomponent.util.vo

/**
 * Created by Septian Adi Wijaya on 08/06/2021.
 * please be sure to add credential if you use people's code
 *
 *
 * berfungsi sebagai pembungkus data yang akan dikelola dan ditampilkan
 */
class Resource<T>(val status: Status, val data: T?, val message: String?) {

    override fun toString(): String {
        return "Resource{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}'
    }

    companion object {
        @JvmStatic
        fun <T> success(data: T?): Resource<T?> {
            return Resource(Status.SUCCESS, data, null)
        }

        @JvmStatic
        fun <T> error(msg: String?, data: T?): Resource<T?> {
            return Resource(Status.ERROR, data, msg)
        }

        @JvmStatic
        fun <T> loading(data: T?): Resource<T?> {
            return Resource(Status.LOADING, data, null)
        }
    }
}