package activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
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

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import bean.HotOnlyBean;

public class HotOnlyActivity extends Activity {
    @ViewInject(R.id.tv_hotonly_return)
    TextView tv_hotonly_return;
    @ViewInject(R.id.lv_hotonly_product)
    ListView lv_hotonly_product;
    private MyHotOnlyAdapter adapter;
    private BitmapUtils bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hot_only);
        bitmap = new BitmapUtils(this);
        initView();
        initData();
    }
    private void initView() {
        ViewUtils.inject(this);
        tv_hotonly_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new MyHotOnlyAdapter();
        lv_hotonly_product.setAdapter(adapter);
    }
    private void initData() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, Constans.GOODS_LISTING+"?id=58463", new RequestCallBack<String>() {
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
    private List<HotOnlyBean> list = new ArrayList<>();
    private void parseJson(String result) {
        try {
            JSONObject json = new JSONObject(result);
            String response = json.getString("response");
            int listCount = json.getInt("listCount");
            if (!"brandProductlist".equals(response)){
                return;
            }
            JSONArray productList = json.getJSONArray("productList");
            for (int i = 0;i<productList.length();i++){
                JSONObject hotJson = productList.getJSONObject(i);
                HotOnlyBean beans = new HotOnlyBean();
                beans.name = hotJson.getString("name");
                beans.pic = hotJson.getString("pic");
                beans.price = hotJson.getInt("price");
                beans.outoftime = hotJson.getLong("outoftime");
                beans.marketprice = hotJson.getInt("marketprice");
                list.add(beans);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class MyHotOnlyAdapter extends BaseAdapter{
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
            HotHolder hotHolder = null;
            if (convertView == null){
                view = View.inflate(getApplicationContext(),R.layout.lv_list_hot_only_itme,null);
                hotHolder = new HotHolder();
                hotHolder.iv_panic1 = (ImageView) view.findViewById(R.id.iv_panic1);
                hotHolder.tv_hot_names = (TextView) view.findViewById(R.id.tv_hot_names);
                hotHolder.tv_hot_yhprice = (TextView) view.findViewById(R.id.tv_hot_yhprice);
                hotHolder.tv_time_hot_special = (TextView) view.findViewById(R.id.tv_time_hot_special);
                hotHolder.tv_time_hot_data = (TextView) view.findViewById(R.id.tv_time_hot_data);
                view.setTag(hotHolder);
            }else{
                view = convertView;
                hotHolder = (HotHolder) view.getTag();
            }
            HotOnlyBean onlyBean = list.get(position);
            bitmap.display(hotHolder.iv_panic1,onlyBean.pic);
            hotHolder.tv_hot_names.setText(onlyBean.name);
            hotHolder.tv_hot_yhprice.getPaint().setFlags(20);
            hotHolder.tv_hot_yhprice.setText(onlyBean.marketprice+"");
            hotHolder.tv_time_hot_special.setText(onlyBean.price+"");
            SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");
            long outoftime = onlyBean.outoftime;
            String format1 = format.format(outoftime);
            hotHolder.tv_time_hot_data.setText(format1);
            return view;
        }
    }
    public class HotHolder{
        ImageView iv_panic1;
        TextView tv_hot_names;
        TextView tv_hot_yhprice;
        TextView tv_time_hot_special;
        TextView tv_time_hot_data;

    }
}
