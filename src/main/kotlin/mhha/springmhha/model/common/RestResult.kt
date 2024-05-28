package mhha.springmhha.model.common

import com.fasterxml.jackson.annotation.JsonProperty

class RestResult: IRestResult {
    @get:JsonProperty("Result")
    @set:JsonProperty("Result")
    override var result: Boolean? = null
    @get:JsonProperty("code")
    @set:JsonProperty("code")
    override var code: Int? = null
    @get:JsonProperty("msg")
    @set:JsonProperty("msg")
    override var msg: String? = null
}