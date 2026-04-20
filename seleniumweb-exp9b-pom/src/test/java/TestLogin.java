import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestLogin {

    private static final String LOGIN_URL = "https://the-internet.herokuapp.com/login";

    private WebDriver driver;
    private LoginPage loginPage;
    private WebDriverWait wait;

    @BeforeClass
    public void setup() {
        configureChromeDriverPath();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.manage().window().maximize();
        driver.get(LOGIN_URL);
        loginPage = new LoginPage(driver);
    }

    @Test
    public void loginTest() {
        loginPage.login("tomsmith", "SuperSecretPassword!");
        wait.until(ExpectedConditions.urlContains("secure"));
        Assert.assertTrue(driver.getCurrentUrl().contains("secure"), "Valid login did not reach secure page.");
    }

    @AfterClass
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
