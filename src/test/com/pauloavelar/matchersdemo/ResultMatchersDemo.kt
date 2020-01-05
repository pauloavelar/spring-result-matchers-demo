package com.pauloavelar.matchersdemo

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.content

@AutoConfigureMockMvc
@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ResultMatchersDemo {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun `it should match the whole full object`() {
        mockMvc.perform(get("/full"))
                .andExpect(content().json("""
                    {
                        "string_property": "STRING",
                        "number_property": 1,
                        "string_array_property": ["STRING1", "STRING2"],
                        "number_array_property": [1, 2, 3]
                    }
                """))
    }

    @Test
    fun `it should match some fields the full object`() {
        mockMvc.perform(get("/full"))
                .andExpect(content().json("""
                    {
                        "string_property": "STRING",
                        "number_property": 1
                    }
                """))
    }

    @Test(expected = AssertionError::class)
    fun `it should fail to match the whole object against a partial object`() {
        mockMvc.perform(get("/partial"))
                .andExpect(content().json("""
                    {
                        "string_property": "STRING",
                        "number_property": 1,
                        "string_array_property": ["STRING1", "STRING2"],
                        "number_array_property": [1, 2, 3]
                    }
                """))
    }

    @Test
    fun `it should match the string array out of order`() {
        mockMvc.perform(get("/full"))
                .andExpect(content().json("""
                    {
                        "string_array_property": ["STRING2", "STRING1"]
                    }
                """))
    }

    @Test(expected = AssertionError::class)
    fun `it should fail to partially match the string array`() {
        mockMvc.perform(get("/full"))
                .andExpect(content().json("""
                    {
                        "string_array_property": ["STRING2"]
                    }
                """))
    }

    @Test
    fun `it should match the number array out of order`() {
        mockMvc.perform(get("/full"))
                .andExpect(content().json("""
                    {
                        "number_array_property": [3, 2, 1]
                    }
                """))
    }

    @Test(expected = AssertionError::class)
    fun `it should fail to partially match the number array`() {
        mockMvc.perform(get("/full"))
                .andExpect(content().json("""
                    {
                        "number_array_property": [1, 2]
                    }
                """))
    }

}