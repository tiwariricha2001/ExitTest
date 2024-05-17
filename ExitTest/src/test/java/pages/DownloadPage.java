package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

public class DownloadPage {
	private WebDriver driver;

    // Constructor to initialize the WebDriver
    public DownloadPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public void clickSellerLink() {
    	WebElement sellerLink = driver.findElement(By.partialLinkText("Become a Seller"));
    	sellerLink.click();
    }
    public void clickShopsyLink() {
    	WebElement shopsyButton = driver.findElement(By.partialLinkText("Shopsy"));
    	shopsyButton.click();
    }
    public void downloadKit() {
    	WebElement downloadButton = driver.findElement(By.xpath("//button[@class='styles__ButtonStyle-sekd9q-0 klTPPh styles__StyledButton-sc-wrjoks-6 fZoYTS']"));
    	Actions actions = new Actions(driver);
   	 	actions.moveToElement(downloadButton).click().perform();
    }
    public void goToTop() {
    	WebElement arrow = driver.findElement(By.cssSelector("div[class='styles__GoTop-sc-1xt26qw-0 gqDWSG']"));
    	arrow.click();
    }
}
