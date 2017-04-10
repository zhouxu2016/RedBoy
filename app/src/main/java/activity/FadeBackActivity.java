package activity;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zhouxu.redboy.R;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

import utils.GlobalConfig;
import utils.NetworkRequestUtils;

@ContentView(R.layout.activity_fadeback)
public class FadeBackActivity extends MyBaseActivity {


    @ViewInject(R.id.btn_commit)
    private Button btn_commit;

    @ViewInject(R.id.et_content)
    private EditText et_content;

    @ViewInject(R.id.et_contactnum)
    private EditText et_contactnum;


    private Context context;
    private String TAG = "FadeBackActivity";
    private long userId;
    private String content;
    private String contactnum;


    @Override
    protected void initView() {
        super.initView();

        context = this;

    }

    @OnClick(R.id.btn_commit)
    public void click(View view) {

        userId = GlobalConfig.getUserId();
        content = et_content.getText().toString().trim();
        contactnum = et_contactnum.getText().toString().trim();


        if(TextUtils.isEmpty(content)) {

            Toast.makeText(context,"请输入反馈信息",Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(contactnum)) {

            Toast.makeText(context,"请输入您的联系方式",Toast.LENGTH_SHORT).show();
            return;
        }


        requsetNetWorkData();

    }

    private void requsetNetWorkData() {

//        String url =  "http://10.0.3.2:8080/ECServerz19/fadeback?userId=1&content=呵呵
//        POST请求

        String url = Constans.fadeback;
        RequestParams params = new RequestParams();

        params.addBodyParameter("userId",userId+"");
        params.addBodyParameter("content",content);

        NetworkRequestUtils.sendPost(url,params,new RequestCallBack<String>() {

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
            String response = jsonObject.getString("response");

            if("error".equals(response)) {

                Toast.makeText(context,"请输入反馈信息",Toast.LENGTH_SHORT).show();
            }
            else {

                Toast.makeText(context,"感谢您的反馈",Toast.LENGTH_SHORT).show();

            }
        }
        catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
