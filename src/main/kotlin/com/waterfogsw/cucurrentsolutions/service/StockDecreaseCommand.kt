package com.waterfogsw.cucurrentsolutions.service

interface StockDecreaseCommand {

  fun decrease(id: Long, quantity: Long)

}
