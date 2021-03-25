package com.kayako.sdk.android.k5.common.utils;

import com.kayako.sdk.helpcenter.locale.Locale;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LocaleUtilsTest {
    private static final String LOCALE_SPAIN = "es-ES";
    private static Locale helpCenterLocale;

    @Before
    public void setUp() {
        helpCenterLocale = new Locale();
        helpCenterLocale.setLocale(LOCALE_SPAIN);
    }

    @Test
    public void givenLocalWhenGetLocaleThenReturnUtilLocale() {
        //Arrange
        String expectedValue = "es";

        //Act
        java.util.Locale returnedLocale = LocaleUtils.getLocale(helpCenterLocale);

        //Assert
        assertEquals(expectedValue, returnedLocale.toString());
    }

    @Test
    public void givenHelpCenterLocaleAndUtilLocaleWhenCompareThenReturnBoolean() {
        //Arrange
        java.util.Locale returnedLocale = LocaleUtils.getLocale(helpCenterLocale);

        //Act
        boolean returnedValue = LocaleUtils
                .areLocalesTheSame(helpCenterLocale, returnedLocale);

        //Assert
        assertTrue(returnedValue);
    }
}
