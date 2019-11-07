
package com.mindvalley.test.newModels;

import com.google.gson.annotations.SerializedName;


public class User {

    @SerializedName("id")
    private String mId;
    @SerializedName("links")
    private Links mLinks;
    @SerializedName("name")
    private String mName;
    @SerializedName("profile_image")
    private ProfileImage mProfileImage;
    @SerializedName("username")
    private String mUsername;

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public Links getLinks() {
        return mLinks;
    }

    public void setLinks(Links links) {
        mLinks = links;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public ProfileImage getProfileImage() {
        return mProfileImage;
    }

    public void setProfileImage(ProfileImage profileImage) {
        mProfileImage = profileImage;
    }

    public String getUsername() {
        return mUsername;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

}
