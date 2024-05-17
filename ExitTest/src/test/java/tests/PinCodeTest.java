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
import pages.PincodePage;
import pages.SearchPage;
import pages.SellingPage;

public class PinCodeTest extends BaseClass{
	private static final Logger logger = LogManager.getLogger(PinCodeTest.class);

    @Test(dataProvider = "pincodeTestData")
    public void pincodeTest(String url, String executionRequired, String pincode) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        ExtentTest test = extent.createTest("PinCodeTest");
        PincodePage pincodePage = new PincodePage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
            pincodePage.enterPincode(pincode);
            logger.info("Entered pincode");
            String pageTitle = driver.getTitle();
            System.out.println(pageTitle);
            Assert.assertEquals(pageTitle, "POCO M6 Pro 5G (Forest Green, 128 GB) (6 GB RAM)", "Page title is not as expected");

        } catch (AssertionError e) {
            String screenshotFileName = "pincodeSS";
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

    @DataProvider(name = "pincodeTestData")
    public Object[][] getLoginTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "data/loginData.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "pincodeSheet");
    }
}
