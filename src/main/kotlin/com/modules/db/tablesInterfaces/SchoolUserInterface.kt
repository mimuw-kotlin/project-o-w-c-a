package com.modules.db.tablesInterfaces

import com.modules.db.suspendTransaction
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable


object TaskTable : IntIdTable("task") {
    val name = varchar("name", 50)
    val description = varchar("description", 50)
    val priority = varchar("priority", 50)
}

abstract class TaskDAO(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<TaskDAO>(TaskTable)
    var name by TaskTable.name
    var description by TaskTable.description
    var priority by TaskTable.priority
}



interface SchoolUserInterface <DAOClass : TaskDAO, Class> {
    suspend fun getAll(): List<Class> = suspendTransaction {
        // .all() - This calls the all method on the TaskDAO class,
        //      which is a method provided by the IntEntityClass in the Exposed library.
        //      The all method retrieves all rows from the TaskTable and returns
        //      them as a collection of TaskDAO objects.
        //      We can call .all() since its from companion object so its like static func

        // .map() - map is a standard func that transforms each elem in collection
        //      using given transformation func

        // ::daoToModel - is a reference to the daoToModel function,
        //      function reference allows you to pass a function as
        //      a parameter without calling it immediately
        DAOClass.all().map(::daoToModel)
    }
    suspend fun getByIndex(index: String)
    suspend fun getByName(name: String)
    suspend fun getByType(userType: String)
    suspend fun removeByIndex(index: String)
    suspend fun <T> addUser(user: T)
}

abstract class SchoolUser <DAOClass> (val tableObj): SchoolUserInterface<DAOClass> {

}