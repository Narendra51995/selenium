package test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.AssertJUnit;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class SignUpPage {

	// Setting up capabilities to run our test script
	public String username = "narendra.patil51995";
	public String accesskey = "Z0oqW1wGrWvhRaT8D4dKCC5rHItw51noUpHeYKl20k2amiM7fF";
	public static RemoteWebDriver driver = null;
	public String gridURL = "@hub.lambdatest.com/wd/hub";
	boolean status = false;

	// Setting up capabilities to run our test script
	@Parameters(value = { "browser", "version" })
	@BeforeClass
	public void setUp(String browser, String version) throws Exception {
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability("browserName", browser);
		capabilities.setCapability("version", version);
		capabilities.setCapability("platform", "win10"); // If this cap isn't specified, it will just get any available
		capabilities.setCapability("build", "Build_001");
		capabilities.setCapability("name", "Test");
		capabilities.setCapability("network", true); // To enable network logs
		capabilities.setCapability("visual", true); // To enable step by step screenshot
		capabilities.setCapability("video", true); // To enable video recording
		capabilities.setCapability("console", true); // To capture console logs
		try {
			driver = new RemoteWebDriver(new URL("https://" + username + ":" + accesskey + gridURL), capabilities);
		} catch (MalformedURLException e) {
			System.out.println("Invalid grid URL");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

	}

	// Opening browser with the given URL and navigate to Registration Page
	@BeforeMethod
	public void openBrowser() {
		driver.manage().deleteAllCookies();

		driver.get("https://www.lambdatest.com/");

		driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(15, TimeUnit.SECONDS);

		WebElement signUpButton = driver.findElement(By.xpath("//a[text()='Sign up for Free']"));
		signUpButton.click();

	}

	// Verifying elements on Registration page
	@Test
	public void verifyElemntsOnPageTest() {
		WebElement lambdaTestLogo = driver.findElement(By.xpath("//a/img[@class='mb-30 h-auto']"));
		lambdaTestLogo.isDisplayed();

		WebElement signUpTitle = driver.findElement(By.xpath("//button[@type='submit']"));
		signUpTitle.isDisplayed();

		WebElement termsText = driver.findElement(By.xpath("//a[@rel='noreferrer']"));
		termsText.isDisplayed();

		WebElement loginLinkText = driver.findElement(By.xpath("//a[@href='/login']"));
		loginLinkText.isDisplayed();

	}

	// Verifying redirection to the terms and conditions page
	@Test
	public void termsRedirectionTest() {
		WebElement termsLink = driver.findElement(By.xpath("//a[@href='https://www.lambdatest.com/terms-of-service']"));
		termsLink.click();

		Set<String> allWindows = driver.getWindowHandles();

		for (String handle : allWindows) {
			driver.switchTo().window(handle);
		}

		String expectedURL = "https://www.lambdatest.com/legal/terms-of-service";
		String actualURL = driver.getCurrentUrl();
		// System.out.println(actualURL);
		AssertJUnit.assertEquals(actualURL, expectedURL);

		String expectedTitle = "Terms of Service | LambdaTest";
		String actualTitle = driver.getTitle();
		// System.out.println(actualTitle);
		AssertJUnit.assertEquals(actualTitle, expectedTitle);
	}

	// Verifying Privacy policy page redirection
	@Test
	public void privacyPolicyRedirectionTest() {
		WebElement privacyPolicyLink = driver.findElement(By.xpath("//a[@href='https://www.lambdatest.com/privacy']"));
		privacyPolicyLink.click();

		Set<String> allWindows = driver.getWindowHandles();

		for (String handle : allWindows) {
			driver.switchTo().window(handle);
		}

		String expectedURL = "https://www.lambdatest.com/legal/privacy";
		String actualURL = driver.getCurrentUrl();
		// System.out.println(actualURL);
		AssertJUnit.assertEquals(actualURL, expectedURL);

		String expectedTitle = "Privacy Policy | LambdaTest";
		String actualTitle = driver.getTitle();
		// System.out.println(actualTitle);
		AssertJUnit.assertEquals(actualTitle, expectedTitle);
	}

	// Verifying redirection to the Login page from Registration page
	@Test
	public void loginRedirectionTest() {
		WebElement loginLink = driver.findElement(By.xpath("//a[@href='/login']"));
		loginLink.click();

		String expectedURL = "https://accounts.lambdatest.com/login";
		String actualURL = driver.getCurrentUrl();
		// System.out.println(actualURL);
		AssertJUnit.assertEquals(actualURL, expectedURL);

		String expectedTitle = "Log in";
		String actualTitle = driver.getTitle();
		// System.out.println(actualTitle);
		AssertJUnit.assertEquals(actualTitle, expectedTitle);
	}

	// Verifying redirection to the landing page
	@Test
	public void landingPageRedirectionTest() {
		WebElement lambdaTestLogo = driver.findElement(By.xpath("//a[@href='/login']"));
		lambdaTestLogo.click();

		String expectedURL = "https://accounts.lambdatest.com/login";
		String actualURL = driver.getCurrentUrl();
		AssertJUnit.assertEquals(actualURL, expectedURL);

	}

	// Registration with all valid data
	@Test
	public void validRegistrationTest() {

		WebElement fullName = driver.findElement(By.name("name"));
		fullName.sendKeys("Narendra");

		WebElement email = driver.findElement(By.name("email"));
		email.sendKeys("patilnarendra12345@gmai.com");

		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("Patil9083");

		WebElement phone = driver.findElement(By.name("phone"));
		phone.sendKeys("8200385364");

		// WebElement termsOfServices =
		// driver.findElement(By.xpath("//samp[contains(@class,'customcheckbox')]"));
		// termsOfServices.click();

		WebElement signUp = driver.findElement(By.xpath("//button[@type='submit']"));
		signUp.click();

		String expectedURL = "https://accounts.lambdatest.com/email/verify";
		String actualURL = driver.getCurrentUrl();
		AssertJUnit.assertEquals(actualURL, expectedURL);

		String expectedTitle = "Verify Your Email Address - LambdaTest";
		String actualTitle = driver.getTitle();
		AssertJUnit.assertEquals(actualTitle, expectedTitle);

	}

	// Registration without providing Company Name field
	/*
	 * @Test public void emptyCompanyNameTest() { WebElement fullName =
	 * driver.findElement(By.name("name")); fullName.sendKeys("Narendra");
	 * 
	 * WebElement email = driver.findElement(By.name("email"));
	 * email.sendKeys("patilnarendra8383@gmai.com");
	 * 
	 * WebElement password = driver.findElement(By.name("password"));
	 * password.sendKeys("Patil9083");
	 * 
	 * WebElement phone = driver.findElement(By.name("phone"));
	 * phone.sendKeys("8200385364");
	 * 
	 * WebElement termsOfServices =
	 * driver.findElement(By.xpath("//samp[contains(@class,'customcheckbox')]"));
	 * termsOfServices.click();
	 * 
	 * WebElement signUp = driver.findElement(By.xpath("//button[@type='submit']"));
	 * signUp.click();
	 * 
	 * String expectedURL = "https://accounts.lambdatest.com/email/verify"; String
	 * actualURL = driver.getCurrentUrl(); Assert.assertEquals(actualURL,
	 * expectedURL);
	 * 
	 * /* Set <String> allWindows = driver.getWindowHandles();
	 * 
	 * for(String handle : allWindows) { driver.switchTo().window(handle); }
	 * 
	 * String expectedTitle = "Verify Your Email Address - LambdaTest"; String
	 * actualTitle = driver.getTitle(); Assert.assertEquals(actualTitle,
	 * expectedTitle); }
	 */

	// Registration without providing Name field
	@Test
	public void emptyNameTest() {
		WebElement fullName = driver.findElement(By.name("name"));
		fullName.sendKeys("");

		WebElement email = driver.findElement(By.name("email"));
		email.sendKeys("patilnarendra8383@gmail.com");

		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("Patil9083");

		WebElement phone = driver.findElement(By.name("phone"));
		phone.sendKeys("8200385364");

		// WebElement termsOfServices =
		// driver.findElement(By.xpath("//samp[contains(@class,'customcheckbox')]"));
		// termsOfServices.click();

		WebElement signUp = driver.findElement(By.xpath("//div/button[@type='submit']"));
		signUp.click();
		String expectedErrorMsg = "Please enter your name";
		WebElement exp = driver.findElement(By.xpath("//div/p[@data-testid='errors-name']"));
		String actualErrorMsg = exp.getText();
		AssertJUnit.assertEquals(actualErrorMsg, expectedErrorMsg);
	}

	// Registration without providing user email field
	@Test
	public void emptyEmailTest() {
		WebElement fullName = driver.findElement(By.name("name"));
		fullName.sendKeys("Narendra");

		WebElement email = driver.findElement(By.name("email"));
		email.sendKeys("");

		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("Patil9083");

		WebElement phone = driver.findElement(By.name("phone"));
		phone.sendKeys("8200385364");

		// WebElement termsOfServices =
		// driver.findElement(By.xpath("//samp[contains(@class,'customcheckbox')]"));
		// termsOfServices.click();

		WebElement signUp = driver.findElement(By.xpath("//div/button[@type='submit']"));
		signUp.click();
		String expectedErrorMsg = "Please enter your email address";
		WebElement exp = driver.findElement(By.xpath("//div/p[@data-testid='errors-email']"));
		String actualErrorMsg = exp.getText();
		AssertJUnit.assertEquals(actualErrorMsg, expectedErrorMsg);
	}

	// Registration with email id which already have account
	@Test
	public void invalidEmailTest() {

		WebElement fullName = driver.findElement(By.name("name"));
		fullName.sendKeys("Narendra");

		WebElement email = driver.findElement(By.name("email"));
		email.sendKeys("patilnarendra8383@gmail.com");

		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("Patil9083");

		WebElement phone = driver.findElement(By.name("phone"));
		phone.sendKeys("8200385364");

		// WebElement termsOfServices =
		// driver.findElement(By.xpath("//samp[contains(@class,'customcheckbox')]"));
		// termsOfServices.click();

		WebElement signUp = driver.findElement(By.xpath("//div/button[@type='submit']"));
		signUp.click();

		String expectedErrorMsg = "This Email is already registered";

		WebElement exp = driver.findElement(By.xpath("//p[@data-testid='errors-email']"));
		String actualErrorMsg = exp.getText();
		AssertJUnit.assertEquals(actualErrorMsg, expectedErrorMsg);
	}

	// Registration without providing password field
	@Test
	public void emptyPasswordTest() {
		WebElement fullName = driver.findElement(By.name("name"));
		fullName.sendKeys("Narendra");

		WebElement email = driver.findElement(By.name("email"));
		email.sendKeys("patilnarendra8383@gmail.com");

		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("");

		WebElement phone = driver.findElement(By.name("phone"));
		phone.sendKeys("8200385364");

		// WebElement termsOfServices =
		// driver.findElement(By.xpath("//samp[contains(@class,'customcheckbox')]"));
		// termsOfServices.click();

		WebElement signUp = driver.findElement(By.xpath("//div/button[@type='submit']"));
		signUp.click();
		String expectedErrorMsg = "Please enter a desired password";
		WebElement exp = driver.findElement(By.xpath("//div/p[@data-testid='errors-password']"));
		String actualErrorMsg = exp.getText();
		AssertJUnit.assertEquals(actualErrorMsg, expectedErrorMsg);
	}

	// Registration with invalid password
	@Test
	public void inValidPasswordTest() {
		WebElement fullName = driver.findElement(By.name("name"));
		fullName.sendKeys("Narendra");

		WebElement email = driver.findElement(By.name("email"));
		email.sendKeys("patilnarendra8383@gmail.com");

		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("T");

		WebElement phone = driver.findElement(By.name("phone"));
		phone.sendKeys("8200385364");

		// WebElement termsOfServices =
		// driver.findElement(By.xpath("//samp[contains(@class,'customcheckbox')]"));
		// termsOfServices.click();

		WebElement signUp = driver.findElement(By.xpath("//div/button[@type='submit']"));
		signUp.click();

		String expectedErrorMsg = "Password should be at least 8 characters long";

		WebElement exp = driver.findElement(By.xpath("//div/p[@data-testid='errors-password']"));
		String actualErrorMsg = exp.getText();

		AssertJUnit.assertEquals(actualErrorMsg, expectedErrorMsg);

		// Password should be at least 8 characters long
	}

	// Registration without providing user phone number field
	@Test
	public void emptyPhoneTest() {
		WebElement fullName = driver.findElement(By.name("name"));
		fullName.sendKeys("Narendra");

		WebElement email = driver.findElement(By.name("email"));
		email.sendKeys("patilnarendra8383@gmail.com");

		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("Patil9083");

		WebElement phone = driver.findElement(By.name("phone"));
		phone.sendKeys("");

		// WebElement termsOfServices =
		// driver.findElement(By.xpath("//samp[contains(@class,'customcheckbox')]"));
		// termsOfServices.click();

		WebElement signUp = driver.findElement(By.xpath("//div/button[@type='submit']"));
		signUp.click();

		String expectedErrorMsg = "Please enter your phone";

		WebElement exp = driver.findElement(By.xpath("//div/p[@data-testid='errors-phone']"));
		String actualErrorMsg = exp.getText();
		AssertJUnit.assertEquals(actualErrorMsg, expectedErrorMsg);
	}

	// Registration with providing invalid user phone number field
	@Test
	public void inValidPhoneTest() {

		WebElement fullName = driver.findElement(By.name("name"));
		fullName.sendKeys("Narendra");

		WebElement email = driver.findElement(By.name("email"));
		email.sendKeys("patilnarendra8383@gmail.com");

		WebElement password = driver.findElement(By.name("password"));
		password.sendKeys("Patil9083");

		WebElement phone = driver.findElement(By.name("phone"));
		phone.sendKeys("98");

		// WebElement termsOfServices =
		// driver.findElement(By.xpath("//samp[contains(@class,'customcheckbox')]"));
		// termsOfServices.click();

		WebElement signUp = driver.findElement(By.xpath("//div/button[@type='submit']"));
		signUp.click();
		String expectedErrorMsg = "Please enter a valid Phone number";
		WebElement exp = driver.findElement(By.xpath("//div/p[@data-testid='errors-phone']"));
		String actualErrorMsg = exp.getText();
		AssertJUnit.assertEquals(actualErrorMsg, expectedErrorMsg);
		// Please enter a valid Phone number
	}

	// Registration without accepting terms and condition tickbox
	/*
	 * @Test public void uncheckedTerms() { WebElement companyName =
	 * driver.findElement(By.name("organization_name"));
	 * companyName.sendKeys("TestCompany");
	 * 
	 * WebElement fullName = driver.findElement(By.name("name"));
	 * fullName.sendKeys("TestName");
	 * 
	 * WebElement email = driver.findElement(By.name("email"));
	 * email.sendKeys("test@test.com");
	 * 
	 * WebElement password = driver.findElement(By.name("password"));
	 * password.sendKeys("Test@123");
	 * 
	 * WebElement phone = driver.findElement(By.name("phone"));
	 * phone.sendKeys("9876543210");
	 * 
	 * //WebElement termsOfServices = driver.findElement(By.id("terms_of_service"));
	 * //termsOfServices.click();
	 * 
	 * WebElement signUp = driver.findElement(By.
	 * xpath("//button[contains(@class,'btn sign-up-btn-2 btn-block')]"));
	 * signUp.click();
	 * 
	 * String expectedTermsErrorMessage =
	 * "To proceed further you must agree to our Terms of Service and Privacy Policy"
	 * ; WebElement uncheckedTermCheckbox =
	 * driver.findElement(By.xpath("//p[@class='error-mass mt-2']")); String
	 * actualTermsErrorMessage = uncheckedTermCheckbox.getText(); //To proceed
	 * further you must agree to our Terms of Service and Privacy Policy
	 * 
	 * Assert.assertEquals(actualTermsErrorMessage, expectedTermsErrorMessage); }
	 */

	// Closing the browser session after completing each test case
	@AfterClass
	public void tearDown() throws Exception {
		if (driver != null) {
			((JavascriptExecutor) driver).executeScript("lambda-status=" + status);
			driver.quit();
		}
	}
}
