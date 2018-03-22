package com.xmodpp.core;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.xmodpp.C1553b;
import com.xmodpp.C1554c;

import java.util.ArrayList;
import java.util.List;

public class LogActivity extends Activity {
    public static List<String> f10172a = new ArrayList<>();
    protected C1556b f10173b = new C1556b(this, f10172a);
    protected ListView f10174c;

    public static synchronized void jni_Log(int i, String str) {
        synchronized (LogActivity.class) {
            switch (i) {
                case 1:
                    f10172a.add("E: " + str);
                    break;
                case 2:
                    f10172a.add("W: " + str);
                    break;
                case 3:
                    f10172a.add("I: " + str);
                    break;
                case 4:
                    f10172a.add("V: " + str);
                    break;
            }
        }
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(C1554c.error_log_activity);
        this.f10174c = (ListView) findViewById(C1553b.listView1);
        this.f10174c.setAdapter(this.f10173b);
        findViewById(C1553b.button1).setOnClickListener(new C1555a(this));
    }
}
