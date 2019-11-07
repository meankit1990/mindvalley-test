
package com.mindvalley.test.newModels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class PinboardModel {

    @SerializedName("color")
    private String mColor;
    @SerializedName("created_at")
    private String mCreatedAt;
    @SerializedName("current_user_collections")
    private List<Object> mCurrentUserCollections;
    @SerializedName("height")
    private Long mHeight;
    @SerializedName("id")
    private String mId;
    @SerializedName("liked_by_user")
    private Boolean mLikedByUser;
    @SerializedName("likes")
    private Long mLikes;
    @SerializedName("urls")
    private Urls mUrls;
    @SerializedName("user")
    private User mUser;
    @SerializedName("width")
    private Long mWidth;

    public String getColor() {
        return mColor;
    }

    public void setColor(String color) {
        mColor = color;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public List<Object> getCurrentUserCollections() {
        return mCurrentUserCollections;
    }

    public void setCurrentUserCollections(List<Object> currentUserCollections) {
        mCurrentUserCollections = currentUserCollections;
    }

    public Long getHeight() {
        return mHeight;
    }

    public void setHeight(Long height) {
        mHeight = height;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Boolean getLikedByUser() {
        return mLikedByUser;
    }

    public void setLikedByUser(Boolean likedByUser) {
        mLikedByUser = likedByUser;
    }

    public Long getLikes() {
        return mLikes;
    }

    public void setLikes(Long likes) {
        mLikes = likes;
    }

    public Urls getUrls() {
        return mUrls;
    }

    public void setUrls(Urls urls) {
        mUrls = urls;
    }

    public User getUser() {
        return mUser;
    }

    public void setUser(User user) {
        mUser = user;
    }

    public Long getWidth() {
        return mWidth;
    }

    public void setWidth(Long width) {
        mWidth = width;
    }

}
