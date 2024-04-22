import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import java.io.IOException
// testrug
class ApiClient private constructor() {

    private val client: OkHttpClient = OkHttpClient()

    @Throws(IOException::class)
    fun search(searchQuery: String): String {
        val mediaType = "application/json".toMediaTypeOrNull()

        val requestBody = RequestBody.create(mediaType, "{\"search\": \"$searchQuery\"}")

        val request = Request.Builder()
            .url("https://trains.p.rapidapi.com/")
            .post(requestBody)
            .addHeader("content-type", "application/json")
            .addHeader("X-RapidAPI-Key", "3f064b6ba5msh32c56adc772b6c8p1f411fjsn0fe042541025")
            .addHeader("X-RapidAPI-Host", "trains.p.rapidapi.com")
            .build()

        val response = client.newCall(request).execute()
        return response.body?.string() ?: throw IOException("Failed to get response body")
    }

    companion object {
        @Volatile private var instance: ApiClient? = null

        fun getInstance(): ApiClient {
            return instance ?: synchronized(this) {
                instance ?: ApiClient().also { instance = it }
            }
        }
    }
}
