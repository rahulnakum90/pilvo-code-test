package tests.health360;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.health360.MedicationsPage;
import utils.SearchMember;

import java.util.Arrays;
import java.util.List;

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
}
