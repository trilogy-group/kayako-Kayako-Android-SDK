package com.kayako.sdk.android.k5.helpcenter.sectionbycategorypage;

import com.kayako.sdk.android.k5.core.HelpCenterPref;
import static junit.framework.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.util.Locale;

@PrepareForTest(HelpCenterPref.class)
@RunWith(PowerMockRunner.class)
public class SectionByCategoryContainerFactoryTest {

    @Mock
    private Locale locale;

    @Mock
    private SectionByCategoryContainerContract.View view;

    @Before
    public void setUp() {
        mockStatic(HelpCenterPref.class);
        final HelpCenterPref helpCenterPref = mock(HelpCenterPref.class);
        when(HelpCenterPref.getInstance()).thenReturn(helpCenterPref);
        when(helpCenterPref.getHelpCenterUrl()).thenReturn("/helpUrl");
        when(helpCenterPref.getLocale()).thenReturn(locale);
    }

    @Test
    public void getPresenter() {
        //Act
        final SectionByCategoryContainerContract.Presenter presenterOne =
                SectionByCategoryContainerFactory.getPresenter(view);
        final SectionByCategoryContainerContract.Presenter presenterSecond =
                SectionByCategoryContainerFactory.getPresenter(view);

        //Assert
        assertEquals(presenterOne, presenterSecond);
    }
}
