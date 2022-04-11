package com.example.programame_project_api.services;

import com.example.programame_project_api.entities.persistEntities.AuthenticationRequest;
import com.example.programame_project_api.entities.persistEntities.Teacher;
import com.example.programame_project_api.entities.UserRole;
import com.example.programame_project_api.repositories.TeacherRepository;
import com.example.programame_project_api.repositories.UserRepository;
import com.example.programame_project_api.servicesTools.ServicesTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${users.firstadmin.user}")
    private String firstAdminUser;

    @Value("${users.firstadmin.password}")
    private String firstAdminPassword;


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

            int idUser = (int) user.get("id");
            if (servicesTools.isUserAdmin(token)) {
                if (teacherRepository.existsById(idUser)) {
                    if (!hasUserRoleAdmin(getEmailFromTeacher(idUser))) {
                        return updateUserAndTeacher(user);
                    } else {
                        return servicesTools.createResponseEntity(HttpStatus.FORBIDDEN,
                                "Users with the admin role cannot be updated");
                    }
                } else {
                    return servicesTools.createResponseEntity(HttpStatus.FORBIDDEN,
                            "User doesn´t exists");
                }
            } else {
                return servicesTools.createResponseEntity(HttpStatus.FORBIDDEN,
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

    public ResponseEntity isUserAdmin(String token) {

        try {

            boolean isUserAdmin = false;
            if (servicesTools.isUserAdmin(token)) {
                isUserAdmin = true;
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(isUserAdmin);
            } else {
                return ResponseEntity
                        .status(HttpStatus.OK)
                        .body(isUserAdmin);
            }
        } catch (Exception e) {
            return servicesTools.createResponseEntity(
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e.getMessage());
        }
    }

    public ResponseEntity getNameUserFromToken(String token) {

        try {

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(servicesTools.extractEmailFromToken(token));

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

    private boolean hasUserRoleAdmin(String userName) {

        AuthenticationRequest user = userRepository.findByUsername(userName);
        return user.getUserRole() == UserRole.ADMINISTRATOR;

    }


    public void createFirstUser() {
        // Si estamos en el primer arranque, no hay usuario administrador creado.
        // Creamos uno con los valores configurados en application.properties
        // (valores por defecto "test1234" / "1234")
        if (!existSomeUserAdmin(userRepository.findAll())) {
            if (!userRepository.existsByUsername(firstAdminUser)) { 
                AuthenticationRequest user = new AuthenticationRequest(firstAdminUser,
                        passwordEncoder.encode(firstAdminPassword),
                        UserRole.ADMINISTRATOR);
                userRepository.save(user);

            }
        }
    }

    private boolean existSomeUserAdmin(List<AuthenticationRequest> listUsers) {
        for (AuthenticationRequest user : listUsers) {
            if (user.getUserRole() == UserRole.ADMINISTRATOR)
                return true;
        }
        return false;


    }


    private ResponseEntity createUserAndTeacher(Map<String, Object> user) {

        AuthenticationRequest userData = new AuthenticationRequest(
                (String) user.get("email"),
                passwordEncoder.encode((String) user.get("password")),
                UserRole.COMMONUSER);

        Teacher teacher = new Teacher(
                (String) user.get("email"),
                (String) user.get("name"));
        userRepository.save(userData);
        teacherRepository.save(teacher);
        return new ResponseEntity(HttpStatus.CREATED);


    }

    private ResponseEntity updateUserAndTeacher(Map<String, Object> user) {

        if ((boolean) user.get("isPasswordChange")) {
            if (isPasswordDataOk(user)) {
                doUpdateOfDataUserWithPassword(user);
                return servicesTools.createResponseEntity(HttpStatus.OK,
                        "Update user ok");
            } else {
                return servicesTools.createResponseEntity(HttpStatus.UNPROCESSABLE_ENTITY,
                        "Passwords do not match");
            }
        } else {
            doUpdateOfDataUser(user);
            return servicesTools.createResponseEntity(HttpStatus.OK,
                    "Update user ok");
        }
    }


    private void doUpdateOfDataUserWithPassword(Map<String, Object> user) {

        Teacher teacher = teacherRepository.findById((int) user.get("id"));
        updatePassword((String) user.get("password"), teacher.getEmail());
        updateUsername((String) user.get("email"), teacher.getEmail());
        teacherRepository.update(user, teacher.getEmail());
    }

    private void doUpdateOfDataUser(Map<String, Object> user) {

        Teacher teacher = teacherRepository.findById((int) user.get("id"));
        updateUsername((String) user.get("email"), teacher.getEmail());
        teacherRepository.update(user, teacher.getEmail());
    }

    private boolean isPasswordDataOk(Map<String, Object> user) {

        if (user.get("password").equals(user.get("passwordRepeat"))) {
            return true;
        } else {
            return false;
        }


    }

    private String getEmailFromTeacher(int id) {

        Teacher teacher = teacherRepository.findById(id);
        if (teacher != null) {
            return teacher.getEmail();
        } else {
            return "";
        }
    }


    private void updateUsername(String username, String oldUsername) {

        AuthenticationRequest userData = userRepository.findByUsername(oldUsername);
        userData.setUsername(username);
        userRepository.save(userData);

    }

    private void updatePassword(String password, String oldUsername) {

        AuthenticationRequest userData = userRepository.findByUsername(oldUsername);
        userData.setPassword(passwordEncoder.encode(password));
        userRepository.save(userData);

    }

    private ResponseEntity createUserWithAdminRole(Map<String, Object> user) {

        AuthenticationRequest userData = new AuthenticationRequest(
                (String) user.get("email"),
                passwordEncoder.encode((String) user.get("password")),
                UserRole.COMMONUSER);
        userRepository.save(userData);
        return new ResponseEntity(HttpStatus.CREATED);

    }


}
