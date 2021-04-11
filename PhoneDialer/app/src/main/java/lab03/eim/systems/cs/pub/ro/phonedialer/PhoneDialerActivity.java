package lab03.eim.systems.cs.pub.ro.phonedialer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import lab03.eim.systems.cs.pub.ro.phonedialer.Constants;

public class PhoneDialerActivity extends AppCompatActivity {

    private TextView numberText;
    private ImageButton callImageButton;
    private ImageButton hangupImageButton;
    private ImageButton backspaceImageButton;
    private Button genericButton;

    private CallImageButtonClickListener callImageButtonClickListener = new CallImageButtonClickListener();
    private class CallImageButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (ContextCompat.checkSelfPermission(PhoneDialerActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(
                        PhoneDialerActivity.this,
                        new String[]{Manifest.permission.CALL_PHONE},
                        Constants.PERMISSION_REQUEST_CALL_PHONE);
            } else {
                Intent intent = new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse("tel:" + numberText.getText().toString()));
                startActivity(intent);
            }
        }
    }

    private HangupImageButtonClickListener hangupImageButtonClickListener = new HangupImageButtonClickListener();
    private class HangupImageButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            finish();
        }
    }

    private BackspaceButtonClickListener backspaceButtonClickListener = new BackspaceButtonClickListener();
    private class BackspaceButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            String phoneNumber = numberText.getText().toString();
            if (phoneNumber.length() > 0) {
                phoneNumber = phoneNumber.substring(0, phoneNumber.length() - 1);
                numberText.setText(phoneNumber);
            }
        }
    }

    private GenericButtonClickListener genericButtonClickListener = new GenericButtonClickListener();
    private class GenericButtonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            numberText.setText(numberText.getText().toString() + ((Button)view).getText().toString());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_dialer);

        //setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        numberText = (TextView)findViewById(R.id.textViewNumber);

        callImageButton = (ImageButton)findViewById(R.id.buttonCall);
        callImageButton.setOnClickListener(callImageButtonClickListener);
        hangupImageButton = (ImageButton)findViewById(R.id.buttonHangUp);
        hangupImageButton.setOnClickListener(hangupImageButtonClickListener);
        backspaceImageButton = (ImageButton)findViewById(R.id.buttonBack);
        backspaceImageButton.setOnClickListener(backspaceButtonClickListener);

        for (int index = 0; index < Constants.buttonIds.length; index++) {
            genericButton = (Button)findViewById(Constants.buttonIds[index]);
            genericButton.setOnClickListener(genericButtonClickListener);
        }
    }
}