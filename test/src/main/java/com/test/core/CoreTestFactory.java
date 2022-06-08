package com.test.core;

import java.io.File; 
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.*;
 
public class CoreTestFactory {
	static Logger logger = Logger.getLogger("CoreTestFactory");
	public static ThreadLocal<WebDriver> driver = new InheritableThreadLocal<>();
	
	
	public static WebDriver getDriver() {
		return CoreTestFactory.driver.get();
	}

	public static <T> T getPage(Class<T> page) {
		return org.openqa.selenium.support.PageFactory.initElements(CoreTestFactory.getDriver(), page);
	}

	public static void launchWebsite(String URL, String browser) {
		if(CoreTestFactory.getDriver() != null) {
			CoreTestFactory.getDriver().close();
			CoreTestFactory.getDriver().quit();
		}
		WebDriver driver = null;
		try {
			
		
		if (browser.equalsIgnoreCase("chrome")) {
			File downloadfolder = new File(
					System.getProperty("user.dir") + File.separator + "lib" + File.separator + "Downloads");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("disable-extensions");
			options.addArguments("disable-popup-blocking");
			options.addArguments("--disable-backgrounding-occluded-windows");
			options.addArguments("--allow-running-insecure-content");
			DesiredCapabilities capabilities = new DesiredCapabilities();
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			capabilities.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
			capabilities.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION, true);
			capabilities.setCapability(CapabilityType.UNEXPECTED_ALERT_BEHAVIOUR,  UnexpectedAlertBehaviour.ACCEPT);
			HashMap<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("profile.default_content_settings.popups", 0);
			prefs.put("safebrowsing.enable", "false");
			prefs.put("download.prompt_for_download", "false");
			prefs.put("download.default_directory", downloadfolder.getAbsolutePath());
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + File.separator + "chromedriver.exe");
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			driver.manage().deleteAllCookies();
//			driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
//			driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
			driver.get(URL);
			logger.info("URL successfully launched in Chrome browser");
			
			CoreTestFactory.driver.set(driver);
			
		}
		}catch(Exception e) {
			System.out.println(e);
		}
		
		
	}
}
