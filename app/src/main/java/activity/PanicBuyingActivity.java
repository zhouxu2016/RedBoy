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
import com.google.gson.Gson;
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

import bean.PanicBean;


public class PanicBuyingActivity extends Activity {
    @ViewInject(R.id.lv_list_commodity)
    ListView lv_list_commodity;
    @ViewInject(R.id.tv_immed_return)
    TextView tv_immed_return;
    private List<PanicBean> list  = new ArrayList<>();
    private MyPanicAdapter adapter;
    private BitmapUtils bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panicbuying);
        bitmap = new BitmapUtils(this);
        initView();
        initData();

    }
    private void initView() {
        ViewUtils.inject(this);
        adapter = new MyPanicAdapter();
        lv_list_commodity.setAdapter(adapter);
        tv_immed_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initData() {
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpMethod.GET, Constans.NEW_PRODICT, new RequestCallBack<String>() {
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
                PanicBean newsa = new PanicBean();
                newsa.name = jsonObject1.getString("name");
                newsa.marketprice = jsonObject1.getInt("marketprice");
                newsa.outoftime = jsonObject1.getLong("outoftime");
                newsa.pic = jsonObject1.getString("pic");
                newsa.price = jsonObject1.getInt("price");
                list.add(newsa);
            }
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public class MyPanicAdapter extends BaseAdapter{

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
            ViewHoder holder = null;
            if (convertView == null){
                view = View.inflate(getApplicationContext(),R.layout.lv_list_commodity_itme,null);
                holder = new ViewHoder();
                holder.iv_sspanic1 = (ImageView) view.findViewById(R.id.iv_sspanic1);
                holder.tv_name = (TextView) view.findViewById(R.id.tv_name);
                holder.tv_yhprice = (TextView) view.findViewById(R.id.tv_yhprice);
                holder.tv_time_tv_special = (TextView) view.findViewById(R.id.tv_time_tv_special);
                holder.tv_time_data = (TextView) view.findViewById(R.id.tv_time_data);
                view.setTag(holder);
            }else{
                view = convertView;
                holder = (ViewHoder) view.getTag();
            }
            PanicBean bean = list.get(position);
            bitmap.display(holder.iv_sspanic1,bean.pic);
            holder.tv_name.setText(bean.name);
            holder.tv_yhprice.getPaint().setFlags(20);
            holder.tv_yhprice.setText(bean.marketprice+"");
            holder.tv_time_tv_special.setText(bean.price+"");
            long outoftime = bean.outoftime;
            SimpleDateFormat formats = new SimpleDateFormat("yy:MM:dd:HH:mm:ss");
            String format = formats.format(outoftime);
            holder.tv_time_data.setText(format);
            return view;
        }
        public class ViewHoder{
            ImageView iv_sspanic1;
            TextView tv_name;
            TextView tv_yhprice;
            TextView tv_time_tv_special;
            TextView tv_time_data;
            TextView tv_immediately_buying;//立即抢购
        }
    }
}
