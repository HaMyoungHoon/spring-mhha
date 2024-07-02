package mhha.springmhha.controller

import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.tags.Tag
import mhha.springmhha.config.FConstants
import mhha.springmhha.model.common.IRestResult
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.net.URI

@Tag(name = "RedirectController")
@RestController
@RequestMapping(value = ["/"])
@CrossOrigin(origins = [FConstants.HTTP_MHHA, FConstants.HTTPS_MHHA], allowedHeaders = ["*"])
class RedirectController {
	@Hidden
	@GetMapping
	fun redirectEmptyPath(): ResponseEntity<*> {
		return ResponseEntity<IRestResult>(HttpHeaders().apply {
			location = URI.create("/swagger-ui/index.html")
		}, HttpStatus.MOVED_PERMANENTLY)
	}
	@Hidden
	@GetMapping(value = ["ads.txt"])
	fun adsTxt(): String {
		return File("./ads.txt").bufferedReader().let { x ->
			x.use { y -> y.readText() }
		}
	}
}