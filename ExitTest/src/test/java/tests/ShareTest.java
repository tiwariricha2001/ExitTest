package tests;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;

import Maven.ExitTest.ExcelUtils;
import pages.SharePage;
import pages.SignUpPage;

@Listeners({ TestListener.class }) // Add TestListener as a listener
public class ShareTest extends BaseClass{
	private static final Logger logger = LogManager.getLogger(ShareTest.class);

    @Test(dataProvider = "shareTestData")
    public void shareTest(String url, String executionRequired) throws IOException {
        // Check if test execution is required based on the Excel data
        if (!executionRequired.equalsIgnoreCase("Yes")) {
            logger.info("Test execution not required. Skipping the test.");
            return;
        }

        ExtentTest test = extent.createTest("ShareTest");
        SharePage sharePage = new SharePage(driver);
        driver.get(url);
        driver.manage().window().maximize();

        try {
            sharePage.clickShareButton();
            logger.info("Clicked on Share Button");
            sharePage.seletEmail();
            logger.info("Selected Email Option");
            String pageTitle = driver.getTitle();
            System.out.println(pageTitle);
            Assert.assertEquals(pageTitle, "POCO M6 Pro 5G (Forest Green, 128 GB) (6 GB RAM)", "Page title is not as expected");

        } catch (AssertionError e) {
            String screenshotFileName = "shareSS";
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

    @DataProvider(name = "shareTestData")
    public Object[][] getLoginTestData() throws IOException, InvalidFormatException {
        // Provide the path to your Excel file
        String excelFilePath = "data/loginData.xlsx";

        // Read test data from Excel
        return ExcelUtils.getTestData(excelFilePath, "shareSheet");
    }
}
