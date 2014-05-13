package com.mk.security.security.ui;

import com.mk.security.security.R;
import com.mk.security.security.adapter.MainUIAdapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.InputStream;

public class MainActivity extends Activity implements AdapterView.OnItemClickListener
{
    private GridView gridView;

    private MainUIAdapter adapter;
    private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
//        //设置不要显示标题栏
//        requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

        sp = this.getSharedPreferences("config", Context.MODE_PRIVATE);
        gridView = (GridView) findViewById(R.id.gv_main);
        adapter = new MainUIAdapter(this);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(this);
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(final AdapterView<?> adapterView, View view, int position, long id) {
                //这个是因为，如果我们的手机被盗了，用户一看到第一个手机防盗，
                // 那样肯定会先卸载我们的程序的，所以我们在手机防盗这个item里面，设置了一个重命名的功能
                if (position == 0) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("设置");
                    builder.setMessage("请输入要设置的名称");
                    final EditText et = new EditText(MainActivity.this);
                    et.setHint("新名称");
                    builder.setView(et);
                    builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            String name = et.getText().toString();
                            if (name.equals("")) {
                                Toast.makeText(MainActivity.this, "输入内容不能为空", Toast.LENGTH_SHORT).show();
                            } else {
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putString("lostName", name);
                                editor.commit();

                                TextView tv = (TextView) findViewById(R.id.tv_main_name);
                                tv.setText(name);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
                    builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            // TODO Auto-generated method stub
                        }
                    });
                    builder.create().show();
                }
                return false;
            }
        });
	}

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        switch (i) {
            case 0 : //手机防盗
                Log.i("Security","手机防盗");
                Intent intent = new Intent(this, LostProtectedActivity.class);
                startActivity(intent);
                break;
            case 1 : //通讯卫士
                break;
            case 2 : //软件管理
                break;
            case 3 : //流量管理
                break;
            case 4 : //任务管理
                break;
            case 5 : //手机杀毒
                break;
            case 6 : //系统优化
                break;
            case 7 : //高级工具
                break;
            case 8 : //设置中心
                break;
            default :
                break;
        }
    }
}
