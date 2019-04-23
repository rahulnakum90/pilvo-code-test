package pages.health360;

import base.BasePage;
import org.openqa.selenium.By;

public class AllergiesPage extends BasePage {
    public By allergieLink = By.linkText("Allergies");
    public By inputAllergy = By.id("Allergy");
    public By selectInformationSource = By.id("InformationSource");
    public By linkFirstRecord = By.xpath("//table//a");
    public By linkCopyRecord = By.xpath("//*[text()=' Copy ']");
    public By btnBack = By.id("btnBack");
    public By btnContinuewithoutsaving = By.xpath("//span[text()='Continue without saving']");




    //public By
}
