package com.example.lenovo.translate;

/**
 * Created by lenovo on 2019/7/6.
 */

public class Word {
    private String word;
    private String translation;

    public Word( String word,String translation) {
        this.translation = translation;
        this.word = word;
    }

    public String getTranslation() {
        return translation;
    }

    public String getWord() {
        return word;
    }
}
