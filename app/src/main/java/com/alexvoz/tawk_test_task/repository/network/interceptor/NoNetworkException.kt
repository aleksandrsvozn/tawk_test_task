package com.alexvoz.tawk_test_task.repository.network.interceptor

import java.io.IOException

/**
 *  No Network Exception class
 *
 *  @param message String (optional)
 */
class NoNetworkException(message: String? = null) : IOException(message)