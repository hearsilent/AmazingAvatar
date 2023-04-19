package hearsilent.amazingavatar.libs

import hearsilent.amazingavatar.callbacks.AvatarCallback
import hearsilent.amazingavatar.models.AvatarModel
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import java.util.concurrent.TimeUnit

object NetworkHelper {
    private var mClient: OkHttpClient? = null
    private fun init() {
        mClient = OkHttpClient().newBuilder().followRedirects(false).followSslRedirects(false)
            .connectTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS).build()
    }

    private val client: OkHttpClient
        get() {
            mClient ?: init()
            return mClient!!
        }

    private const val AVATAR_URL = "https://tinyfac.es/api/data?limit=1&quality=0"

    fun getAvatar(callback: AvatarCallback) {
        val request: Request = Request.Builder().url(AVATAR_URL).get().build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                callback.onFail()
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.code == 200) {
                    try {
                        val responseBodyCopy = response.peekBody(Long.MAX_VALUE)
                        val body = responseBodyCopy.string()
                        val jsonArray = JSONArray(body)
                        val jsonObject = jsonArray.getJSONObject(0)
                        val model = AvatarModel()
                        model.url = jsonObject.getString("url")
                        model.firstName = jsonObject.getString("first_name")
                        model.lastName = jsonObject.getString("last_name")
                        callback.onSuccess(model)
                    } catch (ignore: Exception) {
                        callback.onFail()
                    }
                } else {
                    callback.onFail()
                }
            }
        })
    }
}