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
import pages.NavigationPage;

public class NavigationTest extends BaseClass{
	private static final Logger logger = LogManager.getLogger(NavigationTest.class);

    @Test(dataProvider = "navigationTestData")
    public void navigationTest(String url, String executionRequired) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        ExtentTest test = extent.createTest("NavigationTest");
        NavigationPage navigationPage = new NavigationPage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
            navigationPage.selectHomeandFurniture();
            logger.info("Clicked on HomePage Banner");
            navigationPage.goToFurnitureStudio();
            logger.info("Clicked on HomePage Banner");
            try {
                Thread.sleep(3000); // Sleep for 2 seconds (adjust the duration as needed)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String pageTitle = driver.getTitle();
            System.out.println(pageTitle);
            Assert.assertEquals(pageTitle, "Tshirts Starts Rs.111 Online at Best Prices in India | Flipkart", "Page title is not as expected");

        } catch (AssertionError e) {
            String screenshotFileName = "navigationSS";
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

    @DataProvider(name = "navigationTestData")
    public Object[][] getLoginTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "data/loginData.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "navigationSheet");
    }
}
