package com.developer.crdzbird.who_is_connected;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

public class License {

    private Context context;
    private View myLicense;


    License(Context context) {
        this.context = context;
    }

    protected View getView() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
        myLicense = inflater.inflate(R.layout.license, null);




        return myLicense;
    }

}
