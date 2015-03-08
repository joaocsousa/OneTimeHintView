package com.tinycoolthings.onetimehintview.util;

import org.junit.Assert;
import org.junit.Test;

import static com.tinycoolthings.onetimehintview.util.Utils.isValid;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static java.lang.Integer.MAX_VALUE;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by joaosousa on 08/03/15.
 */
public class UtilsTest {

	@Test
	public void testIsValid() throws Exception {
		assertFalse(isValid(null));
		assertFalse(isValid(""));
		assertTrue(isValid(10));
		assertTrue(isValid(-10));
		assertFalse(isValid(MAX_VALUE));
		assertFalse(isValid(FALSE));
		assertTrue(isValid(TRUE));
		assertTrue(isValid(true));
	}
}
