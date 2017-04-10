package activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

@ContentView(R.layout.activity_login)
public class LoginActivity extends BaseActivity {

	@ViewInject(R.id.tv_forgot_psw)
	TextView tv_forgot_psw;
	@ViewInject(R.id.et_password)
	EditText et_password;
	@ViewInject(R.id.et_username)
	EditText et_username;
	private Context context;
	private String TAG = "LoginActivity";

	@Override
	protected void init() {
		super.init();
		context = this;
	}

	@Override
	protected void initView() {
		super.initView();
		setRegisterClick();
	}

	private void setRegisterClick() {
		tv_forgot_psw.getPaint().setUnderlineText(true);
		SpannableString ss = new SpannableString(tv_forgot_psw.getText());
		ss.setSpan(new ForegroundColorSpan(Color.BLUE), 0, 5,
				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		// ss.setSpan(new TextClickSapn(), 0, tvRegister.getText().toString()
		// .length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
		tv_forgot_psw.setText(ss);
		tv_forgot_psw.setMovementMethod(LinkMovementMethod.getInstance());
	}

	@OnClick(R.id.btn_login)
	public void login(View view) {
		String username = et_username.getText().toString();
		String password = et_password.getText().toString();
		if (TextUtils.isEmpty(username)) {
			Toast.makeText(getApplicationContext(), "用户名不能为空",
					Toast.LENGTH_SHORT).show();
			return;
		}
		if (TextUtils.isEmpty(password)) {
			Toast.makeText(getApplicationContext(), "密码不能为空",
					Toast.LENGTH_SHORT).show();
			return;
		}

		RequestParams params = new RequestParams();
		params.addBodyParameter("username", username);
		params.addBodyParameter("password", password);

		NetworkRequestUtils.sendPost(Constans.LOGIN, params, new RequestCallBack<String>() {

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {

				Log.i(TAG,"网络请求成功");
				String jsonStr = responseInfo.result;
				parseJson(jsonStr);

			}

			@Override
			public void onFailure(HttpException e, String s) {

				e.printStackTrace();
				Log.i(TAG,"网络请求失败: "+s);
			}
		});

		/*Map<String, String> params = new HashMap<String, String>();
		params.put("username", username);
		params.put("password", password);
		HttpManager http = new HttpManager(this, Constans.LOGIN, params,
				new OnRequestResonseListener() {

					@Override
					public void onSucesss(String json) {
						parseJson(json);
					}

					@Override
					public void onFailure(String errorMsg) {
						Toast.makeText(getApplicationContext(), errorMsg,
								Toast.LENGTH_SHORT).show();
					}
				});
		http.post();*/
	}

	protected void parseJson(String json) {
		try {
			JSONObject jsonObject = new JSONObject(json);
			String response = jsonObject.getString("response");
			if ("error".equals(response)) {
				Toast.makeText(getApplicationContext(), "登录失败",
						Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(getApplicationContext(), "登录成功",
						Toast.LENGTH_SHORT).show();

				JSONObject userinfo = jsonObject.getJSONObject("userInfo");
				long userid = userinfo.getLong("userId");

				//获取用户等级

				/*
				String level = userinfo.getString("level");
//				获取用户积分
				long bonus = userinfo.getLong("bonus");
//				使用sp存储
				SpUtils.setLongValue(context,"userid",userid);
				SpUtils.setStringValue(context,"level",level);
				SpUtils.setLongValue(context,"bonus",bonus);*/

				GlobalConfig.setUserId(userid);

				startActivity(new Intent(getBaseContext(),AccountCenterActivity.class));
				finish();
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	@OnClick(R.id.btn_regist)
	public void regist(View view) {
		startActivity(new Intent(getBaseContext(), RegistActivity.class));
	}
}
