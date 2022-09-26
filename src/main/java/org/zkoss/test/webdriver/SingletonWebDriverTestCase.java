/* SingletonWebDriverTestCase.java

	Purpose:
		
	Description:
		
	History:
		12:11 PM 2022/9/26, Created by jumperchen

Copyright (C) 2022 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.test.webdriver;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;

/**
 * A webdriver test case with a singleton server instance.
 * @author jumperchen
 * @since 1.0.4
 */
@ExtendWith(SingletonServer.class)
public abstract class SingletonWebDriverTestCase extends BaseTestCase {
}
