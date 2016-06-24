package com.developer.crdzbird.nfc_reader.Record;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by crdzbird on 06-24-16.
 */

public interface ParsedNdefRecord {
    public View getView(Activity activity, LayoutInflater inflater, ViewGroup parent,
                        int offset);

}
