package com.waterfogsw.cucurrentsolutions.service

import com.waterfogsw.cucurrentsolutions.domain.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class PessimisticLockStockService(
  private val stockRepository: StockRepository
) {

  @Transactional
  fun decrease(id: Long, quantity: Long) {
    val persistStock = stockRepository.findByIdWithPessimisticLock(id)
    persistStock.decrease(quantity)
  }

}
