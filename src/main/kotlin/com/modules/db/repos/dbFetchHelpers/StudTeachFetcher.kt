package com.modules.db.repos.dbFetchHelpers

import com.modules.db.reposInterfaces.SchoolUsersInterface
import com.modules.db.suspendTransaction
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.id.IntIdTable


// !!! Wanted to make this more generic but since we need TABLE
// and every table has different columns, we can't make it generic
// or at least I don't know how to do it yet !!!
//class StudTeachFetcher<ModelT, DaoT : IntEntity> (
//                private val compObjDAO: IntEntityClass<DaoT>,
//                private val daoToModel: (DaoT) -> ModelT,
//                private val table: IntIdTable) : SchoolUsersInterface<ModelT>
//{
//    override suspend fun getAll(): List<ModelT> = suspendTransaction {
//        compObjDAO.all().map(daoToModel)
//    }
//
//    override suspend fun getByIndex(index: String) {
////        compObjDAO
////            .find {table eq index}
//        TODO("Not yet implemented")
//    }
//
//    override suspend fun removeByIndex(index: String) {
//        throw UnsupportedOperationException("Not yet implemented")
//    }
//
//    override suspend fun addRow(newRow: ModelT) {
//        throw UnsupportedOperationException("Not yet implemented")
//    }
//
//    override suspend fun getByClassNbr(clsNbr: String) {
//        throw UnsupportedOperationException("Not yet implemented")
//    }
//}