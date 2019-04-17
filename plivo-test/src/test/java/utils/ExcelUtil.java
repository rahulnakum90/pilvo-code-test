package utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class ExcelUtil {

    public static Sheet readExcel(String fileName, String sheetName) throws IOException {
        try{
        FileInputStream excelFile = new FileInputStream(new File(fileName));
        XSSFWorkbook workbook = new XSSFWorkbook(excelFile);
        Sheet sheet = workbook.getSheet(sheetName);
        return sheet;}
        catch(Exception e){return null;}
    }

    public static List<Map<String,Object>> convertExcelToList(Sheet sheet){
        List<Map<String,Object>> testDataList = new ArrayList<Map<String,Object>>();  //to store all records
        Set<Map<String, String>> headers = new HashSet<>();  //to store header name and position in excel sheet
        int rowCount = sheet.getLastRowNum();  //number of records

        //read headers
        Row headerRow = sheet.getRow(0);  //header row
        for(int i=0;i<headerRow.getLastCellNum();i++){
            Cell cell = headerRow.getCell(i);
            Map<String,String> mapCell = new HashMap<>();

            mapCell.put("name",cell.getStringCellValue());
            mapCell.put("index", String.valueOf(i));
            headers.add(mapCell);

        }

        //loop for each row
        for(int currentRow=1;currentRow<=rowCount;currentRow++){
            Map<String,Object> testData = new HashMap<>();
            Row row = sheet.getRow(currentRow);  //current row

            //loop for every column in a row
            for(Map<String,String> currentHeader : headers){  // loop through columns to prepare testdata map
                String key;
                String value;
                key = currentHeader.get("name");
                value = row.getCell(Integer.parseInt(currentHeader.get("index"))).getStringCellValue();
                testData.put(key,value);
            }

            testDataList.add(testData);
        }


        return testDataList;
    }

    public static List<Map<String,Object>> perpareDataProvider(String fileName, String sheetName) throws IOException {
        return convertExcelToList(readExcel(fileName,sheetName));
    }
}
