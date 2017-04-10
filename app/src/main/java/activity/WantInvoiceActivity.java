package activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


import com.example.zhouxu.redboy.R;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;



/**
 *
 */
@ContentView(R.layout.activity_want_invoice)
public class WantInvoiceActivity extends LyBaseActivity{
    @ViewInject(R.id.btn_compute)
    private Button btn;

    @ViewInject(R.id.lv_listview)
    private ListView lv;

    @Override
    public void initView() {
        super.initView();

    }

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
