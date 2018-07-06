/**
 *  UdpSocketsModule.java
 *  react-native-udp
 *
 *  Created by Andy Prock on 9/24/15.
 */

package com.tradle.react;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.JavaScriptModule;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.uimanager.ViewManager;
import com.facebook.common.logging.FLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.content.Context;
import android.net.wifi.WifiManager;

public final class UdpSocketsModule implements ReactPackage {
    private static final String TAG = "UdpSocketsModule";

    WifiManager.MulticastLock lock;

    @Override
    public List<NativeModule> createNativeModules(
            ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<NativeModule>();

        /**
         * I have a Moto phone that needed this to receive boring old UDP broadcasted messages.
         */
        if (lock == null) {
            try {
                FLog.w(TAG, "Acquired WifiLock");
                WifiManager wifi;
                wifi = (WifiManager) reactContext.getSystemService(Context.WIFI_SERVICE);
                lock = wifi.createMulticastLock("WiFi_Lock");
                lock.setReferenceCounted(true);
                lock.acquire();
                FLog.w(TAG, "WifiLock acquired!");
            }
            catch (Exception ex) {
                FLog.e(TAG, "WifiLock exception", ex);
            }
        }

        modules.add(new UdpSockets(reactContext));

        return modules;
    }

    @Override
    public List<Class<? extends JavaScriptModule>> createJSModules() {
        return Collections.emptyList();
    }

    @Override
    public List<ViewManager> createViewManagers(
            ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
