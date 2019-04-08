package com.utsavgupta.cmclage.Adapters;


public class xample_iteam {
    private int mImageResource;
    private String mText1;
    private String mText2;
    private String nam;

    public xample_iteam(int imageResource, String name,String text1, String text2) {
        mImageResource = imageResource;
        mText1 = text1;
        mText2 = text2;
        nam=name;
    }
public String getNam(){
        return nam;
}
    public int getImageResource() {
        return mImageResource;
    }

    public String getText1() {
        return mText1;
    }

    public String getText2() {
        return mText2;
    }
}