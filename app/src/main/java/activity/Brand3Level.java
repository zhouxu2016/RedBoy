package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import utils.HttpManager;


@ContentView(R.layout.activity_brand)
public class Brand3Level extends Activity {

    private final String TAG = "Brand3Level";
    @ViewInject(R.id.tv_bar_title)
    private TextView tvTitle;

    @ViewInject(R.id.list_brand)
    private ListView listView;

    private List<String> names = new ArrayList<>();
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        tvTitle.setText(getIntent().getStringExtra("title"));
        getData();
        Log.i(TAG, "onCreate: names.size" + names.size());
        adapter = new ArrayAdapter<>(this, R.layout.item_brand, R.id.text, names);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Brand3Level.this, ProductListActivity.class);
                intent.putExtra("title", names.get(position));
                startActivity(intent);
            }
        });
    }

    private void getData() {
        Map<String, String> params = new HashMap<>();
        params.put("parentId", "106");
        params.put("version", "0");

        HttpManager http = new HttpManager(this, Constans.CATEGORY, params,
                new HttpManager.OnRequestResonseListener() {

                    @Override
                    public void onSucesss(String json) {
                        parseJson(json);
                        Log.i(TAG, "onSucesss: json" + json);
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

    protected void parseJson(String json) {
        try {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray nameArray = jsonObject.getJSONArray("category");
            for (int i = 0; i < nameArray.length(); i++) {
                JSONObject object = nameArray.getJSONObject(i);
                String name = object.getString("name");
                Log.i(TAG, "parseJson: name:" + name);
                names.add(name);
            }
            adapter.notifyDataSetChanged();
            Log.i(TAG, "fornames: " + names.size());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void back(View view) {
        finish();
    }
}
