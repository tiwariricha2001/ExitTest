package tests;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import Maven.ExitTest.ExcelUtils;
import pages.SignUpPage;

@Listeners({ TestListener.class }) // Add TestListener as a listener
public class LoginTest extends BaseClass {
    private static final Logger logger = LogManager.getLogger(LoginTest.class);

    @Test(dataProvider = "loginTestData")
    public void loginTest(String phoneNumber, String executionRequired) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        ExtentTest test = extent.createTest("LoginTest");
        SignUpPage flipkartHomePage = new SignUpPage(driver);
        driver.get("https://www.flipkart.com");
        driver.manage().window().maximize();

        try {
            flipkartHomePage.clickLoginButton();
            logger.info("Clicked on Login Button");
            
            
            flipkartHomePage.enterPhoneNumber(phoneNumber);
            logger.info("Entered Phone Number");
            String pageTitle = driver.getTitle();
            System.out.println(pageTitle);
            Assert.assertEquals(pageTitle, "Online Shopping India | Buy Mobiles, Electronics, Appliances, Clothing and More Online at Flipkart.com", "Page title is not as expected");

        } catch (AssertionError e) {
            String screenshotFileName = "loginSS";
            String screenshotPath = "/ExitTest/screenshots" + screenshotFileName + ".png";
            try {
                getScreenshot(screenshotPath);
                test.fail("Test failed", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                logger.error("Test failed");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @DataProvider(name = "loginTestData")
    public Object[][] getLoginTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "data/loginData.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "loginSheet");
    }
}

