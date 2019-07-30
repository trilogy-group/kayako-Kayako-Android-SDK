package com.kayako.sdk.android.k5.messenger.style;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.kayako.sdk.android.k5.messenger.style.type.Background;
import com.kayako.sdk.android.k5.messenger.style.type.BlankForground;
import com.kayako.sdk.android.k5.messenger.style.type.Foreground;
import com.kayako.sdk.android.k5.messenger.style.type.Gradient;
import com.kayako.sdk.android.k5.messenger.style.type.SolidColor;
import com.kayako.sdk.android.k5.messenger.style.type.Texture;

import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

public class GsonFactory {

    private GsonFactory() {
    }

    private static Gson gson;

    public static Gson getGson() {
        if (gson == null) {
            gson = new GsonBuilder()
                    .excludeFieldsWithModifiers(Modifier.FINAL, Modifier.TRANSIENT, Modifier.STATIC) // Fixes a GSON bug which causes app to fail on Android M
                    .registerTypeAdapter(Background.class, new BackgroundAdapter())
                    .registerTypeAdapter(Foreground.class, new ForegroundAdapter()) // TODO; ForegroundAdapter
                    .create();
        }

        return gson;
    }

    private static class BackgroundAdapter implements JsonSerializer<Background>, JsonDeserializer<Background> {

        @Override
        public Background deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String type = json.getAsJsonObject().get("type").getAsString();

            if (type.equals(Background.BackgroundType.GRADIENT.name())) {
                return context.deserialize(json, Gradient.class);
            } else if (type.equals(Background.BackgroundType.SOLID_COLOR.name())) {
                return context.deserialize(json, SolidColor.class);
            }
            return null;
        }

        @Override
        public JsonElement serialize(Background src, Type typeOfSrc, JsonSerializationContext context) {
            if (src == null || src.getType() == null) {
                return null;
            }

            switch (src.getType()) {
                case GRADIENT:
                    return context.serialize(src, Gradient.class);

                case SOLID_COLOR:
                    return context.serialize(src, SolidColor.class);
            default:
                break;
            }

            return null;
        }
    }

    private static class ForegroundAdapter implements JsonSerializer<Foreground>, JsonDeserializer<Foreground> {

        @Override
        public Foreground deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            String type = json.getAsJsonObject().get("type").getAsString();

            if (type.equals(Foreground.ForegroundType.TEXTURE.name())) {
                return context.deserialize(json, Texture.class);
            } else if (type.equals(Foreground.ForegroundType.NONE.name())) {
                return context.deserialize(json, BlankForground.class);
            }
            return null;
        }

        @Override
        public JsonElement serialize(Foreground src, Type typeOfSrc, JsonSerializationContext context) {
            if (src == null || src.getType() == null) {
                return null;
            }

            switch (src.getType()) {
                case TEXTURE:
                    return context.serialize(src, Texture.class);

                case NONE:
                    return context.serialize(src, BlankForground.class);
            default:
                break;
            }

            return null;
        }
    }

}
