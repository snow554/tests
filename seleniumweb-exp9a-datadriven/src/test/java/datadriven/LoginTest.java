package datadriven;

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
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class LoginTest {

    private static final String LOGIN_URL = "https://the-internet.herokuapp.com/login";

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        configureChromeDriverPath();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.manage().window().maximize();
        driver.get(LOGIN_URL);
    }

    @DataProvider(name = "loginData")
    public Object[][] getData() {
        return new Object[][] {
            { "tomsmith", "SuperSecretPassword!", true },
            { "wronguser", "wrongpass", false }
        };
    }

    @Test(dataProvider = "loginData")
    public void loginTest(String user, String pass, boolean shouldSucceed) {
        driver.findElement(By.id("username")).sendKeys(user);
        driver.findElement(By.id("password")).sendKeys(pass);
        driver.findElement(By.cssSelector("button[type='submit']")).click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#flash")));

        String currentUrl = driver.getCurrentUrl();
        if (shouldSucceed) {
            Assert.assertTrue(currentUrl.contains("secure"), "Expected secure URL for valid credentials.");
        } else {
            Assert.assertTrue(currentUrl.contains("login"), "Expected to stay on login page for invalid credentials.");
        }
    }

    @AfterMethod
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
