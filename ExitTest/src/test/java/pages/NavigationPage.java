package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
public class NavigationPage {
	private WebDriver driver;

    // Constructor to initialize the WebDriver
    public NavigationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public void selectHomeandFurniture() {
        WebElement dropDown = driver.findElement(By.cssSelector("div._1ch8e_"));
        dropDown.click();
    }
    public void goToFurnitureStudio() {
    	WebElement furnitureLink = driver.findElement(By.partialLinkText("Men's T-Shirts"));
        furnitureLink.click();
    }
}
