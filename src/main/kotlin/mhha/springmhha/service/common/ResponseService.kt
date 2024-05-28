package mhha.springmhha.service.common

import mhha.springmhha.model.common.IRestResult
import mhha.springmhha.model.common.RestResult
import mhha.springmhha.model.common.RestResultT
import org.springframework.stereotype.Service

@Service
class ResponseService {
    fun getFailResult(code: Int, msg: String): IRestResult {
        return RestResult().apply {
            this.result = false
            this.code = code
            this.msg = msg
        }
    }
    fun getSuccessResult(): IRestResult {
        return RestResult().apply {
            this.result = true
        }
    }
    fun <T> getResult(data: T): IRestResult {
        return RestResultT<T>().apply {
            this.result = true
            this.data = data
        }
    }
}