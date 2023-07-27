/* ExternalWebXml.java

	Purpose:
		
	Description:
		
	History:
		12:44 PM 2023/7/27, Created by jumperchen

Copyright (C) 2023 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.test.webdriver;

import java.util.Properties;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestInstancePostProcessor;

/**
 * Load an external web.xml for testing.
 *
 * @author jumperchen
 */
public class ExternalWebXml
		implements TestInstancePostProcessor, BeforeAllCallback,
		AfterAllCallback { // extends ExternalResource

	private static final String BACKUP_STORE_KEY = "backup";
	public static final String WEB_XML_KEY = "jetty.webxml.path";

	private final String configPath;

	/**
	 * Pass a path to web.xml manually.
	 *
	 * @param configPath a path to web.xml
	 */
	public ExternalWebXml(String configPath) {
		this.configPath = configPath;
	}

	/**
	 * Pass a test class and will find web.xml by a convention rule.
	 * <p>
	 * e.g. F95_ZK_1234Test {@literal =>} /test2/F95-ZK-1234-web.xml
	 *
	 * @param testClass a test class (WebDriverTestCase only)
	 */
	public ExternalWebXml(Class<? extends BaseTestCase> testClass) {
		String className = testClass.getName().replace(BaseTestCase.PACKAGE, "")
				.replace('.', '/').replace('_', '-');
		int lastTest = className.lastIndexOf("Test");
		this.configPath = className.substring(0, lastTest) + "-web.xml";
	}

	// Junit 5
	public void afterAll(ExtensionContext context) throws Exception {
		final ExtensionContext.Store store = context.getStore(
				ExtensionContext.Namespace.create(getClass()));
		final Properties backup = store.get(BACKUP_STORE_KEY, Properties.class);
		System.setProperties(backup);
	}

	// Junit 5
	public void beforeAll(ExtensionContext context) throws Exception {
		final Properties backup = new Properties();
		backup.putAll(System.getProperties());
		final ExtensionContext.Store store = context.getStore(
				ExtensionContext.Namespace.create(getClass()));
		if (store.get(BACKUP_STORE_KEY, Properties.class) == null) {
			store.put(BACKUP_STORE_KEY, backup);
		}
		prepareSystemProperty(this.configPath);
	}

	private void prepareSystemProperty(String configPath) {
		if (configPath == null) {
			System.clearProperty(WEB_XML_KEY);
		} else {
			System.setProperty(WEB_XML_KEY, configPath);
		}
	}

	public void postProcessTestInstance(Object testInstance,
			ExtensionContext context) throws Exception {
		beforeAll(context);
	}
}
