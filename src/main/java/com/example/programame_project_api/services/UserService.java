package com.example.programame_project_api.services;

import com.example.programame_project_api.entities.AuthenticationRequest;
import com.example.programame_project_api.entities.Teacher;
import com.example.programame_project_api.entities.UserRole;
import com.example.programame_project_api.repositories.TeacherRepository;
import com.example.programame_project_api.repositories.UserRepository;
import com.example.programame_project_api.servicesTools.ServicesTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    private ServicesTools servicesTools;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TeacherRepository teacherRepository;

    @Transactional
    public ResponseEntity createUser(Map<String, Object> user, String token) {

        try {

            if (servicesTools.isUserAdmin(token)) {
                if (isUserDataOk(user)) {
                    if ((boolean) user.get("hasUserAdminRole")) {
                        return createUserWithAdminRole(user);
                    } else {
                        return createUserAndTeacher(user);
                    }
                } else {
                    return servicesTools.createResponseEntity(
                            HttpStatus.FORBIDDEN,
                            "Incorrect data or the user already exists");
                }
            } else {
                return servicesTools.createResponseEntity(
                        HttpStatus.FORBIDDEN,
                        "User doesn´t have permissions");
            }

        } catch (Exception e) {
            return servicesTools.createResponseEntity(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }
    }


    @Transactional
    public ResponseEntity updateUser(Map<String, Object> user, String token) {

        try {

            if (servicesTools.isUserAdmin(token)) {
                if (isUserDataOk(user)) {
                    if (!hasUserRoleAdmin((String) user.get("email"))) {
                        return updateUserAndTeacher(user);
                    } else {
                        return servicesTools.createResponseEntity(
                                HttpStatus.FORBIDDEN,
                                "Users with the admin role cannot be updated");
                    }
                } else {
                    return servicesTools.createResponseEntity(
                            HttpStatus.FORBIDDEN,
                            "Incorrect data");
                }
            } else {
                return servicesTools.createResponseEntity(
                        HttpStatus.FORBIDDEN,
                        "User doesn´t have permissions");
            }

        } catch (Exception e) {
            return servicesTools.createResponseEntity(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }
    }

    @Transactional
    public ResponseEntity deleteUser(String email, String token) {

        try {

            if (servicesTools.isUserAdmin(token)) {
                    if (!hasUserRoleAdmin(email)) {
                        userRepository.delete(userRepository.findByUsername(email));
                        teacherRepository.delete(teacherRepository.findByEmail(email));
                        return servicesTools.createResponseEntity(
                                HttpStatus.OK,
                                "Update user ok");
                    } else {
                        return servicesTools.createResponseEntity(
                                HttpStatus.FORBIDDEN,
                                "Users with the admin role cannot be delete");
                    }
            } else {
                return servicesTools.createResponseEntity(
                        HttpStatus.FORBIDDEN,
                        "User doesn´t have permissions");
            }

        } catch (Exception e) {
            return servicesTools.createResponseEntity(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }
    }





    private boolean isUserDataOk(Map<String, Object> user) {

        return user.get("password").equals(user.get("passwordRepeat"))
                && !userRepository.existsByUsername((String) user.get("email"));

    }

    private boolean hasUserRoleAdmin(String userName){

        AuthenticationRequest user = userRepository.findByUsername(userName);
        return user.getUserRole() == UserRole.ADMINISTRATOR;

    }

    private ResponseEntity createUserAndTeacher(Map<String, Object> user) {

        AuthenticationRequest userData = new AuthenticationRequest(
                (String) user.get("email"),
                (String) user.get("password"),
                UserRole.COMMONUSER);


        Teacher teacher = new Teacher(
                (String) user.get("email"),
                (String) user.get("name"));
        userRepository.save(userData);
        teacherRepository.save(teacher);
        return new ResponseEntity(HttpStatus.CREATED);


    }

    private  ResponseEntity updateUserAndTeacher(Map<String, Object> user){

        int idTeacher = (int) user.get("id");
        Teacher teacher = teacherRepository.findById(idTeacher);

        AuthenticationRequest userData = new AuthenticationRequest(
                (String) user.get("email"),
                (String) user.get("password"),
                UserRole.COMMONUSER);

        Teacher teacherData = new Teacher(
                (String) user.get("email"),
                (String) user.get("name"));

        userRepository.update(userData, teacher.getEmail());
        teacherRepository.update(teacherData,teacher.getEmail());

        return servicesTools.createResponseEntity(
                HttpStatus.OK,
                "Update user ok");



    }

    private ResponseEntity createUserWithAdminRole(Map<String, Object> user) {

        AuthenticationRequest userData = new AuthenticationRequest(
                (String) user.get("email"),
                (String) user.get("password"),
                UserRole.COMMONUSER);
        userRepository.save(userData);
        return new ResponseEntity(HttpStatus.CREATED);

    }


}
