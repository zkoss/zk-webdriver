/* WebDriverTestCase.java

	Purpose:
		
	Description:
		
	History:
		11:54 AM 2022/9/26, Created by jumperchen

Copyright (C) 2022 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.test.webdriver;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

/**
 * A webdriver test case with a prototype server instance.
 * @author jumperchen
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public abstract class WebDriverTestCase extends BaseTestCase {

	@RegisterExtension
	@Order(Integer.MAX_VALUE)
	static protected PrototypeServer prototypeServer = new PrototypeServer();
}
