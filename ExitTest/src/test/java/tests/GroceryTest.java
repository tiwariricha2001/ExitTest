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
import pages.SharePage;

public class GroceryTest extends BaseClass{
	private static final Logger logger = LogManager.getLogger(GroceryTest.class);

    @Test(dataProvider = "groceryTestData")
    public void groceryTest(String url, String executionRequired, String pincode) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        ExtentTest test = extent.createTest("GroceryTest");
        GroceryPage navigationPage = new GroceryPage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
            navigationPage.clickGroceryLink();
            logger.info("Navigated to Grocery Store");
            navigationPage.enterPincode(pincode);
            logger.info("Entered pincode to check service availability");
            String pageTitle = driver.getTitle();
            System.out.println(pageTitle);
            Assert.assertEquals(pageTitle, "Flipkart Grocery Store - Buy Groceries Online & Get Rs.1 Deals at Flipkart.com", "Page title is not as expected");
           
        } catch (AssertionError e) {
            String screenshotFileName = "grocerySS";
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

    @DataProvider(name = "groceryTestData")
    public Object[][] getLoginTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "data/loginData.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "grocerySheet");
    }
}
