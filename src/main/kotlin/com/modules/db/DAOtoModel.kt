package com.modules.db

import com.modules.db.DAO.*
import com.modules.db.dataModels.*

fun adminDAOToModel(dao: AdminDAO) = AdminModel(
    username = dao.username,
    password = dao.password,
    user_type = dao.user_type
)

fun studentDAOToModel(dao: StudentsDAO) = StudentModel(
    index = dao.index,
    username = dao.username,
    user_type = dao.user_type,
    class_nbr = dao.class_nbr
)

fun teacherDAOToModel(dao: TeachersDAO) = TeacherModel(
    index = dao.index,
    username = dao.username,
    user_type = dao.user_type,
    class_nbr = dao.class_nbr
)

fun classDAOToModel(dao: ClassesDAO) = ClassModel(
    class_nbr = dao.class_nbr,
    class_teacher_name = dao.class_teacher_name
)

fun subjectDAOToModel(dao: SubjectsDAO) = SubjectModel(
    subject_code = dao.subject_index,
    subject_name = dao.subject_name,
    description = dao.description
)

fun passwordDAOToModel(dao: PasswordsDAO) = PasswordModel(
    username = dao.username,
    password = dao.password
)