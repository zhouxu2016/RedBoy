package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
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

import bean.HotGoodsBean;

public class HotGoodsActivity extends Activity {

    private final AdapterView.OnItemClickListener hotGoodsOnItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            int cid = list.get(position).id;
            Intent intent = new Intent(HotGoodsActivity.this,GoodsListingActivity.class);
            intent.putExtra("cid",cid);
            startActivity(intent);
        }
    };
    @ViewInject(R.id.gv_lists_hotgoods)
    GridView gv_lists_hotgoods;
    @ViewInject(R.id.tv_hotsgoods_return)
    TextView tv_hotsgoods_return;
    @ViewInject(R.id.tv_pinpai)
    TextView tv_pinpai;
    private List<HotGoodsBean> list = new ArrayList<>();
    private BitmapUtils bitmap;
    private MyHotGoodsAdayter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hotgoods);
        bitmap = new BitmapUtils(this);
        initView();
        initData();

    }

    private void initView() {
        ViewUtils.inject(this);
        tv_hotsgoods_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new MyHotGoodsAdayter();
        gv_lists_hotgoods.setAdapter(adapter);

        gv_lists_hotgoods.setOnItemClickListener(hotGoodsOnItemClickListener);
    }

    private void initData() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, Constans.HOT_GOODS,new RequestCallBack<String>() {
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

    private void parseJson(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            String response = jsonObject.getString("response");
            String key = jsonObject.getString("key");
            tv_pinpai.setText(key);
            if (!"brand".equals(response)){
                return;
            }
            JSONArray value = jsonObject.getJSONArray("value");
            for (int i=0;i<value.length();i++){
                JSONObject jsonObject1 = value.getJSONObject(i);
                HotGoodsBean ben = new HotGoodsBean();
                ben.id = jsonObject1.getInt("id");
                ben.name = jsonObject1.getString("name");
                ben.pic = jsonObject1.getString("pic");
                list.add(ben);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
    public class MyHotGoodsAdayter extends BaseAdapter{
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
            ViewHolder holder = null;
            if (convertView == null){
                view = View.inflate(getApplicationContext(),R.layout.lv_list_hotgoods_item,null);
                holder = new ViewHolder();
                holder.tv_hotgoods_title_name = (TextView) view.findViewById(R.id.tv_hotgoods_title_name);
                holder.imgs_goods_hot = (ImageView) view.findViewById(R.id.imgs_goods_hot);
                view.setTag(holder);
            }else{
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            HotGoodsBean goodsBeans = list.get(position);
            System.out.println(goodsBeans.name);
            bitmap.display(holder.imgs_goods_hot,goodsBeans.pic);
            holder.tv_hotgoods_title_name.setText(goodsBeans.name);
            return view;
        }
    }
    public class ViewHolder{
        TextView tv_hotgoods_title_name;
        ImageView imgs_goods_hot;
    }
}
