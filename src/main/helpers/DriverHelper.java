package helpers;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.openqa.selenium.logging.LogType.BROWSER;

public class DriverHelper {

    public static void configureDriver() {
        //Основные настройки
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        Configuration.browser = "chrome";
//        Configuration.headless = true;
        Configuration.timeout = 240000;
        Configuration.browserSize ="1920x1000";
    }

    public static String getConsoleLogs() {
        return String.join("\n", Selenide.getWebDriverLogs(BROWSER));
    }

}
