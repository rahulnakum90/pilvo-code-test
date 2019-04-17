package utils;

import base.BasePage;
import base.BaseTest;
import org.openqa.selenium.By;

public class SearchMember extends BasePage {
    public String searchMemberPageUrl = properties.getProperty("URL") +"/search/member";
    public By btnMainSearch = By.id("btnmainSearch");
    By inputMemberId = By.id("MemberID");
    public By btnSearchHeader = By.id("btnSearchHeader");
    By tableRows = By.xpath("//table/tbody/tr");
    public By linkMemberClick = By.xpath("//table/tbody/tr[1]/td[1]/div | //table/tbody/tr[1]/td[1]/a");

    public void searchMemberByMemberId(String memberId) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        driver.navigate().to(searchMemberPageUrl);
        driver.findElement(inputMemberId).sendKeys(memberId);
        driver.findElement(btnSearchHeader).click();
        driver.findElement(linkMemberClick).click();
        if(driver.findElements(tableRows).size()==0){
            Exception e =new Exception("No member found for memberID : "+memberId);
            try {
                throw e;
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

    }

    public void searchDefaultMember(){
        searchMemberByMemberId(properties.getProperty("MemberID"));
    }

}

