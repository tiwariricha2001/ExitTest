package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class CustomerCarePage {
	private WebDriver driver;

    // Constructor to initialize the WebDriver
    public CustomerCarePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public void goToCustomerCareInfo() {
    	WebElement dropdownButton = driver.findElement(By.xpath("//img[@alt='Dropdown with more help links']"));
    	dropdownButton.click();
    	WebElement customerCareLink = driver.findElement(By.partialLinkText("24x7 Customer Care"));
    	customerCareLink.click();
    }
}
