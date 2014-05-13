package com.mk.security.security.ui;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mk.security.security.R;
import com.mk.security.security.utils.MD5Encoder;

public class LostProtectedActivity extends ActionBarActivity implements View.OnClickListener {
    private SharedPreferences sp;
    private Dialog dialog;
    private EditText password;
    private EditText confirmPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_lost_protected);
        sp = getSharedPreferences("cofig", Context.MODE_PRIVATE);

        if (isSetPassword()) {
            showLoginDialog();
        } else {
            showFirstDialog();
        }
    }

    private void showLoginDialog() {
        dialog = new Dialog(this, R.style.MyDialog);
        View view = View.inflate(this, R.layout.login_dialog, null);
        password = (EditText) view.findViewById(R.id.et_protected_password);
        Button yes = (Button) view.findViewById(R.id.bt_protected_login_yes);
        Button cancel = (Button) view.findViewById(R.id.bt_protected_login_no);
        yes.setOnClickListener(this);
        cancel.setOnClickListener(this);
        dialog.setContentView(view);
        dialog.show();
    }

    private void showFirstDialog() {
        dialog = new Dialog(this, R.style.MyDialog);
        //这样来填充一个而已文件，比较方便
        View view = View.inflate(this, R.layout.first_dialog,null);
        password = (EditText) view.findViewById(R.id.et_protected_first_password);
        confirmPassword = (EditText) view.findViewById(R.id.et_protected_confirm_password);
        Button yes = (Button) view.findViewById(R.id.bt_protected_first_yes);
        Button cancel = (Button) view.findViewById(R.id.bt_protected_first_no);
        yes.setOnClickListener(this);
        cancel.setOnClickListener(this);
        dialog.setContentView(view);
        dialog.show();

    }

    private boolean isSetPassword() {
        String pwd = sp.getString("password", "");
        if (pwd.equals("") || pwd == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lost_protected, menu);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case  R.id.bt_protected_first_yes:
                String fp = password.getText().toString().trim();
                String cp = confirmPassword.getText().toString().trim();
                if (fp.equals("") || cp.equals("")) {
                    Toast.makeText(this, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    if (fp.equals(cp)) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putString("password", MD5Encoder.encode(fp));
                        editor.commit();
                    }else {
                        Toast.makeText(this, "两次密码不相同", Toast.LENGTH_SHORT).show();
                        return;
                    }
                }
                dialog.dismiss();
                break;

            case R.id.bt_protected_first_no:
                dialog.dismiss();
                finish();
                break;

            case  R.id.bt_protected_login_yes:
                String pwd = password.getText().toString();
                if (pwd.equals("")) {
                    Toast.makeText(this, "请输入密码", Toast.LENGTH_SHORT).show();
                } else {
                    String str = sp.getString("password", "");
                    if (MD5Encoder.encode(pwd).equals(str)) {
                        dialog.dismiss();
                    } else {
                        Toast.makeText(this, "密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case R.id.bt_protected_login_no:
                dialog.dismiss();
                finish();
                break;
            default:
                break;
        }
    }
}
