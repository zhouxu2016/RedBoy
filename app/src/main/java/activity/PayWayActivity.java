package activity;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.zhouxu.redboy.R;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 *
 */
@ContentView(R.layout.activity_pay_way)
public class PayWayActivity extends LyBaseActivity{

    @ViewInject(R.id.btn_compute)
    private Button btn;

    @ViewInject(R.id.lv_listview)
    private ListView lv;
    private String[] payitem=new String[]{"货到付款","货到POS机付款","支付宝"};
    @Override
    public void initData() {
        lv.setAdapter(new ArrayAdapter<String>(ctx,R.layout.item_pay_way,R.id.tv_pay_way,payitem));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String payway=payitem[position];
                Intent intent=new Intent();
                intent.putExtra("payway",payway);
                setResult(200,intent);
                finish();
            }
        });
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode){
            case KeyEvent.KEYCODE_BACK:
                finish();
                break;
        }

        return super.onKeyDown(keyCode, event);
    }
}
