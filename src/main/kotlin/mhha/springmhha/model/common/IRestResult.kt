package mhha.springmhha.model.common

import com.fasterxml.jackson.annotation.JsonProperty

interface IRestResult {
    var result: Boolean?
    var code: Int?
    var msg: String?
}