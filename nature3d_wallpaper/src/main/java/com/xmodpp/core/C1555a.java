package com.xmodpp.core;

import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;

class C1555a implements OnClickListener {
    final /* synthetic */ LogActivity f10177a;

    C1555a(LogActivity logActivity) {
        this.f10177a = logActivity;
    }

    public void onClick(View view) {
        try {
            Intent intent = new Intent("android.intent.action.SEND");
            intent.addFlags(335544320);
            intent.setType("text/plain");
            StringBuilder stringBuilder = new StringBuilder();
            for (String append : LogActivity.f10172a) {
                stringBuilder.append(append);
                stringBuilder.append("<br/>");
            }
            intent.putExtra("android.intent.extra.TEXT", stringBuilder.toString());
            this.f10177a.startActivity(Intent.createChooser(intent, "Send"));
        } catch (Exception e) {
        }
    }
}
