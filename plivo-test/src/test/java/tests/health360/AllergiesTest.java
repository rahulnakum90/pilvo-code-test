package tests.health360;

import base.BasePage;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.SearchMember;

public class AllergiesTest extends BasePage {
    SearchMember searchMember;

    @BeforeClass
    public void setup(){
        searchMember =new SearchMember();
        searchMember.searchDefaultMember();
    }

    @Test
    public void validatePageControl(){

    }
}
