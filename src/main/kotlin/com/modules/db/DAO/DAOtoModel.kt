package com.modules.db.DAO

fun StudentDAOToModel(dao: StudentsDAO) = Task(
    name = dao.name,
    description = dao.description,
    priority = Priority.valueOf(dao.priority)
)
