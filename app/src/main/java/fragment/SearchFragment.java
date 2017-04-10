package fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.zhouxu.redboy.R;
import com.example.zhouxu.redboy.SearchResultActivity;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import activity.AccountCenterActivity;
import activity.Constans;
import activity.HelpCenterActivity;
import activity.LoginActivity;
import bean.SearchHistory;
import utils.GlobalConfig;
import utils.UiUtils;

public class SearchFragment extends BaseFragment implements View.OnClickListener {


    private static final String TAG = "SearchFragment";
    private EditText etsearch;
    private Button btnsearch;
    private RelativeLayout rlhotsearch;
    private RelativeLayout rlhistorysearch;
    private ListView lv_hotsearch;
    private ListView lv_historysearch;
    private List<String> hotkeywords = new ArrayList<String>();
    private List<SearchHistory> searchhistory = new ArrayList<SearchHistory>();
    private ArrayAdapter<String> adapter;
    private ArrayAdapter<SearchHistory> adapter2;
    private DbUtils dbUtils;

    @Override
    public int getlayoutId() {
        return R.layout.fragment_search;
    }

    public void initView(View view) {
        etsearch = (EditText) view.findViewById(R.id.et_search);
        btnsearch = (Button) view.findViewById(R.id.btn_search);
        rlhotsearch = (RelativeLayout) view.findViewById(R.id.rl_hot_search);
        rlhistorysearch = (RelativeLayout) view.findViewById(R.id.rl_history_search);
        lv_hotsearch = (ListView) view.findViewById(R.id.lv_hot_search);
        lv_historysearch = (ListView) view.findViewById(R.id.lv_history_search);


    }


    public void initListener() {
        adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, hotkeywords);
        lv_hotsearch.setAdapter(adapter);
        adapter2 = new ArrayAdapter<SearchHistory>(getActivity(), android.R.layout.simple_list_item_1, searchhistory);
        lv_historysearch.setAdapter(adapter2);
        btnsearch.setOnClickListener(this);

        rlhistorysearch.setOnClickListener(this);
        rlhotsearch.setOnClickListener(this);
        lv_hotsearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String itemstr = (String) parent.getItemAtPosition(position);
                etsearch.setText(itemstr);
            }
        });
        lv_historysearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                SearchHistory searchHistory = (SearchHistory) parent.getItemAtPosition(position);
                etsearch.setText(searchHistory.name);
            }
        });
    }


    public void initData() {
        parseJson();
        dbUtils = DbUtils.create(getActivity(), "search.db");
        updateSearchHistory();


    }

    private void updateSearchHistory() {
        try {
            List<SearchHistory> dbUtilsAll = dbUtils.findAll(SearchHistory.class);
            if (dbUtilsAll != null) {
                searchhistory.clear();
                if (dbUtilsAll.size() > 10) {
                    for (int i = dbUtilsAll.size()-1; i >= dbUtilsAll.size()-11; i--) {
                        searchhistory.add(dbUtilsAll.get(i));
                    }

                } else {
                    for (int i = dbUtilsAll.size() - 1; i >= 0; i--) {
                        searchhistory.add(dbUtilsAll.get(i));
                    }

                }

                adapter2.notifyDataSetChanged();

            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void parseJson() {
        String url = Constans.BASEURL + "/search/recommend";
        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                String jsonstr = responseInfo.result;
                try {

                    JSONObject jb = new JSONObject(jsonstr);
                    String searchrecommend = jb.getString("response");
                    if (!"searchrecommend".equals(searchrecommend)) {
                        Toast.makeText(getActivity(), "获取失败", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    JSONArray keywords = jb.getJSONArray("searchKeywords");
                    for (int i = 0; i < keywords.length(); i++) {
                        hotkeywords.add(keywords.getString(i));
                    }
                    Log.i(TAG, "onSuccess hotkeywords: " + hotkeywords);
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(HttpException e, String s) {

                e.printStackTrace();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_search:
                String searchcontent = etsearch.getText().toString().trim();
                if (TextUtils.isEmpty(searchcontent)) {
                    Toast.makeText(getActivity(), "搜索不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                SearchHistory sh = new SearchHistory(searchcontent);
                try {
                    dbUtils.save(sh);
                    updateSearchHistory();
                    Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                    intent.putExtra("name", searchcontent);
                    startActivity(intent);
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;
            case R.id.rl_hot_search:
                boolean shown = lv_hotsearch.isShown();
                if (shown) {
                    lv_hotsearch.setVisibility(View.GONE);
                } else {
                    lv_hotsearch.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.rl_history_search:
                boolean shown2 = lv_historysearch.isShown();
                if (shown2) {
                    lv_historysearch.setVisibility(View.GONE);
                } else {
                    lv_historysearch.setVisibility(View.VISIBLE);
                }
                break;
        }

    }

    public static class MoreFragment extends Fragment {

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

            View view = UiUtils.inflate(R.layout.fragment_more);
            ViewUtils.inject(this, view);
            return view;
        }

        @OnClick({ R.id.item_account, R.id.rl_help_center })

        public void click(View view) {

            switch (view.getId()) {

            case R.id.item_account:

                if (GlobalConfig.isLogin()) {
                    startActivity(new Intent(getActivity(),
                            AccountCenterActivity.class));
                }

                else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
                break;

            case R.id.rl_help_center:

                startActivity(new Intent(getActivity(), HelpCenterActivity.class));

                break;

            default:
                break;
            }
        }
    }
}
