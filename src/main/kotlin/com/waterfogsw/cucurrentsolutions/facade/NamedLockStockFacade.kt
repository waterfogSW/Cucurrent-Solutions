package com.waterfogsw.cucurrentsolutions.facade

import com.waterfogsw.cucurrentsolutions.domain.LockRepository
import com.waterfogsw.cucurrentsolutions.service.StockService
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class NamedLockStockFacade(
  private val lockRepository: LockRepository,
  private val stockService: StockService,
) {

  @Transactional
  fun decrease(id: Long, quantity: Long) {
    try {
      lockRepository.getLock(id.toString())
      stockService.decrease(id, quantity)
    } finally {
      lockRepository.releaseLock(id.toString())
    }

  }

}
