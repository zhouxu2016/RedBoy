package activity;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhouxu.redboy.R;
import com.lidroid.xutils.DbUtils;
import com.lidroid.xutils.exception.DbException;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.view.annotation.ContentView;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import bean.Help;
import utils.NetworkRequestUtils;
import utils.SpUtils;

@ContentView(R.layout.activity_help_center)
public class HelpCenterActivity extends MyBaseActivity {

    @ViewInject(R.id.btn_back)
    private Button btn_back;

    @ViewInject(R.id.lv_listview)
    private ListView lv_listview;

    private Context context;
    private String TAG = "HelpCenterActivity";
//   适配器的数据源集合
    private List<Help> helpList = new ArrayList<>();
    private MyHelpAdapter adapter;
    private DbUtils dbUtils;


    @Override
    protected void initView() {

        super.initView();
        context = this;

        adapter = new MyHelpAdapter();
        lv_listview.setAdapter(adapter);
        lv_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(context,HelpDetailActivity.class);
                intent.putExtra("id",helpList.get(position).id);
                intent.putExtra("helpTitle",helpList.get(position).title);
                startActivity(intent);


            }
        });

    }

    /**
     * 初始化数据库请求网络数据
     */

    @Override
    protected void init() {
        super.init();
// 使用DbUtils创建数据库对象
        dbUtils = DbUtils.create(context, "help.db");
//        json数据类型不知的话的,String是万能的
        String version = SpUtils.getStringValue(context, "version");

        System.out.println(version+"==========");
        if(!"0".equals(version)) {
//            是老用户
//            先获取数据库中的数据,然后请求服务器上的数据,使客户端的数据和服务端一样
            requestDBData();
        }

//      如果是新用户就请求网络数据
        requsetNetworkData(version);



    }

    private void requestDBData() {

//        使用DbUtils获取所有的数据

        try {

            List<Help> helps = dbUtils.findAll(Help.class);

            if(helps != null) {

                helpList.addAll(helps);
//                数据源改变,更新适配器
                adapter.notifyDataSetChanged();
            }
        }


        catch (DbException e) {
            e.printStackTrace();
        }

    }

    private void requsetNetworkData(String version) {

//        拼接url http://localhost:8080/ECServerz19/help?version=0
        String url = Constans.helpCenter + "?version="+version;
//        网络请求
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


    /**  json解析
     * @param jsonStr
     */
    private void parseJson(String jsonStr) {

        try {

            JSONObject jsonObject = new JSONObject(jsonStr);
            String version = jsonObject.getString("version");
            SpUtils.setStringValue(context,"version",version);
//            获取json数组
            JSONArray helpJsonArray = jsonObject.getJSONArray("helpList");
            for(int i =0 ; i < helpJsonArray.length() ; i++) {

                JSONObject helpJsonObject = helpJsonArray.getJSONObject(i);
                int id = helpJsonObject.getInt("id");
                String title = helpJsonObject.getString("title");

                Help help = new Help(id,title);
                helpList.add(help);
//                更新适配器
                adapter.notifyDataSetChanged();
//                将请求解析之后的数据保存在本地数据库
                dbUtils.save(help);

            }


        }

        catch (Exception e) {
            e.printStackTrace();
        }

    }

    private class MyHelpAdapter extends BaseAdapter {

        @Override
        public int getCount() {

            return helpList.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder = null;
            if(convertView == null) {

                holder = new ViewHolder();
                convertView = View.inflate(context,R.layout.item__help_center,null);
                holder.tv_title = (TextView) convertView.findViewById(R.id.tv_bar_title);

                convertView.setTag(holder);
            }
            else {

                holder = (ViewHolder) convertView.getTag();
            }

            holder.tv_title.setText(helpList.get(position)+"  :");

            return convertView;

        }

        @Override
        public Object getItem(int position) {
            return helpList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }
    }

    private class ViewHolder {

        private TextView tv_title;
        private ImageView iv_image;

    }


    @OnClick(R.id.btn_back )
    public void click(View view) {

        finish();
    }



}
