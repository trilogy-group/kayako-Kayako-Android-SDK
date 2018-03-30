package com.kayako.sdk.android.k5.messenger.messagelistpage;

import android.os.Handler;
import com.kayako.sdk.android.k5.common.utils.NetworkUtils;
import com.kayako.sdk.android.k5.core.Kayako;
import com.kayako.sdk.android.k5.messenger.data.conversation.ConversationStore;
import com.kayako.sdk.auth.FingerprintAuth;
import com.kayako.sdk.base.callback.EmptyCallback;
import com.kayako.sdk.base.callback.ItemCallback;
import com.kayako.sdk.base.callback.ListCallback;
import com.kayako.sdk.error.KayakoException;
import com.kayako.sdk.error.ResponseMessages;
import com.kayako.sdk.error.response.Notification;
import com.kayako.sdk.messenger.Messenger;
import com.kayako.sdk.messenger.conversation.Conversation;
import com.kayako.sdk.messenger.conversation.PostConversationBodyParams;
import com.kayako.sdk.messenger.message.Message;
import com.kayako.sdk.messenger.message.PostMessageBodyParams;
import com.kayako.sdk.messenger.message.PutMessageBodyParams;
import com.kayako.sdk.messenger.rating.PostRatingBodyParams;
import com.kayako.sdk.messenger.rating.PutRatingBodyParams;
import com.kayako.sdk.messenger.rating.Rating;
import java.util.List;
import javax.annotation.Generated;
import org.junit.Test;
import com.aurea.unittest.commons.pojo.chain.TestChain;
import com.openpojo.validation.Validator;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.aurea.unittest.commons.pojo.Testers;

@Generated("GeneralPatterns")
public class MessageListContainerRepositoryPojoTest {

    @Test
    public void test_validate_MessageListContainerRepository_Constructors() {
        Validator validator = TestChain.startWith(Testers.constructorTester()).buildValidator();
        validator.validate(PojoClassFactory.getPojoClass(MessageListContainerRepository.class));
    }
}
