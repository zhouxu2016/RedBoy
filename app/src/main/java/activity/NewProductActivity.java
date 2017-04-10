package activity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
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

import bean.newProductBean;

public class NewProductActivity extends Activity {
    @ViewInject(R.id.lv_newproduct)
    ListView lv_newproduct;
    @ViewInject(R.id.tv_new_return)
    TextView tv_new_return;
    private MyNewAdapter adapter;
    private BitmapUtils bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newproduct);
        bitmap = new BitmapUtils(this);
        initView();
        initData();
    }
    private void initView() {
        ViewUtils.inject(this);
        tv_new_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new MyNewAdapter();
        lv_newproduct.setAdapter(adapter);
    }

    private void initData() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, Constans.NEW_PRODICT, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String result = responseInfo.result;
                System.out.println(result);
                parseJson(result);
            }

            @Override
            public void onFailure(HttpException e, String s) {

            }
        });
    }
    private List<newProductBean> list = new ArrayList<newProductBean>();
    private void parseJson(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            String response = jsonObject.getString("response");
            if (!"newProduct".equals(response)){
                return;
            }
            int listCount = jsonObject.getInt("listCount");
            if (listCount == 0){
                return;
            }
            JSONArray productlist = jsonObject.getJSONArray("productlist");
            for (int i = 0;i<productlist.length();i++){
                JSONObject jsonObject1 = productlist.getJSONObject(i);
                newProductBean news = new newProductBean();
                news.name = jsonObject1.getString("name");
                news.marketprice = jsonObject1.getInt("marketprice");
                news.outoftime = jsonObject1.getLong("outoftime");
                news.pic = jsonObject1.getString("pic");
                news.price = jsonObject1.getInt("price");
                list.add(news);
                System.out.println(news.toString());
            }
            adapter.notifyDataSetChanged();
            int count = adapter.getCount();
            System.out.println("++++++++++++++++++++"+count);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public class MyNewAdapter extends BaseAdapter{
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
                view = View.inflate(getApplicationContext(),R.layout.lv_list_news_appear_itme,null);
                holder = new ViewHolder();
                holder.iv_panic1 = (ImageView) view.findViewById(R.id.iv_panic1);
                holder.tv_news_names = (TextView) view.findViewById(R.id.tv_news_names);
                holder.tv_news_yhprice = (TextView) view.findViewById(R.id.tv_news_yhprice);
                holder.tv_time_news_special = (TextView) view.findViewById(R.id.tv_time_news_special);
                holder.tv_time_news_data = (TextView) view.findViewById(R.id.tv_time_news_data);
                view.setTag(holder);
            }else{
                view = convertView;
                holder = (ViewHolder) view.getTag();
            }
            System.out.println(holder);
            newProductBean bean = list.get(position);
            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>."+bean.toString());
            bitmap.display(holder.iv_panic1,bean.pic);
            holder.tv_news_names.setText(bean.name);
            holder.tv_news_yhprice.getPaint().setFlags(20);
            holder.tv_news_yhprice.setText(bean.marketprice+"");
            holder.tv_time_news_special.setText(bean.price+"");
            long outoftime = bean.outoftime;
            SimpleDateFormat formats = new SimpleDateFormat("yy:MM:dd:HH:mm:ss");
            String format = formats.format(outoftime);
            holder.tv_time_news_data.setText(format);
            return view;
        }
    }
    public class ViewHolder{
        ImageView iv_panic1;
        TextView tv_news_names;
        TextView tv_news_yhprice;
        TextView tv_time_news_special;
        TextView tv_time_news_data;
    }
}
