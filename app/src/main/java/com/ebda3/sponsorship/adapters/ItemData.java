package com.ebda3.sponsorship.adapters;

/**
 * Created by Ahmed on 6/19/2018.
 */

public class ItemData {

    String text;
    String imageId;
    public ItemData(String text, String imageId){
        this.text=text;
        this.imageId=imageId;
    }

    public String getText(){
        return text;
    }

    public String getImageId(){
        return imageId;
    }
}