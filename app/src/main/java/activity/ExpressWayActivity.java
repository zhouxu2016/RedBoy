package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhouxu.redboy.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * .
 */
@ContentView(R.layout.activity_express_way)
public class ExpressWayActivity extends LyBaseActivity {

    @ViewInject(R.id.btn_compute)
    private Button btn;

    @Override
    public void initData() {

    }
    @Override
    public void initListener() {
        btn.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_compute:
                Intent intent=new Intent(this,PayCenterActivity.class);
                startActivity(intent);
                break;
        }
    }

}
