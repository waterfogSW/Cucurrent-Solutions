package com.waterfogsw.cucurrentsolutions.service

import com.waterfogsw.cucurrentsolutions.repository.StockRepository
import org.springframework.stereotype.Service

@Service
class SynchronizedStockService(
  private val stockRepository: StockRepository
) {

  @Synchronized
  fun decrease(id: Long, quantity: Long) {
    val persistStock = stockRepository
        .findById(id)
        .orElseThrow { RuntimeException("Not found") }

    persistStock.decrease(quantity)
    stockRepository.saveAndFlush(persistStock)
  }

}
