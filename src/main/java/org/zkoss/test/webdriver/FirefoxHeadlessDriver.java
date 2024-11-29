/* FirefoxHeadlessDriver.java

	Purpose:

	Description:

	History:
		10:36 AM 2024/11/29, Created by jumperchen

Copyright (C) 2024 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.test.webdriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxDriverService;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * A local running Firefox GeckoDriver. By default it runs in headless mode.
 * @author jumperchen
 */
public class FirefoxHeadlessDriver extends FirefoxDriver {
	static {
		WebDriverManager.firefoxdriver().setup();
		System.setProperty("webdriver.gecko.logfile", String.format("%s/geckodriver.log", System.getProperty("java.io.tmpdir")));
		System.setProperty("webdriver.gecko.log.level", "debug");
	}

	public FirefoxHeadlessDriver() {
		this(true);
	}

	public FirefoxHeadlessDriver(boolean headless) {
		this(new FirefoxOptions(), headless);
	}

	public FirefoxHeadlessDriver(FirefoxOptions options) {
		this(options, true);
	}

	public FirefoxHeadlessDriver(FirefoxOptions options, boolean headless) {
		super(headlessSettings(options, headless));
	}

	public FirefoxHeadlessDriver(FirefoxDriverService service) {
		this(service, new FirefoxOptions());
	}

	public FirefoxHeadlessDriver(FirefoxDriverService service, FirefoxOptions options) {
		this(service, options, true);
	}

	public FirefoxHeadlessDriver(FirefoxDriverService service, FirefoxOptions options, boolean headless) {
		super(service, headlessSettings(options, headless));
	}

	private static FirefoxOptions headlessSettings(FirefoxOptions options, boolean headless) {
		if (headless) {
			options.addArguments("--headless");
		}
		return options;
	}
}
