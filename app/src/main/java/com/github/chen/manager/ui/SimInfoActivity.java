package com.github.chen.manager.ui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.TextView;

import com.github.chen.manager.R;

public class SimInfoActivity extends AppCompatActivity {

    private TextView mInfo;

    public static void start(Context context) {
        Intent starter = new Intent(context, SimInfoActivity.class);
//        starter.putExtra();
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

//        mInfo = (TextView) findViewById(R.id.tv_info);
//        TelephonyManager telManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
//        String txt = "运营商代号："+telManager.getNetworkOperator()+"\n";
//        txt += "运营商名称："+telManager.getNetworkOperatorName()+"\n";
//        txt += "网络类型："+telManager.getNetworkType()+"\n";
//        txt += "SIM卡的国别："+telManager.getNetworkCountryIso()+"\n";
//        txt += "SIM卡序列号："+telManager.getSimSerialNumber()+"\n";
//        txt += "SIM卡状态："+telManager.getPhoneType()+"\n";
//
//        Log.d("SimInfoActivity","sim info :"+txt);
//        mInfo.setText(txt);
    }
}
