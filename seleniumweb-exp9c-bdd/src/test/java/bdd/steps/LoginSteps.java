package bdd.steps;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import io.cucumber.java.After;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps {

    private static final String LOGIN_URL = "https://the-internet.herokuapp.com/login";

    private WebDriver driver;
    private WebDriverWait wait;

    @Given("user is on login page")
    public void openPage() {
        configureChromeDriverPath();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.manage().window().maximize();
        driver.get(LOGIN_URL);
    }

    @When("user enters username {string}")
    public void enterUsername(String user) {
        driver.findElement(By.id("username")).sendKeys(user);
    }

    @When("user enters password {string}")
    public void enterPassword(String pass) {
        driver.findElement(By.id("password")).sendKeys(pass);
    }

    @When("clicks login button")
    public void clickLogin() {
        driver.findElement(By.cssSelector("button[type='submit']")).click();
    }

    @Then("login should be successful")
    public void verifyLogin() {
        wait.until(ExpectedConditions.urlContains("secure"));
        Assert.assertTrue(driver.getCurrentUrl().contains("secure"), "Login was not successful.");
    }

    @After
    public void closeBrowser() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void configureChromeDriverPath() {
        Path relativeDriverPath = Paths.get(".", "drivers", "chromedriver.exe").normalize();
        if (Files.exists(relativeDriverPath)) {
            System.setProperty("webdriver.chrome.driver", relativeDriverPath.toAbsolutePath().toString());
        }
    }
}
