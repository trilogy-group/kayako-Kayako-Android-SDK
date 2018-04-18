package com.kayako.sdk.android.k5.messenger.style;

import com.kayako.sdk.android.k5.messenger.style.type.BlankForground;
import com.kayako.sdk.android.k5.messenger.style.type.Foreground;
import com.kayako.sdk.android.k5.messenger.style.type.Texture;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import java.util.LinkedList;
import java.util.List;
import com.kayako.sdk.android.k5.messenger.style.ForegroundFactory.ForegroundOption;

public class ForegroundFactoryTest {

    private final List<ForegroundOption> foreGroundList =
            new LinkedList<>();

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        foreGroundList.add(ForegroundOption.CONFETTI);
        foreGroundList.add(ForegroundOption.DOTS);
        foreGroundList.add(ForegroundOption.CONSTELLATION);
        foreGroundList.add(ForegroundOption.SAND);
        foreGroundList.add(ForegroundOption.CHEERIOS);
        foreGroundList.add(ForegroundOption.MOSAIC);
        foreGroundList.add(ForegroundOption.POLKA);
        foreGroundList.add(ForegroundOption.STARS);
        foreGroundList.add(ForegroundOption.NACHOS);
        foreGroundList.add(ForegroundOption.ZIGZAG);
    }

    @Test
    public void textureForegroundOption() {
        for (ForegroundOption foregroundOption : foreGroundList) {
            //Act
            final Foreground foreground = ForegroundFactory.getForeground(foregroundOption);

            //Assert
            errorCollector.checkThat(foreground, instanceOf(Texture.class));
            errorCollector.checkThat(foreground.getType(), is(Foreground.ForegroundType.TEXTURE));
        }
    }

    @Test
    public void whenNoTextureThenBlankForeground() {
        //Arrange & Act
        final Foreground foreground = ForegroundFactory.getForeground(ForegroundOption.NO_TEXTURE);

        //Assert
        errorCollector.checkThat(foreground, instanceOf(BlankForground.class));
        errorCollector.checkThat(foreground.getType(), is(Foreground.ForegroundType.NONE));
    }

    @Test
    public void whenNullThenGetForegroundReturnNull() {
        //Arrange & Act
        final Foreground foreground = ForegroundFactory.getForeground(null);

        //Assert
        errorCollector.checkThat(foreground, nullValue());
    }
}
