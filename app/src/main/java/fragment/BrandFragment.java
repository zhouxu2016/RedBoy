package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhouxu.redboy.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import activity.Brand2Level;
import activity.Constans;
import adapter.MyBaseAdapter;
import bean.Category;
import utils.HttpManager;
import utils.UiUtils;


public class BrandFragment extends Fragment {
    private final AdapterView.OnItemClickListener myItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(getActivity(), Brand2Level.class);
            intent.putExtra("title", categoryList.get(position).name);
            startActivity(intent);
        }
    };
    private List<Category> categoryList = new ArrayList<>();
    private int[] imageViewIds = {R.drawable.brand_ym,
            R.drawable.brand_bb,
            R.drawable.brand_cz,
            R.drawable.brand_yf, R.drawable.brand_yf};
    @ViewInject(R.id.tv_bar_title)
    private TextView tvTitle;
    @ViewInject(R.id.lv_brand_first)
    private ListView listView;
    @ViewInject(R.id.btn_left)
    private Button btLeft;
    private MyAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = UiUtils.inflate(R.layout.fragment_brand);
        ViewUtils.inject(this, view);
        btLeft.setVisibility(View.GONE);
        getData();
        tvTitle.setText("品牌");
        initView();
        return view;
    }

    private void initView() {
        listView.setOnItemClickListener(myItemClickListener);
    }

    private void getData() {
        Map<String, String> params = new HashMap<>();
        params.put("parentId", "0");
        params.put("version", "0");

        HttpManager http = new HttpManager(getActivity(), Constans.CATEGORY, params,
                new HttpManager.OnRequestResonseListener() {

                    @Override
                    public void onSucesss(String json) {
                        Log.i("BranFragment", "onSucesss: json" + json);
                        parseJson(json);
                    }

                    @Override
                    public void onFailure(String errorMsg) {
                        Log.i("BranFragment", "onFailure: errorMsg" + errorMsg);
                        Toast.makeText(getActivity(), errorMsg,
                                Toast.LENGTH_SHORT).show();
                    }
                });
        http.post();
    }

    public void parseJson(String json) {
        Log.i("BranFragment", json);
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray categoryArray = jsonObject.getJSONArray("category");
            for (int i = 0; i < categoryArray.length(); i++) {
                JSONObject object = categoryArray.getJSONObject(i);
                String name = object.getString("name");
                String tag = object.getString("tag");
                Category category = new Category(name, tag);
                categoryList.add(category);
            }
            adapter = new MyAdapter(imageViewIds.length);
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
            Category category = categoryList.get(position);
            Holder holder = null;
            if (convertView == null) {
                convertView = View.inflate(getActivity(), R.layout.item_brand_first, null);
                holder = new Holder();
                holder.iv = (ImageView) convertView.findViewById(R.id.iv);
                holder.tvName = (TextView) convertView.findViewById(R.id.tv_bar_title);
                holder.tvTag = (TextView) convertView.findViewById(R.id.tv_desc);
                convertView.setTag(holder);
            } else {
                holder = (Holder) convertView.getTag();
            }
            holder.iv.setImageResource(imageViewIds[position]);
            holder.tvName.setText(category.name);
            holder.tvTag.setText(category.tag);
            return convertView;
        }
    }

    private class Holder {
        public ImageView iv;
        public TextView tvName;
        public TextView tvTag;

    }
}
