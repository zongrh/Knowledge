package com.webviewjs.js_android.method3;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.JavascriptInterface;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.webviewjs.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Js_webview_android_3 extends AppCompatActivity {

    WebView wView;

    @SuppressLint("JavascriptInterface")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.js_webview_android);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //设置WebView的相关设置,依次是:
                //支持js,不保存表单,不保存密码,不支持缩放
                //同时绑定Java对象
                wView = (WebView) findViewById(R.id.webview);
                wView.getSettings().setJavaScriptEnabled(true);
                wView.getSettings().setSaveFormData(false);
                wView.getSettings().setSavePassword(false);
                wView.getSettings().setSupportZoom(false);
                wView.getSettings().setDefaultTextEncodingName("UTF-8");
                wView.addJavascriptInterface(new SharpJS(), "sharp");
                wView.loadUrl("file:///android_asset/demo3.html");
            }
        });
    }

    //自定义一个Js的业务类,传递给JS的对象就是这个,调用时直接javascript:sharp.contactlist()
    public class SharpJS {

        @JavascriptInterface
        public void contactlist() {
            wView.post(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("contactlist()方法执行了！");
                        String json = buildJson(getContacts());
                        wView.loadUrl("javascript:show('" + json + "')");
                    } catch (Exception e) {
                        System.out.println("设置数据失败" + e);
                    }
                }
            });

        }

        @JavascriptInterface
        public void call(String phone) {
            System.out.println("call()方法执行了！");
            Intent it = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phone));
            startActivity(it);
        }
    }

    //将获取到的联系人集合写入到JsonObject对象中,再添加到JsonArray数组中
    public String buildJson(List<Contact> contacts) throws Exception {
        JSONArray array = new JSONArray();
        for (Contact contact : contacts) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", contact.getId());
            jsonObject.put("name", contact.getName());
            jsonObject.put("phone", contact.getPhone());
            array.put(jsonObject);
        }
        return array.toString();
    }

    //定义一个获取联系人的方法,返回的是List<Contact>的数据
    public List<Contact> getContacts() {
        List<Contact> Contacts = new ArrayList<Contact>();
        //①查询raw_contacts表获得联系人的id
        ContentResolver resolver = getContentResolver();
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        //查询联系人数据
        Cursor cursor = resolver.query(uri, null, null, null, null);
        while (cursor.moveToNext()) {
            Contact contact = new Contact();
            //获取联系人姓名,手机号码
            contact.setId(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID)));
            contact.setName(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)));
            contact.setPhone(cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));
            Contacts.add(contact);
        }
        cursor.close();
        Log.e("Contacts", "Contacts   " + Contacts.size());
        return Contacts;
    }
}
