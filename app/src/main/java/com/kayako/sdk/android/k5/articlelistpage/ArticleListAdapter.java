package com.kayako.sdk.android.k5.articlelistpage;

import com.kayako.sdk.android.k5.common.adapter.ListItemRecyclerViewAdapter;
import com.kayako.sdk.android.k5.common.data.ListItem;

import java.util.List;

/**
 * @author Neil Mathew <neil.mathew@kayako.com>
 */
public class ArticleListAdapter extends ListItemRecyclerViewAdapter {

    public ArticleListAdapter(List<ListItem> items, OnItemClickListener listener) {
        super(items, listener);
    }

    // TODO: Override  onBindViewHolder(final RecyclerView.ViewHolder viewHolder, int position) to use Image Spans to show the Pinned Icon
}
