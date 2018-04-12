package com.kayako.sdk.android.k5.common.adapter.messengerlist.helper;

import com.kayako.sdk.android.k5.common.adapter.BaseIdentityListItem;
import com.kayako.sdk.android.k5.common.adapter.BaseListItem;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import java.util.HashMap;
import java.util.Map;
import static org.mockito.Mockito.when;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;

@RunWith(MockitoJUnitRunner.class)
public class DiffUtilsHelperTest {

    private static final String NEW_LINE = "\n";
    private final Map<String, String > map = new HashMap<>();

    @Mock
    private BaseListItem baseListItem;

    @Mock
    private BaseIdentityListItem baseIdentityListItem;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Before
    public void setUp() {
        final String key = "key_String";
        final String value = "value_String";
        map.put(key, value);
    }

    @Test
    public void whenBothItemTypeNotSameThenFalse() {
        when(baseListItem.getItemType()).thenReturn(1,2);
        errorCollector.checkThat(DiffUtilsHelper.areItemsSame(baseListItem, baseListItem), is(false));
    }

    @Test
    public void whenOfBaseIdentityListItemAndSameIdThenTrue() {
        when(baseIdentityListItem.getId()).thenReturn(1L, 1L);
        errorCollector.checkThat(DiffUtilsHelper.areItemsSame(baseIdentityListItem, baseIdentityListItem), is(true));
    }

    @Test
    public void whenContentsSameThenTrue() {
        when(baseListItem.getContents()).thenReturn(map);
        errorCollector.checkThat(DiffUtilsHelper.areItemsSame(baseListItem, baseListItem), is(true));
    }

    @Test
    public void whenItemTypesAreSameThenFalse() {
        errorCollector.checkThat(DiffUtilsHelper.areItemsSame(baseListItem, baseListItem), is(false));
    }

    @Test
    public void areContentsSame() {
        when(baseListItem.getContents()).thenReturn(map);
        errorCollector.checkThat(DiffUtilsHelper.areContentsSame(baseListItem, baseListItem), is(true));
    }

    @Test
    public void whenNullObjectPassedInConvertToStringThenReturnNull() {
        errorCollector.checkThat(DiffUtilsHelper.convertToString(null), nullValue());
    }

    @Test
    public void convertToStringWithValidParams() {
        System.out.print(NEW_LINE);
        // Arrange
        final String expectedValue = new StringBuilder()
                .append("1").append("=").append("1").append(NEW_LINE)
                .append("2").append("=").append("2").append(NEW_LINE)
                .append("3").append("=").append("3").append(NEW_LINE)
                .toString();

        final Map<String,String> mapLocal = new HashMap<>();
        mapLocal.put("1", "1");
        mapLocal.put("2", "2");
        mapLocal.put("3", "3");

        // Act
        final String convertedValue = DiffUtilsHelper.convertToString(mapLocal);

        // Assert
        errorCollector.checkThat(convertedValue, is(expectedValue));
    }
}
