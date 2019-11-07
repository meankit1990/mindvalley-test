
package com.mindvalley.test.newModels;

import com.google.gson.annotations.SerializedName;


public class Links {

    @SerializedName("html")
    private String mHtml;
    @SerializedName("likes")
    private String mLikes;
    @SerializedName("photos")
    private String mPhotos;
    @SerializedName("self")
    private String mSelf;

    public String getHtml() {
        return mHtml;
    }

    public void setHtml(String html) {
        mHtml = html;
    }

    public String getLikes() {
        return mLikes;
    }

    public void setLikes(String likes) {
        mLikes = likes;
    }

    public String getPhotos() {
        return mPhotos;
    }

    public void setPhotos(String photos) {
        mPhotos = photos;
    }

    public String getSelf() {
        return mSelf;
    }

    public void setSelf(String self) {
        mSelf = self;
    }

}
