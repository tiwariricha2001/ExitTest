/*package tests;

import java.io.IOException;
import java.time.Duration;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import Maven.ExitTest.ExcelUtils;
import pages.FilterPage;

public class FilterTest extends BaseClass{
	private static final Logger logger = LogManager.getLogger(FilterTest.class);

    @Test(dataProvider = "filterTestData")
    public void filterTest(String url, String executionRequired) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        ExtentTest test = extent.createTest("FilterTest");
        FilterPage filterPage = new FilterPage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
            filterPage.clickBannerImage();
            logger.info("Clicked on Product Banner");
            filterPage.minPriceFilter();
            logger.info("Applied Min Price Filter");
            try {
                Thread.sleep(2000); // Sleep for 2 seconds (adjust the duration as needed)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            filterPage.brandFilter();
            try {
                Thread.sleep(2000); // Sleep for 2 seconds (adjust the duration as needed)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            logger.info("Applied Brand Filter");
            String pageTitle = driver.getTitle();
            System.out.println(pageTitle);
            Assert.assertEquals(pageTitle, "Moto Edge 40 Neo - Buy Moto Edge 40 Neo Online at Low Prices In India | Flipkart.com", "Page title is not as expected");

        } catch (AssertionError e) {
            String screenshotFileName = "filterSS";
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

    @DataProvider(name = "filterTestData")
    public Object[][] getLoginTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "data/loginData.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "filterSheet");
    }
}*/

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
import pages.FilterPage;

public class FilterTest extends BaseClass {
    private static final Logger logger = LogManager.getLogger(FilterTest.class);

    @Test(dataProvider = "bannerTestData", priority = 1, groups = {"banner"}, enabled = true)
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

            try {
                Thread.sleep(4000); // Sleep for 4 seconds (adjust the duration as needed)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String pageTitle = driver.getTitle();
            String expectedTitle = "Moto Edge 40 Neo - Buy Moto Edge 40 Neo Online at Low Prices In India | Flipkart.com";
            Assert.assertEquals(pageTitle, expectedTitle, "Page title is not as expected");

        } catch (AssertionError e) {
            String screenshotFileName = "bannerSS";
            String screenshotPath = "/ExitTest/screenshots/" + screenshotFileName + ".png";
            try {
                getScreenshot(screenshotPath);
                test.fail("Test failed", MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                logger.error("Test failed");
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @Test(dataProvider = "filterTestData", priority = 2, groups = {"filter"}, enabled = true, dependsOnMethods = {"bannerTest"})
    public void filterTest(String url, String executionRequired) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        ExtentTest test = extent.createTest("FilterTest");
        FilterPage filterPage = new FilterPage(driver);
        //driver.get(url);
        driver.manage().window().maximize();

        try {
            filterPage.minPriceFilter();
            logger.info("Applied Min Price Filter");

            try {
                Thread.sleep(2000); // Sleep for 2 seconds (adjust the duration as needed)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            filterPage.brandFilter();

            try {
                Thread.sleep(2000); // Sleep for 2 seconds (adjust the duration as needed)
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            logger.info("Applied Brand Filter");
            String pageTitle = driver.getTitle();
            System.out.println(pageTitle);
            Assert.assertEquals(pageTitle, "Moto Edge 40 Neo - Buy Moto Edge 40 Neo Online at Low Prices In India | Flipkart.com", "Page title is not as expected");

        } catch (AssertionError e) {
            String screenshotFileName = "filterSS";
            String screenshotPath = "/ExitTest/screenshots/" + screenshotFileName + ".png";
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
    public Object[][] getBannerTestData() throws IOException, InvalidFormatException {
        String excelFilePath = "data/loginData.xlsx";
        return ExcelUtils.getTestData(excelFilePath, "bannerSheet");
    }

    @DataProvider(name = "filterTestData")
    public Object[][] getFilterTestData() throws IOException, InvalidFormatException {
        String excelFilePath = "data/loginData.xlsx";
        return ExcelUtils.getTestData(excelFilePath, "filterSheet");
    }
}
