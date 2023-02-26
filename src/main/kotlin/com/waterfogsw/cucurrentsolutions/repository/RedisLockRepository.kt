package com.waterfogsw.cucurrentsolutions.repository

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.time.Duration

@Repository
class RedisLockRepository(
  private val redisTemplate: RedisTemplate<String, String>
) {

  fun lock(id: Long): Boolean {
    return redisTemplate
        .opsForValue()
        .setIfAbsent(generateKey(id), "lock", Duration.ofMillis(3_000L))!!
  }

  fun unlock(id: Long): Boolean {
    return redisTemplate.delete(generateKey(id))
  }

  private fun generateKey(id: Long): String {
    return id.toString()
  }

}
