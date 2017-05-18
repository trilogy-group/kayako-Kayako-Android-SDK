package com.kayako.sdk.android.k5.messenger.data.conversationstarter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LastActiveAgentsData that = (LastActiveAgentsData) o;

        if (brandName != null ? !brandName.equals(that.brandName) : that.brandName != null)
            return false;
        if (averageReplyTime != null ? !averageReplyTime.equals(that.averageReplyTime) : that.averageReplyTime != null)
            return false;
        if (user1 != null ? !user1.equals(that.user1) : that.user1 != null) return false;
        if (user2 != null ? !user2.equals(that.user2) : that.user2 != null) return false;
        return user3 != null ? user3.equals(that.user3) : that.user3 == null;

    }

    @Override
    public int hashCode() {
        int result = brandName != null ? brandName.hashCode() : 0;
        result = 31 * result + (averageReplyTime != null ? averageReplyTime.hashCode() : 0);
        result = 31 * result + (user1 != null ? user1.hashCode() : 0);
        result = 31 * result + (user2 != null ? user2.hashCode() : 0);
        result = 31 * result + (user3 != null ? user3.hashCode() : 0);
        return result;
    }
}
