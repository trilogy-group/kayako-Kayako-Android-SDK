package com.kayako.sdk.android.k5.core;

import static org.hamcrest.CoreMatchers.is;
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

    @Test
    public void test() {
        //Arrange
        final String debugTag = "debugTag";
        final String debugMessage = "debugMessage";
        final String verboseTag = "verboseTag";
        final String verboseMessage = "verboseMessage";
        final String errorTag = "errorTag";
        final String errorMessage = "errorMessage";
        final String stackTraceTag = "stackTraceTag";
        final String exceptionTag = "exceptionTag";

        //Act
        KayakoLogHelper.addLogListener(printLogListener);
        KayakoLogHelper.d(debugTag, debugMessage);
        KayakoLogHelper.v(verboseTag, verboseMessage);
        KayakoLogHelper.e(errorTag, errorMessage);
        KayakoLogHelper.printStackTrace(stackTraceTag, throwable);
        KayakoLogHelper.logException(exceptionTag, throwable);
        verify(printLogListener).printDebugLogs(stringArgumentCaptor.capture(),
                stringArgumentCaptor.capture());
        verify(printLogListener).printVerboseLogs(stringArgumentCaptor.capture(),
                stringArgumentCaptor.capture());
        verify(printLogListener).printErrorLogs(stringArgumentCaptor.capture(),
                stringArgumentCaptor.capture());
        verify(printLogListener).printStackTrace(stringArgumentCaptor.capture(),
                throwableArgumentCaptor.capture());
        verify(printLogListener).logPotentialCrash(stringArgumentCaptor.capture(),
                throwableArgumentCaptor.capture());
        final List<String> capturedStringValues = stringArgumentCaptor.getAllValues();
        final List<Throwable> capturedThrowableValues = throwableArgumentCaptor.getAllValues();

        //Assert
        errorCollector.checkThat(debugTag, is(capturedStringValues.get(0)));
        errorCollector.checkThat(debugMessage, is(capturedStringValues.get(1)));
        errorCollector.checkThat(verboseTag, is(capturedStringValues.get(2)));
        errorCollector.checkThat(verboseMessage, is(capturedStringValues.get(3)));
        errorCollector.checkThat(errorTag, is(capturedStringValues.get(4)));
        errorCollector.checkThat(errorMessage, is(capturedStringValues.get(5)));
        errorCollector.checkThat(stackTraceTag, is(capturedStringValues.get(6)));
        errorCollector.checkThat(exceptionTag, is(capturedStringValues.get(7)));
        errorCollector.checkThat(throwable, is(capturedThrowableValues.get(0)));
        errorCollector.checkThat(throwable, is(capturedThrowableValues.get(1)));
    }
}
