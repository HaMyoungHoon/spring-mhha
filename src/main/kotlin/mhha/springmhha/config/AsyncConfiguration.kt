package mhha.springmhha.config

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.task.AsyncTaskExecutor
import org.springframework.scheduling.annotation.AsyncConfigurer
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.web.servlet.config.annotation.AsyncSupportConfigurer
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.util.concurrent.Executor


@Configuration
@EnableAsync
@EnableScheduling
class AsyncConfiguration : AsyncConfigurer {
	lateinit var asyncTaskExecutor: AsyncTaskExecutor
	@Bean(name = [FConstants.ASYNC_TASK_EXECUTOR])
	override fun getAsyncExecutor(): Executor? {
		asyncTaskExecutor = ThreadPoolTaskExecutor().apply {
			corePoolSize = Integer.MAX_VALUE
			setThreadNamePrefix("mhha.springmhha.async_executor_thread")
		}

		return asyncTaskExecutor
	}

	@Bean
	fun webMvcConfigurerAdapter(@Qualifier("applicationTaskExecutor") taskExecutor: AsyncTaskExecutor?): WebMvcConfigurer? {
		val executor = taskExecutor ?: asyncTaskExecutor
		return object : WebMvcConfigurer {
			override fun configureAsyncSupport(configurer: AsyncSupportConfigurer) {
				configurer
					.setDefaultTimeout(Long.MAX_VALUE).apply {
						if (taskExecutor != null) {
							setTaskExecutor(executor)
						}
					}
			}
		}
	}
}