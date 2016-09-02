package com.kayako.sdk.android.k5.sectionbycategoryfragment;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class SectionByCategoryPageFactory {

    private static SectionByCategoryPresenter mSectionByCategoryPresenter;

    public static SectionByCategoryPageContract.Presenter getPresenter(SectionByCategoryPageContract.View view) {
        if (mSectionByCategoryPresenter == null) {
            return mSectionByCategoryPresenter = new SectionByCategoryPresenter(view);
        } else {
            mSectionByCategoryPresenter.setView(view);
            return mSectionByCategoryPresenter;
        }
    }
}
