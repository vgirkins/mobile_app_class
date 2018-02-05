package com.csci448.vgirkins.geoquiz;

/**
 * Created by Tori on 1/19/2018.
 */

public class Question {
    private int mTextResId;
    private boolean mAnswerTrue;

    public Question(int textResId, boolean answerTrue){
        this.mTextResId = textResId;
        this.mAnswerTrue = answerTrue;
    };
    public boolean getAnswerTrue(){
        return mAnswerTrue;
    };
    public int getTextResId(){
        return mTextResId;
    };
    public void setAnswerTrue(boolean answerTrue){
        this.mAnswerTrue = answerTrue;
    };
    public void setTextResId(int textResId){
        this.mTextResId = textResId;
    };
    }
