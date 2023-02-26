package com.waterfogsw.cucurrentsolutions.domain

import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.*

interface StockRepository : JpaRepository<Stock, Long> {

  @Lock(LockModeType.PESSIMISTIC_WRITE)
  @Query("select s from Stock s where s.id = :id")
  fun findByIdWithPessimisticLock(id: Long): Stock

  @Lock(LockModeType.OPTIMISTIC)
  @Query("select s from Stock s where s.id = :id")
  fun findByIdWithOptimisticLock(id: Long): Stock

  @Query(value = "select get_lock(:key, 3000)")
  fun getLock(key: String)

  @Query(value = "select release_lock(:key)")
  fun releaseLock(key: String)

}
