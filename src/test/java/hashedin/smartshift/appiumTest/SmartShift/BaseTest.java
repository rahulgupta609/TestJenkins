package hashedin.smartshift.appiumTest.SmartShift;

import java.net.MalformedURLException ;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.URL;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.IOException;

public class BaseTest {
    WebDriver driver;
    //logger instance
    public static final Logger logger = Logger.getLogger("MyLog");

    @Before
    public void setup()throws MalformedURLException{
    	System.out.println("In SetUp() Method");
        DesiredCapabilities capabilities=new DesiredCapabilities();
        capabilities.setCapability("device","Android");
        capabilities.setCapability("deviceName","Android");
        capabilities.setCapability("platformName","Android");
        try {
            FileUtils.cleanDirectory(new File(Constants.SCREENSHOT_DIRECTORY_PATH));
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(Constants.APK_PATH, Constants.APK_NAME);
        capabilities.setCapability("app",file.getAbsolutePath());

        logger.info("Creating driver Instance");
        driver = new RemoteWebDriver(new URL(Constants.url),capabilities);
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);

        // file to include logs
        FileHandler fh;
        try {
            // This block configure the logger with handler and formatter
            fh = new FileHandler(Constants.logFilePath, false);
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @After
    public void tearDown(){
//        driver.quit();
    }

    @Test
    public void testSuite() throws Exception{
        System.out.println("In BaseTest class Test Suite");
        //signupsuccess
        //signup failure
        //loginsuccess
        //loginfailure

        new SignupSuccessTestClass(driver, logger);
        //new SignupFailureTestClass(driver, logger); file exits, logic to be added

    }

}
