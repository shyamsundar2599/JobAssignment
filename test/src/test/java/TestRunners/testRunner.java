package TestRunners;

import java.io.File;  
import org.junit.runner.RunWith;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import com.test.core.CoreTestFactory;

import cucumber.api.junit.Cucumber;
import io.cucumber.java.Scenario;
import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/java/features/", glue = { "stepDefinition" }, tags = "@test1", monochrome = true)


public class testRunner extends AbstractTestNGCucumberTests{
	
	public static Scenario scenario;
	
	@BeforeSuite(alwaysRun = true)
	public static void beforeRun() {
		String chromePath = System.getProperty("user.dir")+File.separator+"chromedriver.exe";
		if(new File(chromePath).exists() || new File(chromePath).isDirectory()) {
			System.out.println("Running in local Machine");
		}else {
			System.out.println("Running in Agent Machine");
		}
	}
	
	@AfterSuite(alwaysRun = true)
	public static void afterRun() {
		CoreTestFactory.getDriver().quit();
	}
	
	
}
