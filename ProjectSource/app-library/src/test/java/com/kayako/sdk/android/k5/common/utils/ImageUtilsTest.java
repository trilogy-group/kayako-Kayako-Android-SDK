package com.kayako.sdk.android.k5.common.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestListener;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.ChannelDecoration;
import com.kayako.sdk.android.k5.common.view.CircleImageView;
import com.kayako.sdk.android.k5.common.view.CropCircleTransformation;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.auth.Auth;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;
import java.io.File;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.suppress;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        Glide.class,
        LazyHeaders.class,
        LazyHeaders.Builder.class,
        ImageUtils.class,
        GlideUrl.class,
        ImageView.class,
        View.class,
        TextUtils.class,
        Kayako.class,
        AsyncTask.class
})
@SuppressStaticInitializationFor("com.bumptech.glide.load.model.LazyHeaders$Builder")
public class ImageUtilsTest {
    private static final File file = new File("");
    private static final String URL = "url";
    private static final boolean FALSE = false;
    private static final boolean TRUE = true;
    private static final int PLACE_HOLDER_DRAWABLE = 1;
    private static final int AVATAR_RES_ID = 2;
    private static final int WIDTH = 300;
    private static final int HEIGHT = 600;
    private static final boolean SHOW_PLACE_HOLDER = true;
    private static final boolean CONFIGURE_SIZE = true;
    private ImageView view;
    private CircleImageView circleView;

    @Rule
    public final ErrorCollector collector = new ErrorCollector();
    @Mock
    private CircleImageView mockCircleView;
    @Mock
    private Context context;
    @Mock
    private RequestManager requestManager;
    @Mock
    private DrawableTypeRequest<Object> drawableTypeRequest;
    @Mock
    private DrawableRequestBuilder<Object> drawableRequestBuilder;
    @Mock
    private LazyHeaders.Builder headersBuilder;
    @Mock
    private LazyHeaders lazyHeaders;
    @Mock
    private Glide glide;
    @Mock
    private CropCircleTransformation cropCircleTransformation;
    @Mock
    private ChannelDecoration channelDecoration;
    @Mock
    private Auth auth;
    @Mock
    private ImageUtils.OnImageLoadedListener onImageLoadedListener;
    @Captor
    private ArgumentCaptor<ImageView> imageViewArgumentCaptor;
    @Captor
    private ArgumentCaptor<Object> loadParameterCaptor;
    @Captor
    private ArgumentCaptor<Integer> loadIntParameterCaptor;
    @Captor
    private ArgumentCaptor<File> loadFileParameterCaptor;
    @Captor
    private ArgumentCaptor<String> loadStringParameterCaptor;
    @Captor
    private ArgumentCaptor<CircleImageView> circleImageViewArgumentCaptor;

    @Before
    public void setUp() throws Exception {
        setupGlide();
        setupDrawableTypeRequest();
        setupLazyHeaders();
        setupDrawableRequestBuilder();
        setupCropCircleTransformation();
        cropCircleTransformation = new CropCircleTransformation(context);
        view = new ImageView(context);
        suppress(method(ImageView.class, "setScaleType"));
        circleView = new CircleImageView(context);
    }

    private void setupGlide() {
        mockStatic(Glide.class);
        when(Glide.with(context)).thenReturn(requestManager);
        when(Glide.get(context)).thenReturn(glide);
        doNothing().when(glide).clearMemory();
        doNothing().when(glide).clearDiskCache();
    }

    private void setupLazyHeaders() throws Exception {
        whenNew(LazyHeaders.Builder.class).withAnyArguments().thenReturn(headersBuilder);
        doReturn(lazyHeaders).when(headersBuilder).build();
        whenNew(LazyHeaders.class).withAnyArguments().thenReturn(lazyHeaders);
    }

    private void setupDrawableTypeRequest() {
        stubChainDrawReq().load(loadParameterCaptor.capture());
        stubChainDrawReq().load(loadIntParameterCaptor.capture());
        stubChainDrawReq().load(loadFileParameterCaptor.capture());
        stubChainDrawReq().load(loadStringParameterCaptor.capture());
        stubChain().skipMemoryCache(FALSE);
        stubChain().diskCacheStrategy(DiskCacheStrategy.SOURCE);
        stubChain().centerCrop();
    }

    private RequestManager stubChainDrawReq() {
        return doReturn(drawableTypeRequest).when(requestManager);
    }

    private DrawableTypeRequest<Object> stubChain() {
        return doReturn(drawableTypeRequest).when(drawableTypeRequest);
    }

    private void setupDrawableRequestBuilder() {
        stubChainDrawTypeReq().listener(any(RequestListener.class));
        stubChainDrawTypeReq().dontAnimate();
        stubChainDrawTypeReq().override(WIDTH, HEIGHT);
        stubChainReqBReqB().dontAnimate();
        stubChainReqBReqB().placeholder(R.drawable.ko__placeholder_avatar);
        stubChainReqBReqB().bitmapTransform(cropCircleTransformation);
        stubChainReqBReqB().centerCrop();
        stubChainReqBReqB().skipMemoryCache(FALSE);
        stubChainReqBReqB().skipMemoryCache(TRUE);
        stubChainReqBReqB().diskCacheStrategy(DiskCacheStrategy.SOURCE);
        stubChainReqBReqB().diskCacheStrategy(DiskCacheStrategy.RESULT);
    }

    private DrawableRequestBuilder<Object> stubChainDrawTypeReq() {
        return doReturn(drawableRequestBuilder).when(drawableTypeRequest);
    }

    private DrawableRequestBuilder<Object> stubChainReqBReqB() {
        return doReturn(drawableRequestBuilder).when(drawableRequestBuilder);
    }

    private void setupCropCircleTransformation() throws Exception {
        whenNew(CropCircleTransformation.class).withAnyArguments()
                .thenReturn(cropCircleTransformation);
    }

    private void checkView() {
        verify(drawableRequestBuilder).into(imageViewArgumentCaptor.capture());
        collector.checkThat(imageViewArgumentCaptor.getValue(), is(view));
    }

    private void checkViewZero() {
        verify(drawableRequestBuilder, times(0))
                .into(imageViewArgumentCaptor.capture());
    }

    private void checkCircleView() {
        verify(drawableRequestBuilder).into(circleImageViewArgumentCaptor.capture());
        collector.checkThat(circleImageViewArgumentCaptor.getValue(), is(circleView));
    }

    @Test
    public void givenImageUrlIntoImageViewWhenSetAvatarImageThenShouldCallView() {
        //Act
        ImageUtils.setAvatarImage(context, view, URL);

        //Assert
        checkView();
    }

    @Test
    public void givenImageUrlIntoImageViewWhenSetImageThenShouldCallView() {
        //Arrange
        doReturn(drawableRequestBuilder).when(drawableRequestBuilder)
                .placeholder(PLACE_HOLDER_DRAWABLE);

        //Act
        ImageUtils.setImage(context, view, URL, PLACE_HOLDER_DRAWABLE);

        //Assert
        checkView();
    }

    @Test
    public void givenImageUrlIntoCircleImageViewWhenSetAvatarImageThenShouldCallCircleView() {
        //Act
        ImageUtils.setAvatarImage(context, circleView, URL);

        //Assert
        checkCircleView();
    }

    @Test
    public void givenAvatarResIdIntoImageViewWhenSetAvatarImageThenShouldCallView() {
        //Act
        ImageUtils.setAvatarImage(context, view, AVATAR_RES_ID);

        //Assert
        checkViewZero();
    }

    @Test
    public void givenChannelDecorationWhenSetChannelImageThenImageViewSetResource() {
        //Arrange
        when(channelDecoration.getSourceDrawable()).thenReturn(AVATAR_RES_ID);
        suppress(method(ImageView.class, "setImageResource"));
        suppress(method(ImageView.class, "getDrawable"));
        suppress(method(View.class, "getWidth"));
        suppress(method(View.class, "getHeight"));

        //Act
        ImageUtils.setChannelImage(context, mockCircleView, channelDecoration);

        //Assert
        verify(mockCircleView).setImageResource(AVATAR_RES_ID);
    }

    @Test
    public void givenFileWhenAttachImageThenImageViewRequest() {
        //Act
        ImageUtils.loadFileAsAttachmentImage
                (context, view, file, SHOW_PLACE_HOLDER, CONFIGURE_SIZE);

        //Assert
        checkView();
    }

    @Test
    public void givenUrlWhenAttachImageThenImageViewRequest() {
        //Arrange
        mockStatic(TextUtils.class);
        when(TextUtils.isEmpty(URL)).thenReturn(false);

        //Act
        ImageUtils.loadUrlAsAttachmentImage
                (context, view, URL, SHOW_PLACE_HOLDER,
                        CONFIGURE_SIZE, auth, onImageLoadedListener);

        //Assert
        checkView();
    }

    @Test
    public void givenContextWhenClearCacheThenClearCache() {
        //Arrange
        mockStatic(Kayako.class);
        when(Kayako.getApplicationContext()).thenReturn(context);
        suppress(method(AsyncTask.class, "execute", Object.class));

        //Act
        ImageUtils.clearCache();

        //Assert
        verify(glide).clearMemory();
    }
}
