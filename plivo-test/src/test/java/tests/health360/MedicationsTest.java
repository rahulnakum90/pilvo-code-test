package tests.health360;

import org.apache.commons.lang3.RandomStringUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.health360.MedicationsPage;
import utils.SearchMember;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unchecked")
public class MedicationsTest extends MedicationsPage {
    SearchMember searchMember;
    @BeforeClass
    public void setup(){
        searchMember =new SearchMember(driver);
        searchMember.searchDefaultMember();
        driver.findElement(medicationsLink).click();
    }

    @Test(priority = 1)
    public void validatePageControl(){
        //Validate Filters
        List<String> expectedFilters = Arrays.asList("Card","Table");
        validateButtonFilters(expectedFilters);
    }

    @Test(priority = 2)
    public void validateTableView(){
        List<String> expectedHeader = Arrays.asList("Drug Name, Strength, Dosage Form", "Status","Directions for Use","Prescriber");
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
            put("inputDrugName", "TestinputDrugName_"+ RandomStringUtils.randomAlphanumeric(5));
        }};
        reliableClick(btnTableView);
        int rowCountBeforeAdd = calculateRowCountInTable(singleTableXpath);
        reliableClick(btnNew);
        driver.findElement(inputDrugName).sendKeys(testData.get("inputDrugName"));
        reliableClick(btnSave);
        wait.until(ExpectedConditions.visibilityOfElementLocated(messageContainer));
        Assert.assertEquals(driver.findElement(messageContainer).getText().trim(),testData.get("inputDrugName"),"Saved message is not displayed");
        try{reliableClick(messageContainer);}catch (Exception e){}
        reliableClick(medicationsLink);
        reliableClick(btnTableView);
        Assert.assertTrue(driver.findElement(By.xpath(singleTableXpath)).getText().contains(testData.get("inputDrugName")));
        Assert.assertEquals(calculateRowCountInTable(singleTableXpath),rowCountBeforeAdd+1,"Table row count are not matched");
    }
    @Test(priority = 5)
    public void validateEditRecord(){
        Map<String,String> testData =new HashMap<String,String>() {{
            put("inputDrugName", "TestinputDrugName_"+ RandomStringUtils.randomAlphanumeric(5));
        }};
        reliableClick(medicationsLink);
        reliableClick(btnTableView);
        int rowCountBeforeAdd = calculateRowCountInTable(singleTableXpath);
        reliableClick(linkFirstRecord);
        clearInputFieldUsingJavaScript(driver.findElement(inputDrugName));
        driver.findElement(inputDrugName).sendKeys(testData.get("inputDrugName"));
        reliableClick(btnSave);
        wait.until(ExpectedConditions.visibilityOfElementLocated(messageContainer));
        Assert.assertEquals(driver.findElement(messageContainer).getText().trim(),testData.get("inputDrugName"),"Saved message is not displayed");
        try{reliableClick(messageContainer);}catch (Exception e){}
        reliableClick(medicationsLink);
        reliableClick(btnTableView);
        Assert.assertTrue(driver.findElement(By.xpath(singleTableXpath)).getText().contains(testData.get("inputDrugName")),"Edited value not found in table");
        Assert.assertEquals(calculateRowCountInTable(singleTableXpath),rowCountBeforeAdd,"Table row count are not matched");
    }

    @Test(priority = 6)
    public void validateCopyRecord(){
        Map<String,String> testData =new HashMap<String,String>() {{
            put("inputDrugName", "TestinputDrugName_"+ RandomStringUtils.randomAlphanumeric(5));
        }};
        reliableClick(medicationsLink);
        reliableClick(btnTableView);
        int rowCountBeforeAdd = calculateRowCountInTable(singleTableXpath);
        reliableClick(linkCopyRecord);
        clearInputFieldUsingJavaScript(driver.findElement(inputDrugName));
        driver.findElement(inputDrugName).sendKeys(testData.get("inputDrugName"));
        reliableClick(btnSave);
        wait.until(ExpectedConditions.visibilityOfElementLocated(messageContainer));
        Assert.assertEquals(driver.findElement(messageContainer).getText().trim(),testData.get("inputDrugName"),"Saved message is not displayed");
        try{reliableClick(messageContainer);}catch (Exception e){}
        reliableClick(medicationsLink);
        reliableClick(btnTableView);
        Assert.assertEquals(calculateRowCountInTable(singleTableXpath),rowCountBeforeAdd+1,"Copied Record row is not found");
    }

    @Test(priority = 7)
    public void validateDeleteRecord(){
        reliableClick(medicationsLink);
        reliableClick(btnTableView);
        int rowCountBeforeAdd = calculateRowCountInTable(singleTableXpath);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //reliableClick(linkFirstRecord);
        deleteRecord();
        System.out.println("Record is deleted");
        reliableClick(medicationsLink);
        reliableClick(btnTableView);
        Assert.assertEquals(calculateRowCountInTable(singleTableXpath),rowCountBeforeAdd-1,"Record row is not deleted");

    }

    @Test(priority = 8)
    public void validtaeGoBackWithoutSaving(){
        Map<String,String> testData =new HashMap<String,String>() {{
            put("inputDrugName", "TestinputDrugName_"+ RandomStringUtils.randomAlphanumeric(5));
        }};
        reliableClick(medicationsLink);
        reliableClick(btnTableView);
        //reliableClick(linkFirstRecord);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        clearInputFieldUsingJavaScript(driver.findElement(inputDrugName));
        driver.findElement(inputDrugName).sendKeys(testData.get("inputDrugName"));
        reliableClick(btnBack);
        reliableClick(btnContinuewithoutsaving);
        try{reliableClick(btnContinuewithoutsaving);}catch (Exception e){}
        Assert.assertEquals(driver.findElements(messageContainer).size(),0,driver.findElements(messageContainer)+"is displayed");

    }
}
