package mhha.springmhha.advice

import com.google.gson.Gson
import mhha.springmhha.advice.exception.CommunicationException
import mhha.springmhha.model.common.IRestResult
import mhha.springmhha.model.common.RestResult
import mhha.springmhha.model.common.RestResultT
import org.json.JSONObject
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.util.MultiValueMap
import org.springframework.web.client.RestTemplate
import java.util.Optional

class FHttpRequest(private val url: String) {
    private var req: HttpEntity<MultiValueMap<String, Any>> = HttpEntity(null, null)

    fun setParam(mediaType: MediaType, params: MultiValueMap<String, Any>): FHttpRequest {
        val headers = HttpHeaders().apply {
            contentType = mediaType
        }
        this.req = HttpEntity(params, headers)
        return this
    }
    fun <T> get(responseType: Class<T>) = Optional.ofNullable(RestTemplate().getForEntity(this.url, responseType)).get()
    fun <T> post(responseType: Class<T>) = Optional.ofNullable(RestTemplate().postForEntity(this.url, this.req, responseType)).get()
    fun <T, Ret> getData(data: ResponseEntity<T>, responseType: Class<Ret>): Ret {
        val responseResult = data.body ?: throw CommunicationException()
        if ((responseResult as IRestResult).result == false) {
            throw Exception(responseResult.msg)
        }

        if (responseType.name.contains("int", true) || responseType.name.contains("float", true) ||
                responseType.name.contains("double", true) || responseType.name.contains("float", true)) {
            return (responseResult as RestResultT<Ret>).data!!
        }

        val linkedBuff: LinkedHashMap<String, Any> = (responseResult as RestResultT<Ret>).data as LinkedHashMap<String, Any>
        val jsonObject = JSONObject()
        for (key in linkedBuff.keys) {
            jsonObject.put(key, linkedBuff[key])
        }
        return Gson().fromJson(jsonObject.toString(), responseType)
    }
}