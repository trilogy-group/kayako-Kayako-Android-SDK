package com.kayako.sdk.android.k5.messenger.style;

import android.graphics.Color;
import com.kayako.sdk.android.k5.messenger.style.type.Background;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.util.LinkedHashMap;
import java.util.Map;
import static com.kayako.sdk.android.k5.messenger.style.BackgroundFactory.BackgroundOption;

@RunWith(PowerMockRunner.class)
@PrepareForTest(Color.class)
public class BackgroundFactoryTest {

    private final Map<BackgroundOption, Boolean> gradientMap = new LinkedHashMap<>();
    private final Map<BackgroundOption, Boolean> solidColorMap = new LinkedHashMap<>();

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        gradientMap.put(BackgroundOption.EGGPLANT, true);
        gradientMap.put(BackgroundOption.BEETROOT, true);
        gradientMap.put(BackgroundOption.PEACH, false);
        gradientMap.put(BackgroundOption.RAW_MANGO, false);
        gradientMap.put(BackgroundOption.GREEN_APPLE, false);
        gradientMap.put(BackgroundOption.AQUA, false);
        gradientMap.put(BackgroundOption.MIDNIGHT_BLUE, true);
        solidColorMap.put(BackgroundOption.PURPLE, true);
        solidColorMap.put(BackgroundOption.BLUE, true);
        solidColorMap.put(BackgroundOption.TEAL, false);
        solidColorMap.put(BackgroundOption.GREEN, true);
        solidColorMap.put(BackgroundOption.YELLOW, false);
        solidColorMap.put(BackgroundOption.ORANGE, true);
        solidColorMap.put(BackgroundOption.RED, true);
        mockStatic(Color.class);
        when(Color.parseColor(ArgumentMatchers.anyString())).thenReturn(1);
    }

    @Test
    public void gradientBackGroundOption() {
        for (Map.Entry<BackgroundOption, Boolean> entry : gradientMap.entrySet()) {
            //Act
            final Background background = BackgroundFactory.getBackground(entry.getKey());

            //Assert
            errorCollector.checkThat(background.isDarkBackground(), is(entry.getValue()));
            errorCollector.checkThat(background.getType(), is(Background.BackgroundType.GRADIENT));
        }
    }

    @Test
    public void solidColorBackGroundOption() {
        for (Map.Entry<BackgroundOption, Boolean> entry : solidColorMap.entrySet()) {
            //Act
            final Background background = BackgroundFactory.getBackground(entry.getKey());

            //Assert
            errorCollector.checkThat(background.isDarkBackground(), is(entry.getValue()));
            errorCollector.checkThat(background.getType(), is(Background.BackgroundType.SOLID_COLOR));
        }
    }
}
