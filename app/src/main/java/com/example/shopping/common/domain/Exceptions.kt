package com.example.shopping.common.domain

import java.io.IOException

class NetworkUnavailableException(message: String = "No network available :(") : IOException(message)