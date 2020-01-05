package com.pauloavelar.matchersdemo

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.PropertyNamingStrategy
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

fun main(args: Array<String>) {
    runApplication<MatchersDemo>(*args)
}

@Configuration
@SpringBootApplication
open class MatchersDemo {
    @Bean open fun objectMapper(builder: Jackson2ObjectMapperBuilder): ObjectMapper {
        return builder
                .propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .build()
    }
}

@RestController
open class DemoController {
    @GetMapping("/full")
    fun getFullObject() = ResponseObject(
            stringProperty = "STRING",
            numberProperty = 1,
            stringArrayProperty = arrayOf("STRING1", "STRING2"),
            numberArrayProperty = arrayOf(1, 2, 3)
    )

    @GetMapping("/partial")
    fun getPartialObject() = ResponseObject(
            stringProperty = "STRING",
            numberArrayProperty = arrayOf(1, 2, 3)
    )
}

@Suppress("ArrayInDataClass")
data class ResponseObject(
        val stringProperty: String? = null,
        val numberProperty: Number? = null,
        val numberArrayProperty: Array<Number>? = null,
        val stringArrayProperty: Array<String>? = null
)
