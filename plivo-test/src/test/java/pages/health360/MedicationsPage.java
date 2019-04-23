package pages.health360;

import base.BasePage;
import org.openqa.selenium.By;

public class MedicationsPage extends BasePage {
    public By medicationsLink = By.linkText("Medications");
    //public By inputDrugName = By.xpath("//*[@class='form-control ng-pristine ng-valid ng-touched']");
    public By inputDrugName = By.xpath("//label[contains(text(),'Drug Name')]/../..//input");
    //*[contains(@class,'form-control') and contains(@role='combobox')]



}
