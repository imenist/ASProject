package com.example.lenovo.translate;


import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public MyDatabaseHelper dbHelper;
    private List<Word> wordList = new ArrayList<>();
    private SQLiteDatabase sd;
    private SQLiteDatabase sw;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*
        数据库初始化
        */
        dbHelper = new MyDatabaseHelper(this,"word.db",null,1);
        sd = dbHelper.getReadableDatabase();
        //各模块初始化
        Button translate = (Button)findViewById(R.id.translate_main);
        Button refresh = (Button)findViewById(R.id.refresh_list);
        translate.setOnClickListener(this);
        //刷新列表
        refreshList();
        //
        lv = (ListView)findViewById(R.id.lv);
        final WordAdapter adapter = new WordAdapter(MainActivity.this,R.layout.word_item,wordList);
        lv.setAdapter(adapter);
        /*lv.setAdapter(new BaseAdapter() {
            @Override
            public int getCount() {
                return wordList.size();
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
                View view;
                if(convertView == null){
                    view = View.inflate(getBaseContext(),R.layout.word_item,null);
                }else {
                    view = convertView;
                }
                Word wd = wordList.get(position);
                TextView WordTranslation = view.findViewById(R.id.translation);
                TextView WordWord = view.findViewById(R.id.word);
                WordTranslation.setText(wd.getTranslation());
                WordWord.setText(wd.getWord());
                return view;
            }
        });
        */
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int position, long id) {
                sw = dbHelper.getWritableDatabase();
                final Word wd = wordList.get(position);
                wordList.remove(position);
                adapter.notifyDataSetChanged();
                deleteDatabase(wd);
                lv.invalidate();
                Toast.makeText(MainActivity.this,wd.getWord()+"删除成功",Toast.LENGTH_SHORT).show();
            }
        });
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wordList.removeAll(wordList);
                refreshList();
                lv.setAdapter(adapter);
            }
        });
    }


    @Override
    public void onClick(View V){
        if(V.getId() == R.id.translate_main){
            dbHelper.getWritableDatabase(); //创建表
            Intent intent = new Intent(MainActivity.this,SecondActivity.class);
            //startActivityForResult(intent,1);  //下一个活动返回数据回这里
            startActivity(intent);  //普通的开启下一个活动

        }
    }

    public void refreshList(){
        sd = dbHelper.getReadableDatabase();
        Cursor cursor = sd.rawQuery("select * from word",null);
        while (cursor.moveToNext()){
            String word = cursor.getString(cursor.getColumnIndex("inputText"));
            String translation = cursor.getString(cursor.getColumnIndex("translation"));
            Word wd = new Word(word,translation);
            wordList.add(wd);
        }
    }

    public void deleteDatabase(Word wd){
        sw = dbHelper.getWritableDatabase();
        sw.delete("word"," inputText = ?",new String[]{wd.getWord()});
    }
}
