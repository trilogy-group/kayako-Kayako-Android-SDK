package com.kayako.sdk.android.k5.helpcenter.sectionbycategorypage;

import com.kayako.sdk.android.k5.core.HelpCenterPref;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.util.Locale;
import static junit.framework.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@PrepareForTest(HelpCenterPref.class)
@RunWith(PowerMockRunner.class)
public class SectionByCategoryFactoryTest {

    @Mock
    private Locale locale;

    @Mock
    private SectionByCategoryContract.View view;

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
        final SectionByCategoryContract.Presenter presenterOne =
                SectionByCategoryFactory.getPresenter(view);
        final SectionByCategoryContract.Presenter presenterSecond =
                SectionByCategoryFactory.getPresenter(view);

        //Assert
        assertEquals(presenterOne, presenterSecond);
    }
}
