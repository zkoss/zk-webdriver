/* ExternalZkXml.java

	Purpose:
		
	Description:
		
	History:
		Tue Oct 27 10:51:03 CST 2020, Created by rudyhuang

Copyright (C) 2020 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.test.webdriver;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import org.zkoss.lang.Library;

/**
 * Load an external zk.xml for testing.
 *
 * @author rudyhuang
 * @author jumperchen
 */
public class ExternalZkXml implements BeforeAllCallback, AfterAllCallback { // extends ExternalResource
	private final String configPath;

	/**
	 * Pass a path to zk.xml manually.
	 *
	 * @param configPath a path to zk.xml
	 */
	public ExternalZkXml(String configPath) {
		this.configPath = configPath;
	}

	/**
	 * Pass a test class and will find zk.xml by a convention rule.
	 * <p>
	 * e.g. F95_ZK_1234Test {@literal =>} /test2/F95-ZK-1234-zk.xml
	 *
	 * @param testClass a test class (WebDriverTestCase only)
	 */
	public ExternalZkXml(Class<? extends BaseTestCase> testClass) {
		String className = testClass.getName()
				.replace(BaseTestCase.PACKAGE, "")
				.replace('.', '/')
				.replace('_', '-');
		int lastTest = className.lastIndexOf("Test");
		this.configPath = className.substring(0, lastTest) + "-zk.xml";
	}

	// Junit 4
	protected void before() {
		Library.setProperty("org.zkoss.zk.config.path", configPath);
	}

	// Junit 4
	protected void after() {
		Library.setProperty("org.zkoss.zk.config.path", null);
	}

	// Junit 5
	public void afterAll(ExtensionContext context) throws Exception {
		after();
	}

	// Junit 5
	public void beforeAll(ExtensionContext context) throws Exception {
		before();
	}
}
