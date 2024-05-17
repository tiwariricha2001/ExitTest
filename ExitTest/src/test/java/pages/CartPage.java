package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CartPage {
	private WebDriver driver;

    // Constructor to initialize the WebDriver
    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    //@FindBy(xpath= "//button[@class='QqFHMw vslbG+ In9uk2']")
    
	/*public void clickCartButton() 
	{
		WebElement cartButton = driver.findElement(By.xpath("//button[@class='QqFHMw vslbG+ In9uk2']"));
        cartButton.click();
    }*/
    public WebElement getCartButtonElement() {
        return driver.findElement(By.xpath("//button[@class='QqFHMw vslbG+ In9uk2']"));
    }
    public WebElement getPlaceOrderButtonElement() {
        return driver.findElement(By.xpath("//button[@class='QqFHMw zA2EfJ _7Pd1Fp']"));
    }

    public void clickCartButton() {
        getCartButtonElement().click();
    }
    public void clickPlaceOrderButton() {
    	getPlaceOrderButtonElement().click();
    }
    public void enterEmail(String email) {
    	WebElement emailBox = driver.findElement(By.xpath("//input[@class='r4vIwl Jr-g+f']"));
    	emailBox.sendKeys(email);
    	emailBox.sendKeys(Keys.ENTER);
    }
}
