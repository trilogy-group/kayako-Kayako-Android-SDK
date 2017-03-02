package com.kayako.sdk.android.k5.kre.helpers.presence;

import android.support.annotation.NonNull;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 24/FEB/2017: The meaning behind the joins{} and leaves{} has changed.
 * Before, it meant a user has joined (become active) or left (no longer active).
 * Now, we rely on meta data to understand the user state. joins{} represents updated information while leaves{} represent the obsolete information to be replaced
 */
class KrePresenceJsonHelper {

    private static final String JSON_NODE_LEAVES = "leaves";
    private static final String JSON_NODE_JOINS = "joins";
    private static final String JSON_NODE_METAS = "metas";

    private static final String JSON_NODE_META_USER = "user";
    private static final String JSON_ELEMENT_META_USER_ID = "id";
    private static final String JSON_ELEMENT_META_USER_FULL_NAME = "full_name";
    private static final String JSON_ELEMENT_META_USER_AVATAR = "avatar";

    private static final String JSON_ELEMENT_META_LAST_ACTIVE_AT = "last_active_at";
    private static final String JSON_ELEMENT_META_IS_VIEWING = "is_viewing";
    private static final String JSON_ELEMENT_META_IS_UPDATING = "is_updating";
    private static final String JSON_ELEMENT_META_IS_TYPING = "is_typing";
    private static final String JSON_ELEMENT_META_IS_FOREGROUND = "is_foreground";

    public static Set<PresenceUser> parsePresenceStateJson(String json) {
        JsonObject body = new JsonParser().parse(json).getAsJsonObject();
        Set<PresenceUser> users = new HashSet<>();

        if (!body.isJsonNull()) {
            Set<PresenceUser> userToRemove = extractPresenceUserFromJsonObject(body);
            users.addAll(userToRemove);
        }
        return users;
    }

    public static Set<PresenceUser> parsePresenceDiffJsonAndGetJoins(String json) {
        JsonObject body = new JsonParser().parse(json).getAsJsonObject();
        JsonObject leavesNode = body.getAsJsonObject(JSON_NODE_JOINS);
        if (!leavesNode.isJsonNull()) {
            return extractPresenceUserFromJsonObject(leavesNode);
        } else {
            return Collections.EMPTY_SET;
        }
    }

    public static Set<PresenceUser> parsePresenceDiffJsonAndGetLeaves(String json) {
        JsonObject body = new JsonParser().parse(json).getAsJsonObject();
        JsonObject leavesNode = body.getAsJsonObject(JSON_NODE_LEAVES);
        if (!leavesNode.isJsonNull()) {
            return extractPresenceUserFromJsonObject(leavesNode);
        } else {
            return Collections.EMPTY_SET;
        }
    }

    private static Set<PresenceUser> extractPresenceUserFromJsonObject(@NonNull JsonObject node) {
        Set<PresenceUser> users = new HashSet<>();
        for (Map.Entry<String, JsonElement> entry : node.entrySet()) {
            JsonObject bodyObject = entry.getValue().getAsJsonObject();
            JsonObject metaObject = bodyObject.getAsJsonArray(JSON_NODE_METAS).get(0).getAsJsonObject(); // one element in [{
            JsonObject userObject = metaObject.get(JSON_NODE_META_USER).getAsJsonObject();

            PresenceMetaActivityData presenceMetaActivityData = extractMetaActivityData(metaObject);
            PresenceMetaUserData presenceMetaUserData = extractMetaUserData(userObject);
            users.add(new PresenceUser(presenceMetaUserData, presenceMetaActivityData));
        }
        return users;
    }

    private static PresenceMetaUserData extractMetaUserData(@NonNull JsonObject userNode) {
        return new PresenceMetaUserData(
                userNode.get(JSON_ELEMENT_META_USER_ID).getAsLong(),
                userNode.get(JSON_ELEMENT_META_USER_FULL_NAME).getAsString(),
                userNode.get(JSON_ELEMENT_META_USER_AVATAR).getAsString()
        );
    }

    private static PresenceMetaActivityData extractMetaActivityData(@NonNull JsonObject metaNode) {
        Boolean isTyping = metaNode.has(JSON_ELEMENT_META_IS_TYPING) ? metaNode.get(JSON_ELEMENT_META_IS_TYPING).getAsBoolean() : null;
        Boolean isUpdating = metaNode.has(JSON_ELEMENT_META_IS_UPDATING) ? metaNode.get(JSON_ELEMENT_META_IS_UPDATING).getAsBoolean() : null;
        Boolean isViewing = metaNode.has(JSON_ELEMENT_META_IS_VIEWING) ? metaNode.get(JSON_ELEMENT_META_IS_VIEWING).getAsBoolean() : null;
        Boolean isForeground = metaNode.has(JSON_ELEMENT_META_IS_FOREGROUND) ? metaNode.get(JSON_ELEMENT_META_IS_FOREGROUND).getAsBoolean() : null;
        Long lastActiveAt = metaNode.has(JSON_ELEMENT_META_LAST_ACTIVE_AT) ? metaNode.get(JSON_ELEMENT_META_LAST_ACTIVE_AT).getAsLong() : null;

        return new PresenceMetaActivityData(
                isTyping,
                isUpdating,
                lastActiveAt,
                isViewing,
                isForeground
        );
    }
}
