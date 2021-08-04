package com.example.shopping.common.utils

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter

object DateTimeUtils {
  fun parse(dateTimeString: String): LocalDateTime = try {
      // todo
      LocalDateTime.parse("2021-08-04T14:39:51+0000")
    } catch (e: Exception) {
      val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ssZ")
      LocalDateTime.parse("2021-08-04T14:39:51+0000", dateFormatter)
    }
}