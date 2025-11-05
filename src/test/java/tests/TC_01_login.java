package tests;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import assesment.Brainholic_.enrichminion_test;

public class TC_01_login extends enrichminion_test {
	WebDriverWait wait;

	  // ====== XPATH LOCATORS ======

	    // ====== LOCATORS USING @FindBy ======
	    @FindBy(xpath = "//button[text()='Register']")
	    private WebElement Register;
	    @FindBy(xpath = "//a[text()='Login']")
	    private WebElement linkLogin;
	    @FindBy(xpath = "//input[@id='email']")
	    private WebElement inputEmail;
	    @FindBy(xpath = "//input[@id='password']")
	    private WebElement inputPassword;
	    @FindBy(xpath = "//button[text()='Register']")
	    private WebElement buttonRegister;
	    @FindBy(xpath = "//button[text()='Login']")
	    private WebElement buttonlogin;
	    	    // ====== TEST CASES ======
	    public TC_01_login() {}
	    
	    public TC_01_login(WebDriver driver) {
	        this.driver = driver;
	        PageFactory.initElements(driver, this);
	        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
	    }
	    @Test(priority = 1)
	    public void testSignup() {
	        wait.until(ExpectedConditions.visibilityOf(Register)).click();
	        wait.until(ExpectedConditions.visibilityOf(inputEmail)).click();
	        wait.until(ExpectedConditions.visibilityOf(inputEmail)).sendKeys("zekypulo@fxzig.com");
	        wait.until(ExpectedConditions.visibilityOf(inputPassword)).click();
	        wait.until(ExpectedConditions.visibilityOf(inputPassword)).sendKeys("zxcvbnm");
	        wait.until(ExpectedConditions.visibilityOf(buttonRegister)).click();
	        WebElement successMsg = driver.findElement(By.xpath("//*[contains(text(),'Check your email for the confirmation link')]"));
	        Assert.assertTrue(successMsg.isDisplayed(), "Signup failed!");	    }

	    @Test(priority = 2)
	    public void testLogin() {
	        wait.until(ExpectedConditions.visibilityOf(inputEmail)).sendKeys("zekypulo@fxzig.com");
	        wait.until(ExpectedConditions.visibilityOf(inputPassword)).sendKeys("zxcvbnm");
	        wait.until(ExpectedConditions.visibilityOf(buttonlogin)).click();
	        WebElement successMsg = driver.findElement(By.xpath("//*[contains(text(),'Invalid login credentials')]"));
	        Assert.assertTrue(driver.getCurrentUrl().contains("/dashboard"), "Login failed!");
	    }
	}