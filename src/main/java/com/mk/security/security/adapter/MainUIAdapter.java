package com.mk.security.security.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mk.security.security.R;

/**
 * Created by Administrator on 2014/5/12.
 */
public class MainUIAdapter extends BaseAdapter {
    private static final String[] NAMES = new String[] {"手机防盗", "通讯卫士", "软件管理", "流量管理", "任务管理", "手机杀毒",
            "系统优化", "高级工具", "设置中心"};
    private static final int[] ICONS = new int[] {R.drawable.widget01, R.drawable.widget02, R.drawable.widget03,
            R.drawable.widget04, R.drawable.widget05, R.drawable.widget06, R.drawable.widget07,
            R.drawable.widget08, R.drawable.widget09};
    //声明成静态，起到一定的优化作用，关于adapter还有别的优化方法的，有机会我们再说
    private static ImageView imageView;
    private static TextView textView;

    private Context context;
    //对于一个没有被载入或者想要动态载入的界面, 都需要使用inflate来载入
    private LayoutInflater inflater;
    private SharedPreferences sp;

    public MainUIAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(this.context);
        sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);


    }


    @Override
    public int getCount() {
        return NAMES.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        /*View view = inflater.inflate(R.layout.main_item,null);
        imageView = (ImageView) view.findViewById(R.id.iv_main_icon);
        textView = (TextView) view.findViewById(R.id.tv_main_name);
        imageView.setImageResource(ICONS[position]);
        textView.setText(NAMES[position]);

        if (position == 0) {
            String name = sp.getString("lostName", "");
            if (!name.equals("")) {
                textView.setText(name);
            }
        }
        return view;*/

        //convertView 相当于缓存一样，只要我们判断一下它是不是为null，就可以知道现在这个view有没有绘制过出来
        //如果没有，那么就重新绘制，如果有，那么就可以使用缓存啦，这样就可以大大的节省view绘制的时间了，进行了优化，使ListView更加流畅
        MainViews views;
        View view;
        if(convertView == null){
            views = new MainViews();
            view = inflater.inflate(R.layout.main_item,null);
            views.imageView = (ImageView) view.findViewById(R.id.iv_main_icon);
            views.textView = (TextView) view.findViewById(R.id.tv_main_name);
            views.imageView.setImageResource(ICONS[position]);
            views.textView.setText(NAMES[position]);
            view.setTag(views);
        } else {
            view = convertView;
            views = (MainViews) view.getTag();
            views.imageView = (ImageView) view.findViewById(R.id.iv_main_icon);
            views.textView = (TextView) view.findViewById(R.id.tv_main_name);
            views.imageView.setImageResource(ICONS[position]);
            views.textView.setText(NAMES[position]);
        }
        if (position == 0) {
            String name = sp.getString("lostName", "");
            if (!name.equals("")) {
                textView.setText(name);
            }
        }
        return view;
    }

    //==================================================================================

    //一个存放所有要绘制的控件的类
    private class MainViews
    {
        ImageView imageView;
        TextView textView;
    }
}
