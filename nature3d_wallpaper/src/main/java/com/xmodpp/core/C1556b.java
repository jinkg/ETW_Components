package com.xmodpp.core;

import android.database.DataSetObserver;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

class C1556b implements ListAdapter {
    final /* synthetic */ LogActivity f10178a;
    private List f10179b;

    public C1556b(LogActivity logActivity, List list) {
        this.f10178a = logActivity;
        this.f10179b = list;
    }

    public boolean areAllItemsEnabled() {
        return false;
    }

    public int getCount() {
        return this.f10179b != null ? this.f10179b.size() : 0;
    }

    public Object getItem(int i) {
        return this.f10179b != null ? (String) this.f10179b.get(i) : null;
    }

    public long getItemId(int i) {
        return (long) i;
    }

    public int getItemViewType(int i) {
        return 0;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            view = new TextView(this.f10178a);
        } else {
            TextView textView = (TextView) view;
        }
        String str = (String) this.f10179b.get(i);
        if (str != null) {
            ((TextView)view).setText(str);
        }
        return view;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public boolean hasStableIds() {
        return false;
    }

    public boolean isEmpty() {
        return this.f10179b.size() == 0;
    }

    public boolean isEnabled(int i) {
        return false;
    }

    public void registerDataSetObserver(DataSetObserver dataSetObserver) {
    }

    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
    }
}
