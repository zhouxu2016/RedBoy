package activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhouxu.redboy.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import adapter.MyBaseAdapter;
import bean.Product;
import utils.HttpManager;

import static utils.UiUtils.getContext;

/**
 * Created by yuki on 11/12 0012.
 */
@ContentView(R.layout.activity_product_list)
public class ProductListActivity extends Activity implements View.OnClickListener {
    @ViewInject(R.id.btn_left)
    private Button btnBack;
    @ViewInject(R.id.btn_right)
    private Button btnSelect;
    @ViewInject(R.id.rb1)
    private RadioButton orderBySell;
    @ViewInject(R.id.rb2)
    private RadioButton orderByPrice;
    @ViewInject(R.id.rb3)
    private RadioButton orderByComment;
    @ViewInject(R.id.rb4)
    private RadioButton orderByTime;
    @ViewInject(R.id.lv)
    private ListView listView;
    @ViewInject(R.id.tv_bar_title)
    private TextView tvTitle;
    private List<Product> products = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        tvTitle.setText("商品列表");
        btnSelect.setVisibility(View.VISIBLE);
        btnSelect.setText("筛选");
        btnBack.setOnClickListener(this);
        btnSelect.setOnClickListener(this);
        orderBySell.setOnClickListener(this);
        orderByPrice.setOnClickListener(this);
        orderByComment.setOnClickListener(this);
        orderByTime.setOnClickListener(this);
        orderBySell.setEnabled(true);
        getData();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ProductListActivity.this, ProductDetailsActivity.class);
                intent.putExtra("productId", products.get(position).getProductId());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_left:
                finish();
                break;
            case R.id.btn_right:
                Intent intent = new Intent(ProductListActivity.this, SelectProductListActivity.class);
                startActivity(intent);
                break;
            case R.id.rg1:
                orderBySell.setEnabled(true);
                break;
            case R.id.rg2:
                orderByPrice.setEnabled(true);
                break;
            case R.id.rg3:
                orderByComment.setEnabled(true);
                break;
            case R.id.rg4:
                orderByTime.setEnabled(true);
                break;

        }
    }

    private void getData() {
        Map<String, String> params = new HashMap<>();
        params.put("cid", "1006");
//        params.put("version", "1");

        HttpManager http = new HttpManager(this, Constans.PRODUCTLIST, params,
                new HttpManager.OnRequestResonseListener() {

                    @Override
                    public void onSucesss(String json) {
                        Log.i("ProductListActivity", "onSucesss: json" + json);
                        parseJson(json);
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        Log.i("ProductListActivity", "onFailure: errorMsg" + errorMsg);
                        Toast.makeText(getApplicationContext(), errorMsg,
                                Toast.LENGTH_SHORT).show();
                    }
                });
        http.post();
    }

    public void parseJson(String json) {
        Log.i("ProductListActivity", json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray productArray = jsonObject.getJSONArray("productList");
            for (int i = 0; i < productArray.length(); i++) {
                JSONObject object = productArray.getJSONObject(i);
                Product product = new Product();
                product.setName(object.getString("name"));
                product.setPrice(object.getLong("price"));
                product.setMarketprice(object.getLong("marketprice"));
                product.setCommentcount(object.getInt("commentcount"));
                product.setProductId(object.getString("productId"));

                products.add(product);
            }
            MyAdapter adapter = new MyAdapter(products.size());
            listView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class MyAdapter extends MyBaseAdapter {

        public MyAdapter(int itemCount) {
            super(itemCount);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Product product = products.get(position);
            Holder holder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.item_product, null);
                holder = new Holder();
                ViewUtils.inject(convertView);
//                holder.iv = (ImageView) convertView.findViewById(R.id.iv);
                holder.tvName = (TextView) convertView.findViewById(R.id.name);
                holder.newPrice = (TextView) convertView.findViewById(R.id.newprice);
                holder.oldPrice = (TextView) convertView.findViewById(R.id.oldprice);
                holder.oldPrice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                holder.oldPrice.getPaint().setAntiAlias(true);
                holder.commentNum = (TextView) convertView.findViewById(R.id.commentcount);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
//            holder.iv.setImageResource(imageViewIds[position]);
            holder.tvName.setText(product.getName());
            holder.newPrice.setText("¥ " + product.getPrice());
            holder.oldPrice.setText("¥ " + product.getMarketprice());
            holder.commentNum.setText("已有" + product.getCommentcount() + "人评价");
            return convertView;
        }
    }

    private class Holder {
        //        public ImageView iv;
        public TextView tvName;
        private TextView name;
        private TextView youhui;
        private TextView newPrice;
        private TextView oldPrice;
        private TextView commentNum;
    }

    public void back(View view) {
        finish();
    }
}
