// Copyright 2016 Google Inc.
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.tarlabs.netflixlauncher;

import android.accessibilityservice.AccessibilityService;
import android.app.Service;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.util.Log;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class NetflixLauncherService extends AccessibilityService {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("Accessibility::", "Service Created");

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        try {
            ApplicationInfo ai = getApplicationInfo();
            AccessibilityNodeInfo wind  = getRootInActiveWindow();

            Log.d("Accessibility::", event.toString());
            Log.d("::", event.getSource().getViewIdResourceName());
            Log.d("::", event.getSource().getLabelFor().getViewIdResourceName());
            Log.d("::", event.getSource().getLabeledBy().getViewIdResourceName());
        } catch (Exception ex) {

        }
    }

    @Override
    public void onInterrupt() {

    }
}
