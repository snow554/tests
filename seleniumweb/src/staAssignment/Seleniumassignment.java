package staAssignment;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Seleniumassignment {

    public static void main(String[] args) {
        WebDriver driver = null;

        try {
            driver = new ChromeDriver();
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));

            driver.manage().window().maximize();

            driver.get("https://demowebshop.tricentis.com/");

            wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Log in"))).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Email"))).sendKeys("1testing@gmail.com");
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("Password"))).sendKeys("123456");

            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.button-1.login-button"))).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("small-searchterms"))).sendKeys("book");
            wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input.button-1.search-box-button"))).click();

            WebElement firstProduct = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".product-item")));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", firstProduct);

            By listAddToCart = By.cssSelector("input.product-box-add-to-cart-button, button.product-box-add-to-cart-button");
            if (!firstProduct.findElements(listAddToCart).isEmpty()) {
                WebElement addButton = firstProduct.findElement(listAddToCart);
                wait.until(ExpectedConditions.elementToBeClickable(addButton));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", addButton);
            } else {
                firstProduct.findElement(By.cssSelector(".product-title a")).click();
                WebElement detailAddButton = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("button.add-to-cart-button, input.add-to-cart-button")));
                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", detailAddButton);
                wait.until(ExpectedConditions.elementToBeClickable(detailAddButton)).click();
            }

            wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(By.cssSelector(".cart-qty"), "(0)")));

            Thread.sleep(2000);
            System.out.println("Test execution completed.");
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Execution interrupted: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Test execution failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }
}
