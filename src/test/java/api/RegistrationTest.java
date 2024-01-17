package api;

import api.dto.User;
import api.dto.UserCreationDTO;
import api.restSpecification.RegSpec;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;



@DisplayName(value = "Проверка регистрации")
public class RegistrationTest {
    private User newUser;
    private UserCreationDTO userCreationDTO;
    RegSpec regSpec = new RegSpec();


    @Before
    public void before() {
        newUser = new User();
        newUser = newUser.generateUser();

    }

    @Test
    @DisplayName(value = "Проверка регистрации только с обязательными полями")
    public void registryWithRequiredFieldsFieldsTest() {
        userCreationDTO = UserCreationDTO.builder().login(newUser.getLogin())
                .password(newUser.getPassword())
                .build();
        regSpec.createRequestSpecReg(userCreationDTO);
        regSpec.createResponseSpecReg(201);
        regSpec.postRegistration();
    }


    @Test
    @DisplayName(value = "Проверка регистрации со всеми полями")
    public void registryWithAllFieldsFieldsTest() {
        userCreationDTO = UserCreationDTO.builder().login(newUser.getLogin())
                .password(newUser.getPassword())
                .email((newUser.getEmail()))
                .roles(newUser.getRoles())
                .build();
        regSpec.createRequestSpecReg(userCreationDTO);
        regSpec.createResponseSpecReg(201);
        regSpec.postRegistration();
    }


    @Test
    @DisplayName(value = "Проверка регистрации только с логином")
    public void registryOnlyWithLoginTest() {
        userCreationDTO = UserCreationDTO.builder().login(newUser.getLogin())
                .build();
        regSpec.createRequestSpecReg(userCreationDTO);
        regSpec.createResponseSpecReg(500);
        regSpec.postRegistration();
    }

    @Test
    @DisplayName("Создание пользователя и заметки")
    public void createNewUserAndNote() {
        userCreationDTO = UserCreationDTO.builder().login(newUser.getLogin())
                .password(newUser.getPassword())
                .email((newUser.getEmail()))
                .roles(newUser.getRoles())
                .notes(newUser.getNotes())
                .build();

        regSpec.createRequestSpecReg(userCreationDTO);
        regSpec.createResponseSpecReg(201);
        regSpec.createJsonSchemaValidationSpec("schemes/reg_schema.json");
        regSpec.postRegistration();

    }
}
