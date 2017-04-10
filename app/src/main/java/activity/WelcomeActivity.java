package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.example.zhouxu.redboy.MainActivity;
import com.example.zhouxu.redboy.R;

/**
 *
 */
public class WelcomeActivity extends Activity{
    private final int finalNum=10;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case finalNum:
                    Intent intent=new Intent(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        handler.sendEmptyMessageDelayed(finalNum,2000);
    }
}
