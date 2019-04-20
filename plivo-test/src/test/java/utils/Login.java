package utils;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

public class Login extends BasePage {

    public By inputUsername = By.name("username");
    public By inputPassword = By.name("password");
    public By btnLogin = By.xpath("//*[text()=' Log In ']/parent::button[@type='submit']");
    private By homePageTitle = By.xpath("//*[text()='My Dashboard']");
    public By defaultRole = By.xpath("//*[text()=' Default-Configuration - Default-Configuration ']/parent::button");
    private WebDriver loginDriver;

    public Login(WebDriver wd){
        loginDriver = wd;
    }

    public void login(String user, String pwd) {
        loginDriver.navigate().to(properties.getProperty("URL") + "login?");
        loginDriver.findElement(inputUsername).sendKeys(user);
        loginDriver.findElement(inputPassword).sendKeys(pwd);
        loginDriver.findElement(btnLogin).click();
        wait = new WebDriverWait(loginDriver,10);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(btnLogin));
        //select role if displayed
        if(loginDriver.getCurrentUrl().contains("role-select")){
            loginDriver.findElement(defaultRole).click();
        }

        Assert.assertTrue(loginDriver.findElements(btnLogin).size()==0, "Login not successful");
    }
}
