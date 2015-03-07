package com.tinycoolthings.onetimehintview.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * Created by joaosousa on 28/02/15.
 * </p>
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

}
