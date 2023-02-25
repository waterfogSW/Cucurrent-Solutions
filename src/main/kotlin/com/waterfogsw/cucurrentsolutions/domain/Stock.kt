package com.waterfogsw.cucurrentsolutions.domain

import jakarta.persistence.*
import org.hibernate.Hibernate

@Entity
data class Stock(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,

  var productId: Long,

  var quantity: Long,
) {
  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (other == null || Hibernate.getClass(this) != Hibernate.getClass(
        other
      )
    ) return false
    other as Stock

    return id != null && id == other.id
  }

  override fun hashCode(): Int = javaClass.hashCode()

  @Override
  override fun toString(): String {
    return this::class.simpleName + "(id = $id )"
  }
}
