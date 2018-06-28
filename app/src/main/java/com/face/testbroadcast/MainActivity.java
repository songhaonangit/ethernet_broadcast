package com.face.testbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //songhaonan add start 20180621
    public static final String LANKE_ETHERNET = "com.lanke.ethernet";
    public static final String LANKE_ETHERNET_SYSTEM = "com.lanke.ethernet.from.system";
    String ethernet_type;
    String ipadd;
    String gw;
    String netMask;
    String dns1;
    String dns2;
    Button bt_static, bt_dhcp, bt_open, bt_close;
    IntentFilter ethernetFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bt_static = findViewById(R.id.bt_static);
        bt_static.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LANKE_ETHERNET);
                intent.putExtra("Ethernet_Type", "static");
                intent.putExtra("ipAdd", "192.168.8.250");
                intent.putExtra("netMask", "255.255.255.0");
                intent.putExtra("gw", "192.168.8.8");
                intent.putExtra("dns1", "101.198.199.200");
                intent.putExtra("dns2", "211.162.66.66");
                sendBroadcast(intent);
            }
        });

        bt_dhcp = findViewById(R.id.bt_dhcp);
        bt_dhcp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LANKE_ETHERNET);
                intent.putExtra("Ethernet_Type", "dhcp");
                sendBroadcast(intent);
            }
        });

        bt_open = findViewById(R.id.bt_open);
        bt_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LANKE_ETHERNET);
                intent.putExtra("Ethernet_Type", "open");
                sendBroadcast(intent);
            }
        });

        bt_close = findViewById(R.id.bt_close);
        bt_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LANKE_ETHERNET);
                intent.putExtra("Ethernet_Type", "close");
                sendBroadcast(intent);
            }
        });


        //songhaonan add start
        ethernetFilter = new IntentFilter();
        ethernetFilter.setPriority(1000);
        ethernetFilter.addAction(LANKE_ETHERNET_SYSTEM);
        registerReceiver(mEthernetIntentReceiver, ethernetFilter);
        //songhaonan add end


    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        unregisterReceiver(mEthernetIntentReceiver);

    }

    private BroadcastReceiver mEthernetIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            ethernet_type = intent.getStringExtra("Ethernet_Sate");
            if (LANKE_ETHERNET_SYSTEM.equals(action)) {
               // Toast.makeText(context, "---getReceiver---"+LANKE_ETHERNET_SYSTEM, Toast.LENGTH_SHORT).show();
                switch (ethernet_type){
                    case "static":
                        ipadd = intent.getStringExtra("ipAdd");
                        gw = intent.getStringExtra("gw");
                        netMask = intent.getStringExtra("netMask");
                        dns1 = intent.getStringExtra("dns1");
                        dns2 = intent.getStringExtra("dns2");

                        Toast.makeText(context, "---" + ethernet_type + ipadd + gw + netMask + dns1 + dns2 + "---", Toast.LENGTH_LONG).show();

                        break;
                    case "dhcp":
                        Toast.makeText(context, "---" + ethernet_type + "---", Toast.LENGTH_LONG).show();
                        break;
                    case "close":
                        Toast.makeText(context, "---" + ethernet_type + "---", Toast.LENGTH_LONG).show();
                        break;
                    case "open":
                        Toast.makeText(context, "---" + ethernet_type + "---", Toast.LENGTH_LONG).show();
                        break;

                }


            }
        }


    };
}