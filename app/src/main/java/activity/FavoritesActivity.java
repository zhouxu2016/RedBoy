package activity;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhouxu.redboy.R;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bean.Favorite;
import utils.GlobalConfig;
import utils.NetworkRequestUtils;

@ContentView(R.layout.activity_favorites)
public class FavoritesActivity extends MyBaseActivity {

    @ViewInject(R.id.lv_listview)
    private ListView lv_listview;

    private Context context;
    private String TAG = "FavoritesActivity";
    private long userId;
    private BitmapUtils bitmapUtils;

    private List<Favorite> favoriteList = new ArrayList<>();
    private MyFavoritesAdapter adapter;


    @Override
    protected void initView() {

        super.initView();
        context = this;
        bitmapUtils = new BitmapUtils(context);
        userId = GlobalConfig.getUserId();

        adapter = new MyFavoritesAdapter();
        lv_listview.setAdapter(adapter);


//        Message message = Message.obtain();


    }


    @Override
    protected void init() {

        super.init();

//        String url =  "http://10.0.3.2:8080/ECServerz19/favorites?userId=1&page=0&pageNum=10

        String url = Constans.favorites + "?userId=" + userId +"&page=0&pageNum=10";

        Log.i(TAG,url);
        NetworkRequestUtils.sendGet(url, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                String jsonStr = responseInfo.result;
                Log.i(TAG,"网络请求成功"+jsonStr);
                parseJson(jsonStr);
            }

            @Override
            public void onFailure(HttpException e, String s) {

                Toast.makeText(context,"网络请求失败",Toast.LENGTH_SHORT).show();
                e.printStackTrace();

            }
        });

    }


    private void parseJson(String jsonStr) {

        try {

            JSONObject jsonObject = new JSONObject(jsonStr);
            JSONArray productList = jsonObject.getJSONArray("productList");
            Log.i("========",productList+"");
            for(int i = 0 ; i < productList.length() ; i++) {


                JSONObject productJSONObject = productList.getJSONObject(i);

                int id = productJSONObject.getInt("id");

                String name = productJSONObject.getString("name");

                String priceStr = productJSONObject.getString("price");
                Float price = Float.valueOf(priceStr);

                String marketpriceStr = productJSONObject.getString("marketprice");
                Float marketprice = Float.valueOf(marketpriceStr);

                String pic = productJSONObject.getString("pic");

                int commentcount = productJSONObject.getInt("commentcount");

                boolean isgift = productJSONObject.getBoolean("isgift");

                Favorite favorite = new Favorite(id,name,price,marketprice,pic,commentcount,isgift);
                favoriteList.add(favorite);
//                更新适配器
                adapter.notifyDataSetChanged();

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private class MyFavoritesAdapter extends BaseAdapter {


        @Override
        public int getCount() {

            return favoriteList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            ViewHolder holder = null;
            if(convertView == null) {

                holder = new ViewHolder();
                convertView = View.inflate(context,R.layout.item_favorites,null);

                holder.iv_proimg = (ImageView) convertView.findViewById(R.id.iv_proimg);
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_bar_title);
                holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
                holder.tv_markprice = (TextView) convertView.findViewById(R.id.tv_markprice);
                holder.tv_commentcount = (TextView) convertView.findViewById(R.id.tv_commentcount);


                convertView.setTag(holder);
            }
            else {

                holder = (ViewHolder) convertView.getTag();
            }


            Favorite favorite = favoriteList.get(position);

            bitmapUtils.display(holder.iv_proimg,favorite.pic);
            holder.tv_title.setText(favorite.name);
            holder.tv_price.setText("¥ "+favorite.price);

//            textview.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); //中划线

            holder.tv_markprice.setText("¥ "+favorite.marketprice);
            holder.tv_markprice.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG);
            holder.tv_commentcount.setText("已有"+favorite.commentcount+"人评价");

            return convertView;
        }

        @Override
        public Object getItem(int position) {
            return favoriteList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

    private class ViewHolder {

        private ImageView iv_proimg;
        private TextView tv_title;
        private TextView tv_price;
        private TextView tv_markprice;
        private TextView tv_commentcount;

    }


    @OnClick(R.id.btn_clear)
    public void click(View view){

//        清空集合
        favoriteList.clear();
        adapter.notifyDataSetChanged();

    }


}
