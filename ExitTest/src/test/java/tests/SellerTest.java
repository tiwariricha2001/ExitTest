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
import pages.SellingPage;

public class SellerTest extends BaseClass{
	private static final Logger logger = LogManager.getLogger(SellerTest.class);

    @Test(dataProvider = "sellerTestData")
    public void sellerTest(String url, String executionRequired) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        ExtentTest test = extent.createTest("SellerTest");
        SellingPage sellingPage = new SellingPage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
            sellingPage.clickSellerLink();
            logger.info("Clicked Seller Link");
            sellingPage.clickStartSellingLink();
            logger.info("Navigated to Selling Page");
            String pageTitle = driver.getTitle();
            System.out.println(pageTitle);
            Assert.assertEquals(pageTitle, "Sell Online - Start Selling Online on Flipkart Seller Hub", "Page title is not as expected");

        } catch (AssertionError e) {
            String screenshotFileName = "sellerSS";
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

    @DataProvider(name = "sellerTestData")
    public Object[][] getLoginTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "data/loginData.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "sellerSheet");
    }
}
