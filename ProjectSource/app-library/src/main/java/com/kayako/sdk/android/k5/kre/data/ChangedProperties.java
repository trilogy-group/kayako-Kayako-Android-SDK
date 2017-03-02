package com.kayako.sdk.android.k5.kre.data;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ChangedProperties {
    public Long last_assigned_at; // in seconds
    public Integer post_count;
    public Long last_replier;
    public Long last_updated_by;
    public Long updated_at; // in seconds
    public Long last_agent_activity_at;

    public ChangedProperties(Long last_assigned_at, Integer post_count, Long last_replier, Long last_updated_by, Long updated_at, Long last_agent_activity_at) {
        this.last_assigned_at = last_assigned_at;
        this.post_count = post_count;
        this.last_replier = last_replier;
        this.last_updated_by = last_updated_by;
        this.updated_at = updated_at;
        this.last_agent_activity_at = last_agent_activity_at;
    }

    public Long getLastAssignedAt() {
        return last_assigned_at;
    }

    public Integer getPostCount() {
        return post_count;
    }

    public Long getLastReplier() {
        return last_replier;
    }

    public Long getLastUpdatedBy() {
        return last_updated_by;
    }

    public Long getUpdatedAt() {
        return updated_at;
    }

    public Long getLastAgentActivityAt() {
        return last_agent_activity_at;
    }
}
