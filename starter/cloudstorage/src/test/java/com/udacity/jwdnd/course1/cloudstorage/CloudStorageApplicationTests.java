package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void unauthorizedAccess() {
		driver.get("http://localhost:" + this.port + "/home");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void getSignupPage() {
		driver.get("http://localhost:" + this.port + "/signup");
		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void SignupLogin() {
		String username = "testuser";
		String password = "testpass";
		String messageText = "Hello!";
		String baseURL = "http://localhost:" + this.port;

		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Kiril", "Metodiev", username, password);

		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);
	}

	@Test
	public void SignupLoginLogout() {
		String username = "testuser";
		String password = "testpass";
		String baseURL = "http://localhost:" + this.port;

		driver.get(baseURL + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup("Kiril", "Metodiev", username, password);

		driver.get(baseURL + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		driver.get(baseURL + "/login?logout");
		Assertions.assertEquals("You have been logged out", driver.findElement(By.id("logout-msg")).getText());
	}

	@Test
	public void addNewNote() {
		this.SignupLogin();

		NotePage notePage = new NotePage(driver);
		notePage.createNewNote("Test note", "Test description");
		Assertions.assertEquals("A new note was added or edited successfully!", notePage.getSuccessMessage());
	}

	@Test
	public void editNote() {
		this.SignupLogin();

		NotePage notePage = new NotePage(driver);
		notePage.createNewNote("Test note", "Test description");
		notePage.editNote("Test note", "Test description");
		Assertions.assertEquals("A new note was added or edited successfully!", notePage.getSuccessMessage());
	}

	@Test
	public void deleteNote() {
		this.SignupLogin();

		NotePage notePage = new NotePage(driver);
		notePage.createNewNote("Test note", "Test description");
		notePage.deleteNote();
		Assertions.assertEquals("A note was deleted!", notePage.getSuccessMessage());
	}

	@Test
	public void addNewCredential() {
		this.SignupLogin();

		CredentialPage credentialPage = new CredentialPage(driver);
		credentialPage.createNewCredential("Test url", "Test username", "testpass");
		Assertions.assertEquals("A new credential was added successfully!", credentialPage.getSuccessMessage());
	}

	@Test
	public void editCredential() {
		this.SignupLogin();

		CredentialPage credentialPage = new CredentialPage(driver);
		credentialPage.createNewCredential("Test url", "Test username", "testpass");
		credentialPage.editCredential("Test url2", "Test username2", "testpass2");
		Assertions.assertEquals("A new credential was added successfully!", credentialPage.getSuccessMessage());
	}

	@Test
	public void deleteCredential() {
		this.SignupLogin();

		CredentialPage credentialPage = new CredentialPage(driver);
		credentialPage.createNewCredential("Test url", "Test username", "testpass");
		credentialPage.deleteCredential();
		Assertions.assertEquals("A credential was deleted!", credentialPage.getSuccessMessage());
	}
}
