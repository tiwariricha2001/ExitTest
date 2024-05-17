package tests;

import java.io.IOException;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import Maven.ExitTest.ExcelUtils;
import pages.CartPage;
import pages.SignUpPage;

public class CartTest extends BaseClass{
	private static final Logger logger = LogManager.getLogger(CartTest.class);
	private static final String REPORT_FILE = "CartReport.html";
    private ExtentReports cartExtent;

    @BeforeClass
    public void setUpCartExtent() {
        cartExtent = new ExtentReports();
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(REPORT_FILE);
        cartExtent.attachReporter(htmlReporter);
    }

    @AfterClass
    public void tearDownCartExtent() {
        if (cartExtent != null) {
            cartExtent.flush();
            cartExtent = null;
        }
    }
    @Test(dataProvider = "cartTestData")
    public void cartTest(String url, String executionRequired, String email) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        ExtentTest test = cartExtent.createTest("AddtoCartTest");
        CartPage cartPage = new CartPage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
        	WebElement cartButton = cartPage.getCartButtonElement();
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", cartButton);            
            cartPage.clickCartButton();
            logger.info("Clicked on Cart Button");
            cartPage.clickPlaceOrderButton();
            logger.info("Clicked on Place Order Button");
            cartPage.enterEmail(email);
            logger.info("Entered email");
            //cartPage.clickCartButton();
            logger.info("Clicked on Cart Button");
            String pageTitle = driver.getTitle();
            System.out.println(pageTitle);
            //Assert.assertEquals(pageTitle, "Flipkart.com: Secure Payment: Login > Select Shipping Address > Review Order > Place Order", "Page title is not as expected");
            Assert.fail("Deliberate failure to capture Screenshot");

        } catch (AssertionError e) {
            String screenshotFileName = "cartSS";
            String screenshotPath = "/ExitTest/screenshots" + screenshotFileName + ".png";
            try {
                getScreenshot(screenshotPath);
                test.fail("Test failed", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                logger.error("Test failed");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }finally {
                cartExtent.flush(); // Ensure flushing the extent report after each test method
            }
        }
    }

    @DataProvider(name = "cartTestData")
    public Object[][] getLoginTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "data/loginData.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "cartSheet");
    }
}
