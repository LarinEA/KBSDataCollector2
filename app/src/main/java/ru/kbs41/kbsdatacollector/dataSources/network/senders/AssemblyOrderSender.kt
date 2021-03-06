package ru.kbs41.kbsdatacollector.dataSources.network.senders

import ru.kbs41.kbsdatacollector.App
import ru.kbs41.kbsdatacollector.dataSources.dataBase.assemblyOrder.AssemblyOrder
import ru.kbs41.kbsdatacollector.dataSources.network.retrofit.models.DataOutgoing

object AssemblyOrderSender {

    val database = App().database

    private val productDao = database.productDao()
    private val assemblyOrderDao = database.assemblyOrderDao()
    private val assemblyOrderTableGoodsDao = database.assemblyOrderTableGoodsDao()
    private val assemblyOrderTableStampsDao = database.assemblyOrderTableStampsDao()

    suspend fun makeOrderIsSent(assemblyOrder: AssemblyOrder){
        assemblyOrder.isSent = true
        assemblyOrderDao.update(assemblyOrder)
    }

    suspend fun getAllAssemblyOrdersForSending(): List<AssemblyOrder> {

        return assemblyOrderDao.getAssemblyOrderCompleteNotSent()
    }

    suspend fun getAssemblyOrderTableGoods(assemblyOrder: AssemblyOrder): List<DataOutgoing.OrderModel.TableGoodsModel> {

        val tableGoods = assemblyOrderTableGoodsDao.getTableGoodsByDocId(assemblyOrder.id)

        val tableGoodsOutModel: MutableList<DataOutgoing.OrderModel.TableGoodsModel> = arrayListOf()

        tableGoods.forEach {
            val product = productDao.getProductById(it.productId)
            val newItemTableGoods = DataOutgoing.OrderModel.TableGoodsModel(
                product!!.name,
                product!!.guid!!,
                it.qty,
                it.qtyCollected
            )
            tableGoodsOutModel.add(newItemTableGoods)
        }

        return tableGoodsOutModel.toList()
    }

    suspend fun getAssemblyOrderTableStamps(assemblyOrder: AssemblyOrder): List<DataOutgoing.OrderModel.TableStampsModel> {

        val tableStamps = assemblyOrderTableStampsDao.getTableStampsByDocId(assemblyOrder.id)

        val tableStampsOutModel: MutableList<DataOutgoing.OrderModel.TableStampsModel> = arrayListOf()

        tableStamps.forEach {
            val newItemTableGoods = DataOutgoing.OrderModel.TableStampsModel(
                productDao.getProductById(it.productId).guid!!,
                it.barcode
            )
            tableStampsOutModel.add(newItemTableGoods)
        }

        return tableStampsOutModel.toList()
    }

}