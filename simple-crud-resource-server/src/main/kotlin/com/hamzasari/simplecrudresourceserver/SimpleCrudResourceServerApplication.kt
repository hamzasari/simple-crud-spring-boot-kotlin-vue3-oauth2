package com.hamzasari.simplecrudresourceserver

import com.hamzasari.simplecrudresourceserver.model.Todo
import com.hamzasari.simplecrudresourceserver.repository.TodoRepository
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.core.Ordered
import org.springframework.data.rest.core.config.RepositoryRestConfiguration
import org.springframework.data.rest.webmvc.config.RepositoryRestConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.springframework.web.servlet.config.annotation.CorsRegistry
import java.util.*
import java.util.stream.Stream

@SpringBootApplication
class DemoApplication {
	// Bootstrap some test data into the in-memory database
	@Bean
	fun init(repository: TodoRepository): ApplicationRunner {
		return ApplicationRunner { args: ApplicationArguments? ->
			val rd = Random()
			Stream.of<String>(
				"Buy milk",
				"Eat pizza",
				"Update tutorial",
				"Study Vue",
				"Go kayaking"
			).forEach { name: String ->
				val todo = Todo()
				todo.setTitle(name)
				todo.setCompleted(rd.nextBoolean())
				repository.save(todo)
			}
			val items = repository.findAll()
			for (item in items) {
				println(item.toString())
			}
		}
	}

	// Fix the CORS errors
	@Bean
	fun simpleCorsFilter(): FilterRegistrationBean<*> {
		val source = UrlBasedCorsConfigurationSource()
		val config = CorsConfiguration()
		config.allowCredentials = true
		// *** URL below needs to match the Vue client URL and port ***
		config.allowedOrigins = listOf("http://localhost:8080")
		config.allowedMethods = listOf("*")
		config.allowedHeaders = listOf("*")
		source.registerCorsConfiguration("/**", config)
		val bean: FilterRegistrationBean<*> = FilterRegistrationBean(CorsFilter(source))
		bean.order = Ordered.HIGHEST_PRECEDENCE
		return bean
	}

	// Expose IDs of Todo items
	@Component
	internal inner class RestRepositoryConfigurator : RepositoryRestConfigurer {
		override fun configureRepositoryRestConfiguration(config: RepositoryRestConfiguration, cors: CorsRegistry) {
			config.exposeIdsFor(Todo::class.java)
		}
	}

	companion object {
		@JvmStatic
		fun main(args: Array<String>) {
			SpringApplication.run(DemoApplication::class.java, *args)
		}
	}
}