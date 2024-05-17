package tests;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import Maven.ExitTest.ExcelUtils;
import pages.DownloadPage;
import pages.ExchangePage;
import tests.DownloadTest.PopupHandler;

public class ExchangeTest extends BaseClass{
	private static final Logger logger = LogManager.getLogger(ExchangeTest.class);

    @Test(dataProvider = "exchangeTestData")
    public void exchangeTest(String url, String executionRequired, String pincode) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        ExtentTest test = extent.createTest("ExchangeTest");
        ExchangePage exchangePage = new ExchangePage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
            exchangePage.enterPincode(pincode);
            logger.info("Entered Pincode");
            try 
            {
                Thread.sleep(2000); // Sleep for 2 seconds (adjust the duration as needed)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            exchangePage.scrollAndClickExchangeButton();
            logger.info("Clicked exchange Button");
            try {
                Thread.sleep(3000); // Sleep for 2 seconds (adjust the duration as needed)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String pageTitle = driver.getTitle();
            System.out.println(pageTitle);
            Assert.assertEquals(pageTitle, "vivo T2 Pro 5G (Dune Gold, 128 GB) (8 GB RAM)", "Page title is not as expected");

        } catch (AssertionError e) {
            String screenshotFileName = "exchangeSS";
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

    @DataProvider(name = "exchangeTestData")
    public Object[][] getLoginTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "data/loginData.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "exchangeSheet");
    }
}