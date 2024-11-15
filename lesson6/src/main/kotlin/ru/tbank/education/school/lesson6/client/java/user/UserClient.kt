package ru.tbank.education.school.lesson6.client.java.user

import ru.tbank.education.school.lesson6.client.dto.ApiResponse
import ru.tbank.education.school.lesson6.client.dto.User
import ru.tbank.education.school.lesson6.client.lessonObjectMapper
import java.net.URI
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import kotlin.random.Random

class UserClient(private val url: String) {
    private val javaHttpClient = HttpClient.newBuilder().build()

    fun addUser(user: User): ApiResponse? {
        val body = lessonObjectMapper.writeValueAsString(user)
        val request = HttpRequest.newBuilder()
            .uri(URI.create("$url/v2/user"))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build()

        val response = javaHttpClient.send(request, HttpResponse.BodyHandlers.ofString())

        return if (response.statusCode() == 200 || response.statusCode() == 201) {
            lessonObjectMapper.readValue(response.body(), ApiResponse::class.java)
        } else {
            null
        }
    }

    fun deleteUser(username: String): ApiResponse? {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("$url/v2/user/$username"))
            .header("Content-Type", "application/json")
            .DELETE()
            .build()

        val response = javaHttpClient.send(request, HttpResponse.BodyHandlers.ofString())

        return if (response.statusCode() == 200 || response.statusCode() == 201) {
            lessonObjectMapper.readValue(response.body(), ApiResponse::class.java)
        } else {
            null
        }
    }

    fun updateUser(username: String, user: User): ApiResponse? {
        val body = lessonObjectMapper.writeValueAsString(user)
        val request = HttpRequest.newBuilder()
            .uri(URI.create("$url/v2/user/$username"))
            .header("Content-Type", "application/json")
            .PUT(HttpRequest.BodyPublishers.ofString(body))
            .build()

        val response = javaHttpClient.send(request, HttpResponse.BodyHandlers.ofString())

        return if (response.statusCode() == 200 || response.statusCode() == 201) {
            lessonObjectMapper.readValue(response.body(), ApiResponse::class.java)
        } else {
            null
        }
    }

    fun getUser(username : String): User? {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("$url/v2/user/$username"))
            .header("Content-Type", "application/json")
            .GET()
            .build()

        val response = javaHttpClient.send(request, HttpResponse.BodyHandlers.ofString())

        return if (response.statusCode() == 200 || response.statusCode() == 201) {
            lessonObjectMapper.readValue(response.body(), User::class.java)
        } else {
            null
        }
    }
}

fun main() {
    val id = Random.nextLong() * 1000
    val newUser =
        User(
            id = Random.nextLong() * 1000,
            username = "authentic",
            firstName = "Maria",
            lastName = "Nepomyaschaya",
            email = "mmm@gmail.com",
            password = "1337",
            phone = "89116778808",
            userStatus = 1
        )
    val UserClient = UserClient("https://Userstore.swagger.io")

    UserClient.addUser(newUser)
    println(UserClient.updateUser(newUser.username, newUser.copy(username = "???")))
    println(UserClient.getUser(newUser.username))
    println(UserClient.deleteUser(newUser.username))
}