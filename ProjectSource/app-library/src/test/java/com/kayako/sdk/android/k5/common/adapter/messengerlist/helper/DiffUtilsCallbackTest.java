package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DiffUtilsCallbackTest {

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Mock
    BaseListItem baseListItem;

    private final List<BaseListItem> mOldItems = new ArrayList<BaseListItem>();
    private final List<BaseListItem> mNewItems = new ArrayList<BaseListItem>();
    private DiffUtilsCallback diffUtilsCallback;

    @Before
    public void setup() {
        when(baseListItem.getItemType()).thenReturn(1);
        mOldItems.add(baseListItem);
        mOldItems.add(baseListItem);
        mNewItems.add(baseListItem);
        diffUtilsCallback = new DiffUtilsCallback(mOldItems, mNewItems);
    }

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        errorCollector.checkThat(diffUtilsCallback, notNullValue());
        errorCollector.checkThat(diffUtilsCallback.getNewListSize(), is(1));
    }

    @Test
    public void getOldListSize() {
        errorCollector.checkThat(diffUtilsCallback.getOldListSize(), is(2));
    }

    @Test
    public void getNewListSize() {
        errorCollector.checkThat(diffUtilsCallback.getNewListSize(), is(1));
    }

    @Test
    public void areItemsTheSame() {
        errorCollector.checkThat(diffUtilsCallback.areItemsTheSame(0,0), is(false));
    }

    @Test
    public void areContentsTheSame() {
        errorCollector.checkThat(diffUtilsCallback.areContentsTheSame(0,0), is(true));
    }
}
