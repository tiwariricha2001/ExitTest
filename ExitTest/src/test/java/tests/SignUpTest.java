package tests;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;

import Maven.ExitTest.ExcelUtils;
import pages.SearchPage;
import pages.SignUpPage;
import tests.TestListener;

@Listeners({ TestListener.class }) // Add TestListener as a listener
public class SignUpTest extends BaseClass{
	private static final Logger logger = LogManager.getLogger(SignUpTest.class);

    @Test(dataProvider = "signUpTestData")
    public void signUpTest(String url, String executionRequired, String phoneNumber) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        ExtentTest test = extent.createTest("SignUpTest");
        SignUpPage signUpPage = new SignUpPage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
            signUpPage.clickLoginButton();
            logger.info("Clicked Login Button");
            signUpPage.clickSignUpLink();
            logger.info("Clicked SignUp Link");
            signUpPage.enterPhoneNumber(phoneNumber);
            logger.info("Entered Phone Number");
            signUpPage.clickContinueButton();
            logger.info("Clicked Request OTP button");
            String pageTitle = driver.getTitle();
            System.out.println(pageTitle);
            Assert.assertEquals(pageTitle, "Online Shopping India | Buy Mobiles, Electronics, Appliances, Clothing and More Online at Flipkart.com", "Page title is not as expected");

        } catch (AssertionError e) {
            String screenshotFileName = "signUpSS";
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

    @DataProvider(name = "signUpTestData")
    public Object[][] getLoginTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "data/loginData.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "signUpSheet");
    }
}
