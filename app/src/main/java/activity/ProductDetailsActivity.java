package activity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhouxu.redboy.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import bean.Product;
import utils.HttpManager;
import view.CyclerViewPager;

@ContentView(R.layout.activity_product_details)
public class ProductDetailsActivity extends Activity implements View.OnClickListener {
    public static final String TAG = "ProductDetailActivity";
    @ViewInject(R.id.tv_bar_title)
    private TextView tvTitle;
    @ViewInject(R.id.btn_right)
    private Button btnAddBar;
    @ViewInject(R.id.lv_product_details)
    private ListView listView;

    private Product product = new Product();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        getData(getIntent().getStringExtra("productId"));
        tvTitle.setText("商品详情");
        btnAddBar.setVisibility(View.VISIBLE);
        btnAddBar.setText("加入购物车");
        btnAddBar.setOnClickListener(this);
    }

    private void getData(String productId) {
        Map<String, String> params = new HashMap<>();
        params.put("pId", productId);
//        params.put("pId", "1811308248");

        HttpManager http = new HttpManager(this, Constans.PRODUCT, params,
                new HttpManager.OnRequestResonseListener() {

                    @Override
                    public void onSucesss(String json) {
                        Log.i(TAG, "onSucesss: json" + json);
                        parseJson(json);
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        Log.i(TAG, "onFailure: errorMsg" + errorMsg);
                        Toast.makeText(getApplicationContext(), errorMsg,
                                Toast.LENGTH_SHORT).show();
                    }
                });
        http.post();
    }

    public void parseJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("product");
            JSONObject object = jsonArray.getJSONObject(1);
            Log.i(TAG, "product: " + object);
            product.setName(object.getString("name"));
            product.setPrice(object.getLong("price"));
            product.setMarketprice(object.getLong("marketprice"));
            product.setCommentcount(object.getInt("commentcount"));
            Log.i(TAG, "product: " + product);
            MyAdapter adapter = new MyAdapter();
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public void back(View view) {
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_right:
                addCar(1);
                break;
        }
    }

    private void addCar(int i) {

    }

    private class MyAdapter extends BaseAdapter implements View.OnClickListener {

        @Override
        public int getCount() {
            return 5;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Context context = getApplicationContext();
            switch (position) {
                case 0:
                    convertView = View.inflate(context, R.layout.item_product_detail1, null);
                    CyclerViewPager viewPager = (CyclerViewPager) convertView.findViewById(R.id.vpager);
                    MyPagerAdapter pAdapter = new MyPagerAdapter();
                    viewPager.setAdapter(pAdapter);
                    break;
                case 1:
                    convertView = View.inflate(context, R.layout.item_product_detail2, null);
                    TextView marketprice = (TextView) convertView.findViewById(R.id.marketprice);
                    TextView price = (TextView) convertView.findViewById(R.id.price);
                    TextView name = (TextView) convertView.findViewById(R.id.name);

                    name.setText(product.getName());
                    price.setText("¥ " + product.getPrice());
                    marketprice.setText("¥ " + product.getMarketprice());
                    marketprice.getPaint().setAntiAlias(true);
                    marketprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    break;
                case 2:
                    convertView = View.inflate(context, R.layout.item_product_detail3, null);
                    Button addCar = (Button) convertView.findViewById(R.id.btn_addcar);
                    Button addBag = (Button) convertView.findViewById(R.id.btn_addbag);
                    addCar.setOnClickListener(this);
                    break;
                case 3:
                    convertView = View.inflate(context, R.layout.item_product_detail4, null);
                    break;
                case 4:
                    convertView = View.inflate(context, R.layout.item_product_detail5, null);
                    TextView commentCount = (TextView) convertView.findViewById(R.id.commentcount);
                    commentCount.setText("共有" + product.getCommentcount() + "人评论");
                    break;
//                case 5:
//            convertView = View.inflate(context, R.layout.item_product_detail1, null);
//                    break;
            }
            return convertView;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_addcar:
                    addCar(1);
                    break;
                case R.id.btn_addbag:

                    break;
            }
        }

        private class MyPagerAdapter extends PagerAdapter {
            @Override
            public int getCount() {
                return 5;
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView imageView = new ImageView(getApplicationContext());
                imageView.setImageResource(R.drawable.ic_launcher);
                container.addView(imageView);
                return imageView;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        }
    }
}
