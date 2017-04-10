package activity;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhouxu.redboy.R;
import com.google.gson.Gson;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import java.util.ArrayList;
import java.util.List;

import bean.OrderTable;
import utils.GlobalConfig;
import utils.NetworkRequestUtils;


@ContentView(R.layout.activity_orderlist)
public class OrderListActivity extends MyBaseActivity {


    @ViewInject(R.id.tv_orderkind1)
    private TextView tv_orderkind1;

    @ViewInject(R.id.tv_orderkind2)
    private TextView tv_orderkind2;

    @ViewInject(R.id.tv_orderkind3)
    private TextView tv_orderkind3;

    /*@ViewInject(R.id.lv_listview)
    private ListView lv_listview;*/

    @ViewInject(R.id.fl_layout)
    private FrameLayout fl_layout;

    private ListView lv_listview;


    private Context context;
    private String TAG = "OrderListActivity";
    private List<OrderTable.OrderInfor> orderList = new ArrayList<>();
    private MyOrderListAdapter adapter;
    private int type;
    private long userId;


    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//      先注册,初始化view

        ViewUtils.inject(this);
        context = this;
//      初始化网络请求
        init();
//      初始化监听
        initListener();

    }*/


    @Override
    protected void initView() {

        super.initView();
        context = this;
    }


    @Override
    protected void init() {

        super.init();


        type = 1;
        userId = GlobalConfig.getUserId();

        RequestData(type, userId);

    }

    private void RequestData(int type, long userId) {

        //        http://localhost:8080/ECServerz19/orderlist?type=1&userId=22

        String url = Constans.ORDERLIST + "?type="+type+"&userId="+userId;

        NetworkRequestUtils.sendGet(url, new RequestCallBack<String>() {

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                Log.i(TAG,"网络请求成功");
                String jsonStr = responseInfo.result;
                parseJson(jsonStr);
            }

            @Override
            public void onFailure(HttpException e, String s) {

                fl_layout.removeAllViews();
                View textview = View.inflate(context, R.layout.order_textview, null);
                fl_layout.addView(textview);

                e.printStackTrace();
                Log.i(TAG,"网络请求失败: "+s);
            }
        });
    }


    private void parseJson(String jsonStr) {

        Gson gson = new Gson();
        OrderTable orderTable = gson.fromJson(jsonStr, OrderTable.class);
        //Log.i(TAG,"第一个的订单编号是:"+orderTable.orderList.get(0).orderId);
        orderList = orderTable.orderList;

        if(orderList.size()==0) {

//            没有订单信息显示,界面显示没有生成订单
            fl_layout.removeAllViews();
            View textview = View.inflate(context, R.layout.order_textview, null);
            fl_layout.addView(textview);

        }

        else {

//        刷新数据源,更新适配器

//            adapter = new MyOrderListAdapter();
//            lv_listview.setAdapter(adapter);
//            adapter.notifyDataSetChanged();

            fl_layout.removeAllViews();
            View listview = View.inflate(context, R.layout.order_listview, null);
            fl_layout.addView(listview);

            lv_listview = (ListView) listview.findViewById(R.id.lv_listview);

            adapter = new MyOrderListAdapter();

            lv_listview.setAdapter(adapter);

        }
    }


    private class MyOrderListAdapter extends BaseAdapter {


        @Override
        public int getCount() {

            return orderList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if(convertView == null) {

                holder = new ViewHolder();
                convertView = View.inflate(context,R.layout.item_order,null);

                holder.tv_order_id = (TextView) convertView.findViewById(R.id.tv_order_id);
                holder.tv_order_price = (TextView) convertView.findViewById(R.id.tv_order_price);
                holder.tv_order_status = (TextView) convertView.findViewById(R.id.tv_order_status);
                holder.tv_order_time = (TextView) convertView.findViewById(R.id.tv_order_time);

                convertView.setTag(holder);

            }

            else {

                holder = (ViewHolder) convertView.getTag();
            }


            OrderTable.OrderInfor orderInfor = orderList.get(position);
            holder.tv_order_id.setText("订单编号:    "+orderInfor.orderId);
            holder.tv_order_price.setText("订单总额:    ¥"+orderInfor.price);

            if(orderInfor.status == 0) {

                holder.tv_order_status.setText("状态:     未确认");
            }
            holder.tv_order_time.setText(orderInfor.time);

            /*holder.tv_order_id.setText("订单编号:    "+11);
            holder.tv_order_price.setText("订单总额:    ¥"+11);
            holder.tv_order_status.setText("状态:    "+11);
            holder.tv_order_time.setText("11");*/


            return convertView;
        }

        @Override
        public Object getItem(int position) {

            return orderList.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }
    }

    private class ViewHolder {

        private TextView tv_order_id;
        private TextView tv_order_price;
        private TextView tv_order_status;
        private TextView tv_order_time;

    }



    @OnClick({R.id.tv_orderkind1 , R.id.tv_orderkind2 , R.id.tv_orderkind3})
    public void click(View view) {


        switch (view.getId()) {

            case R.id.tv_orderkind1:

                type = 1;
                userId = GlobalConfig.getUserId();
//                重新发起网络请求,重新获取数据,然后更新适配器,显示数据
                RequestData(type,userId);

                tv_orderkind1.setTextColor(Color.WHITE);
                tv_orderkind1.setBackgroundResource(R.drawable.segment_selected_1_bg);


                tv_orderkind2.setTextColor(Color.BLACK);
                tv_orderkind2.setBackgroundResource(R.drawable.segment_normal_2_bg);

                tv_orderkind3.setTextColor(Color.BLACK);
                tv_orderkind3.setBackgroundResource(R.drawable.segment_normal_3_bg);

                break;


            case R.id.tv_orderkind2:


                type = 2;
                userId = GlobalConfig.getUserId();
//                重新发起网络请求,重新获取数据,然后更新适配器,显示数据
                RequestData(type,userId);

                tv_orderkind2.setTextColor(Color.WHITE);
                tv_orderkind2.setBackgroundResource(R.drawable.segment_selected_2_bg);

                tv_orderkind1.setTextColor(Color.BLACK);
                tv_orderkind1.setBackgroundResource(R.drawable.segment_normal_1_bg);

                tv_orderkind3.setTextColor(Color.BLACK);
                tv_orderkind3.setBackgroundResource(R.drawable.segment_normal_3_bg);



                break;


            case R.id.tv_orderkind3:


                type = 3;
                userId = GlobalConfig.getUserId();
//              重新发起网络请求,重新获取数据,然后更新适配器,显示数据
                RequestData(type,userId);

                tv_orderkind3.setTextColor(Color.WHITE);
                tv_orderkind3.setBackgroundResource(R.drawable.segment_selected_3_bg);

                tv_orderkind1.setTextColor(Color.BLACK);
                tv_orderkind1.setBackgroundResource(R.drawable.segment_normal_1_bg);

                tv_orderkind2.setTextColor(Color.BLACK);
                tv_orderkind2.setBackgroundResource(R.drawable.segment_normal_2_bg);

                break;


            default:

                break;


        }


    }


    /**
     *  为ListView 设置条目点击监听
     */


    @Override
    protected void initListener() {

        super.initListener();

        if(orderList.size() == 0){

            return;
        }

        lv_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//                先获取被点击条目的订单ID
                OrderTable.OrderInfor orderInfor = orderList.get(position);
                long orderId = orderInfor.orderId;
//                开启订单详情的Activity界面  OrderListDetail.class
//                并且传递订单ID
                Intent intent = new Intent(context,null);
                intent.putExtra("orderId",orderId);
                startActivity(intent);

            }
        });

    }
}
