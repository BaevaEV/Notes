package ui;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import static helpers.DriverHelper.configureDriver;



public class TestBase {
    public static WebDriver driver;



    protected static String FILE_PATH = System.getProperty("user.dir")+"/src/test/resources/testdata/documents/pixels/";




    @BeforeAll
    public static void setup() {


    }


//    @BeforeMethod
//    public void beforeTest(){
//        addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
//    }

    @AfterAll
    public static void tearDown() {
        driver.quit();
    }

}
