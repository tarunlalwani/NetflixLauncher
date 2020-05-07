/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.tarlabs.netflixlauncher;

import android.app.Activity;
import android.app.PendingIntent;
import android.app.UiAutomation;
import android.content.Intent;
import android.content.pm.InstrumentationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import android.widget.TextView;
import android.app.Instrumentation;

import java.util.List;

/*
 * Main Activity class that loads {@link MainFragment}.
 */
public class MainActivity extends Activity {

    int UNINSTALL_REQUEST_CODE = 1;
    TextView text;
    Activity self;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        self = this;
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.text);
        String appPackage = "com.netflix.ninja";
        Intent intent = new Intent(this, this.getClass());
        final PendingIntent sender = PendingIntent.getActivity(this, 0, intent, 0);

        text.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String packagename = "com.netflix.ninja";

                boolean isPresent= true;
                int applicationState = PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
                try {
                    applicationState = getPackageManager().getApplicationEnabledSetting(packagename);
                } catch (Exception ex) {
                    isPresent = false;
                }

                if (isPresent) {
                    text.setText(R.string.netflix_found);
                    Uri packageUri = Uri.parse("package:" + packagename);
                    if (
                            applicationState != PackageManager.COMPONENT_ENABLED_STATE_DISABLED
                        && applicationState != PackageManager.COMPONENT_ENABLED_STATE_DISABLED_USER
                    ) {
                        Intent intent = getPackageManager().getLaunchIntentForPackage(packagename);
                        startActivity(intent);
                    } else {
                        text.setText(R.string.netflix_enable_app);

                        new Handler().postDelayed( () -> {
                            Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.setData(packageUri);
                            startActivity(intent);
                            startService(new Intent(self, NetflixLauncherService.class));

//                            Intent serviceIntent = new Intent(self, NetflixLauncherService.class);
//                            serviceIntent.putExtra("command", "Enable");
//                            startService(serviceIntent);
                        }, 1000);

                    }

                } else {
                    text.setText(R.string.netflix_app_not_found);
                }

            }
        });
        text.performClick();
    }

    

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (intent == null) {
            intent = new Intent();
        }
        try {
            super.onActivityResult(requestCode, resultCode, intent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
