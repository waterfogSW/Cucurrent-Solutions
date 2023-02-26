package com.waterfogsw.cucurrentsolutions.facade

import com.waterfogsw.cucurrentsolutions.service.StockService
import org.redisson.api.RedissonClient
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class RedissonLockStockFacade(
  private val redissonClient: RedissonClient,
  private val stockService: StockService
) {

  private val log = LoggerFactory.getLogger("RedissonLockStockFacade")

  fun decrease(id: Long, quantity: Long) {
    val lock = redissonClient.getLock(generateKey(id))

    try {
      val result = lock.tryLock(5, 1, TimeUnit.SECONDS)
      if (!result) {
        log.info("Lock 획득 실패")
        return
      }

      stockService.decrease(id, quantity)
    } finally {
      lock.unlock()
    }
  }

  private fun generateKey(id: Long): String = id.toString()

}
