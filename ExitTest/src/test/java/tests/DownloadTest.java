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

public class DownloadTest extends BaseClass{
	private static final Logger logger = LogManager.getLogger(DownloadTest.class);

    @Test(dataProvider = "downloadTestData")
    public void downloadTest(String url, String executionRequired) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        ExtentTest test = extent.createTest("DownloadTest");
        DownloadPage downloadPage = new DownloadPage(driver);
        PopupHandler popupHandler = new PopupHandler();
        driver.get(url);
        driver.manage().window().maximize();

        try {
            downloadPage.clickSellerLink();
            logger.info("Navigated to Seller Page");
            downloadPage.clickShopsyLink();
            logger.info("Navigated to Shopsy Store");
            downloadPage.downloadKit();
            logger.info("Downloaded Kit");
            popupHandler.cancelPopup();
            logger.info("Handled Pop Up");
            downloadPage.goToTop();
            logger.info("Navigated to the Top");
            String pageTitle = driver.getTitle();
            System.out.println(pageTitle);
            Assert.assertEquals(pageTitle, "Start Your Business on Shopsy With 0% Commission | Flipkart Seller Hub", "Page title is not as expected");
        } 
        catch (AssertionError e) {
            String screenshotFileName = "downloadSS";
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

    @DataProvider(name = "downloadTestData")
    public Object[][] getLoginTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "data/loginData.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "downloadSheet");
    }
    public class PopupHandler {

        public void cancelPopup() {
            try {
                Robot robot = new Robot();
                robot.keyPress(KeyEvent.VK_ESCAPE);
                robot.keyRelease(KeyEvent.VK_ESCAPE);
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }
}
