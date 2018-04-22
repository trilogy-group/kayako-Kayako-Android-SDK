package com.kayako.sdk.android.k5.messenger.toolbarview.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.AssignedAgentData;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.LastActiveAgentsData;
import com.kayako.sdk.android.k5.messenger.toolbarview.MessengerToolbarContract;
import com.kayako.sdk.android.k5.messenger.toolbarview.child.MessengerToolbarExpandedFragment;
import static junit.framework.Assert.assertEquals;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.mockito.Mockito.verify;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

@RunWith(PowerMockRunner.class)
public class MessengerToolbarExpandedFragmentTest {

    private final MessengerToolbarExpandedFragment messengerToolbarExpandedFragment
            = new MessengerToolbarExpandedFragment();

    @Mock
    private LayoutInflater inflater;

    @Mock
    private ViewGroup viewGroup;

    @Mock
    private Bundle bundle;

    @Mock
    private View view;

    @Mock
    private TextView textView;

    @Mock
    private AssignedAgentData assignedAgentData;

    @Mock
    private LastActiveAgentsData lastActiveAgentsData;

    @Mock
    private MessengerToolbarContract.OnExpandOrCollapseListener listener;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void onViewCreated() {
        //Arrange
        when(inflater.inflate(R.layout.ko__messenger_toolbar_expanded, viewGroup, false)).thenReturn(view);
        when(view.findViewById(ArgumentMatchers.anyInt())).thenReturn(view);
        when(view.findViewById(R.id.ko__unread_counter)).thenReturn(textView);

        //Act
        messengerToolbarExpandedFragment.onCreateView(inflater, viewGroup, bundle);
        messengerToolbarExpandedFragment.onViewCreated(view, bundle);

        //Assert
        verify(view, times(5)).findViewById(ArgumentMatchers.anyInt());
    }

    @Test
    public void forSimpleViewExpandedViewShouldNotExist() {
        //Arrange
        final String exceptionMessage =
                "This method should never be called. For simple view - expanded view should not exist!";

        //Assert
        thrown.expectMessage(exceptionMessage);
        thrown.expect(IllegalStateException.class);

        //Act
        messengerToolbarExpandedFragment.update("title", 2);
    }

    @Test
    public void forAssignedAgentViewExpandedViewShouldNotExist() {
        //Arrange
        final String exceptionMessage =
                "This method should never be called. For Assigned Agent view - expanded view should not exist!";

        //Assert
        thrown.expectMessage(exceptionMessage);
        thrown.expect(IllegalStateException.class);

        //Act
        messengerToolbarExpandedFragment.update(assignedAgentData, 2);
    }

    @Test
    public void updateWhenLastActiveAgentsData() {
        messengerToolbarExpandedFragment.update(lastActiveAgentsData, 10);
        verify(messengerToolbarExpandedFragment, times(1)).isAdded();
    }

    @Test
    public void setExpandCollapseButtonClicked() {
        //Act
        messengerToolbarExpandedFragment.setExpandCollapseButtonClicked(listener);
        final MessengerToolbarContract.OnExpandOrCollapseListener listenerLocal =
                Whitebox.getInternalState(messengerToolbarExpandedFragment, "mListener");

        //Assert
        assertEquals(listener, listenerLocal);
    }
}
