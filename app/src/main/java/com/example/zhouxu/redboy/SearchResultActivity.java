package com.example.zhouxu.redboy;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import activity.Constans;
import activity.ProductDetailsActivity;
import bean.SearchResultProduct;

/**
 * Created by Administrator on 2016/11/12 0012.
 */


public class SearchResultActivity extends Activity implements View.OnClickListener {
    private static final String TAG = "SearchResultActivity";

    @ViewInject(R.id.tv_result_count)
    private TextView tvresultcount;

    @ViewInject(R.id.btn_search_back)
    private Button btnback;

    @ViewInject(R.id.tv_sort_sales)
    private TextView tvsortsales;

    @ViewInject(R.id.tv_sort_price)
    private TextView tvsortprice;

    @ViewInject(R.id.tv_sort_comm)
    private TextView tvsortcomm;

    @ViewInject(R.id.tv_sort_time)
    private TextView tvsorttime;

    @ViewInject(R.id.lv_search_result)
    private ListView lvsearchresult;

    @ViewInject(R.id.ll_result)
    private LinearLayout llresult;

    @ViewInject(R.id.ll_nodata)
    private LinearLayout llnodata;

    private static final String SALES = "sales";
    private static final String PRICE = "price";
    private static final String COMMENTCOUNT = "commentcount";
    private static final String TIME = "time";
    private MyAdapter adapter;
    private List<SearchResultProduct> datas = new ArrayList<>();
    private String name;
    private BitmapUtils bitmapUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        ViewUtils.inject(this);
        name = getIntent().getStringExtra("name");
        bitmapUtils = new BitmapUtils(this);
        initListener();
    }

    private void parseJson(String name, String orderby) {
        String url = Constans.BASEURL + "/search?keyword=" + name + "&orderby=" + orderby;
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
         //       Log.i(TAG, "onSuccess: " + responseInfo.result);
                String jsonstr = responseInfo.result;
                try {
                    JSONObject jb = new JSONObject(jsonstr);
                    String response = jb.getString("response");
                    if (!"search".equals(response)) {
                        return;
                    }
                    int listCount = jb.getInt("listCount");
                    if (listCount == 0) {
                        llresult.setVisibility(View.GONE);
                        llnodata.setVisibility(View.VISIBLE);
                        return;
                    }
                    tvresultcount.setText("共搜到"+listCount+"条结果");
                    JSONArray productList = jb.getJSONArray("productList");
                    for (int i = 0; i < productList.length(); i++) {
                        JSONObject jsonObject = productList.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        int price = jsonObject.getInt("price");
                        int marketprice = jsonObject.getInt("marketprice");
                        int commentcount = jsonObject.getInt("commentcount");
                        int sales = jsonObject.getInt("sales");
                        int productid=jsonObject.getInt("productId");
                        String pic = jsonObject.getString("pic");
                        String name = jsonObject.getString("name");
                        SearchResultProduct srp = new SearchResultProduct(id, name, pic, price,
                                marketprice, sales, commentcount,productid);
                        datas.add(srp);
                    }
                    Log.i(TAG, "onSuccess: "+datas);
                    adapter.notifyDataSetChanged();


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {
                    e.printStackTrace();
            }
        });

    }


    protected void initListener() {
        tvsortsales.setOnClickListener(this);
        tvsortprice.setOnClickListener(this);
        tvsortcomm.setOnClickListener(this);
        tvsorttime.setOnClickListener(this);
        btnback.setOnClickListener(this);
        adapter = new MyAdapter();
        lvsearchresult.setAdapter(adapter);
        parseJson(name, SALES);
        lvsearchresult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchResultProduct srp= (SearchResultProduct) parent.getItemAtPosition(position);
                Intent intent=new Intent(SearchResultActivity.this, ProductDetailsActivity.class);
                Toast.makeText(SearchResultActivity.this, srp.productid+"", Toast.LENGTH_SHORT).show();
                intent.putExtra("productId",srp.productid+"");
                startActivity(intent);
            }
        });

    }

    private class MyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return datas.size();
        }

        @Override
        public Object getItem(int position) {

            return datas.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = null;
            ViewHolder vh = null;
            if (convertView == null) {
                vh = new ViewHolder();

                view = View.inflate(SearchResultActivity.this, R.layout.item_search_result, null);
                vh.icon = (ImageView) view.findViewById(R.id.icon);
                vh.open = (ImageView) view.findViewById(R.id.open);
                vh.name = (TextView) view.findViewById(R.id.name);
                vh.price = (TextView) view.findViewById(R.id.price);
                vh.marketprice = (TextView) view.findViewById(R.id.marketprice);
                vh.commentcount = (TextView) view.findViewById(R.id.commentcount);
                view.setTag(vh);
            } else {
                view = convertView;
                vh = (ViewHolder) view.getTag();
            }
            final SearchResultProduct srp = datas.get(position);
            vh.name.setText(srp.name);
            vh.price.setText("￥"+srp.price+"");
            vh.marketprice.getPaint().setFlags(20);
            vh.marketprice.setText("￥"+srp.marketprice+"");
            vh.commentcount.setText("已有"+srp.commentcount+"评价");
            bitmapUtils.display(vh.icon,srp.pic);

            return view;

        }
    }

    private class ViewHolder {
        ImageView icon;
        ImageView open;
        TextView name;
        TextView price;
        TextView marketprice;
        TextView commentcount;

    }

    @Override
    public void onClick(View v) {
        int white = getResources().getColor(R.color.white);
        int black=getResources().getColor(R.color.black);
        switch (v.getId()) {
            case R.id.tv_sort_sales:
                tvsortsales.setTextColor(white);
                tvsortprice.setTextColor(black);
                tvsortcomm.setTextColor(black);
                tvsorttime.setTextColor(black);
                tvsortsales.setBackgroundResource(R.drawable.id_segment_selected_1_bg);
                tvsortprice.setBackgroundResource(R.drawable.id_segment_normal_2_bg);
                tvsortcomm.setBackgroundResource(R.drawable.id_segment_normal_2_bg);
                tvsorttime.setBackgroundResource(R.drawable.id_segment_normal_3_bg);
                datas.clear();
                parseJson(name, SALES);

                break;
            case R.id.tv_sort_price:
                tvsortsales.setTextColor(black);
                tvsortprice.setTextColor(white);
                tvsortcomm.setTextColor(black);
                tvsorttime.setTextColor(black);
                tvsortsales.setBackgroundResource(R.drawable.id_segment_normal_1_bg);
                tvsortprice.setBackgroundResource(R.drawable.id_segment_selected_2_bg);
                tvsortcomm.setBackgroundResource(R.drawable.id_segment_normal_2_bg);
                tvsorttime.setBackgroundResource(R.drawable.id_segment_normal_3_bg);
                datas.clear();
                parseJson(name, PRICE);
                break;
            case R.id.tv_sort_comm:
                tvsortsales.setTextColor(black);
                tvsortprice.setTextColor(black);
                tvsortcomm.setTextColor(white);
                tvsorttime.setTextColor(black);
                tvsortsales.setBackgroundResource(R.drawable.id_segment_normal_1_bg);
                tvsortprice.setBackgroundResource(R.drawable.id_segment_normal_2_bg);
                tvsortcomm.setBackgroundResource(R.drawable.id_segment_selected_2_bg);
                tvsorttime.setBackgroundResource(R.drawable.id_segment_normal_3_bg);
                datas.clear();
                parseJson(name, COMMENTCOUNT);
                break;
            case R.id.tv_sort_time:
                tvsortsales.setTextColor(black);
                tvsortprice.setTextColor(black);
                tvsortcomm.setTextColor(black);
                tvsorttime.setTextColor(white);
                tvsortsales.setBackgroundResource(R.drawable.id_segment_normal_1_bg);
                tvsortprice.setBackgroundResource(R.drawable.id_segment_normal_2_bg);
                tvsortcomm.setBackgroundResource(R.drawable.id_segment_normal_2_bg);
                tvsorttime.setBackgroundResource(R.drawable.id_segment_selected_3_bg);
                datas.clear();
                break;
            case R.id.btn_search_back:
                finish();
        }

    }
}
