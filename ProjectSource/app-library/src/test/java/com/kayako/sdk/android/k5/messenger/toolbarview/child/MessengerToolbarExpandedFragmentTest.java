package com.kayako.sdk.android.k5.messenger.toolbarview.child;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.messenger.data.conversationstarter.AssignedAgentData;
import com.kayako.sdk.android.k5.messenger.toolbarview.MessengerToolbarContract;
import static org.hamcrest.CoreMatchers.is;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.mockito.Mockito.verify;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;
import java.util.List;

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

    @Captor
    private ArgumentCaptor<Integer> captor;

    @Mock
    private MessengerToolbarContract.OnExpandOrCollapseListener listener;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void onViewCreated() {
        //Arrange
        when(inflater.inflate(R.layout.ko__messenger_toolbar_expanded, viewGroup, false)).thenReturn(view);
        when(view.findViewById(R.id.ko__messenger_toolbar_title)).thenReturn(view);
        when(view.findViewById(R.id.ko__messenger_toolbar_subtitle)).thenReturn(view);
        when(view.findViewById(R.id.ko__messenger_toolbar_back_button)).thenReturn(view);
        when(view.findViewById(R.id.ko__messenger_toolbar_avatars)).thenReturn(view);
        when(view.findViewById(R.id.ko__unread_counter)).thenReturn(textView);

        //Act
        messengerToolbarExpandedFragment.onCreateView(inflater, viewGroup, bundle);
        messengerToolbarExpandedFragment.onViewCreated(view, bundle);
        verify(view, times(5)).findViewById(captor.capture());

        //Assert
        final List<Integer> capturedValues = captor.getAllValues();
        errorCollector.checkThat(R.id.ko__messenger_toolbar_title, is(capturedValues.get(0)));
        errorCollector.checkThat(R.id.ko__messenger_toolbar_subtitle, is(capturedValues.get(1)));
        errorCollector.checkThat(R.id.ko__messenger_toolbar_back_button, is(capturedValues.get(2)));
        errorCollector.checkThat(R.id.ko__messenger_toolbar_avatars, is(capturedValues.get(3)));
        errorCollector.checkThat(R.id.ko__unread_counter, is(capturedValues.get(4)));
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
    public void setExpandCollapseButtonClicked() {
        //Act
        messengerToolbarExpandedFragment.setExpandCollapseButtonClicked(listener);
        final MessengerToolbarContract.OnExpandOrCollapseListener listenerLocal =
                Whitebox.getInternalState(messengerToolbarExpandedFragment, "mListener");

        //Assert
        errorCollector.checkThat(listener, is(listenerLocal));
    }
}
