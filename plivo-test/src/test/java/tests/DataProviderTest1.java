package tests;

import base.BaseTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static utils.ExcelUtil.perpareDataProvider;

public class DataProviderTest1 extends BaseTest {

    @DataProvider(name="ContactData")
    public Object[] getContactData() throws IOException {
        List<Map<String, Object>> dataList = perpareDataProvider(System.getProperty("user.dir") + properties.getProperty("TestDataExcel"), "Contacts");
        return dataList.toArray();
    }

    @Test(dataProvider = "ContactData")
    public void createNewContact(Map<String,Object> dataMap) throws IOException {
        System.out.println("first Name - " + dataMap.get("FirstName"));

    }
}
