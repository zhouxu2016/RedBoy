package activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhouxu.redboy.R;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

import utils.GlobalConfig;
import utils.NetworkRequestUtils;


@ContentView(R.layout.activity_accountcenter)
public class AccountCenterActivity extends BaseActivity {

	@ViewInject(R.id.tv_username)
	private TextView tv_username;

	@ViewInject(R.id.tv_level)
	private TextView tv_level;

	@ViewInject(R.id.tv_bonus)
	private TextView tv_bonus;

	@ViewInject(R.id.tv_ordercount)
	private TextView tv_ordercount;


	@ViewInject(R.id.tv_favoritesCount)
	private TextView tv_favoritesCount;


	private String username;
	private String level;
	private long bonus;
	private int orderCount;
	private int favoritesCount;
	private Context context;
	private String TAG = "AccountCenterActivity";
	private Intent intent;


	@Override
	protected void init() {
		super.init();
		context = this;
		long userId = GlobalConfig.getUserId();

//		http://localhost:8080/ECServerz19/userinfo?userId=9
		String url = Constans.USERINFO + "?userId="+userId;

		NetworkRequestUtils.sendGet(url, new RequestCallBack<String>() {

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

	}


	protected void parseJson(String json) {

		try {

			JSONObject jsonObject = new JSONObject(json);
			String response = jsonObject.getString("response");
			if ("error".equals(response)) {
				Toast.makeText(getApplicationContext(), "获取信息失败",
						Toast.LENGTH_SHORT).show();
			}
			else {

				JSONObject userInfo = jsonObject.getJSONObject("userInfo");

				username = userInfo.getString("username");
				level = userInfo.getString("level");
				bonus = userInfo.getLong("bonus");


//				获取订单数
				orderCount = userInfo.getInt("orderCount");
//				获取收藏个数
				favoritesCount = userInfo.getInt("favoritesCount");

				System.out.println(username+"?"+level+"?"+bonus+"?"+orderCount);

				showViewData();

			}
		}

		catch (JSONException e) {
			e.printStackTrace();
		}
	}


	/**
	 * @param view  处理账户中心的点击跳转
	 */
	@OnClick({R.id.btn_exit , R.id.item_order , R.id.item_address , R.id.item_gift , R.id.item_collect})
	public void logout(View view){

		switch (view.getId()) {

			case R.id.btn_exit :

				GlobalConfig.setUserId(0);
				finish();
				startActivity(new Intent(getBaseContext(), LoginActivity.class));
				break;

//			我的订单 跳转到订单列表页面
			case R.id.item_order :

				intent = new Intent(context, OrderListActivity.class);
				startActivity(intent);

				break;

//			地址管理
			case R.id.item_address :

//				跳转到地址列表页面
				intent = new Intent(context,ConsigneeAddressActivity.class);
//				需要传递用户的id,地址列表根据用户id显示用户的地址列表
				long userId = GlobalConfig.getUserId();
				intent.putExtra("userId",userId);
				startActivity(intent);
				break;

//			礼品卡
			case R.id.item_gift :

				startActivity(new Intent(context,GiftActivity.class));

				break;

//			收藏夹
			case R.id.item_collect :

				startActivity(new Intent(context,FavoritesActivity.class));

				break;


			default:
				break;

		}

	}

	//	展示数据

	protected void showViewData() {

		tv_username.setText("用户名:   "+username);
		tv_level.setText("会员等级:  "+level);
		tv_bonus.setText("总积分:  "+bonus);
		tv_ordercount.setText("我的订单("+orderCount+")");
		tv_favoritesCount.setText("收藏夹("+favoritesCount+")");

	}


}
