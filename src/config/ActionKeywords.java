package config;

import java.util.concurrent.TimeUnit;

import static executionEngine.DriverScript.OR;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import executionEngine.DriverScript;
import utility.Log;

public class ActionKeywords {
	
		public static WebDriver driver;
		ExtentReports report;
		static ExtentTest logger;
		private static SoftAssert softAssert = new SoftAssert();
		
	public void startTest(){
		report=new ExtentReports(Constants.PROJECT_PATH + "\\ExtentReport\\LearnAutomation.html");
	}
	
	public void startTestCase(String testCaseName){
		logger=report.startTest(testCaseName);
	}
			
	public static void openBrowser(String object,String data){		
		Log.info("Opening Browser");
		logger.log(LogStatus.INFO, "Opening Browser");
		try{				
			if(data.equals("Mozilla")){
				driver=new FirefoxDriver();
				Log.info("Mozilla browser started");				
				}
			else if(data.equals("IE")){
				//Dummy Code, Implement you own code
				driver=new InternetExplorerDriver();
				Log.info("IE browser started");
				}
			else if(data.equals("Chrome")){
				//Dummy Code, Implement you own code
				System.setProperty("webdriver.chrome.driver", "./libs/chromedriver.exe");
				driver=new ChromeDriver();
				Log.info("Chrome browser started");
				}
			
			int implicitWaitTime=(10);
			driver.manage().timeouts().implicitlyWait(implicitWaitTime, TimeUnit.SECONDS);
			logger.log(LogStatus.INFO, "Browser started");
		}catch (Exception e){
			Log.info("Not able to open the Browser --- " + e.getMessage());
			logger.log(LogStatus.ERROR, "Unable to open browser");
			DriverScript.bResult = false;
		}
	}
	
	public static void navigate(String object, String data){
		try{
			Log.info("Navigating to URL");
			logger.log(LogStatus.INFO, "Navigating to URL : "+ Constants.URL);
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.get(Constants.URL);
		}catch(Exception e){
			Log.info("Not able to navigate --- " + e.getMessage());
			logger.log(LogStatus.ERROR, "Unable to open URL"+ e.getMessage());
			DriverScript.bResult = false;
			}
		}
	
	public static void click(String object, String data){
		try{
			Log.info("Clicking on Webelement "+ object);
			logger.log(LogStatus.INFO, "Clicking on Webelement "+ object);
			driver.findElement(By.xpath(OR.getProperty(object))).click();
		 }catch(Exception e){
 			Log.error("Not able to click --- " + e.getMessage());
 			logger.log(LogStatus.ERROR, "Not able to click --- " + e.getMessage());
 			DriverScript.bResult = false;
         	}
		}
	
	public static void input(String object, String data){
		try{
			Log.info("Entering the text in " + object);
			logger.log(LogStatus.INFO, "Entering the text in " + object);
			driver.findElement(By.xpath(OR.getProperty(object))).sendKeys(data);
		 }catch(Exception e){
			 Log.error("Not able to Enter UserName --- " + e.getMessage());
			 logger.log(LogStatus.ERROR, "Not able to Enter UserName --- " + e.getMessage());
			 DriverScript.bResult = false;
		 	}
		}
	
	public static void getText(String object, String data){
		try{
			Log.info("Getting text : " + object);
			logger.log(LogStatus.INFO, "Getting text : " + object);
			String text = driver.findElement(By.xpath(OR.getProperty(object))).getText();
			logger.log(LogStatus.INFO, "text : " + text);
		//	softAssert.assertEquals(text, "some saved value");
			
		 }catch(Exception e){
			 Log.error("Not able to get text --- " + e.getMessage());
			 logger.log(LogStatus.ERROR, "Not able to get text --- " + e.getMessage());
			 DriverScript.bResult = false;
		 	}
		}
	
	

	public static void waitFor(String object, String data) throws Exception{
		try{
			Log.info("Wait for 5 seconds");
			logger.log(LogStatus.INFO, "Wait for 5 seconds");
			Thread.sleep(5000);
		 }catch(Exception e){
			 Log.error("Not able to Wait --- " + e.getMessage());
			 DriverScript.bResult = false;
         	}
		}

	public static void closeBrowser(String object, String data){
		try{
			Log.info("Closing the browser");
			logger.log(LogStatus.INFO, "Closing the browser");
			driver.quit();
		 }catch(Exception e){
			 Log.error("Not able to Close the Browser --- " + e.getMessage());
			 logger.log(LogStatus.ERROR, "Not able to Close the Browser --- " + e.getMessage());
			 DriverScript.bResult = false;
         	}
		}
	
	public void endTestCase(String testCaseName){
		report.endTest(logger);
	}
	
	public void endTest(){
		report.flush();
		System.setProperty("webdriver.chrome.driver", "./libs/chromedriver.exe");
		driver=new ChromeDriver();
		driver.get(Constants.PROJECT_PATH + "\\ExtentReport\\LearnAutomation.html");
	}

	}