package utills;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import tests.TestBase;

import java.time.Duration;
import java.time.chrono.ChronoLocalDate;

import static com.codeborne.selenide.WebDriverRunner.driver;
import static java.lang.Thread.sleep;


public class WaitForLoadCompleted extends TestBase {

    private WebDriverWait driverWait() {
        return new WebDriverWait((WebDriver) driver(), Duration.ofMinutes(2));
    }

    public void waitForLoadCompleted() {
        driverWait()
                .until(driver -> (Boolean) ((JavascriptExecutor) driver)
                        .executeScript("return (!window.jQuery || (!window.jQuery.active && document.readyState==='complete')) " +
                                "&& !document.getElementsByTagName('html')[0].className.includes('siebui-busy')" +
                                "&& document.getElementsByClassName('ant-table at-table-empty').length == 0 " +
                                "&& document.getElementsByClassName('ant-spin-container ant-spin-blur').length == 0" +
                                "&& document.getElementsByClassName('body__blocked').length == 0" +
                                "&& document.getElementsByClassName('f-page-preloader').length == 0" +
                                "&& document.getElementsByClassName('ui-progress-spinner app-lock__spinner').length == 0" +
                                "&& document.getElementsByClassName('ant-notification-notice ant-notification-notice-closable').length == 0" +
                                "&& (document.getElementsByClassName('fourRingSpinner').length == 0 ? true : document.getElementsByClassName('fourRingSpinner')[0].hidden == true)"));
    }


}
