package org.toulibre.capitoledulibre.utils;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.support.customtabs.CustomTabsSession;
import android.util.Log;

import org.toulibre.capitoledulibre.utils.customtabs.CustomTabActivityHelper;
import org.toulibre.capitoledulibre.utils.customtabs.CustomTabsHelper;
import org.toulibre.capitoledulibre.utils.customtabs.CustomTabsHelperFragment;

/**
 * @author bishiboosh
 */

public final class WebUtils {

    private static final String TAG = "WebUtils";

    private WebUtils() {
    }

    public static void openWebLink(Activity activity, Uri uri) {
        String packageToUse = CustomTabsHelper.getPackageNameToUse(activity);
        if (packageToUse != null) {
            CustomTabActivityHelper activityHelper = CustomTabsHelperFragment
                    .getCustomTabsActivityHelper(activity);
            if (activityHelper != null) {
                CustomTabsSession session = activityHelper.getSession();
                if (session != null) {
                    CustomTabsIntent.Builder intentBuilder = new CustomTabsIntent.Builder(session)
                            .setShowTitle(true);

                    try {
                        CustomTabsIntent tabsIntent = intentBuilder.build();
                        tabsIntent.intent.setPackage(packageToUse);
                        CustomTabsHelper.addKeepAliveExtra(activity, tabsIntent.intent);
                        tabsIntent.launchUrl(activity, uri);
                        return;
                    } catch (Exception e) {
                        Log.w(TAG, "Unable to open link in custom tab", e);
                    }
                }
            }
        }
        activity.startActivity(new Intent(Intent.ACTION_VIEW).setData(uri));
    }
}
