package com.ascentsmartwaves.andealr.data;

/**
 * Created by ADMIN on 05-01-2015.
 */
public class FollowersData {

    String followersName,followersHandle;
    String followersProfilePic;

    public FollowersData(String followersName, String followersHandle) {
        this.followersName = followersName;
        this.followersHandle = followersHandle;
    }

    public FollowersData(String followersName, String followersHandle, String followersProfilePic) {
        this.followersName = followersName;
        this.followersHandle = followersHandle;
        this.followersProfilePic = followersProfilePic;
    }

    public String getFollowersName() {
        return followersName;
    }

    public String getFollowersHandle() {
        return followersHandle;
    }

    public String getFollowersProfilePic() {
        return followersProfilePic;
    }
}
