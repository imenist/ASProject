package com.example.lenovo.translate;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by lenovo on 2019/7/6.
 */

public class WordAdapter extends ArrayAdapter<Word> {
    private int resourceId;
    private MyDatabaseHelper dbHelper;
    private SQLiteDatabase sd;

    public WordAdapter(Context context, int textViewResourceId, List<Word> objects){
        super(context,textViewResourceId,objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Word word = getItem(position);
        View view;
        if(convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceId,parent,false);
        }else {
            view = convertView;
        }
        TextView WordTranslation = view.findViewById(R.id.translation);
        TextView WordWord = view.findViewById(R.id.word);
        WordTranslation.setText(word.getTranslation());
        WordWord.setText(word.getWord());
        return view;
    }
}
