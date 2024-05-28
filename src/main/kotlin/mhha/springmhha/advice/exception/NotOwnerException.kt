package mhha.springmhha.advice.exception

import java.lang.RuntimeException

class NotOwnerException : RuntimeException {
    constructor(msg : String, t : Throwable): super(msg, t)
    constructor(msg : String): super(msg)
    constructor(): super()
}