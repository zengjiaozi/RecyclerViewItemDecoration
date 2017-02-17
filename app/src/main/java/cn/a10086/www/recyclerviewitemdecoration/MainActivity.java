package cn.a10086.www.recyclerviewitemdecoration;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private RecyclerView recyclerView;
    private List<DataBean> comingslist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        GridLayoutManager manager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(manager);

        fillData();



        recyclerView.addItemDecoration(new SectionDecoration(dataList, this, new SectionDecoration.DecorationCallback() {
            // 返回标记id (即每一项对应的标志性的字符串)
            @Override
            public String getGroupId(int position) {
                if (dataList.get(position).getName() != null) {
                    return dataList.get(position).getName();
                }
                return "-1";
            }

            // 获取同组中的第一个内容
            @Override
            public String getGroupFirstLine(int position) {
                if (dataList.get(position).getName() != null) {
                    return dataList.get(position).getName();
                }
                return "";
            }
        }));

        MyRecyclerAdapter adapter = new MyRecyclerAdapter(this, comingslist);
        recyclerView.setAdapter(adapter);
    }

    private void fillData() {
        comingslist = new ArrayList<DataBean>();
        for (int i = 0; i < 50; i++) {
            DataBean bean = new DataBean();
            bean.setDate(new Date(System.currentTimeMillis() + i * 60 * 1000 * 60 * 12).toLocaleString());
            bean.setDec("dec" + i);
            bean.setImgId(R.mipmap.ic_launcher);
            bean.setName("name" + i);
            comingslist.add(bean);
        }
        setPullAction(comingslist);
    }

    List<NameBean> dataList;



    private void setPullAction(List<DataBean> comingslist) {
        dataList = new ArrayList<>();

        for (int i = 0; i < comingslist.size(); i++) {
            NameBean nameBean = new NameBean();
            String name0 = comingslist.get(i).getDate();
            nameBean.setName(name0);
            dataList.add(nameBean);
        }




    }
}
