package testngexp8;

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
import org.testng.annotations.Test;

public class EcommerceTest {

    private static final String BASE_URL = "https://www.saucedemo.com/";
    private static final String USERNAME = "standard_user";
    private static final String PASSWORD = "secret_sauce";

    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeMethod
    public void setup() {
        configureChromeDriverPath();
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.manage().window().maximize();
        driver.get(BASE_URL);
    }

    @Test
    public void testAddToCart() {
        driver.findElement(By.id("user-name")).sendKeys(USERNAME);
        driver.findElement(By.id("password")).sendKeys(PASSWORD);
        driver.findElement(By.id("login-button")).click();

        wait.until(ExpectedConditions.urlContains("inventory"));

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.className("shopping_cart_link")).click();

        wait.until(ExpectedConditions.urlContains("cart"));

        Assert.assertTrue(driver.findElements(By.cssSelector(".cart_item")).size() > 0,
            "Cart should contain at least one item.");
        Assert.assertTrue(driver.getPageSource().contains("Sauce Labs Backpack"),
            "Expected Sauce Labs Backpack in cart.");
        System.out.println("Test Passed");
    }

    @AfterMethod(alwaysRun = true)
    public void teardown() {
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
