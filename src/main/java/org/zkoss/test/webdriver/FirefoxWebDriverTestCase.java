/* FirefoxWebDriverTestCase.java

	Purpose:

	Description:

	History:
		2:00 PM 2024/11/29, Created by jumperchen

Copyright (C) 2024 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.test.webdriver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

/**
 * A Firefox WebDriver test case.
 * @author jumperchen
 */
public class FirefoxWebDriverTestCase extends WebDriverTestCase{

	protected FirefoxOptions getFirefixOptions() {
		FirefoxOptions options = new FirefoxOptions();

		options.addArguments("--width=1920", "--height=1080");
		options.addPreference("moz:firefoxOptions", "--remote-allow-origins=*");
		return options;
	}

	protected WebDriver getWebDriver() {
		if (driver == null) {
			FirefoxOptions driverOptions = getFirefixOptions();
			driver = isUsingRemoteWebDriver(driverOptions)
					? new DockerRemoteWebDriver(getRemoteWebDriverUrl(), driverOptions)
					: new FirefoxHeadlessDriver(getFirefixOptions(), isHeadless());
		}
		return driver;
	}
}
