package com.kayako.sdk.android.k5.common.utils.file;

import android.content.Intent;
import android.support.v4.app.Fragment;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        Intent.class
})
public class FileAttachmentUtilTest {
    private static final int REQUEST_CODE = 1;

    @Mock
    private Fragment fragment;

    @Mock
    private Intent intent;

    @Test
    public void givenFragmentWhenFileChooserActivityThenStartActivityForResult(){
        //Arrange
        whenNew(Intent.class)

        //Act
        FileAttachmentUtil.openFileChooserActivityFromFragment(fragment, REQUEST_CODE);

        //Assert
        verify(fragment).startActivityForResult(,REQUEST_CODE);
    }
}
