package base;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.*;
import utils.Login;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class BaseTest {

    public static WebDriver driver;
    public static Properties properties;
    private static ExtentReports extent;
    ExtentTest testLog;
    Login login;
    @BeforeClass
    public void beforeClass() {
        login=new Login();
        String browserName = properties.getProperty("Browser");

        if (browserName.equals("chrome")) {
            System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/test/resources/chromedriver.exe");
            //WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver();

            driver.manage().window().maximize();
            login.login(properties.getProperty("user"),properties.getProperty("password"));
            driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
            System.out.println("logged in");
            Assert.assertEquals(login.homePageTitle,"My Dashboard","Home Page title is not matched");
        }
    }

    @BeforeSuite
    public void OneTimeSetup(){
        //Read properties
        try {
            properties = new Properties();
            FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"/src/test/resources/project.properties");
            properties.load(fis);
        }
        catch(Exception e){
            e.printStackTrace();
        }

        //Initialize Extent Report
        extent = new ExtentReports();
        String workingDir = System.getProperty("user.dir");
        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(workingDir + "/extent.html");
        extent.attachReporter(htmlReporter);
    }

    @BeforeMethod
    public void setupMethod(Method test){
        testLog = extent.createTest(test.getDeclaringClass().getName() +"->"+ test.getName());
    }

    @AfterMethod
    public void tearDownMethod(ITestResult testResult){
        if(testResult.getStatus()==ITestResult.SUCCESS){
            testLog.pass("Test Passed");
        }
        else if(testResult.getStatus()==ITestResult.FAILURE){
            testLog.fail("Test Failed - " + testResult.getThrowable());
        }
    }

    @AfterSuite
    public void afterSuite(){
        extent.flush();
    }


}
