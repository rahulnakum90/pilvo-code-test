package tests.health360;

import base.BasePage;
import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
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

    @Test(priority = 1)
    public void validatePageControl(){
        //Validate Filters
        List<String> expectedFilters = Arrays.asList("All", "Card","Table");
        validateButtonFilters(expectedFilters);
    }

    @Test(priority = 2)
    public void validateTableView(){
        List<String> expectedHeader = Arrays.asList("Allergy", "Type","Action");
        reliableClick(btnTableView);
        //Validate if table displayed
        Assert.assertEquals(driver.findElements(By.xpath(singleTableXpath)).size(),1,"Table not displayed");
        expectedHeader.stream().forEach(e -> Assert.assertTrue(getHeadersFromTable(singleTableXpath).contains(e), e+" - Header is not present"));
    }

    @Test(priority = 3)
    public void validateCardView(){
        reliableClick(btnCardView);
        Assert.assertEquals(driver.findElements(tagCardView).size(),1,"Card view not displayed");
    }

    @Test(priority = 4)
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(messageContainer));
        Assert.assertEquals(driver.findElement(messageContainer).getText().trim(),"Saved!","Saved message is not displayed");
        try{reliableClick(messageContainer);}catch (Exception e){}
        reliableClick(allergieLink);
        reliableClick(btnTableView);
        Assert.assertTrue(driver.findElement(By.xpath(singleTableXpath)).getText().contains(testData.get("inputAllergy")));
        Assert.assertEquals(calculateRowCountInTable(singleTableXpath),rowCountBeforeAdd+1,"Table row count are not matched");
    }

    @Test(priority = 5)
    public void validateEditRecord(){
        Map<String,String> testData =new HashMap<String,String>() {{
            put("inputAllergy", "TestAllergy_"+ RandomStringUtils.randomAlphanumeric(5));
        }};
        reliableClick(allergieLink);
        reliableClick(btnTableView);
        int rowCountBeforeAdd = calculateRowCountInTable(singleTableXpath);
        reliableClick(linkFirstRecord);
        clearInputFieldUsingJavaScript(driver.findElement(inputAllergy));
        driver.findElement(inputAllergy).sendKeys(testData.get("inputAllergy"));
        reliableClick(btnSave);
        wait.until(ExpectedConditions.visibilityOfElementLocated(messageContainer));
        Assert.assertEquals(driver.findElement(messageContainer).getText().trim(),"Saved!","Saved message is not displayed");
        try{reliableClick(messageContainer);}catch (Exception e){}
        reliableClick(allergieLink);
        reliableClick(btnTableView);
        Assert.assertTrue(driver.findElement(By.xpath(singleTableXpath)).getText().contains(testData.get("inputAllergy")),"Edited value not found in table");
        Assert.assertEquals(calculateRowCountInTable(singleTableXpath),rowCountBeforeAdd,"Table row count are not matched");
    }

    @Test(priority = 6)
    public void validateCopyRecord(){
        reliableClick(allergieLink);
        reliableClick(btnTableView);
        int rowCountBeforeAdd = calculateRowCountInTable(singleTableXpath);
        reliableClick(linkCopyRecord);
        reliableClick(btnSave);
        wait.until(ExpectedConditions.visibilityOfElementLocated(messageContainer));
        Assert.assertEquals(driver.findElement(messageContainer).getText().trim(),"Saved!","Saved message is not displayed");
        try{reliableClick(messageContainer);}catch (Exception e){}
        reliableClick(allergieLink);
        reliableClick(btnTableView);
        Assert.assertEquals(calculateRowCountInTable(singleTableXpath),rowCountBeforeAdd+1,"Copied Record row is not found");
    }
    @Test(priority = 7)
    public void validateDeleteRecord(){
        reliableClick(allergieLink);
        reliableClick(btnTableView);
        int rowCountBeforeAdd = calculateRowCountInTable(singleTableXpath);
        reliableClick(linkFirstRecord);
        deleteRecord();
        System.out.println("Record is deleted");
        reliableClick(allergieLink);
        reliableClick(btnTableView);
        Assert.assertEquals(calculateRowCountInTable(singleTableXpath),rowCountBeforeAdd-1,"Record row is not deleted");

    }
    @Test(priority = 8)
    public void validtaeGoBackWithoutSaving(){
        Map<String,String> testData =new HashMap<String,String>() {{
            put("inputAllergy", "TestAllergy_"+ RandomStringUtils.randomAlphanumeric(5));
        }};
        reliableClick(allergieLink);
        reliableClick(btnTableView);
        reliableClick(linkFirstRecord);
        clearInputFieldUsingJavaScript(driver.findElement(inputAllergy));
        driver.findElement(inputAllergy).sendKeys(testData.get("inputAllergy"));
        reliableClick(btnBack);
        reliableClick(btnContinuewithoutsaving);
        try{reliableClick(btnContinuewithoutsaving);}catch (Exception e){}
        Assert.assertEquals(driver.findElements(messageContainer).size(),0,driver.findElements(messageContainer)+"is displayed");

    }


}
