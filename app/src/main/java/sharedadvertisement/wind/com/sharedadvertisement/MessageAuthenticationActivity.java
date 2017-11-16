package sharedadvertisement.wind.com.sharedadvertisement;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Camera;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.activity.CaptureActivity;
import com.wind.adv.AdvancedOptionsActivity;

/**
 * Created by zhengzhe on 2017/11/10.
 */

public class MessageAuthenticationActivity extends Activity {

    private TextView mLogInButton = null;
    private TextView mScanResult = null;
    //打开扫描界面请求码
    private int REQUEST_CODE = 0x01;
    //扫描成功返回码
    private int RESULT_OK = 0xA1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_authentication_layout);
        mLogInButton = (TextView)findViewById(R.id.mal_log_in);
        mScanResult = (TextView)findViewById(R.id.mal_scan_result);
        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCaptureActivity();
            }
        });
    }

    private void startCaptureActivity() {
        //打开二维码扫描界面
        if(com.utils.CommonUtil.isCameraCanUse()){
            Intent intent = new Intent(MessageAuthenticationActivity.this, CaptureActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        }else{
            Toast.makeText(this,"请打开此应用的摄像头权限！",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //扫描结果回调
        if (resultCode == RESULT_OK) { //RESULT_OK = -1
            Bundle bundle = data.getExtras();
            String scanResult = bundle.getString("qr_scan_result");
            //将扫描出的信息显示出来
            mScanResult.setText(scanResult);

            Intent intent = new Intent(MessageAuthenticationActivity.this, AdvancedOptionsActivity.class);
            startActivity(intent);
        }
    }
}
