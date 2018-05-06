package com.kayako.sdk.android.k5.messenger.attachmentpreview;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentHostCallback;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.model.LazyHeaders;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.activities.KayakoAttachmentPreviewActivity;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.AttachmentHelper;
import com.kayako.sdk.android.k5.common.utils.ImageUtils;
import com.kayako.sdk.android.k5.common.utils.file.FileAttachment;
import com.kayako.sdk.android.k5.common.utils.file.FileAttachmentUtil;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.DownloadAttachment;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.FileAttachmentDownloadHelper;
import com.kayako.sdk.auth.Auth;
import com.kayako.sdk.auth.SessionAuth;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import java.io.File;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doNothing;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.reflect.Whitebox.setInternalState;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        ImageUtils.class,
        LazyHeaders.class,
        TextUtils.class,
        LazyHeaders.Builder.class,
        AttachmentPreviewFragment.class,
        FileAttachmentUtil.class,
        AttachmentHelper.class,
        AttachmentHelper.AttachmentFileType.class

})
public class AttachmentPreviewFragmentTest {
    private static final String SESSION_ID = "sessionId";
    private static final String USER_AGENT = "userAgent";
    @Rule
    public final ExpectedException expectedException = ExpectedException.none();
    private AttachmentPreviewFragment underTest;
    @Mock
    private View loadingView;
    @Mock
    private View mRoot;
    @Mock
    private FileAttachmentDownloadHelper mFileAttachmentDownloadHelper;
    @Mock
    private DownloadAttachment mDownloadAttachment;
    @Mock
    private LayoutInflater inflater;
    @Mock
    private View view;
    @Mock
    private Bundle bundle;
    @Mock
    private FragmentHostCallback mHost;
    @Mock
    private Activity mActivity;
    @Mock
    private FragmentActivity activity;
    @Mock
    private View sendButton;
    @Mock
    private View optionsButton;
    @Mock
    private View attachmentDetails;
    @Mock
    private TextView textView;
    @Mock
    private TextView attachmentTime;
    @Mock
    private Auth auth;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        staticMock();
        underTest = spy(new AttachmentPreviewFragment());

        setInternalState(underTest, "mRoot", mRoot);
        setInternalState(underTest, "mHost", mHost);
        setInternalState(underTest, "mDownloadAttachment", mDownloadAttachment);
        setInternalState(underTest, "mFileAttachmentDownloadHelper", mFileAttachmentDownloadHelper);
        setInternalState(mHost, "mActivity", mActivity);

        doReturn(activity).when(underTest).getActivity();
    }

    @Test
    public void givenOnCreateViewThenInflaterShouldInflate() {
        underTest.onCreateView(inflater, null, null);
        verify(inflater).inflate(R.layout.ko__fragment_attachment_preview, null, false);
    }

    @Test
    public void givenOnViewCreatedThenShouldSetOnClickListener() {
        View mockedView = mock(View.class);
        doReturn(mockedView).when(mRoot).findViewById(R.id.ko__button_exit);
        underTest.onViewCreated(view, bundle);
        verify(mockedView).setOnClickListener(any(View.OnClickListener.class));
    }

    @Test
    public void givenOnActivityCreatedWhenActivityNotKayakoAttachmentPreviewActivityThenThrowIllegalStateException() {
        expectedException.expect(Exception.class);
        expectedException.expectMessage("This fragment is meant to be used with KayakoAttachmentPreviewActivity");
        when(underTest.getActivity()).thenReturn(eq(activity));
        underTest.onActivityCreated(bundle);
    }

    @Test
    public void givenOnActivityCreatedWhenImageUrlNotNullThenSetOnClickListener() {
        setUpForOnActivityCreated();
        String imageUrl = "imageUrl";
        String filePath = null;
        boolean showSendButton = true;
        View exitButton = mock(View.class);
        when(bundle.getString(anyString())).thenReturn(imageUrl, filePath, SESSION_ID, USER_AGENT);
        when(bundle.getBoolean(anyString(), anyBoolean())).thenReturn(true, true, true, true);
        doReturn(showSendButton).when(bundle).getBoolean(KayakoAttachmentPreviewActivity.ARG_SHOW_SEND_BUTTON, false);
        doReturn(exitButton).when(mRoot).findViewById(R.id.ko__button_exit);
        underTest.onActivityCreated(bundle);
        verify(exitButton).setOnClickListener(any(View.OnClickListener.class));
    }

    @Test
    public void givenOnActivityCreatedWhenImageUrlNullFilePathNotNullAndShowSendButtonFalseThenSetOnClickListener()
            throws Exception {
        setUpForOnActivityCreated();
        String imageUrl = null;
        String filePath = "filePath";
        boolean showSendButton = false;
        View exitButton = mock(View.class);
        FileAttachment fileAttachment = mock(FileAttachment.class);
        TextView attachmentTime = mock(TextView.class);
        when(FileAttachmentUtil.generateFileAttachment(anyString(), any(File.class))).thenReturn(fileAttachment);
        when(bundle.getString(anyString())).thenReturn(imageUrl, filePath, SESSION_ID, USER_AGENT,
                "attachment_name", "attachment_download_url");
        when(bundle.getLong(anyString(), anyLong())).thenReturn(0L, 0L);
        when(bundle.getBoolean(anyString(), anyBoolean())).thenReturn(true, true, true, true, true, true, true, true);
        doReturn(showSendButton).when(bundle).getBoolean(KayakoAttachmentPreviewActivity.ARG_SHOW_SEND_BUTTON, false);
        doReturn(exitButton).when(mRoot).findViewById(R.id.ko__button_exit);
        doReturn(attachmentTime).when(mRoot).findViewById(R.id.ko__attachment_date);

        underTest.onActivityCreated(bundle);
        verify(exitButton).setOnClickListener(any(View.OnClickListener.class));
    }

    @Test
    public void givenOnActivityCreatedWhenImageUrlAndFilePathIsNullThenIllegalStateException() {
        expectedException.expect(IllegalStateException.class);
        expectedException.expectMessage("INVALID STATE - need at least imageUrl or filePath");
        KayakoAttachmentPreviewActivity kayakoAttachmentPreviewActivity = mock(KayakoAttachmentPreviewActivity.class);
        Intent intent = mock(Intent.class);
        Bundle mockedBundle = mock(Bundle.class);
        when(underTest.getActivity()).thenReturn(kayakoAttachmentPreviewActivity);
        doReturn(intent).when(kayakoAttachmentPreviewActivity).getIntent();
        doReturn(mockedBundle).when(intent).getExtras();
        underTest.onActivityCreated(bundle);
    }

    @Test
    public void givenExtractAuthInfoWhenBundleContainsSessionInfoThenReturnSessionAuth() throws Exception {
        when(bundle.containsKey(anyString())).thenReturn(true, true);
        when(bundle.getString(KayakoAttachmentPreviewActivity.ARG_AGENT_SESSION_ID, null)).thenReturn(SESSION_ID);
        when(bundle.getString(KayakoAttachmentPreviewActivity.ARG_AGENT_USER_AGENT, null)).thenReturn(USER_AGENT);
        SessionAuth sessionAuth = Whitebox.<SessionAuth>invokeMethod(underTest, "extractAuthInfo", bundle);
        Assert.assertNotNull(sessionAuth);
    }

    @Test
    public void
    givenConfigureImageForUrlAttachmentShouldSetImageViewAndLoadingViewVisibleAndAttachmentPlaceholderGone() throws
            Exception {
        ImageView imageView = mock(ImageView.class);
        View attachmentPlaceholder = mock(View.class);
        View loadingView = mock(View.class);
        String imageUrl = "imageUrl";
        Auth auth = null;
        Whitebox.invokeMethod(underTest, "configureImageForUrlAttachment", imageView, attachmentPlaceholder,
                loadingView, imageUrl, auth);
        verify(imageView).setVisibility(View.VISIBLE);
        verify(loadingView).setVisibility(View.VISIBLE);
        verify(attachmentPlaceholder).setVisibility(View.GONE);
    }

    @Test
    public void givenShowAttachmentMenuShouldShowPopup() throws Exception {
        View view = mock(View.class);
        PopupMenu popupMenu = mock(PopupMenu.class);
        whenNew(PopupMenu.class).withArguments(any(FragmentActivity.class), any(View.class)).thenReturn(popupMenu);
        Whitebox.invokeMethod(underTest, "showAttachmentMenu", view);
        verify(popupMenu).setOnMenuItemClickListener(any(AttachmentPreviewFragment.class));
        verify(popupMenu).inflate(R.menu.ko__menu_attachment);
        verify(popupMenu).show();
    }

    @Test
    public void givenOnMenuItemClickWhenMenItemThenOnClickAttachmentToDownload() {
        MenuItem item = mock(MenuItem.class);
        doReturn(R.id.ko__action_download).when(item).getItemId();
        when(FileAttachmentUtil.checkFileAccessPermissions(any(FragmentActivity.class))).thenReturn(true);
        Boolean result = underTest.onMenuItemClick(item);
        verify(mFileAttachmentDownloadHelper).onClickAttachmentToDownload(mDownloadAttachment, true);
        Assert.assertTrue(result);
    }

    @Test
    public void givenOnMenuItemClickWhenMenItemIdIsNotKoActionDownloadThenReturnFalse() {
        MenuItem item = mock(MenuItem.class);
        doReturn(0).when(item).getItemId();
        Boolean result = underTest.onMenuItemClick(item);
        Assert.assertFalse(result);
    }

    @Test
    public void givenFinishByExitThenActivityShouldSetOnResultAndOnBackPressed() {
        underTest.finishByExit();
        verify(activity).setResult(anyInt());
        verify(activity).onBackPressed();
    }

    @Test
    public void givenFinishByExitWhenActivityNullShouldReturn() {
        doReturn(null).when(underTest).getActivity();
        underTest.finishByExit();
    }

    @Test
    public void givenFinishByClickingSendThenActivityShouldSetResultAndFinish() {
        underTest.finishByClickingSend();
        verify(activity).setResult(anyInt());
        verify(activity).finish();
    }

    @Test
    public void givenConfigureImageForFileAttachmentThenImageViewVisibleAttachmentPlaceholderGone() throws Exception {
        ImageView imageView = mock(ImageView.class);
        View attachmentPlaceholder = mock(View.class);
        FileAttachment fileAttachment = mock(FileAttachment.class);
        Context context = mock(Context.class);
        when(FileAttachmentUtil.generateFileAttachment(anyString(), any(File.class))).thenReturn(fileAttachment);
        when(AttachmentHelper.identifyType(anyString(), anyString())).thenReturn(AttachmentHelper.AttachmentFileType
                .IMAGE);
        when(fileAttachment.getMimeType()).thenReturn("");
        when(fileAttachment.getName()).thenReturn("");
        when(underTest.getContext()).thenReturn(context);
        Whitebox.invokeMethod(underTest, "configureImageForFileAttachment", imageView, attachmentPlaceholder, "");
        verify(imageView).setVisibility(View.VISIBLE);
        verify(attachmentPlaceholder).setVisibility(View.GONE);
        verifyStatic();
        ImageUtils.loadFileAsAttachmentImage(any(Context.class), any(ImageView.class), any(File.class), anyBoolean(),
                anyBoolean());
    }

    @Test
    public void givenConfigureViewForPreviewingUploadedAttachmentsThenSetButtons() throws Exception {
        Long time = 10L;
        String name = "name";
        String downloadUrl = "downloadUrl";
        Long fileSize = 10L;
        setupForConfigureViewForPreviewingUploadedAttachments();
        Whitebox.invokeMethod(underTest, "configureViewForPreviewingUploadedAttachments", sendButton, optionsButton,
                attachmentDetails, time, name, downloadUrl, fileSize, auth);
        verify(sendButton).setVisibility(View.GONE);
        verify(optionsButton).setOnClickListener(any(View.OnClickListener.class));
        verify(optionsButton).setVisibility(View.VISIBLE);
    }

    @Test
    public void givenConfigureViewForPreviewingUploadedAttachmentsThenSetTextViews() throws Exception {
        Long time = 10L;
        String name = "name";
        String downloadUrl = "downloadUrl";
        Long fileSize = 10L;
        setupForConfigureViewForPreviewingUploadedAttachments();
        Whitebox.invokeMethod(underTest, "configureViewForPreviewingUploadedAttachments", sendButton, optionsButton,
                attachmentDetails, time, name, downloadUrl, fileSize, auth);
        verify(textView).setText(name);
        verify(attachmentDetails).setVisibility(View.VISIBLE);
        verify(attachmentTime).setText(anyString());
        verify(attachmentTime).setVisibility(View.VISIBLE);
    }

    @After
    public void resetMocks() {
        reset(underTest);
    }

    private void setupForConfigureViewForPreviewingUploadedAttachments() {
        when(mRoot.findViewById(R.id.ko__attachment_name)).thenReturn(textView);
        when(mRoot.findViewById(R.id.ko__attachment_date)).thenReturn(attachmentTime);
    }

    private void setUpForOnActivityCreated() {
        KayakoAttachmentPreviewActivity kayakoAttachmentPreviewActivity = mock(KayakoAttachmentPreviewActivity.class);
        Intent intent = mock(Intent.class);
        View attachmentPlaceholder = mock(View.class);
        ImageView imageView = mock(ImageView.class);
        View loadingView = mock(View.class);
        View sendButton = mock(View.class);
        View attachmentDetails = mock(View.class);
        View optionsButton = mock(View.class);
        TextView textView = mock(TextView.class);
        ImageView imageView1 = mock(ImageView.class);

        when(underTest.getActivity()).thenReturn(kayakoAttachmentPreviewActivity);
        doReturn(intent).when(kayakoAttachmentPreviewActivity).getIntent();
        doReturn(bundle).when(intent).getExtras();
        when(mRoot.findViewById(R.id.ko__attachment_placeholder)).thenReturn(attachmentPlaceholder);
        doReturn(imageView).when(mRoot).findViewById(R.id.ko__attachment_image);
        doReturn(loadingView).when(mRoot).findViewById(R.id.ko__attachment_loader);
        LazyHeaders.Builder builder = mock(LazyHeaders.Builder.class);
        LazyHeaders lazyHeaders = mock(LazyHeaders.class);
        when(builder.build()).thenReturn(lazyHeaders);
        doReturn(sendButton).when(mRoot).findViewById(R.id.ko__reply_box_send_button);
        doReturn(attachmentDetails).when(mRoot).findViewById(R.id.ko__attachment_details);
        doReturn(optionsButton).when(mRoot).findViewById(R.id.ko__button_options);
        when(attachmentPlaceholder.findViewById(R.id.ko__attachment_placeholder_text)).thenReturn(textView);
        doNothing().when(textView).setText(any(CharSequence.class));
        when(attachmentPlaceholder.findViewById(R.id.ko__attachment_placeholder_icon)).thenReturn(imageView1);
        doNothing().when(imageView1).setImageResource(anyInt());
    }


    private void staticMock() {
        mockStatic(ImageUtils.class);
        mockStatic(LazyHeaders.class);
        mockStatic(TextUtils.class);
        mockStatic(LazyHeaders.Builder.class);
        mockStatic(FileAttachmentUtil.class);
        mockStatic(AttachmentHelper.class);
        mockStatic(AttachmentHelper.AttachmentFileType.class);
    }
}
