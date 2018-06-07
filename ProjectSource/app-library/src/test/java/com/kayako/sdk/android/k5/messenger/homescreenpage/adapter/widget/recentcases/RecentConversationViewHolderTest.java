package com.kayako.sdk.android.k5.messenger.homescreenpage.adapter.widget.recentcases;

import android.view.View;
import static junit.framework.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class RecentConversationViewHolderTest {

    @Mock
    private View view;

    @Test
    public void whenValidParamThenObjectCreated() {
        //Act
        final RecentConversationViewHolder recentConversationViewHolder =
                new RecentConversationViewHolder(view);

        //Arrange
        assertNotNull(recentConversationViewHolder);
    }
}
