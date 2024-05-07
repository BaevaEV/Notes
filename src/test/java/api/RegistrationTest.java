package api;

import api.dto.UserDTO;
import api.pojo.User;
import api.restSpecification.RegSpec;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;

import static ui.my_project_steps.MyApiSteps.postMethod;


@DisplayName(value = "Проверка регистрации")
public class RegistrationTest {
    private User newUser;
    private UserDTO userDTO;


    @Before
    public void before() {
        newUser = new User();
        newUser = newUser.generateUser();

    }

    @Test
    @Tag("Api")
    @DisplayName(value = "Проверка регистрации только с обязательными полями")
    public void registryWithRequiredFieldsFieldsTest() {
        userDTO = UserDTO.builder()
                .login(newUser.getLogin())
                .password(newUser.getPassword())
                .build();
        RegSpec.createRequestSpecReg(userDTO);
        RegSpec.createResponseSpecReg(201);
        postMethod(RegSpec.requestSpecification, RegSpec.responseSpecification);
    }


    @Test
    @Tag("Api")
    @DisplayName(value = "Проверка регистрации со всеми полями")
    public void registryWithAllFieldsTest() {
        userDTO = UserDTO.builder()
                .login(newUser.getLogin())
                .password(newUser.getPassword())
                .email((newUser.getEmail()))
                .roles(newUser.getRoles())
                .build();
        RegSpec.createRequestSpecReg(userDTO);
        postMethod(RegSpec.requestSpecification, RegSpec.responseSpecification);
        RegSpec.createResponseSpecReg(201);
    }


    @Test
    @Tag("Api")
    @DisplayName(value = "Проверка регистрации только с логином")
    public void registryOnlyWithLoginTest() {
        userDTO = UserDTO.builder()
                .login(newUser.getLogin())
                .build();
        RegSpec.createRequestSpecReg(userDTO);
        RegSpec.createResponseSpecReg(500);
        postMethod(RegSpec.requestSpecification, RegSpec.responseSpecification);
    }

    @Test
    @Tag("Api")
    @DisplayName("Создание пользователя и заметки")
    public void createNewUserAndNote() {
        userDTO = UserDTO.builder().login(newUser.getLogin())
                .password(newUser.getPassword())
                .email((newUser.getEmail()))
                .roles(newUser.getRoles())
                .notes(newUser.getNotes())
                .build();

        RegSpec.createRequestSpecReg(userDTO);
        RegSpec.createResponseSpecReg(201);
        postMethod(RegSpec.requestSpecification, RegSpec.responseSpecification);
        RegSpec.createJsonSchemaValidationSpec("schemes/reg_schema.json");

    }
}
