package activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhouxu.redboy.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

@ContentView(R.layout.activity_select_product_list)
public class SelectProductListActivity extends Activity {
    @ViewInject(R.id.lv_select)
    private ListView listView;
    @ViewInject(R.id.tv_bar_title)
    private TextView tvTitle;

    private String[] text1 = {"品牌 :", "价格 :", "功能 :", "库存 :"};
    private String[] text2 = {"品牌", "价格", "功能", "库存"};
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        tvTitle.setText("筛选");
        context=getBaseContext();
        MyAdapter adapter = new MyAdapter();
        listView.setAdapter(adapter);
    }
    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return text1.length;
        }

        @Override
        public Object getItem(int position) {
            return text1[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = View.inflate(getBaseContext(), R.layout.activity_select_product_item, null);
            TextView tv1 = (TextView) convertView.findViewById(R.id.tv1);
            TextView tv2 = (TextView) convertView.findViewById(R.id.tv2);
            tv1.setText(text1[position]);
            tv2.setText(text2[position]);
            return convertView;
        }
    }
    public void back(View view){
        finish();
    }
}
