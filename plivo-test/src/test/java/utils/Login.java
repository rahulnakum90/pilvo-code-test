package utils;

import base.BasePage;
import org.openqa.selenium.By;

public class Login extends BasePage {

    public By inputUsername = By.name("username");
    public By inputPassword = By.name("password");
    public By btnLogin = By.xpath("//button[@type='submit']");

    public void login(String user, String pwd) {
        driver.navigate().to(properties.getProperty("URL") + "login?");
        driver.findElement(inputUsername).sendKeys(user);
        driver.findElement(inputPassword).sendKeys(pwd);
        driver.findElement(btnLogin).click();
    }
}
