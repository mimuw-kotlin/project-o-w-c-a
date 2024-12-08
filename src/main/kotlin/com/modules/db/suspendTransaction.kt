package com.modules.db

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
/**
 * Suspends the current coroutine until the transaction is completed.
 * Function is generic, meaning it can return any type T.
 *
 * --> **block** parameter is a lambda with a receiver of
 * type Transaction that returns a value of type T
 * ====== Transaction.(): This part specifies that the lambda has a receiver of type Transaction.
 * ====== Inside the lambda, you can call methods and access properties of Transaction directly.
 * ====== the receiver is an instance of the Transaction class
 *
 * --> **newSuspendTransaction** starts a new database transaction
 * in a coroutine context, it executes the block lambda
 *
 * --> **Dispatchers.IO**  is a coroutine dispatcher in Kotlin that is optimized for
 * offloading blocking IO tasks to a shared pool of threads.
 * It is designed to handle IO-bound operations such as file I/O, network requests
 *
 * @param block the transaction block to execute
 * @return the result of the transaction block
 */
suspend fun <T> suspendTransaction(block: Transaction.() -> T): T =
    newSuspendedTransaction(Dispatchers.IO, statement = block)
