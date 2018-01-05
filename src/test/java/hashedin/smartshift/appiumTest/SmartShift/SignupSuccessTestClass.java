package hashedin.smartshift.appiumTest.SmartShift;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.*;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.swing.*;

import static org.junit.Assert.assertNotNull;


public class SignupSuccessTestClass {

    public SignupSuccessTestClass(WebDriver driver, Logger logger) throws Exception {
        super();
        testOtpSuccessfulySent(driver, logger);
        testVerifyOtpSuccess(driver, logger);
        testSignupSuccess(driver, logger);
        testLoginSuccess(driver, logger);
        testRegistrationSuccess(driver, logger);
        //register page
    }

    @Test
    public void testOtpSuccessfulySent(WebDriver driver, Logger logger) throws Exception{
         takeScreenShotUsingSelenium(driver);
        JSONParser parser= new JSONParser();
        Object obj = parser.parse(new FileReader(Constants.JSON_PATH));

        JSONObject superJsonObject = (JSONObject) obj;

        JSONObject subJsonObj= (JSONObject) superJsonObject.get("PhoneNumber") ;
        Object validNumber= (Object) subJsonObj.get("Valid");
       // Object invalidNumber= subJsonObj.get("Invalid");


        List<WebElement> textFields = driver.findElements(By.className(Constants.OtpTextField));
        textFields.get(0).sendKeys((String)validNumber); //input these data into json file
        takeScreenShotUsingSelenium(driver);
        driver.findElement(By.xpath(Constants.SendOtpButton)).click();
        logger.info("Clicked on Send OTP");
        WebDriverWait wait = new WebDriverWait(driver, 90);
        wait.until(ExpectedConditions.presenceOfElementLocated(By
                .xpath(Constants.VerifyOtpHeader)));
        WebElement result= (WebElement) driver.findElement(By.
                xpath("//android.widget.TextView[@text='Resend OTP']"));
        assertNotNull(result);
        logger.info("Successfully ran testOtpSuccessfulySent testcase");
    }

    public void testVerifyOtpSuccess(WebDriver driver, Logger logger) throws Exception{
        //update the following code to accept input from user
      takeScreenShotUsingSelenium(driver);
        logger.info("Entering testVerifyOtpSuccess testcase");
        System.out.println("Enter OTP");
        String otpVal = JOptionPane.showInputDialog(null,"Enter OTP"); //To create window
        List<WebElement> textFields = driver.findElements(By.className(Constants.OtpTextField));
        textFields.get(0).sendKeys(otpVal);
        driver.findElement(By.xpath(Constants.SUBMIT_BUTTON)).click();
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(Constants.SUBMIT_BUTTON)));
        takeScreenShotUsingSelenium(driver);
//        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[(@text='Register') or (@text='Book a Truck')]")));
        WebElement result= (WebElement) driver.findElement(By.
                xpath("//android.widget.TextView[(@text='Register') or (@text='Book a Truck')]"));
        assertNotNull(result);
        logger.info("Successfully verified OTP");
    }

    public void testLoginSuccess(WebDriver driver, Logger logger) throws Exception{
        WebElement result= (WebElement) driver.findElement(By.
                xpath("//android.widget.TextView[@text='Book a Truck']"));
        takeScreenShotUsingSelenium(driver);
        assertNotNull(result);
        logger.info("Successfully Signed In");

    }

    public void testSignupSuccess(WebDriver driver, Logger logger) throws Exception{
        takeScreenShotUsingSelenium(driver);
        WebDriverWait wait = new WebDriverWait(driver, 60);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//android.widget.TextView[@text='Register']")));
        logger.info("On Signup Page");
    }

    @Test
    private void testRegistrationSuccess(WebDriver driver, Logger logger) {
        //fill registration form
        List<WebElement> textFields = driver.findElements(By.className(Constants.OtpTextField));
        textFields.get(0).sendKeys("Test user");
        textFields.get(1).sendKeys("test@hashedin.com");



    }
     public static  int counter=0;
    public void takeScreenShotUsingSelenium(WebDriver driver) throws IOException {

        TakesScreenshot scrnShot = (TakesScreenshot)driver;
        File SrcFile=scrnShot.getScreenshotAs(OutputType.FILE);
        //Move image file to new destination
        File destFile=new File(Constants.SCREENSHOT_PATH + (counter+1)+".jpg");

        destFile.createNewFile();
        counter++;
        //Copy file at destination
        FileUtils.copyFile(SrcFile, destFile);


    }



}


