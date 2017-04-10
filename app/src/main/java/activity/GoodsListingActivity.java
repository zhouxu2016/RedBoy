package activity;

import android.app.Activity;
import android.content.Intent;
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

import bean.GoodsListingBean;

public class GoodsListingActivity extends Activity {
    @ViewInject(R.id.lv_list_goodslisting)
    ListView lv_list_goodslisting;
    @ViewInject(R.id.tv_listings_return)
    TextView tv_listings_return;
    private int cid;
    private MyGoodsListingAdapter adapter;
    private BitmapUtils bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_goodslisting);
        Intent intent = getIntent();
        cid = intent.getIntExtra("cid", 1021);
        bitmap = new BitmapUtils(this);
        initView();
        initData();
    }

    private void initView() {
        ViewUtils.inject(this);
        System.out.println(Constans.GOODS_LISTING+"?id"+cid);
        tv_listings_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new MyGoodsListingAdapter();
        lv_list_goodslisting.setAdapter(adapter);
    }

    private void initData() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, Constans.GOODS_LISTING+"?id="+cid, new RequestCallBack<String>() {
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
    private List<GoodsListingBean> list = new ArrayList<>();
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
                JSONObject jsonBean = productList.getJSONObject(i);
                GoodsListingBean beans = new GoodsListingBean();
                beans.name = jsonBean.getString("name");
                beans.pic = jsonBean.getString("pic");
                beans.price = jsonBean.getInt("price");
                beans.outoftime = jsonBean.getLong("outoftime");
                beans.marketprice = jsonBean.getInt("marketprice");
                list.add(beans);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public class MyGoodsListingAdapter extends BaseAdapter{

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
                view = View.inflate(getApplicationContext(),R.layout.activity_goodslisting,null);
                holder = new ViewHolder();
                holder.iv_goodslisting_panic1 = (ImageView) view.findViewById(R.id.iv_goodslisting_panic1);
                holder.tv_listing_yhprice = (TextView) view.findViewById(R.id.tv_listing_yhprice);
                holder.tv_time_listing_special = (TextView) view.findViewById(R.id.tv_time_listing_special);
                holder.tv_time_listing_data = (TextView) view.findViewById(R.id.tv_time_listing_data);
                holder.tv_lsiting_names = (TextView) view.findViewById(R.id.tv_lsiting_names);
                view.setTag(holder);
            }else{
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            GoodsListingBean listBean = list.get(position);
            bitmap.display(holder.iv_goodslisting_panic1,listBean.pic);
            holder.tv_lsiting_names.setText(listBean.name);
            holder.tv_listing_yhprice.getPaint().setFlags(20);
            holder.tv_listing_yhprice.setText(listBean.marketprice+"");
            holder.tv_time_listing_special.setText(listBean.price+"");
            SimpleDateFormat format = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");
            long outoftime = listBean.outoftime;
            String format1 = format.format(outoftime);
            holder.tv_time_listing_data.setText(format1);
            return view;
        }
    }
    public class ViewHolder{
        ImageView iv_goodslisting_panic1;
        TextView tv_listing_yhprice;
        TextView tv_lsiting_names;
        TextView tv_time_listing_special;
        TextView tv_time_listing_data;
    }
}
