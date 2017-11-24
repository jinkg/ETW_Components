package com.xmodpp.application;

import android.os.Handler;
import android.os.Looper;
import java.util.HashMap;

public class Signals {
    private static Handler handler = new Handler(Looper.getMainLooper());
    private static HashMap<String, SignalHandlerDouble> handlersDouble = new HashMap();
    private static HashMap<String, SignalHandlerString> handlersString = new HashMap();

    public interface SignalHandlerString {
        void onSignal(String str, String str2);
    }

    public interface SignalHandlerDouble {
        void onSignal(String str, double d);
    }

    public static void RegisterHandler(String str, SignalHandlerDouble signalHandlerDouble) {
        handlersDouble.put(str, signalHandlerDouble);
    }

    public static void RegisterHandler(String str, SignalHandlerString signalHandlerString) {
        handlersString.put(str, signalHandlerString);
    }

    public static native void Send(String str, String str2);

    public static void UnregisterHandlerDouble(String str) {
        handlersDouble.remove(str);
    }

    public static void UnregisterHandlerString(String str) {
        handlersString.remove(str);
    }

    public static void jni_injectSignalDouble(final String str, final double d) {
        handler.post(new Runnable() {
            public void run() {
                SignalHandlerDouble signalHandlerDouble = (SignalHandlerDouble) Signals.handlersDouble.get(str);
                if (signalHandlerDouble != null) {
                    signalHandlerDouble.onSignal(str, d);
                }
            }
        });
    }

    public static void jni_injectSignalString(final String str, final String str2) {
        handler.post(new Runnable() {
            public void run() {
                SignalHandlerString signalHandlerString = (SignalHandlerString) Signals.handlersString.get(str);
                if (signalHandlerString != null) {
                    signalHandlerString.onSignal(str, str2);
                }
            }
        });
    }
}
