package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhouxu.redboy.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 *
 */
@ContentView(R.layout.activity_express_time)
public class ExpressTimeActivity extends LyBaseActivity {

    @ViewInject(R.id.btn_compute)
    private Button btn;

    @ViewInject(R.id.lv_listview)
    private ListView lv;

    private String[] expresstime=new String[]{"工作日、双休日节与假日均可送货","只双休日、假日送货(工作日不用送货)","只工作日送货(双休日、假日不用送货)"};
    @Override
    public void initData() {
        lv.setAdapter(new ArrayAdapter<String>(ctx,R.layout.item_pay_way,R.id.tv_pay_way,expresstime));
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String time = expresstime[position];
                Intent intent=new Intent(ctx,PayCenterActivity.class);
                intent.putExtra("expresstime",time);
                setResult(300,intent);
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
}
