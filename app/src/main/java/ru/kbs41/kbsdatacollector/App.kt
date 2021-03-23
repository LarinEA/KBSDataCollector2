package ru.kbs41.kbsdatacollector

import android.app.Application
import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import ru.kbs41.kbsdatacollector.room.AppDatabase
import ru.kbs41.kbsdatacollector.room.repository.AssemblyOrderRepository
import ru.kbs41.kbsdatacollector.room.repository.ProductRepository

class App(_context: Context? = null) : Application() {

    var context = _context

    val applicationScope = CoroutineScope(SupervisorJob())
    val database by lazy{ AppDatabase.getDatabase(getCurrentContext(), applicationScope) }
    val productRepository by lazy { ProductRepository(database.productDao()) }
    //val assemblyOrdersRepository by lazy { AssemblyOrderRepository(database.assemblyOrderDao()) }

    fun getCurrentContext() : Context {
        if (context != null) {
            return context as Context
        } else {
            return this
        }
    }

    override fun onCreate() {
        super.onCreate()
        AppNotificationManager.instance(applicationContext)
    }

}