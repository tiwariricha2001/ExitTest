package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class SellingPage {
	private WebDriver driver;

    // Constructor to initialize the WebDriver
    public SellingPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public void clickSellerLink() {
    	WebElement sellerLink = driver.findElement(By.partialLinkText("Become a Seller"));
    	sellerLink.click();
    }
    public void clickStartSellingLink() {
    	WebElement sellingButton = driver.findElement(By.xpath("//button[@data-testid='button']"));
    	sellingButton.click();
    }
    public void enterPincode(String pin) {
    	WebElement pincode = driver.findElement(By.name("pincode"));
    	pincode.sendKeys(pin);
    	pincode.sendKeys(Keys.ENTER);
    }
}
