package cn.a10086.www.recyclerviewitemdecoration;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import mine.ActionItem;
import mine.Beans;

/**
 * @author
 * @time 2017/2/14  10:33
 * @desc ${TODD}
 */
public class MainActivity2 extends Activity {


    private RecyclerView recycle_demo;
    private List<Beans> lists;
    private ArrayList<ActionItem> actions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        recycle_demo = (RecyclerView) findViewById(R.id.recycle_demo);

//   设置属性  列数
        GridLayoutManager manger = new GridLayoutManager(this, 1);
        recycle_demo.setLayoutManager(manger);
//        加载数据
        getDatas();


//       给recycleView增加悬浮的窗口

        recycle_demo.addItemDecoration(new SectionDecorationTwo(actions, this, new SectionDecorationTwo.DecorationCallback() {
            @Override
            public String getGroupId(int position) {
                if (actions.get(position).getName() != null) {
                    return actions.get(position).getName();
                }
                return "-1";
            }

            @Override
            public String getGroupFirstLine(int position) {
                if (actions.get(position).getName() != null) {
                    return actions.get(position).getName();
                }
                return "";
            }
        }));
        MyAdapter adapter = new MyAdapter(this, lists);
        recycle_demo.setAdapter(adapter);

    }

    private void getDatas() {
//模拟从网络上获取数据
        lists = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            Beans bean = new Beans();
            bean.setDate(new Date(System.currentTimeMillis() + i * 60 * 1000 * 60 * 12).toLocaleString());
            bean.setName("子条目" + i);
            bean.setDec("描述" + i);
            bean.setImgId(R.mipmap.ic_launcher);
            lists.add(bean);
        }

//        设置顶部的条目的介绍
        fillAction(lists);
    }

    private void fillAction(List<Beans> lists) {
        actions = new ArrayList();

        for (int i = 0; i < lists.size(); i++) {
            ActionItem item = new ActionItem();
            item.setName(lists.get(i).getDate());
            actions.add(item);
        }


    }


}
