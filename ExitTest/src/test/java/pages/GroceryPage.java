package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

public class GroceryPage {
	private WebDriver driver;

    // Constructor to initialize the WebDriver
    public GroceryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public void clickGroceryLink() {
    	WebElement groceryLink = driver.findElement(By.partialLinkText("Grocery"));
    	groceryLink.click();
    }
    public void enterPincode(String pin) {
    	WebElement pincode = driver.findElement(By.name("pincode"));
    	pincode.sendKeys(pin);
    	pincode.sendKeys(Keys.ENTER);
    }
}
