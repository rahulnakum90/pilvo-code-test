package utils;

import base.BasePage;
import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SearchMember extends BasePage {
    public String searchMemberPageUrl = properties.getProperty("URL") +"/search/member";
    public By btnMainSearch = By.id("btnmainSearch");
    By inputMemberId = By.id("MemberID");
    public By btnSearchHeader = By.id("btnSearchHeader");
    By tableRows = By.xpath("//table/tbody/tr");
    public By linkMemberClick = By.xpath("//table/tbody/tr[1]/td[1]/div | //table/tbody/tr[1]/td[1]/a");
    private WebDriver searchDriver;

    public SearchMember(WebDriver wd){
        searchDriver = wd;
    }
    public void searchMemberByMemberId(String memberId) {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        searchDriver.navigate().to(searchMemberPageUrl);
        searchDriver.findElement(inputMemberId).sendKeys(memberId);
        searchDriver.findElement(btnSearchHeader).click();
        searchDriver.findElement(linkMemberClick).click();
        if(searchDriver.findElements(tableRows).size()==0){
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

