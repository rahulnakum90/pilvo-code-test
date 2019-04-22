package base;

import com.gargoylesoftware.htmlunit.javascript.background.JavaScriptExecutor;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;
import java.util.stream.Collectors;

public class BasePage extends  BaseTest {

    public By buttonFilters = By.xpath("//*[@class='component-filter-container']//button");
    public By btnTableView = By.xpath("//mat-button-toggle[@value='Table']//button");
    public By btnCardView = By.xpath("//mat-button-toggle[@value='Card']//button");
    public String singleTableXpath = "//table";
    public By tagCardView = By.xpath("//*[@class='list']");
    public By btnNew = By.id("btnAddNew");
    public By btnSave = By.id("btnSave");
    public By messageContainer = By.xpath("//div[@id='toast-container']//div[@role='alertdialog']");


    public void validateButtonFilters(List expectedHeaderList){

        List<String> actualFilterNames = driver.findElements(buttonFilters).stream().map(e -> e.getText()).collect(Collectors.toList());
        expectedHeaderList.stream().forEach(e -> Assert.assertTrue(actualFilterNames.contains(e), e+" - filter is not present"));
    }

    public List<String> getHeadersFromTable(String tableXpath){
        String headersXpath = tableXpath+"//tr//th";
        return driver.findElements(By.xpath(headersXpath)).stream().map(header -> header.getText().trim()).collect(Collectors.toList());
    }

    public int calculateRowCountInTable(String tableXpath){
        String rowXpath = tableXpath+"//tbody//tr";
        return driver.findElements(By.xpath(rowXpath)).size();

    }

    public void clearInputFieldUsingJavaScript(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor)driver;
        js.executeScript("arguments[0].value='';", element);
    }



    public void reliableClick(By locator){
        int MAX_TRY = 3;
        int retry = 0;
        while (retry<MAX_TRY){
            try {
                try{Thread.sleep(500);}catch (Exception e){}
                wait.until(ExpectedConditions.elementToBeClickable(locator));
                driver.findElement(locator).click();
                break;
            }
            catch (Exception e){
                retry++;
                if(retry==MAX_TRY){
                        throw e;
                }
            }
        }



    }


}
