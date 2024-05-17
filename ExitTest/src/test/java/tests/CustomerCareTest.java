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
import pages.CustomerCarePage;
import pages.GroceryPage;

public class CustomerCareTest extends BaseClass{
	private static final Logger logger = LogManager.getLogger(CustomerCareTest.class);

    @Test(dataProvider = "customerTestData")
    public void customerCareTest(String url, String executionRequired) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        ExtentTest test = extent.createTest("CustomerCareTest");
        CustomerCarePage ccpage = new CustomerCarePage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
        	// Refresh the page
            driver.navigate().refresh();
            ccpage.goToCustomerCareInfo();
            logger.info("Navigated to Customer Care Page");
            String pageTitle = driver.getTitle();
            Assert.assertEquals(pageTitle, "Online Shopping India | Buy Mobiles, Electronics, Appliances, Clothing and More Online at Flipkart.com", "Page title is not as expected");
        } catch (AssertionError e) {
            String screenshotFileName = "customerSS";
            String screenshotPath = "/ExitTest/screenshots" + screenshotFileName + ".png";
            try 
            {
                getScreenshot(screenshotPath);
                test.fail("Test failed", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                logger.error("Test failed");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @DataProvider(name = "customerTestData")
    public Object[][] getLoginTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "data/loginData.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "customerSheet");
    }
}
