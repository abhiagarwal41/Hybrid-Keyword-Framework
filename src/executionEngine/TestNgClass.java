package executionEngine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Properties;

import org.apache.log4j.xml.DOMConfigurator;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import config.ActionKeywords;
import config.Constants;
import utility.ExcelUtils;
import utility.Log;

public class TestNgClass {
	
	public static Properties OR;
	public static ActionKeywords actionKeywords;
	public static String sActionKeyword;
	public static String sPageObject;
	public static Method method[];
		
	public static int iTestStep;
	public static int iTestLastStep;
	public static String sTestCaseID;
	public static String sRunMode;
	public static String sData;
	public static boolean bResult;
	
	public TestNgClass() throws Exception{
		actionKeywords = new ActionKeywords();
		actionKeywords.startTest();
		method = actionKeywords.getClass().getMethods();	
		ExcelUtils.setExcelFile(Constants.Path_TestData);
    	DOMConfigurator.configure("log4j.xml");
		String Path_OR = Constants.Path_OR;
		FileInputStream fs = new FileInputStream(Path_OR);
		OR= new Properties(System.getProperties());
		OR.load(fs);
	}
	
	@Test
	public void LoginTest() throws Exception{
		sTestCaseID="LogIn_01";
		executeTestCase();
		//assert anything 
				
	}
	
	@AfterTest
	public void finishTest(){
		actionKeywords.endTest();
	}
	
	private void executeTestCase() throws Exception{
		actionKeywords.startTestCase(sTestCaseID);
		iTestStep = ExcelUtils.getRowContains(sTestCaseID, Constants.Col_TestCaseID, Constants.Sheet_TestSteps);
		iTestLastStep = ExcelUtils.getTestStepsCount(Constants.Sheet_TestSteps, sTestCaseID, iTestStep);
		bResult=true;
		for (;iTestStep<iTestLastStep;iTestStep++){
    		sActionKeyword = ExcelUtils.getCellData(iTestStep, Constants.Col_ActionKeyword,Constants.Sheet_TestSteps);
    		sPageObject = ExcelUtils.getCellData(iTestStep, Constants.Col_PageObject, Constants.Sheet_TestSteps);
    		sData = ExcelUtils.getCellData(iTestStep, Constants.Col_DataSet, Constants.Sheet_TestSteps);
    		execute_Actions();
			if(bResult==false){
				actionKeywords.endTestCase(sTestCaseID);
				break;
				}						
			}
		if(bResult==true){
		actionKeywords.endTestCase(sTestCaseID);
			}			
	}
	
	private static void execute_Actions() throws Exception {
		
		for(int i=0;i<method.length;i++){
			
			if(method[i].getName().equals(sActionKeyword)){
				method[i].invoke(actionKeywords,sPageObject, sData);
				if(bResult==false)
					ActionKeywords.closeBrowser("","");
				break;
					
				}
		
		}
	}

}
