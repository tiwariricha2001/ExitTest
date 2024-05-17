package tests;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import Maven.ExitTest.ExcelUtils;
import pages.PincodePage;
import pages.SearchPage;
import pages.SignUpPage;

public class SearchTest extends BaseClass{
	private static final Logger logger = LogManager.getLogger(SearchTest.class);

    @Test(dataProvider = "searchTestData")
    public void searchTest(String url, String executionRequired, String productName) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        ExtentTest test = extent.createTest("SearchTest");
        SearchPage searchPage = new SearchPage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
            searchPage.searchProduct(productName);
            logger.info("Searched the product");
            String pageTitle = driver.getTitle();
            System.out.println(pageTitle);
            Assert.assertEquals(pageTitle, "Women Jeans- Buy Products Online at Best Price in India - All Categories | Flipkart.com", "Page title is not as expected");

        } catch (AssertionError e) {
            String screenshotFileName = "searchSS";
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

    @DataProvider(name = "searchTestData")
    public Object[][] getLoginTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "data/loginData.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "searchSheet");
    }
}
