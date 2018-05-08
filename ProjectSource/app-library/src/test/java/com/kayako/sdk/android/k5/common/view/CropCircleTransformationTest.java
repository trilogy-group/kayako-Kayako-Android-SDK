package com.kayako.sdk.android.k5.common.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({
        Glide.class,
        BitmapResource.class,
        CropCircleTransformation.class,
        Paint.class,
        Canvas.class
})
public class CropCircleTransformationTest {
    private static final int OUT_WIDTH = 10;
    private static final int OUT_HEIGHT = 10;
    private static final int SIZE = 1;
    private static final String ID = "CropCircleTransformation()";

    @Mock
    private Glide glide;

    @Mock
    private Context context;

    @Mock
    private BitmapPool bitmapPool;

    @Mock
    private BitmapResource bitmapResource;

    @Mock
    private Bitmap bitmap;

    @Mock
    private Paint paint;

    @Mock
    private Canvas canvas;

    @Test
    public void givenContextWhenConstructClassThenGetBitmapPool() {
        //Arrange
        mockStatic(Glide.class);
        when(Glide.get(context)).thenReturn(glide);

        //Act
        new CropCircleTransformation(context);

        //Assert
        verify(glide).getBitmapPool();
    }

    @Test
    public void givenResourceWidthHeightWhenTransformThenReturnResource()
            throws Exception {
        //Arrange
        CropCircleTransformation cropCircleTransformation =
                new CropCircleTransformation(bitmapPool);
        when(bitmapResource.get()).thenReturn(bitmap);
        when(bitmap.getWidth()).thenReturn(SIZE);
        when(bitmap.getHeight()).thenReturn(SIZE);
        when(bitmapPool.get(SIZE, SIZE, Bitmap.Config.ARGB_8888)).thenReturn(bitmap);
        mockStatic(BitmapResource.class);
        when(BitmapResource.obtain(bitmap, bitmapPool)).thenReturn(bitmapResource);
        mockStatic(CropCircleTransformation.class);
        whenNew(Paint.class).withNoArguments().thenReturn(paint);
        whenNew(Canvas.class).withAnyArguments().thenReturn(canvas);

        //Act
        Resource<Bitmap> returnedValue = cropCircleTransformation
                .transform(bitmapResource, OUT_WIDTH, OUT_HEIGHT);

        //Assert
        assertEquals(bitmapResource, returnedValue);
    }

    @Test
    public void givenCropCircleTransformationWhenGetIDThenGetId() {
        //Arrange
        CropCircleTransformation cropCircleTransformation =
                new CropCircleTransformation(bitmapPool);

        //Act
        String returnedValue = cropCircleTransformation.getId();

        //Assert
        assertEquals(ID, returnedValue);
    }
}
