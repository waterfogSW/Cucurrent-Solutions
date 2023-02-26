package com.waterfogsw.cucurrentsolutions.service

import com.waterfogsw.cucurrentsolutions.repository.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class NamedLockStockService(
  private val stockRepository: StockRepository
) {

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  fun decrease(id: Long, quantity: Long) {
    val persistStock = stockRepository
        .findById(id)
        .orElseThrow { RuntimeException("Not found") }

    persistStock.decrease(quantity)
    stockRepository.saveAndFlush(persistStock)
  }

}
