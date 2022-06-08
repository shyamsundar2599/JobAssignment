package stepDefinition;

import org.apache.log4j.Logger;

import com.test.core.CoreTestFactory;

import io.cucumber.java.en.Then;

public class ComarisonScenarioStepDefinition {
	Logger logger = Logger.getLogger("ComarisonScenarioStepDefinition");
	
	@Then("check Flipkart application is loaded successfully")
	public void check_flipkart_application_is_loaded_successfully() throws Exception {
	    // Write code here that turns the phrase above into concrete actions
	    com.test.PageObjects.shoppingWebPage obj = CoreTestFactory.getPage(com.test.PageObjects.shoppingWebPage.class);
	    obj.isPageDisplayed("Flipkart");
	}

	@Then("search {string} product in Flipkart App")
	public void search_product_in_flipkart_app(String productName) throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		com.test.PageObjects.shoppingWebPage obj = CoreTestFactory.getPage(com.test.PageObjects.shoppingWebPage.class);
	    obj.enterValue("SEARCHTEXTBOX := "+productName, "Flipkart");
	    obj.performAction("Search", "Flipkart");
	}

	@Then("get price of {string} product in FlipKart App")
	public void get_price_of_product_in_flip_kart_app(String productName) throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		com.test.PageObjects.shoppingWebPage obj = CoreTestFactory.getPage(com.test.PageObjects.shoppingWebPage.class);
	    obj.getData("Price",productName,"Flipkart");
	}

	@Then("Close all tabs of browser")
	public void close_all_tabs_of_browser() {
	    // Write code here that turns the phrase above into concrete actions
	    CoreTestFactory.getDriver().quit();
	}

	@Then("check Amazon application is loaded successfully")
	public void check_amazon_application_is_loaded_successfully() throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		com.test.PageObjects.shoppingWebPage obj = CoreTestFactory.getPage(com.test.PageObjects.shoppingWebPage.class);
	    obj.isPageDisplayed("Amazon");
	}

	@Then("search {string} product in Amazon App")
	public void search_product_in_amazon_app(String productName) throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		com.test.PageObjects.shoppingWebPage obj = CoreTestFactory.getPage(com.test.PageObjects.shoppingWebPage.class);
	    obj.enterValue("SEARCHTEXTBOX := "+productName, "Amazon");
	    obj.performAction("Search", "Amazon");
	}

	@Then("get price of {string} product in Amazon App")
	public void get_price_of_product_in_amazon_app(String productName) throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		com.test.PageObjects.shoppingWebPage obj = CoreTestFactory.getPage(com.test.PageObjects.shoppingWebPage.class);
	    obj.getData("Price",productName,"Amazon");
	}

	@Then("compare the price between Amazon and Flipkart App")
	public void compare_the_price_between_amazon_and_flipkart_app() throws Exception {
	    // Write code here that turns the phrase above into concrete actions
		com.test.PageObjects.shoppingWebPage obj = CoreTestFactory.getPage(com.test.PageObjects.shoppingWebPage.class);
	    obj.performAction("compare price");
	}

}
