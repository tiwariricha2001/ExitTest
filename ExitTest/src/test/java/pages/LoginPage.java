package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {
	private WebDriver driver;

    // Constructor to initialize the WebDriver
    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(linkText = "Login")
    private WebElement loginButton;
    
    @FindBy(linkText = "New to Flipkart? Create an account")
    private WebElement signUpLink;

    // Method to click on the login button
    public void clickLoginButton() {
        loginButton.click();
    }
    public void clickSignUpLink() {
        signUpLink.click();
    }
    
    // Method to enter phone number
    public void enterPhoneNumber(String phoneNumber) {
        WebElement phoneNumberField = driver.findElement(By.className("r4vIwl BV+Dqf"));
        phoneNumberField.sendKeys(phoneNumber);
    }
    
    // Method to click on the CONTINUE button
    public void clickContinueButton() {
        WebElement continueButton = driver.findElement(By.cssSelector("button.QqFHMw twnTnD _7Pd1Fp"));
        continueButton.click();
    }
}
