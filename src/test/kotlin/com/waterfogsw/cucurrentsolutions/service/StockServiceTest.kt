package com.waterfogsw.cucurrentsolutions.service

import com.waterfogsw.cucurrentsolutions.domain.Stock
import com.waterfogsw.cucurrentsolutions.repository.StockRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors.newFixedThreadPool

@SpringBootTest
class StockServiceTest(
  @Autowired private val stockService: StockService,
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
  fun 재고_테스트() {
    //given
    val id = 1L
    stockService.decrease(1L, 1L)

    //when
    val persistStock = stockRepository
        .findById(id)
        .get()

    //then
    assertThat(persistStock.quantity).isEqualTo(99)
  }

  @Test
  fun 동시에_100개_요청() {
    //given
    val threadCount = 100
    val executors = newFixedThreadPool(32)
    val latch = CountDownLatch(threadCount)

    //when
    for (i in 0 until threadCount) {
      executors.submit {
        try {
          stockService.decrease(1L, 1L)
        } finally {
          // stockService.decrease()의 예외 발생 여부를 떠나 무조건 실행한다
          latch.countDown()
        }
      }
    }

    //then
    // latch의 카운트가 0이 될때까지 기다린다
    latch.await()

    val persistStock = stockRepository
        .findById(1L)
        .get()
    assertThat(persistStock.quantity).isEqualTo(0)
  }

}
