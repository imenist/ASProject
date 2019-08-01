package com.example.lenovo.translate;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener{
    TextView responseText;
    private MyDatabaseHelper dbHelper;
    private EditText editText_et;
    private String translation = "";
    private String inputText;

    public String getTranslation() {
        return translation;
    }

    public void setTranslation(String translation) {
        this.translation = translation;
    }

    public String getInputText() {
        return inputText;
    }

    public void setInputText(String inputText) {
        this.inputText = inputText;
    }

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new MyDatabaseHelper(this,"word.db",null,1);
        setContentView(R.layout.activity_second);
        Button search_btn = (Button) findViewById(R.id.search_btn);
        Button back_main = (Button)findViewById(R.id.back_main);
        responseText = (TextView)findViewById(R.id.response_text);
        editText_et= (EditText)findViewById(R.id.search_et);
        search_btn.setOnClickListener(this);
        back_main.setOnClickListener(this);
    }
    @Override
    public void onClick(View V){
        inputText = editText_et.getText().toString();
        setInputText(inputText);
        //搜索按钮
        if(V.getId() == R.id.search_btn){
            if (!TextUtils.isEmpty(editText_et.getText())){
                sendRequest(inputText);
                editText_et.setText("");
            }else {
                Toast.makeText(SecondActivity.this,"please input your word",Toast.LENGTH_SHORT).show();
            }
        }
        //通过一个BACK键来返回上一个活动
        if(V.getId() == R.id.back_main){
            //Intent intent = new Intent(SecondActivity.this,MainActivity.class);
            //startActivity(intent);
            finish();   //返回上一个活动（销毁这个活动）
        }
    }

    /*
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(SecondActivity.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
    */

    private void sendRequest(final String qString){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader reader = null;
                try{
                    URL url = new URL("http://fanyi.youdao.com/openapi.do?keyfrom=aaa123ddd&key=336378893&type=data&doctype=json&version=1.1&q="+qString);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    InputStream in = connection.getInputStream();

                    reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    handleJson(response.toString());
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    if(reader != null){
                        try {
                            reader.close();
                        }catch (IOException e){
                            e.printStackTrace();
                        }
                    }
                    if(connection != null){
                        connection.disconnect();
                    }
                    SQLiteDatabase db = dbHelper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("inputText",inputText);
                    values.put("translation",translation);
                    db.insert("word",null,values);
                }
            }
        }).start();
    }

    private void handleJson(String jsonData){
        try {
            JSONObject jsonObject = new JSONObject(jsonData);
            translation = jsonObject.getString("translation");
            setTranslation(translation);
            showResponse(translation);
            //JSONArray jsonArray = new JSONArray(translation);
            /* for(int i=0;i<jsonArray.length();i++){
                JSONObject jo1 = jsonArray.getJSONObject(i);
                String value = jo1.getString("value");
                String key = jo1.getString("key");
                JsonToString(value);
                JsonToString(key);
                showResponse(value);
                showResponse(key);
            }*/
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void showResponse(final String response){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                responseText.setText(response);
            }
        });
    }
}
