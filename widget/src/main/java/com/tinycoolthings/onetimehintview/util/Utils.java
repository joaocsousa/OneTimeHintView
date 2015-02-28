package com.tinycoolthings.onetimehintview.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;

/**
 * Created by joaosousa on 28/02/15.
 */
public class Utils {

	public static boolean isInDebugMode(Context context) {
		return 0 != (context.getApplicationInfo().flags & ApplicationInfo.FLAG_DEBUGGABLE);
	}

}
