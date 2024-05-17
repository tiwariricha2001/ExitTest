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
import pages.GroceryPage;
import pages.SearchFlightPage;

public class BookFlightTest extends BaseClass{
	private static final Logger logger = LogManager.getLogger(BookFlightTest.class);

    @Test(dataProvider = "bookflightTestData")
    public void bookFlightTest(String url, String executionRequired) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        ExtentTest test = extent.createTest("BookFlightTest");
        SearchFlightPage searchFlightPage = new SearchFlightPage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
            searchFlightPage.clickTravelLink();
            logger.info("Clicked on Travel Link");
            try 
            {
                Thread.sleep(4000); // Sleep for 2 seconds (adjust the duration as needed)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String pageTitle = driver.getTitle();
            // Assert that the title matches the expected title
            String expectedTitle = "Flight Booking | Book Flight Tickets at Lowest Airfare on Flipkart.com";
            Assert.assertEquals(pageTitle, expectedTitle, "Page title is not as expected");
            searchFlightPage.enterFromCity();
            logger.info("Selected from city");

        } catch (AssertionError e) {
            String screenshotFileName = "bookflightSS";
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

    @DataProvider(name = "bookflightTestData")
    public Object[][] getLoginTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "data/loginData.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "flightSheet");
    }
}
