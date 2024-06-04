package mhha.springmhha.model.common

import com.fasterxml.jackson.annotation.JsonProperty

class RestResult: IRestResult {
    override var result: Boolean? = null
    override var code: Int? = null
    override var msg: String? = null
}