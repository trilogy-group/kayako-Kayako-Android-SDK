package com.kayako.sdk.android.k5.messenger.style;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.kayako.sdk.android.k5.messenger.style.type.Background;
import com.kayako.sdk.android.k5.messenger.style.type.BlankForground;
import com.kayako.sdk.android.k5.messenger.style.type.Foreground;
import com.kayako.sdk.android.k5.messenger.style.type.Gradient;
import com.kayako.sdk.android.k5.messenger.style.type.SolidColor;
import com.kayako.sdk.android.k5.messenger.style.type.Texture;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.powermock.api.mockito.PowerMockito.when;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import java.lang.reflect.Constructor;
import java.lang.reflect.Type;

@PrepareForTest(JsonObject.class)
@RunWith(PowerMockRunner.class)
public class GsonFactoryTest {

    private static final String SERIALIZE = "serialize";
    private static final String DESERIALIZE = "deserialize";
    private static final String TYPE = "type";
    private Object backgroundAdapterInstance;
    private Object foregroundAdapterInstance;

    @Mock
    private Background background;

    @Mock
    private Type typeOfSrc;

    @Mock
    private JsonSerializationContext context;

    @Mock
    private JsonDeserializationContext deserializationContext;

    @Mock
    private JsonElement jsonElement;

    @Mock
    private JsonObject jsonObject;

    @Mock
    private Foreground foreground;

    @Before
    public void setUp() throws Exception {
        final Class backgroundAdapterClazz = Whitebox.getInnerClassType(GsonFactory.class, "BackgroundAdapter");
        final Constructor backgroundAdapterConstructor = Whitebox.getConstructor(backgroundAdapterClazz);
        backgroundAdapterInstance = backgroundAdapterConstructor.newInstance();
        final Class foregroundAdapterClazz = Whitebox.getInnerClassType(GsonFactory.class, "ForegroundAdapter");
        final Constructor foregroundAdapterConstructor = Whitebox.getConstructor(foregroundAdapterClazz);
        foregroundAdapterInstance = foregroundAdapterConstructor.newInstance();
    }

    @Test
    public void getGson() {
        //Act
        Gson gson = GsonFactory.getGson();

        //Assert
        assertNotNull(gson);
    }

    @Test
    public void serializeWhenBackgroundNull() throws Exception {
        //Arrange
        final Background backgroundLocal = null;

        //Act
        JsonElement jsonElementLocalOne = Whitebox.invokeMethod(backgroundAdapterInstance,
                SERIALIZE, backgroundLocal, typeOfSrc, context);

        //Assert
        assertNull(jsonElementLocalOne);
    }

    @Test
    public void serializeWhenBackgroundGradient() throws Exception {
        //Arrange
        when(background.getType()).thenReturn(Background.BackgroundType.GRADIENT);
        when(context.serialize(background, Gradient.class)).thenReturn(jsonElement);

        //Act
        JsonElement jsonElementLocalSecond = Whitebox.invokeMethod(backgroundAdapterInstance,
                SERIALIZE, background, typeOfSrc, context);

        //Assert
        assertEquals(jsonElementLocalSecond, jsonElement);
    }

    @Test
    public void serializeWhenBackgroundSolid() throws Exception {
        //Arrange
        when(background.getType()).thenReturn(Background.BackgroundType.SOLID_COLOR);
        when(context.serialize(background, SolidColor.class)).thenReturn(jsonElement);

        //Act
        JsonElement jsonElementLocalSecond = Whitebox.invokeMethod(backgroundAdapterInstance,
                SERIALIZE, background, typeOfSrc, context);

        //Assert
        assertEquals(jsonElementLocalSecond, jsonElement);
    }

    @Test
    public void deserializeWhenBackgroundNull() throws Exception {
        //Arrange
        when(jsonElement.getAsJsonObject()).thenReturn(jsonObject);
        when(jsonObject.get(TYPE)).thenReturn(jsonElement);
        when(jsonElement.getAsString()).thenReturn("Null");

        //Act
        Background backgroundLocal = Whitebox.invokeMethod(backgroundAdapterInstance,
                DESERIALIZE, jsonElement, typeOfSrc, deserializationContext);

        //Assert
        assertNull(backgroundLocal);
    }

    @Test
    public void deserializeWhenBackgroundGradient() throws Exception {
        //Arrange
        when(jsonElement.getAsJsonObject()).thenReturn(jsonObject);
        when(jsonObject.get(TYPE)).thenReturn(jsonElement);
        when(jsonElement.getAsString()).thenReturn("GRADIENT");
        when(deserializationContext.deserialize(jsonElement, Gradient.class)).thenReturn(background);

        //Act
        Background backgroundLocal = Whitebox.invokeMethod(backgroundAdapterInstance,
                DESERIALIZE, jsonElement, typeOfSrc, deserializationContext);

        //Assert
        assertEquals(background, backgroundLocal);
    }

    @Test
    public void deserializeWhenBackgroundSolidColour() throws Exception {
        //Arrange
        when(jsonElement.getAsJsonObject()).thenReturn(jsonObject);
        when(jsonObject.get(TYPE)).thenReturn(jsonElement);
        when(jsonElement.getAsString()).thenReturn("SOLID_COLOR");
        when(deserializationContext.deserialize(jsonElement, SolidColor.class)).thenReturn(background);

        //Act
        Background backgroundLocal = Whitebox.invokeMethod(backgroundAdapterInstance,
                DESERIALIZE, jsonElement, typeOfSrc, deserializationContext);

        //Assert
        assertEquals(background, backgroundLocal);
    }

    @Test
    public void serializeWhenForegroundNull() throws Exception {
        //Arrange
        final Foreground foregroundLocal = null;

        //Act
        JsonElement jsonElementLocal = Whitebox.invokeMethod(foregroundAdapterInstance,
                SERIALIZE, foregroundLocal, typeOfSrc, context);

        //Assert
        assertNull(jsonElementLocal);
    }

    @Test
    public void serializeWhenForegroundTexture() throws Exception {
        //Arrange
        when(foreground.getType()).thenReturn(Foreground.ForegroundType.TEXTURE);
        when(context.serialize(foreground, Texture.class)).thenReturn(jsonElement);

        //Act
        JsonElement jsonElementLocal = Whitebox.invokeMethod(foregroundAdapterInstance,
                SERIALIZE, foreground, typeOfSrc, context);

        //Assert
        assertEquals(jsonElementLocal, jsonElement);
    }

    @Test
    public void serializeWhenForegroundNone() throws Exception {
        //Act
        when(foreground.getType()).thenReturn(Foreground.ForegroundType.NONE);
        when(context.serialize(foreground, BlankForground.class)).thenReturn(jsonElement);

        //Act
        JsonElement jsonElementLocal = Whitebox.invokeMethod(foregroundAdapterInstance,
                SERIALIZE, foreground, typeOfSrc, context);

        //Assert
        assertEquals(jsonElementLocal, jsonElement);
    }

    @Test
    public void deserializeWhenForegroundNull() throws Exception {
        //Arrange
        when(jsonElement.getAsJsonObject()).thenReturn(jsonObject);
        when(jsonObject.get(TYPE)).thenReturn(jsonElement);
        when(jsonElement.getAsString()).thenReturn("Null");

        //Act
        Foreground foregroundLocal = Whitebox.invokeMethod(foregroundAdapterInstance,
                DESERIALIZE, jsonElement, typeOfSrc, deserializationContext);

        //Assert
        assertNull(foregroundLocal);
    }

    @Test
    public void deserializeWhenForegroundTexture() throws Exception {
        //Arrange
        when(jsonElement.getAsJsonObject()).thenReturn(jsonObject);
        when(jsonObject.get(TYPE)).thenReturn(jsonElement);
        when(jsonElement.getAsString()).thenReturn("TEXTURE");
        when(deserializationContext.deserialize(jsonElement, Texture.class)).thenReturn(foreground);

        //Act
        Foreground foregroundLocal = Whitebox.invokeMethod(foregroundAdapterInstance,
                DESERIALIZE, jsonElement, typeOfSrc, deserializationContext);

        //Assert
        assertEquals(foreground, foregroundLocal);
    }

    @Test
    public void deserializeWhenForegroundNone() throws Exception {
        //Arrange
        when(jsonElement.getAsJsonObject()).thenReturn(jsonObject);
        when(jsonObject.get(TYPE)).thenReturn(jsonElement);
        when(jsonElement.getAsString()).thenReturn("NONE");
        when(deserializationContext.deserialize(jsonElement, BlankForground.class)).thenReturn(foreground);

        //Act
        Foreground foregroundLocal = Whitebox.invokeMethod(foregroundAdapterInstance,
                DESERIALIZE, jsonElement, typeOfSrc, deserializationContext);

        //Assert
        assertEquals(foreground, foregroundLocal);
    }
}
