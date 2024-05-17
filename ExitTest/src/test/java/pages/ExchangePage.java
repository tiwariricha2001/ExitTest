package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ExchangePage {
	private WebDriver driver;

    // Constructor to initialize the WebDriver
    public ExchangePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    
	@FindBy(id = "pincodeInputId")
    private WebElement pinCodeBar;
	
	public void enterPincode(String pinCode) 
	{
		Actions actions = new Actions(driver);
   	 	actions.moveToElement(pinCodeBar).click().perform();
        pinCodeBar.sendKeys(pinCode);
        // Press Enter to perform the search
        pinCodeBar.sendKeys(Keys.ENTER);
    }
	public void scrollAndClickExchangeButton() {
		WebElement exchangeButton = driver.findElement(By.cssSelector("label.VKzPTL.JESWSS[for='BUY_WITH_EXCHANGE']"));
        exchangeButton.click();
    }
}
