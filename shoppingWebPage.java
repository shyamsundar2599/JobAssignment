package com.test.PageObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.NoSuchElementException;

import org.apache.commons.collections4.bag.SynchronizedSortedBag;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import com.test.core.CoreTestFactory;

public class shoppingWebPage {
	Logger logger = Logger.getLogger("flipkartpage");
	JavascriptExecutor jse = (JavascriptExecutor) CoreTestFactory.getDriver();
	Wait<WebDriver> wait = new FluentWait<WebDriver>(CoreTestFactory.getDriver()).withTimeout(Duration.ofSeconds(30))
			.pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class);

	@FindBy(how = How.XPATH, using = "//input[@title='Search for products, brands and more']")
	@CacheLookup
	public static WebElement flipkartSearchInputTextBox;

	@FindBy(how = How.XPATH, using = "//input[@type = 'text'][@aria-label ='Search']")
	@CacheLookup
	public static WebElement amazonSearchInputTextBox;

	@FindBy(how = How.XPATH, using = "//button[@type = 'submit'] ")
	@CacheLookup
	public static WebElement btnFlipkartSearch;

	@FindBy(how = How.XPATH, using = "//input[@type = 'submit'][contains(@id,'search-submit-button')]")
	@CacheLookup
	public static WebElement btnAmazonSearch;

	public shoppingWebPage isPageDisplayed(Object... arg) throws Exception {
		try {
			Thread.sleep(3000);
			WebElement we = null;
			String page = arg[0].toString().trim();
			Wait<WebDriver> wait = new FluentWait<WebDriver>(CoreTestFactory.getDriver())
					.withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(2))
					.ignoring(NoSuchElementException.class);
			if (page.equalsIgnoreCase("Amazon")) {
				we = CoreTestFactory.getDriver().findElement(By.xpath("//a[@aria-label='Amazon']"));
			} else if (page.equalsIgnoreCase("Flipkart")) {

				we = CoreTestFactory.getDriver().findElement(By.xpath("//a//*[@title='Flipkart']"));
				if (CoreTestFactory.getDriver().findElements(By.xpath("//span[text()='Enter Email/Mobile number']"))
						.size() > 0) {
					CoreTestFactory.getDriver()
							.findElement(By.xpath("//button[text()='Request OTP']/../../../../../../..//button"))
							.click();
					Thread.sleep(2000);
				}
			}
//			we.isDisplayed();
			wait.until(ExpectedConditions.visibilityOf(we));
			logger.info("Pass: " + page + " Webpage has been loaded successfully");
			System.out.println("Pass: " + page + " Webpage has been loaded successfully");
		} catch (Exception e) {
			System.out.println("Exception Occurred in isPageDisplayed method due to following Exception : " + e);
		}
		return this;
	}

	public shoppingWebPage enterValue(Object... args) throws Exception {
		try {
			String field = args[0].toString().split(":=")[0].trim();
			String value = args[0].toString().split(":=")[1].trim();
			String app = args[1].toString().trim();

			switch (field.replace(" ", "").toUpperCase()) {
			case "SEARCHTEXTBOX":
				if (app.equalsIgnoreCase("flipkart")) {
					wait.until(ExpectedConditions.visibilityOf(flipkartSearchInputTextBox));
					flipkartSearchInputTextBox.sendKeys(value);
					Thread.sleep(3000);
				} else if (app.equalsIgnoreCase("amazon")) {
					wait.until(ExpectedConditions.visibilityOf(amazonSearchInputTextBox));
					amazonSearchInputTextBox.sendKeys(value);
				}
				
				break;

			default:
				logger.error("Fail: " + field + " option is not listed in the switch case options");
				System.out.println("Fail: " + field + " option is not listed in the switch case options");
			}

			logger.info("Pass: " + value + " value has been Entered successfully ");
			System.out.println("Pass: " + value + " value has been Entered successfully in "+field);
		} catch (Exception e) {
			logger.error("Error Occurred due to following Exception: " + e);
		}
		return this;
	}

	public shoppingWebPage performAction(Object... args) throws Exception {
		try {
			String action = args[0].toString().trim();
			

			switch (action.replace(" ", "").toUpperCase()) {
			case "SEARCH":
				String app = args[1].toString().trim();
				if (app.equalsIgnoreCase("flipkart")) {
					wait.until(ExpectedConditions.elementToBeClickable(btnFlipkartSearch));
					btnFlipkartSearch.click();
					Thread.sleep(3000);
				} else if (app.equalsIgnoreCase("amazon")) {
					wait.until(ExpectedConditions.elementToBeClickable(btnAmazonSearch));
					btnAmazonSearch.click();
				}
				System.out.println("Pass: Search button has been clicked");
				break;
			case "COMPAREPRICE":
				int flipkartPrice = 0;
				int amazonPrice = 0;
				FileInputStream fi = new FileInputStream(System.getProperty("user.dir") + File.separator + "src"
						+ File.separator + "test" + File.separator + "java" + File.separator + "resources"
						+ File.separator + "TestData.xlsx");
				XSSFWorkbook workbook = new XSSFWorkbook(fi);
				XSSFSheet sheet = workbook.getSheet("TestData");
				int rowCount = sheet.getLastRowNum();
				for (int i = 0; i <= rowCount; i++) {
					String key = sheet.getRow(i).getCell(0).getStringCellValue();
					if (key.toLowerCase().contains("flipkart")) {
						flipkartPrice = Integer.parseInt(sheet.getRow(i).getCell(1).getStringCellValue().replace(",", ""));
					} else if (key.toLowerCase().contains("amazon")) {
						amazonPrice = Integer.parseInt(sheet.getRow(i).getCell(1).getStringCellValue().replace(",", ""));
					} 
				}
				fi.close();
				if (amazonPrice > flipkartPrice) {
					System.out.println("Flipkart Website Product price is Cheaper than Amazon Website");
				} else if (amazonPrice < flipkartPrice) {
					System.out.println("Amazon Website Product price is Cheaper than Flipkart Website");
				} else {
					System.out.println("Both Amazon and Flipkart websites has the price");
				}
				break;
			default:
				logger.error("Fail: " + action + " option is not listed in the switch case options");
			}

			logger.info("Pass: " + action + " action has been performed in webpage");

		} catch (Exception e) {
			logger.error("Error Occurred due to following Exception: " + e);
		}
		return this;
	}

	public shoppingWebPage getData(Object... args) throws Exception {
		try {

			String dataField = args[0].toString().trim();
			String productName = args[1].toString().trim();
			String appName = args[2].toString().trim();

			switch (dataField.replace(" ", "").toUpperCase()) {
			case "PRICE":
				String xpath = null;
				if (appName.equalsIgnoreCase("flipkart")) {
					xpath = "//a//div[text()='" + productName + "']/../..//div[contains(text(),'₹')]";
				} else if (appName.equalsIgnoreCase("amazon")) {
					xpath = "//*[text()='" + productName + "']/../../../..//span[@class='a-price-whole']";
				}
				if (CoreTestFactory.getDriver().findElements(By.xpath(xpath)).size() > 0) {
					String price = null;
					if (appName.equalsIgnoreCase("flipkart")) {
						price = CoreTestFactory.getDriver().findElement(By.xpath(xpath)).getText().split("₹")[1];
					} else if (appName.equalsIgnoreCase("amazon")) {
						price = CoreTestFactory.getDriver().findElement(By.xpath(xpath)).getText();
					}

					FileInputStream fi = new FileInputStream(System.getProperty("user.dir") + File.separator + "src"
							+ File.separator + "test" + File.separator + "java" + File.separator + "resources"
							+ File.separator + "TestData.xlsx");
					XSSFWorkbook workbook = new XSSFWorkbook(fi);
					XSSFSheet sheet = workbook.getSheet("TestData");
					int rowCount = sheet.getLastRowNum();
					
					if(rowCount<2) {
						sheet.createRow(rowCount + 1).createCell(0).setCellValue("Price in " + appName);
						sheet.getRow(rowCount + 1).createCell(1).setCellValue(price);
					}else {
						for(int j =1; j<=rowCount; j++) {
							if(sheet.getRow(j).getCell(0).getStringCellValue().toLowerCase().contains(appName.toLowerCase())) {
								sheet.getRow(j).getCell(1).setCellValue(price);
								break;
							}
						}
					}
					System.out.println("Info: "+productName+" price in "+appName+" Website is Rs."+price);
					
					FileOutputStream fo = new FileOutputStream(System.getProperty("user.dir") + File.separator + "src"
							+ File.separator + "test" + File.separator + "java" + File.separator + "resources"
							+ File.separator + "TestData.xlsx");
					workbook.write(fo);
					workbook.close();
					fo.close();
				}

				break;

			default:
				logger.error("Fail: " + dataField + " option is not listed in the switch case options");
			}

			logger.info("Pass: " + dataField + " action has been performed in webpage");

		} catch (Exception e) {
			logger.error("Error Occurred due to following Exception: " + e);
		}
		return this;
	}

}