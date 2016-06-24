package com.developer.crdzbird.who_is_connected;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class ChangeLog {

    private Context context;
    private View myChangeLog;


    ChangeLog(Context context) {
        this.context = context;
    }

    protected View getView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        myChangeLog = inflater.inflate(R.layout.changelog, null);




        return myChangeLog;
    }

}
