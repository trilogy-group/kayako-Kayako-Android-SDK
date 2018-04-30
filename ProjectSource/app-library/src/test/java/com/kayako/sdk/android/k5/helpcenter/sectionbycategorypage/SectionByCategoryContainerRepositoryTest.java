package com.kayako.sdk.android.k5.helpcenter.sectionbycategorypage;

import com.kayako.sdk.android.k5.core.HelpCenterPref;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import java.util.Locale;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;

@PrepareForTest(HelpCenterPref.class)
@RunWith(PowerMockRunner.class)
public class SectionByCategoryContainerRepositoryTest {

    private static final String HELP_CENTER_URL = "/helpUrl";
    private SectionByCategoryContainerRepository sectionByCategoryContainerRepository;
    private HelpCenterPref helpCenterPref;

    @Mock
    private Locale locale;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Before
    public void setUp() {
        sectionByCategoryContainerRepository = new SectionByCategoryContainerRepository(HELP_CENTER_URL, locale);
        mockStatic(HelpCenterPref.class);
        helpCenterPref = mock(HelpCenterPref.class);
        when(HelpCenterPref.getInstance()).thenReturn(helpCenterPref);
    }

    @Test
    public void isCached() {
        //Act & Assert
        assertFalse(sectionByCategoryContainerRepository.isCached());
    }

    @Test
    public void doHelpCenterPreferencesMatch() {
        //Arrange
        when(helpCenterPref.getHelpCenterUrl()).thenReturn(HELP_CENTER_URL);
        when(helpCenterPref.getLocale()).thenReturn(locale);

        //Act & Assert
        assertTrue(sectionByCategoryContainerRepository.doHelpCenterPreferencesMatch());
    }
}
