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
import pages.ComparePage;
import pages.SharePage;

public class CompareTest extends BaseClass{
	private static final Logger logger = LogManager.getLogger(CompareTest.class);

    @Test(dataProvider = "compareTestData")
    public void compareTest(String url,String url2, String executionRequired) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        ExtentTest test = extent.createTest("CompareTest");
        ComparePage comparePage = new ComparePage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
            comparePage.clickCompareButton();
            logger.info("Clicked on compare CheckBox");
            driver.get(url2);
            logger.info("Opened another phone to add to compare list");
            comparePage.clickCompareButton();
            logger.info("Clicked on compare CheckBox");
            comparePage.goToComparePage();
            logger.info("Opened Compare Page");
            try 
            {
                Thread.sleep(4000); // Sleep for 2 seconds (adjust the duration as needed)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String pageTitle = driver.getTitle();
            System.out.println(pageTitle);
            Assert.assertEquals(pageTitle, "REDMI 12 ( 128 GB Storage, 6 GB RAM ) Online at Best Price On Flipkart.com", "Page title is not as expected");
 
        } catch (AssertionError e) {
            String screenshotFileName = "compareSS";
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

    @DataProvider(name = "compareTestData")
    public Object[][] getLoginTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "data/loginData.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "compareSheet");
    }
}
