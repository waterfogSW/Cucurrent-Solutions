package com.waterfogsw.cucurrentsolutions.facade

import com.waterfogsw.cucurrentsolutions.service.OptimisticLockStockService
import org.springframework.stereotype.Service

@Service
class OptimisticLockStockFacade(
  private val optimisticLockStockService: OptimisticLockStockService
) {

  fun decrease(id: Long, quantity: Long) {
    runWhileException {
      optimisticLockStockService.decrease(id, quantity)
    }
  }

  private fun runWhileException(target: Runnable) {
    while (true) {
      try {
        target.run()
        break
      } catch (e: Exception) {
        Thread.sleep(50)
      }
    }
  }
}
