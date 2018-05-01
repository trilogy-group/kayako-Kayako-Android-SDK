package com.kayako.sdk.android.k5.helpcenter.sectionbycategorypage;

import com.kayako.sdk.android.k5.common.utils.LocaleUtils;
import com.kayako.sdk.android.k5.core.HelpCenterPref;
import com.kayako.sdk.helpcenter.locale.Locale;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.util.Iterator;
import java.util.List;

@PrepareForTest({
        HelpCenterPref.class,
        LocaleUtils.class
})
@RunWith(PowerMockRunner.class)
public class SectionByCategoryContainerPresenterTest {

    private SectionByCategoryContainerPresenter sectionByCategoryContainerPresenter;
    private HelpCenterPref helpCenterPref;

    @Mock
    private SectionByCategoryContainerContract.View view;

    @Mock
    private SectionByCategoryContainerContract.Data data;

    @Mock
    private Locale locale;

    @Mock
    private java.util.Locale localeUtil;

    @Mock
    private List<Locale> localeList;

    @Mock
    private Iterator<Locale> mockIterator;

    @Before
    public void setUp() {
        sectionByCategoryContainerPresenter = new SectionByCategoryContainerPresenter(view, data);
        mockStatic(HelpCenterPref.class);
        helpCenterPref = mock(HelpCenterPref.class);
        when(HelpCenterPref.getInstance()).thenReturn(helpCenterPref);
    }

    @Test
    public void initPage() {
        //Act
        sectionByCategoryContainerPresenter.initPage();

        //Assert
        verify(view, times(1)).hideToolbarSpinner();
        verify(view, times(1)).startBackgroundTask();
    }

    @Test
    public void loadDataInBackgroundWhenNoSpinnerItem() {
        //Act
        sectionByCategoryContainerPresenter.loadDataInBackground();
        sectionByCategoryContainerPresenter.onDataLoaded(true);

        //Assert
        verify(view, times(1)).hideToolbarSpinner();
        verify(view, times(1)).showToolbarTitle();
    }

    @Test
    public void loadDataInBackgroundWhenSpinnerItem() throws Exception {
        //Arrange
        suppress(method(LocaleUtils.class, "areLocalesTheSame"));
        when(data.getPublicLocales(true)).thenReturn(localeList);
        when(localeList.iterator()).thenReturn(mockIterator);
        when(mockIterator.hasNext()).thenReturn(true, false);
        when(mockIterator.next()).thenReturn(locale);
        when(locale.getNativeName()).thenReturn("native_name");
        when(helpCenterPref.getLocale()).thenReturn(localeUtil);

        //Act
        sectionByCategoryContainerPresenter.loadDataInBackground();
        sectionByCategoryContainerPresenter.onDataLoaded(true);

        //Assert
        verify(view, times(1)).showToolbarSpinner();
        verify(view, times(1)).hideToolbarTitle();
    }

    @Test
    public void loadDataInBackgroundWhenSuccessFullFalse() {
        //Arrange
        final boolean isSuccessful = Boolean.FALSE;

        //Act
        sectionByCategoryContainerPresenter.onDataLoaded(isSuccessful);

        //Assert
        verify(view, times(1)).hideToolbarSpinner();
        verify(view, times(1)).showToolbarTitle();
    }
}
