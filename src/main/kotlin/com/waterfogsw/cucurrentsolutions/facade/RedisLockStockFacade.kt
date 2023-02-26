package com.waterfogsw.cucurrentsolutions.facade

import com.waterfogsw.cucurrentsolutions.repository.RedisLockRepository
import com.waterfogsw.cucurrentsolutions.service.StockService
import org.springframework.stereotype.Service

@Service
class RedisLockStockFacade(
  private val redisLockRepository: RedisLockRepository,
  private val stockService: StockService,
) {

  fun decrease(id: Long, quantity: Long) {
    while (!redisLockRepository.lock(id)) {
      Thread.sleep(100)
    }

    try {
      stockService.decrease(id, quantity)
    } finally {
      redisLockRepository.unlock(id)
    }
  }
}
