package tests;

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
import pages.BannerPage;
import pages.SharePage;

public class BannerTest extends BaseClass{
	private static final Logger logger = LogManager.getLogger(BannerTest.class);

    @Test(dataProvider = "bannerTestData")
    public void bannerTest(String url, String executionRequired) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        ExtentTest test = extent.createTest("BannerTest");
        BannerPage bannerPage = new BannerPage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
            bannerPage.clickBannerImage();
            logger.info("Clicked on HomePage Banner");
            // Get the title of the page
            try 
            {
                Thread.sleep(4000); // Sleep for 2 seconds (adjust the duration as needed)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String pageTitle = driver.getTitle();
            // Assert that the title matches the expected title
            String expectedTitle = "Moto Edge 40 Neo - Buy Moto Edge 40 Neo Online at Low Prices In India | Flipkart.com";
            Assert.assertEquals(pageTitle, expectedTitle, "Page title is not as expected");

        } catch (AssertionError e) {
            String screenshotFileName = "bannerSS";
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

    @DataProvider(name = "bannerTestData")
    public Object[][] getLoginTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "data/loginData.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "bannerSheet");
    }
}
