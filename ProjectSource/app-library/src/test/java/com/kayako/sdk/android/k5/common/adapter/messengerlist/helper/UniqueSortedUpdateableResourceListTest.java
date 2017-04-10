package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import com.kayako.sdk.messenger.message.Message;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class UniqueSortedUpdateableResourceListTest {

    @Test
    public void test1() throws Exception {
        UniqueSortedUpdatableResourceList<Message> originalResources = new UniqueSortedUpdatableResourceList<Message>();
        originalResources.addElement(55L, new Message(55L, null, null, null, null, null, null, null, null, null, null, null));
        originalResources.addElement(2L, new Message(2L, null, null, "First One", null, null, null, null, null, null, null, null));
        originalResources.addElement(100L, new Message(100L, null, null, null, null, null, null, null, null, null, null, null));
        originalResources.addElement(4L, new Message(4L, null, null, null, null, null, null, null, null, null, null, null));
        originalResources.addElement(123L, new Message(123L, null, null, null, null, null, null, null, null, null, null, null));
        originalResources.addElement(8L, new Message(8L, null, null, null, null, null, null, null, null, null, null, null));
        originalResources.addElement(2L, new Message(2L, null, null, "Second One", null, null, null, null, null, null, null, null));
        originalResources.addElement(2L, new Message(2L, null, null, "Third One", null, null, null, null, null, null, null, null));

        List<Message> resourceList = originalResources.getList();

        List<Long> ids = new ArrayList<>();
        for (int i = 0; i < resourceList.size(); i++) {
            ids.add(
                    resourceList
                            .get(i)
                            .getId());
        }

        // TEST if the list is sorted by ids
        Long previousId = null;
        for (Long id : ids) {
            if (previousId == null) {
                previousId = id;
            } else {
                Assert.assertTrue("The list should be sorted by ids", previousId < id);
            }
        }

        // TEST that there are no duplicates
        int count = 0;
        for (Long id : ids) {
            if (id == 2L) {
                count++;
            }
        }
        Assert.assertTrue("There should be no duplicates to the list", count == 1);

        // TEST that new elements replaces older element
        for (int i = 0; i < resourceList.size(); i++) {
            if (resourceList.get(i).getId() == 2L) {
                Assert.assertEquals("There should be no duplicates to the list", "Third One", resourceList.get(i).getSubject());
                break;
            }
        }

    }
}