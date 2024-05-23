package ui.pages;


import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ui.db.DBConnection;
import ui.my_project_steps.MyUISteps;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public abstract class TestBase {
//    public static AuthPage authPage;
//    public static RegesrtyPage registryPage;
//    public static MainPage mainPage;
//    public static NotePage notePage;
    public static DBConnection dbConnection;

    public static ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>();
    private static ThreadLocal<AuthPage> authPage = new ThreadLocal<>();
    private static ThreadLocal<MainPage> mainPage = new ThreadLocal<>();
    private static ThreadLocal<NotePage> notePage = new ThreadLocal<>();
    private static ThreadLocal<RegisrtyPage> registryPage = new ThreadLocal<>();
    private static ThreadLocal<MyUISteps> mySteps = new ThreadLocal<>();




    @BeforeAll
    public static void setup() {
        ChromeOptions options = new ChromeOptions();
//        options.addArguments("--remote-allow-origins=*");
//        options.addArguments("--headless");

        WebDriverManager.chromedriver().driverVersion("124").setup();
        ChromeDriver chromeDriver = new ChromeDriver(options);
        chromeDriver.manage().window().setSize(new Dimension(1920, 1080));
        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(240));
        chromeDriver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

        driver.set(chromeDriver);

        authPage.set(new AuthPage(getDriver()));
        registryPage.set(new RegisrtyPage(getDriver()));
        mainPage.set(new MainPage(getDriver()));
        notePage.set(new NotePage(getDriver()));
        mainPage.set(new MainPage(getDriver()));
        mySteps.set(new MyUISteps(getDriver()));

        dbConnection = new DBConnection();
    }

    public static WebDriver getDriver()
    {
        return driver.get();
    }


    @AfterAll
    static void tearDown() {
        if (driver != null) {
            authPage.remove();
            mainPage.remove();
            notePage.remove();
            registryPage.remove();
            mySteps.remove();
            getDriver().quit();
            driver.remove();
        }
        if (dbConnection != null) {
            dbConnection.closeConnection();
        }
    }


    public static AuthPage getAuthPage() {
        return authPage.get();
    }

    public static MainPage getMainPage() {
        return mainPage.get();
    }

    public static NotePage getNotePage() {
        return notePage.get();
    }

    public static RegisrtyPage getRegistryPage() {
        return registryPage.get();
    }

    public static MyUISteps getMySteps() {
        return mySteps.get();
    }


    @Attachment(value = "Attachment Screenshot", type = "image/png")
    public byte[] makeScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

}

