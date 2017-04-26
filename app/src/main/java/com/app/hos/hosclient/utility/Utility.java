package com.app.hos.hosclient.utility;

import android.content.Context;
import android.provider.Settings.Secure;
import com.app.hos.hosclient.utility.exceptions.NullFieldException;

public final class Utility {

    private static Context applicationContext;
    private static String androidId;

    public static Context getApplicationContext() {
        return applicationContext;
    }

    public static void setApplicationContext(Context applicationContext) {
        Utility.androidId = Secure.getString(applicationContext.getContentResolver(), Secure.ANDROID_ID);
        Utility.applicationContext = applicationContext;
    }

    public static String getAndroidId() throws NullFieldException {
        if (Utility.androidId == null) {
            throw new NullFieldException("'androidId' field is null");
        }
        return Utility.androidId;
    }

}
