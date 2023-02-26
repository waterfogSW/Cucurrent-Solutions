package com.waterfogsw.cucurrentsolutions.service

import com.waterfogsw.cucurrentsolutions.repository.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OptimisticLockStockService(
  private val stockRepository: StockRepository
) : StockDecreaseCommand {

  @Transactional
  override fun decrease(id: Long, quantity: Long) {
    val persistStock = stockRepository.findByIdWithOptimisticLock(id)
    persistStock.decrease(quantity)
  }

}
