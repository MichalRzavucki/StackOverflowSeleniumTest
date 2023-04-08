import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;

public class StackOverflowCheck {
    WebDriver webDriver;

    @BeforeTest
    public void setup() {
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
        webDriver.manage().window().maximize();
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
    }

    @Test
    public void stackOverflowCheck() {
        webDriver.get("https://stackoverflow.com/");
        WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));

        WebElement inputField = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("q")));
        inputField.sendKeys("expected con");
        inputField.sendKeys(Keys.ENTER);

        String h1 = webDriver.findElement(By.tagName("h1")).getText();
        Assert.assertEquals(h1, "Human verification");

        String signUpButtonPath = "//a[@data-ga='[\"sign up\",\"Sign Up Navigation\",\"Header\",null,null]']";
        WebElement signUpButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(signUpButtonPath)));
        signUpButton.click();

        WebElement googleButton = webDriver.findElement(By.xpath("//button[@data-provider='google']"));
        String googleButtonText = googleButton.getAttribute("data-provider");

        Assert.assertEquals(googleButtonText, "google");

    }

    @AfterTest
    public void teardown() {
        webDriver.quit();
    }

}
