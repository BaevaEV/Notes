package api;

import api.dto.NoteDTO;
import api.pojo.Note;
import api.restSpecification.NoteSpec;
import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.*;
import ui.my_project_steps.MyApiSteps;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static ui.my_project_steps.MyApiSteps.getMethod;

@DisplayName("Запрос заметок с заглушкой")
public class NoteTestMock {
    private Note newNote;
    private NoteDTO noteDTO;
    private static WireMockServer wireMockServer;


    @BeforeEach
    public void before() {
        newNote = new Note();
        newNote = newNote.generateNote();
        wireMockServer = new WireMockServer();
        wireMockServer.start();
        configureFor("localhost", wireMockServer.port());

    }

    @Test
    @Tag("Api")
    @DisplayName("Все заметки пользователя")
    public void getUsersNotesByLoginTest() {
            // Настройка заглушки для запроса заметок пользователя
            stubFor(get(urlEqualTo("/api/notes"))
                    .willReturn(aResponse()
                            .withStatus(200)
                            .withHeader("Content-Type", "application/json")
                            .withBody("[{\"id\":1,\"name\":\"Note 1\",\"content\":\"Content 1\"},{\"id\":2,\"name\":\"Note 2\",\"content\":\"Content 2\"}]")));
        String name = "Заметка";
        int count = 1000;
        String login = "Katerina";
        String password = "1234";
        String accessToken = MyApiSteps.getAccessToken(login, password);
        NoteSpec.createRequestSpecGetNote(login, accessToken, name, count);
        NoteSpec.createResponseSpecNote(200);
        getMethod(NoteSpec.requestSpecification, NoteSpec.responseSpecification);

    }

    @AfterEach
    public void afterTest() {
        wireMockServer.stop();
    }

}