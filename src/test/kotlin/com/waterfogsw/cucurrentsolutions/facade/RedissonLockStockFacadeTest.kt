package com.waterfogsw.cucurrentsolutions.facade

import com.waterfogsw.cucurrentsolutions.domain.Stock
import com.waterfogsw.cucurrentsolutions.repository.StockRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

@SpringBootTest
class RedissonLockStockFacadeTest(
  @Autowired private val redissonLockStockFacade: RedissonLockStockFacade,
  @Autowired private val stockRepository: StockRepository
) {
  @BeforeEach
  fun setUp() {
    val stock = Stock(productId = 1L, quantity = 100L)
    stockRepository.save(stock)
  }

  @AfterEach
  fun tearDown() {
    stockRepository.deleteAll()
  }

  @Test
  fun 동시에_100개_요청() {
    //given
    val threadCount = 100
    val executors = Executors.newFixedThreadPool(32)
    val latch = CountDownLatch(threadCount)

    //when
    for (i in 0 until threadCount) {
      executors.submit {
        try {
          redissonLockStockFacade.decrease(1L, 1L)
        } finally {
          latch.countDown()
        }
      }
    }

    //then
    latch.await()

    val persistStock = stockRepository
        .findById(1L)
        .get()
    Assertions
        .assertThat(persistStock.quantity)
        .isEqualTo(0)
  }
}
