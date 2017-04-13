package com.kayako.sdk.android.k5.messenger.data.conversationstarter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.messenger.data.conversation.UserViewModel;

public class LastActiveAgentsData {
    private String brandName;
    private Long averageReplyTime;

    private UserViewModel user1;
    private UserViewModel user2;
    private UserViewModel user3;

    public LastActiveAgentsData(@NonNull String brandName, @NonNull Long averageReplyTime) {
        this.brandName = brandName;
        this.averageReplyTime = averageReplyTime;

        if (brandName == null || averageReplyTime == null) {
            throw new IllegalArgumentException("Brand Name and AverageReplyTime can not be null!");
        }
    }


    public LastActiveAgentsData(@NonNull String brandName, @NonNull Long averageReplyTime, @Nullable UserViewModel user1, @Nullable UserViewModel user2, @Nullable UserViewModel user3) {
        this.brandName = brandName;
        this.averageReplyTime = averageReplyTime;
        this.user1 = user1;
        this.user2 = user2;
        this.user3 = user3;

        if (brandName == null || averageReplyTime == null) {
            throw new IllegalArgumentException("Brand Name and AverageReplyTime can not be null!");
        }
    }

    public String getBrandName() {
        return brandName;
    }

    public Long getAverageReplyTime() {
        return averageReplyTime;
    }

    public UserViewModel getUser1() {
        return user1;
    }

    public UserViewModel getUser2() {
        return user2;
    }

    public UserViewModel getUser3() {
        return user3;
    }
}
