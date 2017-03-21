package com.kayako.sdk.android.k5.messenger.toolbarview.child;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class LastActiveAgentsData {
    private String brandName;
    private Long averageReplyTime;

    private ActiveUser user1;
    private ActiveUser user2;
    private ActiveUser user3;

    public LastActiveAgentsData(@NonNull String brandName, @NonNull Long averageReplyTime) {
        this.brandName = brandName;
        this.averageReplyTime = averageReplyTime;

        if (brandName == null || averageReplyTime == null) {
            throw new IllegalArgumentException("Brand Name and AverageReplyTime can not be null!");
        }
    }


    public LastActiveAgentsData(@NonNull String brandName, @NonNull Long averageReplyTime, @Nullable ActiveUser user1, @Nullable ActiveUser user2, @Nullable ActiveUser user3) {
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

    public ActiveUser getUser1() {
        return user1;
    }

    public ActiveUser getUser2() {
        return user2;
    }

    public ActiveUser getUser3() {
        return user3;
    }
}
