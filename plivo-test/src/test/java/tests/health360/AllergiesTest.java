package tests.health360;

import base.BasePage;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.health360.AllergiesPage;
import utils.SearchMember;

import java.util.*;
import java.util.stream.Collectors;

public class AllergiesTest extends AllergiesPage {
    SearchMember searchMember;
    @BeforeClass
    public void setup(){
        searchMember =new SearchMember(driver);
        searchMember.searchDefaultMember();
        driver.findElement(allergieLink).click();
    }

    @Test
    public void validatePageControl(){
        //Validate Filters
        List<String> expectedFilters = Arrays.asList("All", "Card","Table");
        validateButtonFilters(expectedFilters);
    }

    @Test
    public void validateTableView(){
        List<String> expectedHeader = Arrays.asList("Allergy", "Type","Action");
        reliableClick(btnTableView);
        //Validate if table displayed
        Assert.assertEquals(driver.findElements(By.xpath(singleTableXpath)).size(),1,"Table not displayed");
        expectedHeader.stream().forEach(e -> Assert.assertTrue(getHeadersFromTable(singleTableXpath).contains(e), e+" - Header is not present"));
    }

    @Test
    public void validateCardView(){
        reliableClick(btnCardView);
        Assert.assertEquals(driver.findElements(tagCardView).size(),1,"Card view not displayed");
    }

    @Test
    public void validateAddRecord(){
        Map<String,String> testData =new HashMap<String,String>() {{
            put("inputAllergy", "TestAllergy_"+ RandomStringUtils.randomAlphanumeric(5));
        }};
        reliableClick(btnTableView);
        int rowCountBeforeAdd = calculateRowCountInTable(singleTableXpath);

        reliableClick(btnNew);
        driver.findElement(inputAllergy).sendKeys(testData.get("inputAllergy"));
        new Select(driver.findElement(selectInformationSource)).selectByIndex(1);
        reliableClick(btnSave);
        Assert.assertEquals(driver.findElement(messageContainer).getText().trim(),"Saved!","Saved message is not displayed");
        try{reliableClick(messageContainer);}catch (Exception e){}
        reliableClick(allergieLink);
        reliableClick(btnTableView);
        Assert.assertTrue(driver.findElement(By.xpath(singleTableXpath)).getText().contains(testData.get("inputAllergy")));
        Assert.assertEquals(calculateRowCountInTable(singleTableXpath),rowCountBeforeAdd+1,"Table row count are not matched");


    }
}
