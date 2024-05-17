package pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class GiftcardPage {
	private WebDriver driver;

    // Constructor to initialize the WebDriver
    public GiftcardPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public WebElement getGiftcardLinkElement() {
        return driver.findElement(By.xpath("//img[@alt='dm']"));
    }
    public void clickGiftcardLink() {
    	getGiftcardLinkElement().click();
    }
    public void enterEmail(String emailID) {
        WebElement email = driver.findElement(By.xpath("//input[@name='recipient-email[]']"));
        email.sendKeys(emailID);
    }
    public void enterName(String name) {
        WebElement nameBox = driver.findElement(By.xpath("//input[@name='recipient-name[]']"));
        nameBox.sendKeys(name);
    }
    public void selectVoucher() {
        WebElement dropDown = driver.findElement(By.xpath("//select[@name='voucher-value[]']"));
        dropDown.click();
        Select select = new Select(dropDown);
        List<WebElement> options = select.getOptions();
        
        for (WebElement option : options) {
            if (option.getText().trim().equals("100")) {
                option.click();
                break;
            }
        }
    }
    public WebElement getBuyButtonLinkElement() {
        return driver.findElement(By.xpath("//button[contains(@class, 'QqFHMw') and contains(@class, 'AsTLnx') and contains(@class, '_7Pd1Fp') and contains(text(), 'BUY GIFT CARD FOR')]"));
    }
    public void clickBuyButton() {
    	WebElement buyButton = driver.findElement(By.xpath("//button[contains(@class, 'QqFHMw') and contains(@class, 'AsTLnx') and contains(@class, '_7Pd1Fp') and contains(text(), 'BUY GIFT CARD FOR')]"));
    	buyButton.click();
    }
}
