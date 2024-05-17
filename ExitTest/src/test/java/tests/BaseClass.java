package tests;

import java.io.File;
/*import java.io.File;
import java.io.IOException;
import java.time.Duration;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

@Listeners({ TestListener.class }) // Add TestListener as a listener
public class BaseClass {
	protected static WebDriver driver;
    protected ExtentReports extent;
    protected ExtentTest test;
    
    //@Parameters("browser")
    @BeforeClass
    public void setUp(@Optional("chrome") String browser) {
        if (extent == null) {
            extent = new ExtentReports();
            ExtentSparkReporter htmlReporter = new ExtentSparkReporter("TestReport.html");
            extent.attachReporter(htmlReporter);
        }

        if (driver == null) {
            if (browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            } else if (browser.equalsIgnoreCase("edge")) {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            } else if (browser.equalsIgnoreCase("firefox")) {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            } else {
                throw new IllegalArgumentException("Invalid browser specified");
            }
        }

        TestListener.setDriver(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
     // Initialize the test instance
        //test = extent.createTest(getClass().getName());
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
    	ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test skipped: " + result.getThrowable());
        } else {
            test.log(Status.PASS, "Test passed");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }

        if (extent != null) {
            extent.flush();
            extent = null;
        }
    }
 // In the getScreenshot method
    public static void getScreenshot(String screenshotPath) throws IOException 
    {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, new File(screenshotPath));
    }
}*/
import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {
    protected static WebDriver driver;
    protected ExtentReports extent;
    protected ExtentTest test;
    
    @BeforeClass
    public void setUp() {
        // Read configuration from config.properties
        Properties prop = new Properties();
        try (FileInputStream input = new FileInputStream("Utilities/config.properties")) {
            prop.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        String browser = prop.getProperty("browser");
        boolean headless = Boolean.parseBoolean(prop.getProperty("headless"));

        if (extent == null) {
            extent = new ExtentReports();
            ExtentSparkReporter htmlReporter = new ExtentSparkReporter("TestReport.html");
            extent.attachReporter(htmlReporter);
        }

        if (driver == null) {
            if (browser.equalsIgnoreCase("chrome")) {
                WebDriverManager.chromedriver().setup();
                ChromeOptions options = new ChromeOptions();
                if (headless) {
                    options.addArguments("--headless");
                }
                driver = new ChromeDriver(options);
            } else if (browser.equalsIgnoreCase("edge")) {
                WebDriverManager.edgedriver().setup();
                EdgeOptions options = new EdgeOptions();
                if (headless) {
                    // Edge does not support headless mode
                    throw new IllegalArgumentException("Headless mode is not supported for Edge browser.");
                }
                driver = new EdgeDriver(options);
                driver = new EdgeDriver();
            } else if (browser.equalsIgnoreCase("firefox")) {
            	 /*WebDriverManager.firefoxdriver().driverVersion("0.34.0").setup();
            	    FirefoxOptions options = new FirefoxOptions();
            	    if (headless) {
            	        options.addArguments("--headless");
            	    }
            	    driver = new FirefoxDriver(options);*/
            	FirefoxOptions options = new FirefoxOptions();
                if (headless) {
                    options.addArguments("--headless");
                }
                // No need to specify the binary path, the system will use the default installed Firefox
                driver = new FirefoxDriver(options);
            } else {
                throw new IllegalArgumentException("Invalid browser specified");
            }
        }

        TestListener.setDriver(driver);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
    }
    
    @AfterMethod
    public void tearDown(ITestResult result) {
        ExtentTest test = extent.createTest(result.getMethod().getMethodName());
        if (result.getStatus() == ITestResult.FAILURE) {
            test.log(Status.FAIL, "Test failed: " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            test.log(Status.SKIP, "Test skipped: " + result.getThrowable());
        } else {
            test.log(Status.PASS, "Test passed");
        }
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            driver = null;
        }

        if (extent != null) {
            extent.flush();
            extent = null;
        }
    }

    public static void getScreenshot(String screenshotPath) throws IOException {
        File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(srcFile, new File(screenshotPath));
    }
}

