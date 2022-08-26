/* TouchWebDriverTestCase.java

	Purpose:
		
	Description:
		
	History:
		1:30 PM 2021/12/30, Created by jumperchen

Copyright (C) 2021 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.test.webdriver;

import java.util.Collections;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.touch.TouchActions;

/**
 * A base class to test using a Touch Simulator to run a remote Docker WebDriver.
 * @author jumperchen
 */
public abstract class TouchWebDriverTestCase extends DockerWebDriverTestCase {

	@Override
	protected final ChromeOptions getWebDriverOptions() {
		return super.getWebDriverOptions()
				.setExperimentalOption("mobileEmulation", Collections.singletonMap("deviceName", "iPad"))
				.setExperimentalOption("w3c", false); // Temporary workaround for TouchAction
	}

	/**
	 * Returns the touch actions for simulating touch events.
	 * @return
	 */
	protected TouchActions getTouchActions() {
		return new TouchActions(driver);
	}
}
