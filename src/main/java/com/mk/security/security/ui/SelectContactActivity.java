package com.mk.security.security.ui;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mk.security.security.R;
import com.mk.security.security.domain.ContactInfo;
import com.mk.security.security.engine.ContactInfoService;

import java.util.List;

public class SelectContactActivity extends ActionBarActivity {
    private ListView lv;
    private List<ContactInfo> infos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_contact);

        infos = new ContactInfoService(this).getContactInfos();

//        for (int i = 0; i < infos.size(); i++) {
//            ContactInfo contactInfo =  infos.get(i);
//            Log.i("Security", i + " 联系人:" + contactInfo.getName() + " 联系电话：" + contactInfo.getPhone());
//        }
        lv = (ListView) findViewById(R.id.lv_select_contact);
        lv.setAdapter(new SelectContactAdapter());

//        lv.setAdapter(new ArrayAdapter<ContactInfo>(this,R.layout.contact_item,infos));
//        setContentView(lv);


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                String number = infos.get(position).getPhone();
                Intent intent = new Intent();
                intent.putExtra("number", number);
                //把要返回的数据设置进去，便通过onActivityResult(int, int, Intent)拿到
                setResult(1, intent);
                finish();
            }
        });
    }

    private class SelectContactAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return infos.size();
        }

        @Override
        public Object getItem(int position) {
            return infos.get(position);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ContactInfo info = infos.get(position);
//            View view;
            ContactViews views;
            if (convertView == null) {
                Log.i("Security", position + "为空");
                views = new ContactViews();
                convertView = View.inflate(SelectContactActivity.this, R.layout.contact_item, null);
                views.tv_name = (TextView) convertView.findViewById(R.id.tv_contact_name);
                views.tv_number = (TextView) convertView.findViewById(R.id.tv_contact_number);


                convertView.setTag(views);
            } else {
//                view = convertView;
                views = (ContactViews) convertView.getTag();
                Log.i("Security", position + "不为空");
            }

            if (info.getName() != null) {
                views.tv_name.setText("联系人：" + info.getName());
            } else {
                views.tv_name.setText("unknow");
            }
            if (info.getPhone() != null) {
                views.tv_number.setText("联系电话：" + info.getPhone());
            } else {
                views.tv_number.setText("unknow");
            }

            return convertView;
        }
    }

    private class ContactViews
    {
        TextView tv_name;
        TextView tv_number;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.select_contact, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
