package fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.zhouxu.redboy.R;
import com.example.zhouxu.redboy.SearchResultActivity;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

import activity.HotGoodsActivity;
import activity.HotOnlyActivity;
import activity.ImmediatelyBuyingActivity;
import activity.NewProductActivity;
import activity.PanicBuyingActivity;
import utils.UiUtils;

public class HomeFragment extends Fragment {
    @ViewInject(R.id.vp_pager)
    ViewPager vp_pager;
    @ViewInject(R.id.ll_point)
    LinearLayout ll_point;
    @ViewInject(R.id.rl_buying)
    RelativeLayout rl_buying;
    @ViewInject(R.id.rl_promotion_bulletin)
    RelativeLayout rl_promotion_bulletin;
    @ViewInject(R.id.rl_new_product)
    RelativeLayout rl_new_product;
    @ViewInject(R.id.rl_hotgoods)
    RelativeLayout rl_hotgoods;
    @ViewInject(R.id.rl_remen_danping)
    RelativeLayout rl_remen_danping;
    @ViewInject(R.id.img_sousou)
    ImageView img_sousou;
    @ViewInject(R.id.et_sousou)
    EditText et_sousou;
    private int[] imagers = {R.drawable.image1, R.drawable.image2, R.drawable.image3, R.drawable.image4, R.drawable.image5,R.drawable.image6};

    private List<ImageView> imags = new ArrayList<ImageView>();
    private ArrayList<View> points = new ArrayList<View>();
    //viewPager滑动事件
    private final ViewPager.OnPageChangeListener pagerOnpageChangeListener = new ViewPager.OnPageChangeListener() {

        int lastposition = 0;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            //根据选中的页面位置.改变集合中点的颜色;
            View view = points.get(position);
            view.setBackgroundResource(R.drawable.slide_adv_selected);
            View lastview = points.get(lastposition);
            lastview.setBackgroundResource(R.drawable.slide_adv_normal);

            lastposition = position;

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 99:
                    int positon = (int) msg.obj;
                    Message message = Message.obtain();
                    message.what = 99;
                    if (positon%6==0){
                        vp_pager.setCurrentItem(0,false);
                        message.obj=1;
                    }else{
                        vp_pager.setCurrentItem(positon);
                        message.obj=positon+1;
                    }
                    handler.sendMessageDelayed(message,2000);
                    break;
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = UiUtils.inflate(R.layout.fragment_home);
        ViewUtils.inject(this, v);
        initView();
        initData();

        MyHomeAdapter adapter = new MyHomeAdapter();
        vp_pager.setAdapter(adapter);
        adapter.notifyDataSetChanged();


        Message message = Message.obtain();
        message.what = 99;
        message.obj = 1;
        //两秒让viewpaer显示下一个界面
        handler.sendMessageDelayed(message,2000);
        return v;
    }
    public class MyHomeAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imags.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object objects) {
            return view == objects;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView view = imags.get(position);
            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }
    private void initView() {
        //搜索
        img_sousou.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trim = et_sousou.getText().toString().trim();
                if (!TextUtils.isEmpty(trim)){
                    Intent intent = new Intent(getActivity(), SearchResultActivity.class);
                    intent.putExtra("name",trim);
                    startActivity(intent);
                }else{
                    Toast.makeText(getActivity(), "请输入要搜索的内容", Toast.LENGTH_SHORT).show();
                }
            }
        });

        vp_pager.setOnPageChangeListener(pagerOnpageChangeListener);

        //点击限时抢购
        rl_buying.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PanicBuyingActivity.class);
                startActivity(intent);
            }
        });
        //点击促销快报
        rl_promotion_bulletin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImmediatelyBuyingActivity.class);
                startActivity(intent);
            }
        });
        //新品上架
        rl_new_product.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), NewProductActivity.class);
                startActivity(intent);
            }
        });
        //热门单品
        rl_remen_danping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HotOnlyActivity.class);
                startActivity(intent);
            }
        });
        //推荐品牌
        rl_hotgoods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HotGoodsActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initData() {
        //准备VIewpager需要的页面
        for (int i=0;i<imagers.length;i++){
            ImageView iv = new ImageView(getActivity());
            iv.setBackgroundResource(imagers[i]);
            imags.add(iv);
        }
        //准备指示点
        for (int i = 0;i<imagers.length;i++){
            View views = new View(getActivity());
            if (i==0){
                views.setBackgroundResource(R.drawable.slide_adv_selected);
            }else{
                views.setBackgroundResource(R.drawable.slide_adv_normal);
            }
            points.add(views);
            //把view添加到线性布局中.

            //view 的宽高
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10,10);
            params.setMargins(0,0,10,0);//距离右边点五个像素
            ll_point.addView(views,params);
        }
    }
}
