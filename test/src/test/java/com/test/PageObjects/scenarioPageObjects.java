package com.test.PageObjects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;

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

public class scenarioPageObjects {
	Logger logger = Logger.getLogger("scenarioPageObjects");
	JavascriptExecutor jse = (JavascriptExecutor) CoreTestFactory.getDriver();
	Wait<WebDriver> wait = new FluentWait<WebDriver>(CoreTestFactory.getDriver()).withTimeout(Duration.ofSeconds(30))
			.pollingEvery(Duration.ofSeconds(1)).ignoring(NoSuchElementException.class);

	@FindBy(how = How.XPATH, using = "//gui-select[contains(@class,'gui-select')]")
	@CacheLookup
	public static WebElement gridRowsDropdown;

	public scenarioPageObjects enterValue(Object... args) throws Exception {
		try {

			String userEnteredValue = args[0].toString().trim();
			wait.until(ExpectedConditions.visibilityOf(gridRowsDropdown));
			gridRowsDropdown.click();
			WebElement we = CoreTestFactory.getDriver().findElement(
					By.xpath("//*[@class='gui-option'][normalize-space(text())='" + userEnteredValue + "']"));
			Thread.sleep(3000);
//			wait.until(ExpectedConditions.visibilityOf(we));
//			wait.until(ExpectedConditions.elementToBeClickable(we));
			we.click();
			if (CoreTestFactory.getDriver().findElement(By.xpath("//*[@class='gui-select-value']")).getText().trim()
					.equalsIgnoreCase(userEnteredValue)) {
				logger.info("Pass: " + userEnteredValue + " value has been successfully selected in dropdown");
				System.out.println("Pass: " + userEnteredValue + " value has been successfully selected in dropdown");
			} else {
				logger.error("Fail: " + userEnteredValue + " value is not selected in dropdown");
				System.out.println("Fail: " + userEnteredValue + " value is not selected in dropdown");
			}
		} catch (Exception e) {
			logger.error("Error Occurred due to following Exception: " + e);
			System.out.println("Error Occurred due to following Exception: " + e);
		}
		return this;
	}

	public scenarioPageObjects verifyData(Object... arg) throws Exception {
		try {
			String verifyInput = arg[0].toString().trim();
			String choice = arg[1].toString().trim();
			switch (choice.replace(" ", "").toUpperCase()) {
			case "GRIDROWCOUNT":

				WebElement we = null;
				String ActualGridRowCountUI = CoreTestFactory.getDriver()
						.findElement(By.xpath("(//*[@class = 'gui-structure-info-panel']//div//div//b)[1]")).getText();
//				Thread.sleep(1000);
				if (ActualGridRowCountUI.equals(verifyInput)) {
					System.out.println("Pass: " + ActualGridRowCountUI + " Rows has been displayed in the Grid");
					logger.info("Pass: " + ActualGridRowCountUI + " Rows has been displayed in the Grid");
				} else {
					
					logger.error("Expected: " + verifyInput + " Rows has been displayed in the Grid");
					logger.error("Fail: " + ActualGridRowCountUI + " Rows has been displayed in the Grid");
					System.out.println("Fail: " + ActualGridRowCountUI + " Rows has been displayed in the Grid");
				}
				break;
			default:
				logger.error("Fail: " + choice + " option is not listed in the switch case options");

			}
		} catch (Exception e) {
			logger.error("Error Occurred due to following Exception: " + e);
		}
		return this;
	}

	public scenarioPageObjects reportCreation(Object... arg) throws Exception {
		try {
			String reportFields = arg[0].toString().trim();
			String[] fields = reportFields.split("#");
			String Condition = arg[1].toString().trim();
			int ColumnNumber = 0;
			jse.executeScript("window.scrollTo(0,0)");
			switch (Condition.split(":=")[0].toUpperCase().replace(" ", "")) {

			case "STATUS":
				FileInputStream fi = new FileInputStream(
						System.getProperty("user.dir") + File.separator + "src" +File.separator+ "test" +File.separator+ "java" +File.separator+ "resources" +File.separator+ "outputFile.xlsx");
				XSSFWorkbook workbook = new XSSFWorkbook(fi);
				workbook.removeSheetAt(0);
				XSSFSheet sheet = workbook.createSheet("report");
				Row row = sheet.createRow(0);
				for (int cell = 0; cell < fields.length; cell++) {
					row.createCell(cell).setCellValue(fields[cell]);
				}

				FileOutputStream fo = new FileOutputStream(
						System.getProperty("user.dir") + File.separator + "src" +File.separator+ "test" +File.separator+ "java" +File.separator+ "resources" +File.separator+ "outputFile.xlsx");
				workbook.write(fo);

				String cellValue = null;
				String ActualGridRowCountUI = CoreTestFactory.getDriver()
						.findElement(By.xpath("(//*[@class = 'gui-structure-info-panel']//div//div//b)[1]")).getText();
				for (int i = 1; i <= Integer.parseInt(ActualGridRowCountUI); i++) {
					try {
						int elementCount = CoreTestFactory.getDriver()
								.findElements(By.xpath("//*[@class='gui-cell-number'][normalize-space(text())=" + i
										+ "]/../../..//div[normalize-space(text())='" + Condition.split(":=")[1].trim()
										+ "']"))
								.size();

						if (elementCount > 0) {
							fi = new FileInputStream(
									System.getProperty("user.dir") + File.separator + "src" +File.separator+ "test" +File.separator+ "java" +File.separator+ "resources" +File.separator+ "outputFile.xlsx");
							workbook = new XSSFWorkbook(fi);
							sheet = workbook.getSheet("report");
							int rowIndex = sheet.getLastRowNum() + 1;
							row = sheet.createRow(rowIndex);
							WebElement we1 = CoreTestFactory.getDriver()
									.findElement(By.xpath("//*[@class='gui-cell-number'][normalize-space(text())=" + i
											+ "]/../../..//div[normalize-space(text())='"
											+ Condition.split(":=")[1].trim() + "']"));
							jse.executeScript("arguments[0].scrollIntoView(true)", we1);

							for (int cell = 0; cell < fields.length; cell++) {

								int TotalColumnCount = CoreTestFactory.getDriver()
										.findElements(By.xpath("//*[@class='gui-header-title']")).size();

								for (int j = 1; j <= TotalColumnCount; j++) {

									if (fields[cell].trim().equalsIgnoreCase(CoreTestFactory.getDriver()
											.findElement(By.xpath("(//*[@class='gui-header-title'])[" + j + "]//div"))
											.getText())) {
										ColumnNumber = j;

										break;
									}
								}

								if (fields[cell].trim().equalsIgnoreCase("Project Name")
										|| fields[cell].trim().equalsIgnoreCase("Project ID")) {

									cellValue = CoreTestFactory.getDriver()
											.findElement(By.xpath(
													"((//*[@class='gui-cell-number'][normalize-space(text())=" + i
															+ "]/../../..//div[@class='gui-cell gui-structure-cell'])["
															+ ColumnNumber + "]//span)[2]"))
											.getText();
									row.createCell(cell).setCellValue(cellValue);
								} else if (fields[cell].trim().equalsIgnoreCase("Progress")) {
									cellValue = CoreTestFactory.getDriver()
											.findElement(By
													.xpath("(//*[@class='gui-cell-number'][normalize-space(text())=" + i
															+ "]/../../..//div[@class='gui-cell gui-structure-cell'])["
															+ ColumnNumber + "]//gui-percentage-view"))
											.getText().trim();
									row.createCell(cell).setCellValue(cellValue);
								}
							}
							fo = new FileOutputStream(
									System.getProperty("user.dir") + File.separator + "src" +File.separator+ "test" +File.separator+ "java" +File.separator+ "resources" +File.separator+ "outputFile.xlsx");
							workbook.write(fo);
							fo.close();
						}
					} catch (Exception e) {
						if (i != Integer.parseInt(ActualGridRowCountUI)) {
							continue;
						}
					}
				}

				fo.close();
				logger.info("Pass: Report generated successfully");
				System.out.println("Pass: Report generated successfully for the Required condition");
				System.out.println("!!You can find the report outputFile.xlsx in resources folder");
				break;
			default:
				logger.error("Fail: " + Condition.split(":=")[0] + " option is not listed in the switch case options");
			}
		} catch (Exception e) {
			logger.error("The Following Exception occurred in report creation method - " + e);
		}
		return this;
	}

	public scenarioPageObjects isPageDisplayed(Object... arg) throws Exception {
		try {
			Thread.sleep(3000);
			Wait<WebDriver> wait = new FluentWait<WebDriver>(CoreTestFactory.getDriver())
					.withTimeout(Duration.ofSeconds(30)).pollingEvery(Duration.ofSeconds(2))
					.ignoring(NoSuchElementException.class);
			WebElement we = CoreTestFactory.getDriver()
					.findElement(By.xpath("(//*[contains(normalize-space(text()),'Generic UI Grid')])[1]"));
//			we.isDisplayed();
			wait.until(ExpectedConditions.visibilityOf(we));
			logger.info("Pass: Webpage has been loaded successfully");
			System.out.println("Pass: Webpage has been loaded successfully");
		} catch (Exception e) {
			System.out.println("Exception Occurred in isPageDisplayed method due to following Exception : " + e);
		}
		return this;
	}
}
