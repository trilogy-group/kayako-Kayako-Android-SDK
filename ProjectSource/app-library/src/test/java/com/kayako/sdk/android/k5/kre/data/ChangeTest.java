package com.kayako.sdk.android.k5.kre.data;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.CoreMatchers.is;

@RunWith(MockitoJUnitRunner.class)
public class ChangeTest {

    private static final long RESOURCE_ID = 12_345L;
    private static final String RESOURCE_TYPE = "USER RESOURCE";
    private static final String RESOURCE_URL = "/testUrl";

    @Mock
    private ChangedProperties changed_properties;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void whenValidParamsConstructorThenObjectCreated() {
        final Change change = new Change(RESOURCE_ID, RESOURCE_TYPE, RESOURCE_URL, changed_properties);
        errorCollector.checkThat(change.getResourceId(), is(RESOURCE_ID));
        errorCollector.checkThat(change.getResourceType(), is(RESOURCE_TYPE));
        errorCollector.checkThat(change.getResourceUrl(), is(RESOURCE_URL));
        errorCollector.checkThat(change.getChangedProperties(), is(changed_properties));
    }
}
