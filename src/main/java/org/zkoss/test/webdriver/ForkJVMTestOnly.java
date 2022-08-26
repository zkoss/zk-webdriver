/* ForkJVM.java

	Purpose:
		
	Description:
		
	History:
		7:18 PM 2021/11/20, Created by jumperchen

Copyright (C) 2021 Potix Corporation. All Rights Reserved.
*/
package org.zkoss.test.webdriver;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.Tag;

/**
 * Used for isolating JVM for test in ZATS tests ({@literal @}{@code ForkJVMTestOnly}).
 * It means the flag of {@code reuseForks=false} for maven surefire.
 * @author jumperchen
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Tag("ForkJVMTestOnly")
public @interface ForkJVMTestOnly {
}
