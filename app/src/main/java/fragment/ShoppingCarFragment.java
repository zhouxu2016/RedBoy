package fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhouxu.redboy.R;
import com.google.gson.Gson;
import com.lidroid.xutils.BitmapUtils;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.util.LogUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import activity.Constans;
import activity.PayCenterActivity;
import bean.CartBean;
import utils.UiUtils;

public class ShoppingCarFragment extends Fragment {

	@ViewInject(R.id.btn_workout)
	private Button btn_workout;

	@ViewInject(R.id.shopcar_product_list)
	ListView shopcar_product_list;
	
	@ViewInject(R.id.tv_shoppin_count)
	TextView tv_shoppin_count;
	

	private bean.CartBean cartbean;
	


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
							 Bundle savedInstanceState) {
		View view = UiUtils.inflate(R.layout.shoppingcar_fragment_full);
		
		ViewUtils.inject(this,view);

		////一进来就先获取数据
		requestDate();
		
		injectView(view);

		bitmapUtils=new BitmapUtils(getContext());

		return view;
	}


	

	/**
	 * 注册查找控件
	 */
	private void injectView(View view) {
		ViewUtils.inject(this,view);
		btn_workout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//点击结算按钮,跳转到结算中心界面
				startActivity(new Intent(getActivity(),PayCenterActivity.class));
			}
		});
	}

	//联网获取购物车信息
	private void requestDate() {
		HttpUtils httpUtils = new HttpUtils();
		String url = Constans.SHOPPINGCAR+"?userId=1";
		httpUtils.send(HttpRequest.HttpMethod.GET, url, new RequestCallBack<Object>() {
			@Override
			public void onSuccess(ResponseInfo<Object> responseInfo) {
				String result = (String) responseInfo.result;
				System.out.println("======服务器返回的json串是=====" + result);
//				Gson gson = new Gson();
//				//将服务器返回的json串封装成javabean对象
//				HomePagerInfo homePagerInfo = gson.fromJson(result, HomePagerInfo.class);
//				//将从服务器获取的图片集合赋值给为Adapter提供数据的集合
//				System.out.println("---gosn 解析后的javabean是--" + homePagerInfo);
//				topImages = homePagerInfo.homToppic;
//				adapter.notifyDataSetChanged();
				paserResultJson(result);
			}

			@Override
			public void onFailure(HttpException error, String msg) {

				System.out.println("----联网失败的原因是--" + msg);
			}
		});
	}


	
	//解析数据

	// TODO: 2016/11/13  
	private void paserResultJson(String result) {

		Gson gson=new Gson();
		cartbean = gson.fromJson(result, CartBean.class);
		
		System.out.println("++++++++++++++"+cartbean.cart.cartItemList.size());
        
		
		//在得到数据后为listview设置适配器
		MyAdapter adapter=new MyAdapter();
		shopcar_product_list.setAdapter(adapter);
		adapter.notifyDataSetChanged();

		tv_shoppin_count.setText(cartbean.cart.cartItemList.size()+"");
		
		
	}

//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		//// TODO: 2016/11/15     用户登录还没有处理就先直接跳转到activity 
//		int userId = (int) GlobalConfig.getUserId();
//		if (userId == 0) {
//			Intent intent = new Intent(getActivity(), LoginActivity.class);
//			getActivity().startActivity(intent);
////            getActivity().finish();
////            FragmentManager fragmentManager = getFragmentManager();
////            FragmentTransaction transaction = fragmentManager.beginTransaction();
////            transaction.replace(R.id.ll_shop_car,new MoreFragment());
////            transaction.commit();
//		}
//		if (cartbean.cart.cartItemList.size()==0) {
//			//如果购物车没有物品跳转到空界面 
//			Intent intent = new Intent(getActivity(), shopCarNull.class);
//			startActivity(intent);
//		}
//	}




	private BitmapUtils bitmapUtils;
	//加入购物车的商品的listview的适配器
	public class MyAdapter extends BaseAdapter {
		@Override
		public int getCount() {
			return cartbean.cart.cartItemList.size();
		}

		@Override
		public Object getItem(int i) {
			return null;
		}

		@Override
		public long getItemId(int i) {
			return 0;
		}

		@Override
		public View getView(int i, View convertView, ViewGroup viewGroup) {
//			View view=null;
			ViewHolder vh;
			if (convertView==null){
//				convertView= UiUtils.inflate(R.layout.item_listview_shopping_car);
				convertView= UiUtils.inflate(R.layout.item_listview_shopping_car);
				
				//convertView=View.inflate(UiUtils.getContext(),R.layout.item_listview_shopping_car,null);
				vh = new ViewHolder();
				//找到子view
				ImageView product_icon=(ImageView)convertView.findViewById(R.id.iv_product_pic);
				TextView product_name=(TextView)convertView.findViewById(R.id.tv_product_name);
				 vh.tv_product_count=(TextView)convertView.findViewById(R.id.tv_product_count);
				TextView product_size=(TextView)convertView.findViewById(R.id.tv_product_size);
				TextView product_color=(TextView)convertView.findViewById(R.id.tv_product_color);
				TextView product_price=(TextView)convertView.findViewById(R.id.tv_product_price);
				TextView product_sum=(TextView)convertView.findViewById(R.id.tv_product_pay_money);
				ImageView product_go= (ImageView) convertView.findViewById(R.id.iv_lv_product_go);

				//打包
				vh.ivlockiv_product_icon=product_icon;
				vh.tv_product_name=product_name;
//				vh.tv_product_count=product_count;
				vh.tv_product_size=product_size;
				vh.tv_product_color=product_color;
				vh.tv_product_price=product_price;
				vh.tv_product_sum=product_sum;
				vh.iv_lv_product_go=product_go;

				convertView.setTag(vh);

			}else {
				convertView=convertView;
				vh = (ViewHolder) convertView.getTag();
			}


			CartBean.Cart.CartItemList itemList = cartbean.cart.cartItemList.get(i);
			LogUtils.d("itemList.product.brandId:======"+itemList.product.commentcount+"");
			//设置数据
			vh.tv_product_count.setText("数量"+"1");  //数量
			vh.tv_product_name.setText(itemList.product.name);   //商品名字
			vh.tv_product_price.setText("价格"+itemList.product.price);   //商品价格
			//图片需要联网
			bitmapUtils.display(vh.ivlockiv_product_icon,itemList.product.pic);   //图片
			
			
			return convertView;
		}
	}

	public class ViewHolder{
		public TextView tv_product_count;
		public ImageView iv_lv_product_go;
		public TextView tv_product_name;
		public ImageView ivlockiv_product_icon;
		public TextView tv_product_size;
		public TextView tv_product_color;
		public TextView tv_product_price;
		public TextView tv_product_sum;
	}
	
}
