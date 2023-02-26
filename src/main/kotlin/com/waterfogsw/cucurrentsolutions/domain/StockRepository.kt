package com.waterfogsw.cucurrentsolutions.domain

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.*

interface StockRepository : JpaRepository<Stock, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select s from Stock s where s.id = :id")
  fun findByIdWithPessimisticLock(id: Long): Stock

}
