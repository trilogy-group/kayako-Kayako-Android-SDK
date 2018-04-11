package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class DiffUtilsCallbackTest {

    private final List<BaseListItem> mOldItems = new ArrayList<>();
    private final List<BaseListItem> mNewItems = new ArrayList<>();
    private DiffUtilsCallback diffUtilsCallback;

    @Mock
    private BaseListItem baseListItem;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

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
        errorCollector.checkThat(diffUtilsCallback.getOldListSize(), is(2));
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