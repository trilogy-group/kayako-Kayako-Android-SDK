package com.kayako.sdk.android.k5.helpcenter.articlelistpage;

import com.kayako.sdk.android.k5.core.HelpCenterPref;
import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.util.Locale;

@PrepareForTest(HelpCenterPref.class)
@RunWith(PowerMockRunner.class)
public class ArticleListFactoryTest {

    @Mock
    private ArticleListContract.View view;

    @Mock
    private Locale locale;

    @Test
    public void getPresenter() {
        //Arrange
        mockStatic(HelpCenterPref.class);
        final HelpCenterPref helpCenterPref = mock(HelpCenterPref.class);
        when(HelpCenterPref.getInstance()).thenReturn(helpCenterPref);
        when(helpCenterPref.getHelpCenterUrl()).thenReturn("/helpUrl");
        when(helpCenterPref.getLocale()).thenReturn(locale);

        //Act
        final ArticleListContract.Presenter presenterOne =
                ArticleListFactory.getPresenter(view);
        final ArticleListContract.Presenter presenterSecond =
                ArticleListFactory.getPresenter(view);

        //Assert
        assertEquals(presenterOne, presenterSecond);
    }
}
