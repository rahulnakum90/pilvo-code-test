package base;

import org.openqa.selenium.By;
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

    public void validateButtonFilters(List expectedHeaderList){

        List<String> actualFilterNames = driver.findElements(buttonFilters).stream().map(e -> e.getText()).collect(Collectors.toList());
        expectedHeaderList.stream().forEach(e -> Assert.assertTrue(actualFilterNames.contains(e), e+" - filter is not present"));
    }

    public List<String> getHeadersFromTable(String tableXpath){
        String headersXpath = tableXpath+"//tr//th";
        return driver.findElements(By.xpath(headersXpath)).stream().map(header -> header.getText().trim()).collect(Collectors.toList());
    }

    public void reliableClick(By locator){
        int MAX_TRY = 2;
        int retry = 0;
        while (retry<MAX_TRY){
            try {
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
