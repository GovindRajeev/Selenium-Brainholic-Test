package assesment.Brainholic_;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import tests.TC_01_login;
import tests.TC_02_xllogin;

public class enrichminion_test {
    protected WebDriver driver;

    @BeforeClass
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://enrichminion.vercel.app/");
       // TC_01_login oTC_01_login = new TC_01_login(driver);
      //  oTC_01_login.testLogin();
        TC_02_xllogin oTC_02_xllogin = new TC_02_xllogin();
        oTC_02_xllogin.driver = driver;
         oTC_02_xllogin.initPage();
    }

    @AfterClass
    public void tearDown() {
        if (driver != null) {
        //    driver.quit();
        }
    }
}
