package mhha.springmhha.model.common

import com.fasterxml.jackson.annotation.JsonProperty

class RestResult: IRestResult {
    @get:JsonProperty("Result")
    @set:JsonProperty("Result")
    override var result: Boolean? = null
    @get:JsonProperty("Code")
    @set:JsonProperty("Code")
    override var code: Int? = null
    @get:JsonProperty("Msg")
    @set:JsonProperty("Msg")
    override var msg: String? = null
}