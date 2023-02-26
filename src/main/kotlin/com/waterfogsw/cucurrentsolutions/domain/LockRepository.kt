package com.waterfogsw.cucurrentsolutions.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface LockRepository : JpaRepository<Stock, String> {

  @Query(value = "select GET_LOCK(:key, 3000)", nativeQuery = true)
  fun getLock(key: String)

  @Query(value = "select RELEASE_LOCK(:key)", nativeQuery = true)
  fun releaseLock(key: String)

}
