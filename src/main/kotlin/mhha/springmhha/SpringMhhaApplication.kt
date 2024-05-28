package mhha.springmhha

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.ApplicationPidFileWriter
import org.springframework.boot.runApplication
import org.springframework.context.ConfigurableApplicationContext

@SpringBootApplication
class SpringMhhaApplication {
    companion object {
        var ctx: ConfigurableApplicationContext? = null
    }
}

fun main(args: Array<String>) {
    val app = SpringApplicationBuilder()
    app.sources(SpringMhhaApplication::class.java)
            .listeners(ApplicationPidFileWriter("./spring-mhha.pid"))
            .build()

    SpringMhhaApplication.ctx = app.run(*args)
//    runApplication<SpringMhhaApplication>(*args)
}
