package com.kayako.sdk.android.k5.kre.data;

import static org.hamcrest.CoreMatchers.is;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;

public class ViewCountChangeTest {

    private static final long RESOURCE_ID = 1_234L;
    private static final String RESOURCE_TYPE = "test_resource_type";
    public static final String RESOURCE_URL = "test_resource_url";
    private final ViewCountChangedProperties viewCountChangedProperties =
            new ViewCountChangedProperties();

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void whenValidParamsThenObjectCreated() {
        //Act
        final ViewCountChange viewCountChange = new ViewCountChange();
        viewCountChange.resource_id = RESOURCE_ID;
        viewCountChange.resource_url = RESOURCE_URL;
        viewCountChange.resource_type = RESOURCE_TYPE;
        viewCountChange.changed_properties = viewCountChangedProperties;

        //Assert
        errorCollector.checkThat(viewCountChange.getResourceId(), is(RESOURCE_ID));
        errorCollector.checkThat(viewCountChange.getChangedProperties(),
                is(viewCountChangedProperties));
        errorCollector.checkThat(viewCountChange.getResourceType(), is(RESOURCE_TYPE));
        errorCollector.checkThat(viewCountChange.getResourceUrl(), is(RESOURCE_URL));
    }
}
