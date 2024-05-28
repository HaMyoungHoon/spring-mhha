package mhha.springmhha.model.common

import com.fasterxml.jackson.annotation.JsonProperty

interface IRestResult {
    @get:JsonProperty("Result")
    @set:JsonProperty("Result")
    var result: Boolean?
    @get:JsonProperty("Code")
    @set:JsonProperty("Code")
    var code: Int?
    @get:JsonProperty("Msg")
    @set:JsonProperty("Msg")
    var msg: String?
}