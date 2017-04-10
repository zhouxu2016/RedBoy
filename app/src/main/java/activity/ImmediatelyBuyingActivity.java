package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhouxu.redboy.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bean.ImmedBean;

public class ImmediatelyBuyingActivity extends Activity {
    @ViewInject(R.id.lv_immediateiy)
    ListView lv_immediateiy;
    @ViewInject(R.id.tv_immed_return)
    TextView tv_immed_return;
    private MyImmedAdapter adapter;
    private BitmapUtils bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_immediateiy_buying);
        bitmap = new BitmapUtils(this);
        initView();
        initData();
    }

    private void initView() {
        ViewUtils.inject(this);
        tv_immed_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();

            }
        });
        adapter = new MyImmedAdapter();
        lv_immediateiy.setAdapter(adapter);
        lv_immediateiy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ImmediatelyBuyingActivity.this,ImmediatelyListingActivity.class);
                int bid = list.get(position).id;
                intent.putExtra("bid",bid);
                startActivity(intent);
            }
        });
    }
    private void initData() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, Constans.HOT_GOODS, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                parseJson(result);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }
    private List<ImmedBean> list = new ArrayList<>();
    private void parseJson(String result) {
        try {
            JSONObject json = new JSONObject(result);
            String response = json.getString("response");
            String key = json.getString("key");
            if (!"brand".equals(response)){
                return;
            }
            JSONArray value = json.getJSONArray("value");
            for (int i=0;i<value.length();i++){
                JSONObject object = value.getJSONObject(i);
                ImmedBean beans = new ImmedBean();
                beans.id = object.getInt("id");
                beans.name = object.getString("name");
                beans.pic = object.getString("pic");
                list.add(beans);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class MyImmedAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            ImmedHolder holder = null;
            if (convertView == null){
                view = View.inflate(getApplicationContext(),R.layout.lv_list_immediateiy_item,null);
                holder = new ImmedHolder();
                holder.tv_immed_title = (TextView) view.findViewById(R.id.tv_immed_title);
                holder.img_goods = (ImageView) view.findViewById(R.id.img_goods);
                view.setTag(holder);
            }else{
                view = convertView;
                holder = (ImmedHolder) view.getTag();
            }
            ImmedBean immed = list.get(position);
            holder.tv_immed_title.setText(immed.name);
            bitmap.display(holder.img_goods,immed.pic);
            return view;
        }
    }
    public class ImmedHolder{
        TextView tv_immed_title;
        ImageView img_goods;
    }
}
