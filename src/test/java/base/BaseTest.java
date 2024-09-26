package base;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.asserts.SoftAssert;

import java.time.Duration;

public class BaseTest {

    public WebDriver driver;
    SoftAssert sa = new SoftAssert();

    @BeforeMethod
    public WebDriver setUp() {

        // get target browser from CLI if given, otherwise use "chrome"
        String browserName = System.getProperty("browser") != null ? System.getProperty("browser") : "chrome";

        if (browserName.contains("chrome")) {
            ChromeOptions options = new ChromeOptions();
            if (browserName.contains("headless")){  // if headless param given on CLI
                options.addArguments("headless");       // then run test in headless mode
            }
            WebDriverManager.chromedriver().setup();
            driver = new ChromeDriver(options);
        } else if (browserName.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();

        } else if (browserName.equalsIgnoreCase("edge")) {
            driver = new EdgeDriver();
        }
        driver.manage().window().setSize(new Dimension(1440, 900));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));  // basic implicit wait

        return driver;
    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

    public void goToURL(String url) {
        driver.get(url);
    }


}
