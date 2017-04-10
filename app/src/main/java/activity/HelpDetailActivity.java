package activity;


import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhouxu.redboy.R;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import utils.NetworkRequestUtils;

@ContentView(R.layout.activity_help_detail)
public class HelpDetailActivity extends MyBaseActivity {


    @ViewInject(R.id.btn_back)
    private Button btn_back;

    @ViewInject(R.id.tv_helptitle)
    private TextView tv_helptitle;

    @ViewInject(R.id.tv_detail_title)
    private TextView tv_detail_title;

    @ViewInject(R.id.tv_detail_content)
    private TextView tv_detail_content;
    private long id;
    private Context context;
    private String TAG = "HelpDetailActivity";
    private String title;
    private String content;


    @Override
    protected void initView() {
        super.initView();

        context = this;
        Intent intent = getIntent();
        id = intent.getLongExtra("id",0);
        String helpTitle = intent.getStringExtra("helpTitle");
        tv_helptitle.setText(helpTitle);


    }

    @Override
    protected void init() {

        super.init();

//        String url =  "http://10.0.3.2:8080/ECServerz19/help_detail?id=1";

        String url = Constans.helpDetail + "?id=" + id;
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
            JSONArray help = jsonObject.getJSONArray("help");
            JSONObject helpJSONObject = help.getJSONObject(0);

            title = helpJSONObject.getString("title");
            content = helpJSONObject.getString("content");

            showData();


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void showData() {

        tv_detail_title.setText(title);
        tv_detail_content.setText(content);
    }

    @OnClick(R.id.btn_back )
    public void click(View view) {

        finish();
    }

}
