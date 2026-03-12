package com.kd.skills.agent.llm

import com.fasterxml.jackson.databind.ObjectMapper
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.time.Duration

class OpenAiCompatibleClient(
    private val baseUrl: String,
    private val apiKey: String,
    private val model: String,
    private val timeout: Duration,
) {
    private val mapper = ObjectMapper()

    fun chat(systemPrompt: String, userPrompt: String): String {
        val normalizedBaseUrl = baseUrl.trim().removeSuffix("/")
        val uri = URI.create("$normalizedBaseUrl/chat/completions")

        val bodyNode = mapper.createObjectNode()
        bodyNode.put("model", model)
        bodyNode.put("temperature", 0.2)
        val messagesNode = bodyNode.putArray("messages")
        messagesNode.addObject().put("role", "system").put("content", systemPrompt)
        messagesNode.addObject().put("role", "user").put("content", userPrompt)

        val requestBody = mapper.writeValueAsString(bodyNode)

        val request = HttpRequest.newBuilder()
            .uri(uri)
            .timeout(timeout)
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer $apiKey")
            .POST(HttpRequest.BodyPublishers.ofString(requestBody))
            .build()

        val response = httpClient(timeout).send(request, HttpResponse.BodyHandlers.ofString())
        if (response.statusCode() !in 200..299) {
            throw RuntimeException("LLM request failed: HTTP ${response.statusCode()} - ${response.body()}")
        }

        val root = mapper.readTree(response.body())
        return root["choices"]?.get(0)?.get("message")?.get("content")?.asText()
            ?: throw RuntimeException("LLM response missing choices[0].message.content")
    }

    private fun httpClient(timeout: Duration): HttpClient {
        return HttpClient.newBuilder()
            .connectTimeout(timeout)
            .build()
    }
}

