package sharedadvertisement.wind.com.sharedadvertisement;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.activity.CaptureActivity;
import com.wind.adv.AdvancedOptionsActivity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utils.LogUtil;

/**
 * Created by zhengzhe on 2017/11/10.
 */

public class MessageAuthenticationActivity extends Activity {

    private TextView mLogInButton = null;
    //打开扫描界面请求码
    private int REQUEST_CODE = 0x01;
    //扫描成功返回码
    private int RESULT_OK = 0xA1;
    private EditText mPhoneNum = null;
    private EditText mInputAuthenticationCode = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.message_authentication_layout);
        mLogInButton = (TextView)findViewById(R.id.mal_log_in);
        mPhoneNum = (EditText)findViewById(R.id.phone_num);
        mInputAuthenticationCode = (EditText)findViewById(R.id.input_authentication_code);
        mInputAuthenticationCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String result = ((CharSequence)s).toString();
                if ("123456".equals(result)) {
                    mLogInButton.setEnabled(true);
                }
            }
        });

        mLogInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                storeUserPhone();
                mInputAuthenticationCode.setText(null);
                startCaptureActivity();
                mLogInButton.setEnabled(false);
            }
        });
        mGetAuthenticationCode = (TextView)findViewById(R.id.get_authentication_code);
        mGetAuthenticationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isMobileNO(((CharSequence)(mPhoneNum.getText())).toString())) {
                    sendSms(((CharSequence)(mPhoneNum.getText())).toString(), "验证码：123456");
                }
            }
        });

        initReceiverIntent(this);
    }

    private void storeUserPhone() {
        LogUtil.d("MessageAuthenticationActivity + storeUserPhone() + phone = " + mPhoneNum.getText().toString());
        SharedPreferences sp = getSharedPreferences("SharedAdvertisement", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit().putString("user_phone", mPhoneNum.getText().toString());
        editor.commit();
    }

    private boolean isMobileNO(String mobiles) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    private static final String SEND_SMS_ACTION = "SEND_SMS_ACTION";
    private static final String  DELIVERED_SMS_ACTION = "DELIVERED_SMS_ACTION";
    private PendingIntent mSendPendingIntent;
    private PendingIntent mBackIntent;
    private TextView mGetAuthenticationCode;

    private void initReceiverIntent(Context context) {
        Intent sendIntent = new Intent(SEND_SMS_ACTION);
        mSendPendingIntent = PendingIntent.getBroadcast(context, 0, sendIntent, 0);
        Intent deliverIntent = new Intent(DELIVERED_SMS_ACTION);
        mBackIntent = PendingIntent.getBroadcast(context, 0,
                deliverIntent, 0);

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                switch (getResultCode()) {
                    case Activity.RESULT_OK:
                        Toast.makeText(context, "短信发送成功", Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        break;
                    default:
                        break;
                }
            }
        }, new IntentFilter(SEND_SMS_ACTION));

        context.registerReceiver(new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                Toast.makeText(context,
                        "收信人已经成功接收", Toast.LENGTH_SHORT)
                        .show();
            }
        }, new IntentFilter(DELIVERED_SMS_ACTION));
    }

    private void sendSms(String phoneNumber, String message) {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(phoneNumber, null, message, mSendPendingIntent, mBackIntent);
    }

    private void startCaptureActivity() {
        //打开二维码扫描界面
        if(utils.CommonUtil.isCameraCanUse()) {
            Intent intent = new Intent(MessageAuthenticationActivity.this, CaptureActivity.class);
            startActivityForResult(intent, REQUEST_CODE);
        } else {
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

            Intent intent = new Intent(MessageAuthenticationActivity.this, AdvancedOptionsActivity.class);
            startActivity(intent);
        }
    }
}
