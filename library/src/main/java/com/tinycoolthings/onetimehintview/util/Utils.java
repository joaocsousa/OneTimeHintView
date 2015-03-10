package com.tinycoolthings.onetimehintview.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * Created by joaosousa on 28/02/15.
 * <p>
 * Utils class with helper methods.
 */
public class Utils {

	/**
	 * Returns if an application is with debug enabled or not.
	 *
	 * @param context The context used to retrieve the app info.
	 * @return true if the application has debug enabled, false otherwise.
	 */
	public static boolean isDebugEnabled(Context context) {
		return 0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE);
	}

	/**
	 * Returns if an object of any type is valid.
	 *
	 * @param victim The object to test.
	 * @param <T>    The object's type.
	 * @return If victim is null returns always false, otherwise returns the Boolean value,
	 * true if victim instance of Integer and not {@link Integer#MAX_VALUE}, true if victim instance of String and not empty.
	 */
	public static <T extends Object> boolean isValid(T victim) {
		return victim != null && (
			victim instanceof Boolean && (Boolean) victim ||
				victim instanceof Integer && (Integer) victim != Integer.MAX_VALUE ||
				victim instanceof String && !((String) victim).isEmpty()
		);
	}

}
