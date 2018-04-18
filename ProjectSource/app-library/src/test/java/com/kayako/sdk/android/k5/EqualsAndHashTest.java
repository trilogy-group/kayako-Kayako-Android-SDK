package com.kayako.sdk.android.k5;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ErrorCollector;
import static org.hamcrest.CoreMatchers.is;

public abstract class EqualsAndHashTest<T> {

    protected T one;
    protected T same;
    protected T secondSame;
    protected T other;

    @Rule
    public final ErrorCollector errorCollector = new ErrorCollector();

    @Test
    public void reflexivity() {
        //Assert
        errorCollector.checkThat(one, is(one));
    }

    @Test
    public void nullInequality() {
        //Assert
        errorCollector.checkThat(one.equals(null), is(false));
    }

    @Test
    public void symmetry() {
        //Assert
        errorCollector.checkThat(one, is(same));
        errorCollector.checkThat(same, is(one));
        errorCollector.checkThat(one.hashCode(), is(same.hashCode()));
        errorCollector.checkThat(one.equals(other), is(false));
        errorCollector.checkThat(other.equals(one), is(false));
    }

    @Test
    public void transitivity() {
        //Assert
        errorCollector.checkThat(one, is(same));
        errorCollector.checkThat(same, is(secondSame));
        errorCollector.checkThat(one, is(secondSame));
        errorCollector.checkThat(one.hashCode(), is(same.hashCode()));
        errorCollector.checkThat(one.hashCode(), is(secondSame.hashCode()));
    }
}
