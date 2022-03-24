package com.example.programame_project_api.repositories;

import com.example.programame_project_api.entities.AuthenticationRequest;
import com.example.programame_project_api.entities.Teacher;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeacherRepository extends CrudRepository<Teacher,Long> {

    Teacher findByEmail(String email);
    Teacher findById(long id);
    List<Teacher> findAll();

    public default void update(Teacher teacher, String oldEmail){

        Teacher teacherData = findByEmail(oldEmail);
        teacherData.setEmail(teacher.getEmail());
        teacherData.setName(teacher.getName());
        save(teacherData);

    }
}
