package ru.kbs41.kbsdatacollector.room.db

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

class AssemblyOrderTableGoodsWithQtyCollectedAndProducts(
        val row: Int?,
        val orderID: Long?,
        val productName: String?,
        val productId: Long?,
        val qty: Double?,
        val qtyCollected: Double?

) {
}