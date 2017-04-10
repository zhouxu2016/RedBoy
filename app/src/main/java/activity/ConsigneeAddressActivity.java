package activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhouxu.redboy.R;
import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;
import bean.AddressListRes;


/**
 *
 */
@ContentView(R.layout.activity_consignee_address)
public class ConsigneeAddressActivity extends LyBaseActivity {

    @ViewInject(R.id.btn_compute)
    private Button btn;

    @ViewInject(R.id.btn_new_address)
    private Button btnnewaddress;

    @ViewInject(R.id.lv_listview)
    private ListView listview;

    private List<AddressListRes.AddressList> addresslists;
    @Override
    public void initView() {
        super.initView();

        ViewUtils.inject(this);

        addresslists = new ArrayList<>();
        HttpUtils http=new HttpUtils();
        http.send(HttpRequest.HttpMethod.GET, Constans.addresslist + "?userId=1", new RequestCallBack<Object>() {
            @Override
            public void onSuccess(ResponseInfo<Object> responseInfo) {
                String result = (String)responseInfo.result;
                Log.i("??????","网络请求成功");
                paresen(result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Toast.makeText(ConsigneeAddressActivity.this,"数据异常,请稍后!",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void paresen(String result) {
        Gson gson=new Gson();
        AddressListRes addressListRes = gson.fromJson(result,AddressListRes.class);
        addresslists = addressListRes.addresslist;
       // System.out.println(addressListRes);
        flushdata(addresslists);
    }

    private void flushdata(List<AddressListRes.AddressList> addresslists) {

        MyAdapter adapter=new MyAdapter();
        listview.setAdapter(adapter);
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return addresslists.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v=null;
            ViewHolder holder=null;

            if(convertView==null){
                holder=new ViewHolder();
                v=View.inflate(ConsigneeAddressActivity.this,R.layout.item_addresslist,null);
                holder.tvname= (TextView) v.findViewById(R.id.tv_name);
                holder.tvcall= (TextView) v.findViewById(R.id.tv_call);
                holder.tvaddress= (TextView) v.findViewById(R.id.tv_address);
                holder.tvaddresslist= (TextView) v.findViewById(R.id.tv_addresslist);
                v.setTag(holder);
            }else{
                holder = (ViewHolder) v.getTag();
                v=convertView;
            }
            AddressListRes.AddressList addresslist=addresslists.get(position);
            holder.tvname.setText(addresslist.name);
            holder.tvcall.setText(addresslist.phoneNumber);
            holder.tvaddress.setText(addresslist.provinceId+addresslist.cityId+addresslist.areaId);
            holder.tvaddresslist.setText(addresslist.addressDetail);
            return v;
        }
    }
    class ViewHolder{
        public TextView tvname;
        public TextView tvcall;
        public TextView tvaddress;
        public TextView tvaddresslist;

    }

    @Override
    public void initData() {

    }


    @Override
    public void initListener() {
        btn.setOnClickListener(this);
        btnnewaddress.setOnClickListener(this);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AddressListRes.AddressList addressList = addresslists.get(position);
                Intent data=new Intent(ctx,PayCenterActivity.class);
                data.putExtra("name",addressList.name);
                data.putExtra("call",addressList.phoneNumber);
                data.putExtra("addresslist",addressList.provinceId+addressList.cityId+addressList.areaId+addressList.addressDetail);
                ConsigneeAddressActivity.this.setResult(100,data);
                finish();
                //startActivity(data);
            }
        });

    }
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btn_compute:
                Intent intent1=new Intent(this,PayCenterActivity.class);
                startActivity(intent1);
                break;
            case R.id.btn_new_address:
                Intent intent2=new Intent(this,NewAddressActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
