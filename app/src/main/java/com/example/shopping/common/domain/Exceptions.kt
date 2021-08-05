package com.example.shopping.common.domain

import java.io.IOException

class NetworkException(message: String): Exception(message)
class NoMoreClientsException(message: String): Exception(message)
class CreateUserException(message: String): Exception(message)
class NoMoreTodosException(message: String): Exception(message)
class NetworkUnavailableException(message: String = "No network available :(") : IOException(message)