package com.kayako.sdk.android.k5.core;

import static org.hamcrest.CoreMatchers.is;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class KayakoLogHelperTest {

    @Mock
    private KayakoLogHelper.PrintLogListener printLogListener;

    @Mock
    private Throwable throwable;

    @Captor
    private ArgumentCaptor<String> stringArgumentCaptor;

    @Captor
    private ArgumentCaptor<Throwable> throwableArgumentCaptor;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        KayakoLogHelper.addLogListener(printLogListener);
    }

    @Test
    public void givenKayakoLogHelperWhenDThenParametersArePassed() {
        //Arrange
        final String debugTag = "debugTag";
        final String debugMessage = "debugMessage";

        //Act
        KayakoLogHelper.d(debugTag, debugMessage);
        verify(printLogListener).printDebugLogs(stringArgumentCaptor.capture(),
                stringArgumentCaptor.capture());
        final List<String> capturedStringValues = stringArgumentCaptor.getAllValues();

        //Assert
        errorCollector.checkThat(debugTag, is(capturedStringValues.get(0)));
        errorCollector.checkThat(debugMessage, is(capturedStringValues.get(1)));
    }

    @Test
    public void givenKayakoLogHelperWhenVThenParametersArePassed() {
        //Arrange
        final String verboseTag = "verboseTag";
        final String verboseMessage = "verboseMessage";

        //Act
        KayakoLogHelper.v(verboseTag, verboseMessage);
        verify(printLogListener).printVerboseLogs(stringArgumentCaptor.capture(),
                stringArgumentCaptor.capture());
        final List<String> capturedStringValues = stringArgumentCaptor.getAllValues();

        //Assert
        errorCollector.checkThat(verboseTag, is(capturedStringValues.get(0)));
        errorCollector.checkThat(verboseMessage, is(capturedStringValues.get(1)));
    }

    @Test
    public void givenKayakoLogHelperWhenEThenParametersArePassed() {
        //Arrange
        final String errorTag = "errorTag";
        final String errorMessage = "errorMessage";

        //Act
        KayakoLogHelper.e(errorTag, errorMessage);
        verify(printLogListener).printErrorLogs(stringArgumentCaptor.capture(),
                stringArgumentCaptor.capture());
        final List<String> capturedStringValues = stringArgumentCaptor.getAllValues();

        //Assert
        errorCollector.checkThat(errorTag, is(capturedStringValues.get(0)));
        errorCollector.checkThat(errorMessage, is(capturedStringValues.get(1)));
    }

    @Test
    public void givenKayakoLogHelperWhenPrintStackTraceThenParametersArePassed() {
        //Arrange
        final String stackTraceTag = "stackTraceTag";

        //Act
        KayakoLogHelper.printStackTrace(stackTraceTag, throwable);
        verify(printLogListener).printStackTrace(stringArgumentCaptor.capture(),
                throwableArgumentCaptor.capture());

        //Assert
        errorCollector.checkThat(stackTraceTag, is(stringArgumentCaptor.getValue()));
        errorCollector.checkThat(throwable, is(throwableArgumentCaptor.getValue()));
    }

    @Test
    public void givenKayakoLogHelperWhenLogExceptionThenParametersArePassed() {
        //Arrange
        final String exceptionTag = "exceptionTag";

        //Act
        KayakoLogHelper.logException(exceptionTag, throwable);
        verify(printLogListener).logPotentialCrash(stringArgumentCaptor.capture(),
                throwableArgumentCaptor.capture());

        //Assert
        errorCollector.checkThat(exceptionTag, is(stringArgumentCaptor.getValue()));
        errorCollector.checkThat(throwable, is(throwableArgumentCaptor.getValue()));
    }
}
