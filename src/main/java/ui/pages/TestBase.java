package ui.pages;


import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Attachment;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import ui.my_project_steps.MySteps;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class TestBase {
    public static WebDriver driver;
    public static AuthPage authPage;
    public static RegesrtyPage registryPage;
    public static MainPage mainPage;
    public static NotePage notePage;
    public static ManagePage managePage;


    @BeforeAll
    public static void setup() {
        WebDriverManager.chromedriver().driverVersion("120").setup();
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(240));
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        authPage = new AuthPage(driver);
        registryPage = new RegesrtyPage(driver);
        mainPage = new MainPage(driver);
        notePage = new NotePage(driver);
        managePage = new ManagePage();
    }

    @AfterAll
    static void tearDown() {
        driver.quit();
    }

    @Attachment(value = "Attachment Screenshot", type = "image/png")
    public byte[] makeScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

}

