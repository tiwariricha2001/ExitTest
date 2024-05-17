package tests;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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
import pages.GiftcardPage;

public class GiftcardTest extends BaseClass{
	private static final Logger logger = LogManager.getLogger(GiftcardTest.class);
	private static final String REPORT_FILE = "GiftCardReport.html";
    private ExtentReports giftcardExtent;

    @BeforeClass
    public void setUpCartExtent() {
        giftcardExtent = new ExtentReports();
        ExtentSparkReporter htmlReporter = new ExtentSparkReporter(REPORT_FILE);
        giftcardExtent.attachReporter(htmlReporter);
    }

    @AfterClass
    public void tearDownCartExtent() {
        if (giftcardExtent != null) {
            giftcardExtent.flush();
            giftcardExtent = null;
        }
    }
    @Test(dataProvider = "giftcardTestData")
    public void giftcardTest(String url, String executionRequired, String email, String Name) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        ExtentTest test = giftcardExtent.createTest("GiftcardTest");
        GiftcardPage giftcardPage = new GiftcardPage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
        	WebElement giftcardLink = giftcardPage.getGiftcardLinkElement(); 
        	
        	 Actions actions = new Actions(driver);
        	 try {
                 Thread.sleep(2000); // Sleep for 2 seconds (adjust the duration as needed)
             } catch (InterruptedException e) {
                 e.printStackTrace();
             }
        	 actions.moveToElement(giftcardLink).click().perform();
            logger.info("Navigated to Giftcard Creation Page");
            giftcardPage.enterEmail(email);
            logger.info("Entered Email");
            giftcardPage.enterName(Name);
            logger.info("Entered Name");
            giftcardPage.selectVoucher();
            logger.info("Selected Voucher value");
            WebElement buyButton = giftcardPage.getBuyButtonLinkElement();
            actions.moveToElement(buyButton).click().perform();
            logger.info("Clicked on Buy Voucher button");
            String pageTitle = driver.getTitle();
            System.out.println(pageTitle);
            //Assert.assertEquals(pageTitle, "Flipkart.com: Secure Payment: Login > Select Shipping Address > Review Order > Place Order", "Page title is not as expected");
            Assert.fail("Deliberate failure to capture screenshot");
        } catch (AssertionError e) {
            String screenshotFileName = "giftcardSS";
            String screenshotPath = "/ExitTest/screenshots" + screenshotFileName + ".png";
            try 
            {
                getScreenshot(screenshotPath);
                test.fail("Test failed", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                logger.error("Test failed");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }finally {
                giftcardExtent.flush(); // Ensure flushing the extent report after each test method
            }
        }
    }

    @DataProvider(name = "giftcardTestData")
    public Object[][] getLoginTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "data/loginData.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "giftcardSheet");
    }
}
