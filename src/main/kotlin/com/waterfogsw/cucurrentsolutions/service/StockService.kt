package com.waterfogsw.cucurrentsolutions.service

import com.waterfogsw.cucurrentsolutions.domain.StockRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class StockService(
  private val stockRepository: StockRepository
) {

  @Transactional
  fun decrease(id: Long, quantity: Long) {
    val persistStock = stockRepository
        .findById(id)
        .orElseThrow { RuntimeException("Not found") }

    persistStock.decrease(quantity)
  }

}
