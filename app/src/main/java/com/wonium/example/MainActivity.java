package com.wonium.example;

import android.Manifest;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.wonium.extension.utils.DeviceUtil;
import com.wonium.extension.utils.ToastUtil;

import ru.alexbykov.nopermission.PermissionHelper;

/**
 * @author Wonium
 * blog https://blog.wonium.com
 */

public class MainActivity extends AppCompatActivity {
    private PermissionHelper permissionHelper;

    TextView mTvSSID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
         mTvSSID = findViewById(R.id.tvSSID);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        permissionHelper = new PermissionHelper(this);
        getWifiSSid();
    }

    private void getWifiSSid() {
        permissionHelper.check(Manifest.permission.ACCESS_FINE_LOCATION).onSuccess(this::onSuccess).onDenied(this::onDenied).onNeverAskAgain(this::onNeverAskAgain).run();
    }
    private void onSuccess() {

        mTvSSID.setText(DeviceUtil.INSTANCE.getWIFISSID(this));
    }

    private void onDenied() {
        ToastUtil.INSTANCE.show(this, "权限被拒绝，9.0系统无法获取SSID");
    }

    private void onNeverAskAgain() {
        ToastUtil.INSTANCE.show(this, "权限被拒绝，9.0系统无法获取SSID,下次不会在询问了");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        permissionHelper.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
