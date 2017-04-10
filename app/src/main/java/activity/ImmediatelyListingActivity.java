package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
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

import bean.ListInfoBean;

/**
 * Created by Administrator on 2016/11/15 0015.
 */
public class ImmediatelyListingActivity extends Activity {
    @ViewInject(R.id.tv_return_list)
    TextView tv_return_list;

    @ViewInject(R.id.gv_list_item_immediately)
    GridView gv_list_item_immediately;
    private int bid;
    private MyListingAdapter adapter;
    private BitmapUtils bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_immediately);
        bitmap = new BitmapUtils(this);
        Intent intent = getIntent();
        bid = intent.getIntExtra("bid", 0);
        initView();
        initData();
    }
    private void initView() {
        ViewUtils.inject(this);
        tv_return_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new MyListingAdapter();
        gv_list_item_immediately.setAdapter(adapter);
    }

    private void initData() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, Constans.GOODS_LISTING+"?id="+bid, new RequestCallBack<String>() {
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
    private List<ListInfoBean> list = new ArrayList<>();
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
                JSONObject jsonObject = productList.getJSONObject(i);
                ListInfoBean info = new ListInfoBean();
                info.name = jsonObject.getString("name");
                info.pic = jsonObject.getString("pic");
                list.add(info);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public class MyListingAdapter extends BaseAdapter{
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
                view = View.inflate(getApplicationContext(),R.layout.item_immediately_listing,null);
                holder = new ViewHolder();
                holder.img_imgpic = (ImageView) view.findViewById(R.id.img_imgpic);
                holder.tv_title_name = (TextView) view.findViewById(R.id.tv_title_name);
                view.setTag(holder);
            }else{
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            ListInfoBean bean = list.get(position);
            bitmap.display(holder.img_imgpic,bean.pic);
            holder.tv_title_name.setText(bean.name);
            return view;
        }
    }
    public class ViewHolder{
        ImageView img_imgpic;
        TextView tv_title_name;
    }
}
