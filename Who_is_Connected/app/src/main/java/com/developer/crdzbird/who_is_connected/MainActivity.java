package com.developer.crdzbird.who_is_connected;

import android.content.Context;
import android.content.Intent;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {


    private static final String TAG = "MainActivity";

    private TextView txtSsid;
    private TextView txtMyIpAddr;
    private TextView txtDisplay;
    private TextView txtScan254;
    private ArrayList<String> hosts;
    private ArrayAdapter<String> arrayAdapter;
    private Button btnScan;
    private String checkIp;
    private String myIp;
    private String myMac;
    private VendorsDbHelper myDbHelper;
    private String ssid;
    private Button btnSettings;

    private Context context = this;


    private ListView lstDevList;

    private NetworkCheck networkCheck;
    private NetworkCheckAll networkCheckAll;
    //private NetworkCheckAllPing networkCheckAllPing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtSsid = (TextView) findViewById(R.id.mainactivity_ssid);
        txtMyIpAddr = (TextView) findViewById(R.id.mainactivity_myipaddr);
        txtScan254 = (TextView) findViewById(R.id.mainactivity_texformscan254);
        txtDisplay = (TextView) findViewById(R.id.mainactivity_texform);
        lstDevList = (ListView) findViewById(R.id.mainactivity_devlist);
        btnScan = (Button) findViewById(R.id.mainactivity_scan);
        btnSettings = (Button) findViewById(R.id.mainactivity_settings_button);




        myDbHelper = new VendorsDbHelper(this);
        try {
            myDbHelper.createDatabase();
        } catch (Exception e) {
            Log.e(TAG, "can't create db", e);
        }
        try {
            myDbHelper.openDatabase();
        } catch (Exception e) {
            Log.e(TAG, "can't open db", e);
        }




        WifiManager manager=(WifiManager) getSystemService(Context.WIFI_SERVICE);
        WifiInfo info=manager.getConnectionInfo();
        ssid = info.getSSID();

        if (Build.VERSION.SDK_INT < 23)
        {
            myMac = info.getMacAddress();
        }
        else
        {
            try {
                BufferedReader br = new BufferedReader(new FileReader("/sys/class/net/wlan0/address"));
                try {
                    String line = br.readLine();
                    if(line.matches("..:..:..:..:..:.."))
                    {
                        myMac = line;
                    }
                    else
                    {
                        myMac = "not discovered";
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        int myIpAddress = info.getIpAddress();
        checkIp  = String.format("%d.%d.%d.", (myIpAddress & 0xff), (myIpAddress >> 8 & 0xff), (myIpAddress >> 16 & 0xff));
        myIp = String.format("%d.%d.%d.%d", (myIpAddress & 0xff), (myIpAddress >> 8 & 0xff), (myIpAddress >> 16 & 0xff), (myIpAddress >> 24 & 0xff));


        txtSsid.setText(ssid);
        txtMyIpAddr.setText(String.format(getString(R.string.mainactivity_displaymyip), myIp));
        txtDisplay.setText(getString(R.string.status_default));
        hosts = new ArrayList<>();
        arrayAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                hosts);
            lstDevList.setAdapter(arrayAdapter);

        btnSettings.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Information.class);
                startActivity(intent);

            }
        });


        btnScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                    networkCheckAll = new NetworkCheckAll();
                    networkCheckAll.execute();



                /**  this method is deprecated due freezing in some android devices.
                else
                {
                    networkCheckAllPing = new NetworkCheckAllPing();
                    networkCheckAllPing.execute();
                }**/



            }
        });




        lstDevList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {

                String hostInfo = (String) listView.getItemAtPosition(position);
                Intent intent = new Intent(MainActivity.this, DeviceInformation.class);
                intent.putExtra("hostinfo", hostInfo);
                startActivity(intent);
            }
        });

    }

    private class NetworkCheckAll extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            txtScan254.setText(String.format(getString(R.string.mainactivity_reaching), checkIp));
            txtDisplay.setText(getString(R.string.status_waiting));

        }

        @Override
        protected Void doInBackground(Void... params) {
            scanAll();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            txtScan254.setText(String.format(getString(R.string.mainactivity_finishedreaching), checkIp));
            networkCheck = new NetworkCheck();
            networkCheck.execute();


        }
    }


    /**private class NetworkCheckAllPing extends AsyncTask<Void, Void, Void> {

        //@Override
        protected void onPreExecute() {
            super.onPreExecute();

            txtScan254.setText(String.format(getString(R.string.mainactivity_pinging), checkIp));

        }

        //@Override
        protected Void doInBackground(Void... params) {


            pingAll();

            return null;
        }

        //@Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            txtScan254.setText(String.format(getString(R.string.mainactivity_finishedpinging), checkIp));


        }
    }**/

    private void scanAll()
    {
        InetAddress inetAddress;
        for (int i = 1; i < 255; i++)
        {
            try {
                inetAddress = InetAddress.getByName(checkIp + i);
                try {
                    inetAddress.isReachable(28);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            //ifHostUp(checkIp + i);
            Log.d(TAG, checkIp + i);
        }
    }

    /**private void pingAll()
    {
        InetAddress inetAddress;
        for (int i = 1; i < 255; i++)
        {
            ifHostUp(checkIp + i);
            Log.d(TAG, checkIp + i);
        }
    }**/




    private class NetworkCheck extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            txtDisplay.setText(getString(R.string.status_searching));
        }

        @Override
        protected Void doInBackground(Void... params) {
            hosts = scanSubNet();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

            /**
            try {
                SavedData data = new SavedData();
                HashSet<String> set = new HashSet<>(hosts);
                data.saveIt(context, set);
            }
            catch (Exception e)
            {
                Log.e(TAG, "qqqq", e);
            }**/

            updatedAdapter(hosts);
            txtDisplay.setText(String.format(getString(R.string.status_finished), hosts.size()));
        }
    }


    private ArrayList<String> scanSubNet(){

        ArrayList<String> ips = DeviceHelper.getIps();
        ArrayList<String> hosts = new ArrayList<>();
        InetAddress inetAddress;
        String hostAddr;

        if(ifHostUp(myIp))
        {
            try {
                inetAddress = InetAddress.getByName(myIp);
                String macPart = myMac.substring(0, 8);
                hosts.add(inetAddress.getHostAddress() + "\n" + myMac.toUpperCase() + "\n" + myDbHelper.getVendor(macPart.toUpperCase()));
                Log.d(TAG, inetAddress.getHostAddress());
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        }

        for(int i=0; i < ips.size(); i++){
            Log.e(TAG, "Trying: " + ips.get(i));
            try {
                hostAddr = ips.get(i);
                inetAddress = InetAddress.getByName(hostAddr);
                if(ifHostUp(hostAddr) && !myIp.equals(hostAddr)){
                    String macPart = DeviceHelper.getMac(inetAddress.getHostAddress()).substring(0, 8);
                    hosts.add(inetAddress.getHostAddress() + "\n" + DeviceHelper.getMac(inetAddress.getHostAddress()).toUpperCase() + "\n" + myDbHelper.getVendor(macPart.toUpperCase()));
                    Log.d(TAG, inetAddress.getHostAddress());
                }
                else if(!ifHostUp(hostAddr) && !myIp.equals(hostAddr)){
                    String macPart = DeviceHelper.getMac(inetAddress.getHostAddress()).substring(0, 8);
                    hosts.add(inetAddress.getHostAddress()  + " unpingable" + "\n" + DeviceHelper.getMac(inetAddress.getHostAddress()).toUpperCase() + "\n" + myDbHelper.getVendor(macPart.toUpperCase()));
                    Log.d(TAG, inetAddress.getHostAddress());
                }

            }  catch (IOException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(hosts);

        ArrayList<String> unp = new ArrayList<>();
        for (Iterator<String> it = hosts.iterator(); it.hasNext();) {
            String str = it.next();
            if (str.contains("unpingable")) {
                it.remove();
                unp.add(str);
            }
        }

        for (String str : unp)
        {
            hosts.add(str);
        }


        return hosts;
    }

    private void updatedAdapter(ArrayList<String> itemsArrayList) {
        arrayAdapter.clear();
        if (itemsArrayList != null){

            for (String str : itemsArrayList) {

                arrayAdapter.add(str);
            }
        }
        arrayAdapter.notifyDataSetChanged();
    }

    private boolean ifHostUp(String host){
        Runtime runtime = Runtime.getRuntime();
        try
        {
            Process process = runtime.exec("/system/bin/ping -q -w 1 -c 1 " + host);
            int exitValue = process.waitFor();
            return exitValue == 0;
        }
        catch (InterruptedException e)
        {
            Log.d(TAG, "interrupted", e);
        }
        catch (IOException e)
        {
            Log.d(TAG, "IOException caught", e);
        }
        return ifHostUp(host);
    }





}
