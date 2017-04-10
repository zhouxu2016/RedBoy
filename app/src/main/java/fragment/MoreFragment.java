package fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.zhouxu.redboy.R;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import activity.AboutActivity;
import activity.AccountCenterActivity;
import activity.FadeBackActivity;
import activity.HelpCenterActivity;
import activity.LoginActivity;
import utils.GlobalConfig;
import utils.UiUtils;

public class MoreFragment extends Fragment {

	@ViewInject(R.id.btn_call)
	private Button btn_call;
	private String newPhoneNumber;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

		View view = UiUtils.inflate(R.layout.fragment_more);
		ViewUtils.inject(this, view);

		String phoneNumber = btn_call.getText().toString().toString();
//		客服电话:400-1445-4566
		String substring = phoneNumber.substring(5, phoneNumber.length());
		newPhoneNumber = substring.replaceAll("-", "");


		/*
		String[] split = substring.split("-");
		StringBuilder builder = new StringBuilder();
		for(String s : split) {

			builder.append(s);
		}

		builder.toString();
*/
		return view;
	}

	@OnClick({ R.id.item_account, R.id.rl_help_center, R.id.rl_userfadeback, R.id.rl_about, R.id.btn_call})

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

			case R.id.rl_help_center :

				startActivity(new Intent(getActivity(), HelpCenterActivity.class));

				break;

			case R.id.rl_userfadeback :

				startActivity(new Intent(getActivity(), FadeBackActivity.class));

				break;

			case R.id.rl_about :

				startActivity(new Intent(getActivity(), AboutActivity.class));

				break;

			case R.id.btn_call :

//				隐式意图开启系统的拨打电话页面打电话

				// 系统在它的Activity中配置了这么一组过滤器 那么我们如果想打开这个界面 就需要去满足这里的所有条件
		/*
		 * <intent-filter> <action android:name="android.intent.action.CALL" />
		 * <category android:name="android.intent.category.DEFAULT" />
		 <data
		 * android:scheme="tel" /> </intent-filter>
		 */


				Intent intent = new Intent();
//              设置动作
				intent.setAction(Intent.ACTION_CALL);
				// 设置类别 如果是DEFAULT 代码可以不用写 系统会自动加上
				intent.addCategory("android.intent.category.DEFAULT");
//			    设置数据
				intent.setData(Uri.parse("tel:"+newPhoneNumber));
//			    开启(发送)意图
				startActivity(intent);

				break;


			default:
				break;


		}


	}

}
