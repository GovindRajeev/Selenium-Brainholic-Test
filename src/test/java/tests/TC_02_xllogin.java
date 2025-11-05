package tests;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;
import org.testng.Assert;
import org.testng.annotations.*;
import assesment.Brainholic_.enrichminion_test;

public class TC_02_xllogin extends enrichminion_test {

    WebDriverWait wait;
    String inputFile = "./testdata/loginData.xlsx";
    String outputFile = "./testdata/loginReport.xlsx";

    @FindBy(xpath = "//input[@id='email']")
    private WebElement inputEmail;

    @FindBy(xpath = "//input[@id='password']")
    private WebElement inputPassword;

    @FindBy(xpath = "//button[text()='Login']")
    private WebElement buttonLogin;

    // ✅ No-argument constructor — required by TestNG
    public TC_02_xllogin() {}

    @BeforeClass
    public void initPage() {
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    @DataProvider(name = "loginData")
    public Object[][] getLoginData() throws Exception {
        FileInputStream fis = new FileInputStream(inputFile);
        XSSFWorkbook wb = new XSSFWorkbook(fis);
        XSSFSheet sheet = wb.getSheetAt(0);
        int rowCount = sheet.getLastRowNum();

        Object[][] data = new Object[rowCount][3];
        for (int i = 1; i <= rowCount; i++) {
            XSSFRow row = sheet.getRow(i);
            data[i - 1][0] = row.getCell(0).getStringCellValue();
            data[i - 1][1] = row.getCell(1).getStringCellValue();
            data[i - 1][2] = row.getCell(2).getStringCellValue();
        }
        wb.close();
        fis.close();
        return data;
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String email, String password, String expected) throws Exception {
        try {
            wait.until(ExpectedConditions.visibilityOf(inputEmail)).clear();
            inputEmail.sendKeys(email);
            wait.until(ExpectedConditions.visibilityOf(inputPassword)).clear();
            inputPassword.sendKeys(password);
            wait.until(ExpectedConditions.elementToBeClickable(buttonLogin)).click();

            Thread.sleep(2000);
            String currentURL = driver.getCurrentUrl();

            String actual = currentURL.contains("/dashboard") ? "Success" : "Fail";
            String status = expected.equalsIgnoreCase(actual) ? "PASS" : "FAIL";
            writeResult(email, password, expected, actual, status);

            Assert.assertEquals(actual, expected, "Login test failed for: " + email);

        } catch (Exception e) {
            writeResult(email, password, expected, "Error", "FAIL");
        }
    }

    public void writeResult(String email, String password, String expected, String actual, String status) {
        try {
            XSSFWorkbook wb;
            XSSFSheet sheet;
            java.io.File file = new java.io.File(outputFile);

            if (file.exists()) {
                FileInputStream fis = new FileInputStream(file);
                wb = new XSSFWorkbook(fis);
                sheet = wb.getSheetAt(0);
                fis.close();
            } else {
                wb = new XSSFWorkbook();
                sheet = wb.createSheet("Report");
                Row header = sheet.createRow(0);
                header.createCell(0).setCellValue("Email");
                header.createCell(1).setCellValue("Password");
                header.createCell(2).setCellValue("ExpectedResult");
                header.createCell(3).setCellValue("ActualResult");
                header.createCell(4).setCellValue("Status");
            }

            int rowCount = sheet.getLastRowNum() + 1;
            Row row = sheet.createRow(rowCount);
            row.createCell(0).setCellValue(email);
            row.createCell(1).setCellValue(password);
            row.createCell(2).setCellValue(expected);
            row.createCell(3).setCellValue(actual);
            row.createCell(4).setCellValue(status);

            FileOutputStream fos = new FileOutputStream(outputFile);
            wb.write(fos);
            fos.close();
            wb.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
