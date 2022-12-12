package testng;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class LoginTest {
	WebDriver driver;

	By USERNAME =By.xpath("//*[@id=\"username\"]");
	By PASSWORD =By.xpath("//*[@id=\"password\"]");
	By LOGIN = By.xpath("/html/body/div/div/div/form/div[3]/button");
	By DASHBOARD = By.xpath("//*[@id=\"page-wrapper\"]/div[2]/div/h2");
	
	//save credentials under values, to use them later on. Avoiding hardcoding
	String userName="demo@techfios.com";
	String passWord="abc123";
	String expectedTitle="Dashboard";
	
	@BeforeMethod
	public void initDriver() {
		System.setProperty("webdriver.chrome.driver", "driver\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.get("https://techfios.com/billing/?ng=admin/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	@Test
	 public void loginTest() {
		driver.findElement(USERNAME).sendKeys(userName);
		driver.findElement(PASSWORD).sendKeys(passWord);
		driver.findElement(LOGIN).click();
		Assert.assertEquals(driver.findElement(DASHBOARD).getText(), expectedTitle, "Page not Found!");
	}
	
	
	  @AfterMethod public void tearDown()
	  { driver.close();
	  }
	 
}

