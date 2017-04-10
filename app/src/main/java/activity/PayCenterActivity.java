package activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.zhouxu.redboy.R;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;


/**
 *
 */
@ContentView(R.layout.pay_center_layout)
public class PayCenterActivity extends Activity implements View.OnClickListener {

    @ViewInject(R.id.btn_compile)
    private Button compile;

    @ViewInject(R.id.tv_address)
    private TextView tvaddress;

    @ViewInject(R.id.tv_pay_way)
    private TextView tvpayway;

    @ViewInject(R.id.tv_express_time)
    private TextView tvexpresstime;

    @ViewInject(R.id.tv_express_way)
    private TextView tvexpressway;

    @ViewInject(R.id.tv_favorable)
    private TextView tvfavorable;

    @ViewInject(R.id.tv_want_invoice)
    private TextView tvwantinvoice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewUtils.inject(this);
        initdata();
        listener();
    }

    public void listener() {
        compile.setOnClickListener(this);
        tvaddress.setOnClickListener(this);
        tvpayway.setOnClickListener(this);
        tvexpresstime.setOnClickListener(this);
        tvexpressway.setOnClickListener(this);
        tvfavorable.setOnClickListener(this);
        tvwantinvoice.setOnClickListener(this);

    }
    public void initdata() {
//        HttpUtils http=new HttpUtils();
//        RequestParams params=new RequestParams();
//        http.send(HttpRequest.HttpMethod.POST, Constans.paycenter, params, new RequestCallBack<Object>() {
//            @Override
//            public void onFailure(HttpException e, String s) {
//
//            }
//            @Override
//            public void onSuccess(ResponseInfo<Object> responseInfo) {
//
//            }
//        });
    }
   // private  boolean isnull=true;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case 100:
                if(data!=null){
                    String name = data.getStringExtra("name");
                    String call = data.getStringExtra("call");
                    String addresslist = data.getStringExtra("addresslist");
                    tvaddress.setText("收货人信息\n"+name+"\n"+call+"\n"+addresslist);
                }
                break;
            case 200:
                if (data!=null){
                    String payway=data.getStringExtra("payway");
                    tvpayway.setText("付款方式\n"+payway);
                }
                break;
            case 300:
                if(data!=null){
                    String expresstime = data.getStringExtra("expresstime");
                    tvexpresstime.setText("送货时间\n"+expresstime);
                }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_compile:
                break;
            case R.id.tv_address:
                Intent intent1=new Intent(this,ConsigneeAddressActivity.class);
                startActivityForResult(intent1,100);
                break;
            case R.id.tv_pay_way:
                Intent intent2=new Intent(this,PayWayActivity.class);
                startActivityForResult(intent2,200);

                break;
            case R.id.tv_express_time:
                Intent intent3=new Intent(this,ExpressTimeActivity.class);
                startActivityForResult(intent3,300);

                break;
            case R.id.tv_express_way:
                Intent intent4=new Intent(this,ExpressWayActivity.class);
                startActivityForResult(intent4,400);
                break;
            case R.id.tv_favorable:
                Intent intent5=new Intent(this,FavorableActivity.class);
                startActivityForResult(intent5,500);
                break;
            case R.id.tv_want_invoice:
                Intent intent6=new Intent(this,ExpressWayActivity.class);
                startActivityForResult(intent6,600);
                break;

        }
    }
}
