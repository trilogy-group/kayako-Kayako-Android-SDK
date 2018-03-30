package com.kayako.sdk.android.k5.messenger.messagelistpage;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.Attachment;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.AttachmentUrlType;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.AttachmentHelper;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.TypingViewHelper;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.helper.UnsentMessage;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.EmptyListItem;
import com.kayako.sdk.android.k5.common.adapter.messengerlist.view.InputEmailListItem;
import com.kayako.sdk.android.k5.common.fragments.ListPageState;
import com.kayako.sdk.android.k5.common.fragments.OnScrollListListener;
import com.kayako.sdk.android.k5.common.utils.FailsafePollingHelper;
import com.kayako.sdk.android.k5.common.utils.file.FileAttachment;
import com.kayako.sdk.android.k5.core.MessengerPref;
import com.kayako.sdk.android.k5.messenger.data.conversation.unreadcounter.UnreadCounterRepository;
import com.kayako.sdk.android.k5.messenger.data.conversation.viewmodel.UserViewModel;
import com.kayako.sdk.android.k5.messenger.data.realtime.OnConversationChangeListener;
import com.kayako.sdk.android.k5.messenger.data.realtime.OnConversationClientActivityListener;
import com.kayako.sdk.android.k5.messenger.data.realtime.OnConversationMessagesChangeListener;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.AddReplyHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.AssignedAgentToolbarHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.ClientIdHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.ConversationHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.ConversationMessagesHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.FileAttachmentDownloadHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.FileAttachmentHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.MessengerListHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.MarkReadHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.MessengerPrefHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.OffboardingHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.OnboardingHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.OptimisticSendingViewHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.RealtimeHelper;
import com.kayako.sdk.android.k5.messenger.messagelistpage.helpers.ReplyBoxViewHelper;
import com.kayako.sdk.base.requester.AttachmentFile;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.message.Message;
import com.kayako.sdk.messenger.message.MessageSourceType;
import com.kayako.sdk.messenger.message.PostMessageBodyParams;
import com.kayako.sdk.messenger.rating.PostRatingBodyParams;
import com.kayako.sdk.messenger.rating.PutRatingBodyParams;
import com.kayako.sdk.messenger.rating.Rating;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class MessageListContainerPresenterPojoTest {

    @Test
    public void test_validate_MessageListContainerPresenter_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(MessageListContainerPresenter.class));
    }
}
