package stepDefinition;
 
import io.cucumber.java.en.Then;
import io.cucumber.java.en.Given;

import java.util.NoSuchElementException;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.test.core.CoreTestFactory;

import io.cucumber.java.en.And;

public class scenarioStepDefinition {
	Logger logger = Logger.getLogger("scenarioStepDefinition");
	
	@Given("^Launch \"([^\"]*)\" in Chrome WebBrowser$")
	public void launch_in_Chrome_WebBrowser(String URL) throws Throwable {
	    CoreTestFactory.launchWebsite(URL, "chrome");
	}

	@Then("^select \"([^\"]*)\" in the row size dropdown in webpage$")
	public void select_in_the_row_size_dropdown_in_webpage(String inputVal) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		com.test.PageObjects.scenarioPageObjects obj = CoreTestFactory.getPage(com.test.PageObjects.scenarioPageObjects.class);
		obj.enterValue(inputVal);

	}

	@Then("^verify GUI Grid displays \"([^\"]*)\" rows in UI$")
	public void verify_GUI_Grid_displays_rows_in_UI(String verifyVal) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		com.test.PageObjects.scenarioPageObjects obj = CoreTestFactory.getPage(com.test.PageObjects.scenarioPageObjects.class);
		obj.verifyData(verifyVal, "Grid Row Count");

	}

	@Then("^generate report with \"([^\"]*)\" as columns for project with \"([^\"]*)\" in webpage$")
	public void generate_report_with_for_project_with_in_webpage(String Fields, String Condition) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		com.test.PageObjects.scenarioPageObjects obj = CoreTestFactory.getPage(com.test.PageObjects.scenarioPageObjects.class);
		obj.reportCreation(Fields, Condition);

	}

	

	@Then("^check application is loaded successfully$")
	public void check_application_is_loaded_successfully() {
		// Write code here that turns the phrase above into concrete actions
		try {
			com.test.PageObjects.scenarioPageObjects obj = CoreTestFactory.getPage(com.test.PageObjects.scenarioPageObjects.class);
			obj.isPageDisplayed();
		} catch (NoSuchElementException n) {
			logger.info("Fail: Webpage is not loaded");
		} catch (Exception e) {
			logger.info("Fail: Error Occurred due to following exception : " + e);
		}

	}	
	
	

	

}
