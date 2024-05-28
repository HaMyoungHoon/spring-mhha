package mhha.springmhha.advice.exception

import java.lang.RuntimeException

class AuthenticationEntryPointException : RuntimeException {
    constructor(msg : String, t : Throwable): super(msg, t)
    constructor(msg : String): super(msg)
    constructor(): super()
}