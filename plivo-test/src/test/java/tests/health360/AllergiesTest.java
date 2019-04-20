package tests.health360;

import base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.health360.AllergiesPage;
import utils.SearchMember;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
}
