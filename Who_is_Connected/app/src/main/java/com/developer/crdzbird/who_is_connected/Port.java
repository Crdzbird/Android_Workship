package com.developer.crdzbird.who_is_connected;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class Port {

    private Context context;
    private View myPort;

    Port(Context context) {
        this.context = context;
    }

    protected View getView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        myPort = inflater.inflate(R.layout.port, null);
        return myPort;
    }


}
