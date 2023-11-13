package tests;

import MyProjectSteps.StepsCCX;
import MyProjectSteps.AuthLEProductBlock;
import MyProjectSteps.AuthOXProductBlock;
import Pages.*;
import Pages.CCX.*;
import Pages.ClientCard;
import Pages.Credits.Application;
import Pages.Credits.PreliminaliryCalculation;
import io.qameta.allure.selenide.AllureSelenide;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.*;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.logevents.SelenideLogger.addListener;
import static helpers.AttachmentsHelper.*;
import static helpers.DriverHelper.configureDriver;
import static helpers.DriverHelper.getConsoleLogs;

public class TestBase {

    public static AuthorizationPage authorizationPage;
    public static ClientSearchCCX clientSearchCCX;
    public static StepsCCX stepsCCX;
    public static AuthLEProductBlock authLEProductBlock;
    public static ClientSearchLE clientSearchLE;
    public static ClientSearchOutlet clientSearchOutlet;
    public static AuthOXProductBlock authOXProductBlock;
    public static ClientSearchOX clientSearchOX;
    public static ProductAccountsCCX productAccountsCCX;
    public static Application application;
    public static PreliminaliryCalculation preliminaliryCalculation;
    public static ClientCard clientCard;
    public static ProductCardsCCX productCardsCCX;
    public static ProductCreditCCX productCreditCCX;
    public static ProductOrderCCX productOrderCCX;
    public static ManagePage managePage;


    public WebDriver driver;


    protected static String FILE_PATH = System.getProperty("user.dir")+"/src/test/resources/testdata/documents/pixels/";

//    @BeforeClass
//    public void setUp() {
//        configureDriver();
//        authorizationPage = new AuthorizationPage();
//        clientSearchCCX = new ClientSearchCCX();
//        authCCXProductBlock = new AuthCCXProductBlock();
//        authLEProductBlock = new AuthLEProductBlock();
//        clientSearchLE = new ClientSearchLE();
//        clientSearchOutlet = new ClientSearchOutlet();
//        authOXProductBlock = new AuthOXProductBlock();
//        clientSearchOX = new ClientSearchOX();
//        products = new Products();
//        application = new Application();
//        preliminaliryCalculation = new PreliminaliryCalculation();
//
//    }


    @BeforeSuite
    public void beforeSuite() {
        configureDriver();
        productCardsCCX = new ProductCardsCCX();
        authorizationPage = new AuthorizationPage();
        clientSearchCCX = new ClientSearchCCX();
        authLEProductBlock = new AuthLEProductBlock();
        clientSearchLE = new ClientSearchLE();
        clientSearchOutlet = new ClientSearchOutlet();
        authOXProductBlock = new AuthOXProductBlock();
        clientSearchOX = new ClientSearchOX();
        productAccountsCCX = new ProductAccountsCCX();
        application = new Application();
        preliminaliryCalculation = new PreliminaliryCalculation();
        clientCard = new ClientCard();
        stepsCCX = new StepsCCX();
        productCreditCCX = new ProductCreditCCX();
        productOrderCCX = new ProductOrderCCX();
        managePage = new ManagePage();

    }


    @BeforeMethod
    public void beforeTest(){
        addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(true));
    }

    @AfterMethod
    public void closeDriverAfterTest(){
        attachAsText("Browser console logs", getConsoleLogs());
        closeWebDriver();
    }

}
