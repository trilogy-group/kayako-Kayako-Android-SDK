package com.kayako.sdk.android.k5.common.adapter.messengerlist;

import com.kayako.sdk.android.k5.common.adapter.loadmorelist.LoadMoreListType;

public interface MessengerListType extends LoadMoreListType {

    // Message Types
    int SIMPLE_MESSAGE_OTHER = 2; // Simple Message sent by someone else
    int SIMPLE_MESSAGE_SELF = 3; // Simple Message sent by you (without avatar)
    int SIMPLE_MESSAGE_CONTINUED_OTHER = 4; // Simple message (without avatar) continued from previous message by someones else
    int SIMPLE_MESSAGE_CONTINUED_SELF = 5; // Simple message (without avatar) continued from previous mesage by you

    int ATTACHMENT_MESSAGE_OTHER = 6; // Attachment with message by other
    int ATTACHMENT_MESSAGE_SELF = 7; // Attachment with message by self (without avatar)
    int ATTACHMENT_MESSAGE_CONTINUED_OTHER = 8; // Attachment with message (without avatar) by other
    int ATTACHMENT_MESSAGE_CONTINUED_SELF = 9; // Attachment with message (without avatar) by self

    // Header/Footer Types
    int CONVERSATION_INFO_HEADER = 10;
    int TYPING_FOOTER = 11;

    // Separator Types
    int DATE_SEPARATOR = 12;
    int UNREAD_SEPARATOR = 13;
    int EMPTY_SEPARATOR = 14;

    // Info Types
    int SIMPLE_INFO = 15;

    // Input Types
    int INPUT_FIELD_EMAIL = 16;
    int INPUT_FIELD_FEEDBACK_RATING = 17;
    int INPUT_FIELD_FEEDBACK_COMMENT = 18;
    int INPUT_FIELD_FEEDBACK_COMPLETED = 19;

    // Bot Message
    int BOT_MESSAGE = 20;
    int SYSTEM_MESSAGE = 21;
}
