package com.wonium.example;

import android.Manifest;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.wonium.extension.utils.DeviceUtil;

import ru.alexbykov.nopermission.PermissionHelper;

public class MainActivity extends AppCompatActivity {
    PermissionHelper permissionHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView tvMac = findViewById(R.id.tvMac);
        tvMac.setText(DeviceUtil.INSTANCE.getMacAddress(this));
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show());
         permissionHelper = new PermissionHelper(this);
        XXXX();
    }

    private void XXXX(){
       //don't use getActivity in fragment!

        permissionHelper.check(Manifest.permission.ACCESS_FINE_LOCATION)
                .onSuccess(this::onSuccess)
                .onDenied(this::onDenied)
                .onNeverAskAgain(this::onNeverAskAgain)
                .run();
    }
    private void onSuccess(){
        TextView tvSSID =findViewById(R.id.tvSSID);
        tvSSID.setText(DeviceUtil.INSTANCE.getWIFISSID(this));
    }
    private void onDenied(){

    }
    private void onNeverAskAgain(){

    }

}
