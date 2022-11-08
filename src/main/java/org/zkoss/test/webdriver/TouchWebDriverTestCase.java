/* TouchWebDriverTestCase.java

	Purpose:
		
	Description:
		
	History:
		1:30 PM 2021/12/30, Created by jumperchen

Copyright (C) 2021 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.test.webdriver;

import static java.time.Duration.ofMillis;
import static org.openqa.selenium.interactions.PointerInput.MouseButton.LEFT;
import static org.openqa.selenium.interactions.PointerInput.Origin.viewport;

import java.util.Collections;

import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Pause;
import org.openqa.selenium.interactions.PointerInput;
import org.openqa.selenium.interactions.Sequence;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * A base class to test using a Touch Simulator to run a remote Docker WebDriver.
 * @author jumperchen
 */
public abstract class TouchWebDriverTestCase extends DockerWebDriverTestCase {

	@Override
	protected final ChromeOptions getWebDriverOptions() {
		return super.getWebDriverOptions()
				.setExperimentalOption("mobileEmulation", Collections.singletonMap("deviceName", "iPad"));
	}

	private final static PointerInput FINGER = new PointerInput(
			PointerInput.Kind.TOUCH, "finger");

	public void swipe(Point start, Point end) {
		swipe(start, end, 0);
	}
	public void swipe(Point start, Point end, int duration) {
		Sequence swipe = new Sequence(FINGER, 1)
				.addAction(FINGER.createPointerMove(ofMillis(0), viewport(), start.getX(), start.getY()))
				.addAction(FINGER.createPointerDown(LEFT.asArg()))
				.addAction(FINGER.createPointerMove(ofMillis(duration), viewport(), end.getX(), end.getY()))
				.addAction(FINGER.createPointerUp(LEFT.asArg()));
		((RemoteWebDriver) driver).perform(Collections.singleton(swipe));
	}

	public void swipe(WebElement start, WebElement end) {
		swipe(start, end);
	}
	public void swipe(WebElement start, WebElement end, int duration) {
		swipe(start.getLocation(), end.getLocation(), duration);
	}
	public void tap(Point point) {
		tap(point, 0);
	}

	public void tap(Point point, int duration) {
		Sequence tap = new Sequence(FINGER, 1)
				.addAction(FINGER.createPointerMove(ofMillis(0), viewport(), point.getX(), point.getY()))
				.addAction(FINGER.createPointerDown(LEFT.asArg()))
				.addAction(new Pause(FINGER, ofMillis(duration)))
				.addAction(FINGER.createPointerUp(LEFT.asArg()));
		((RemoteWebDriver) driver).perform(Collections.singleton(tap));
	}

	public void tap(WebElement start, int duration) {
		tap(start.getLocation(), duration);
	}

	public void tap(WebElement start) {
		tap(start.getLocation(), 0);
	}

}
