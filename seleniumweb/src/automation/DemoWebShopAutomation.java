package automation;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class DemoWebShopAutomation {

    public static void main(String[] args) {
        // Use relative path to ChromeDriver as requested.
        System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");

        WebDriver driver = null;

        try {
            driver = new ChromeDriver();

            driver.manage().window().maximize();
            Thread.sleep(1500);

            driver.get("http://demowebshop.tricentis.com/");
            Thread.sleep(1500);

            driver.findElement(By.linkText("Log in")).click();
            Thread.sleep(1500);

            WebElement emailInput = driver.findElement(By.id("Email"));
            emailInput.clear();
            emailInput.sendKeys("test@gmail.com");
            Thread.sleep(1000);

            WebElement passwordInput = driver.findElement(By.id("Password"));
            passwordInput.clear();
            passwordInput.sendKeys("123456");
            Thread.sleep(1000);

            driver.findElement(By.cssSelector("input.button-1.login-button")).click();
            Thread.sleep(1500);

            WebElement searchInput = driver.findElement(By.id("small-searchterms"));
            searchInput.clear();
            searchInput.sendKeys("book");
            driver.findElement(By.cssSelector("input.button-1.search-box-button")).click();
            Thread.sleep(1500);

            driver.findElement(By.linkText("Computing and Internet")).click();
            Thread.sleep(1500);

            driver.findElement(By.id("add-to-cart-button-13")).click();
            Thread.sleep(2000);

            System.out.println("Test scenario executed successfully.");
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
