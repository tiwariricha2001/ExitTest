package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class GroupCompanyPage {
	private WebDriver driver;

    // Constructor to initialize the WebDriver
    public GroupCompanyPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public void goToGroupCompany() {
    	// Scroll to the bottom of the page
        ((JavascriptExecutor) driver).executeScript("window.scrollTo(0, document.body.scrollHeight)");

        // Find the link to Myntra and click on it
        WebElement myntraLink = driver.findElement(By.partialLinkText("Myntra"));
        myntraLink.click();
    }
}
