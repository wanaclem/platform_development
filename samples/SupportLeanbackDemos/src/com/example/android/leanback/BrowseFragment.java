/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package com.example.android.leanback;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v17.leanback.widget.ArrayObjectAdapter;
import android.support.v17.leanback.widget.HeaderItem;
import android.support.v17.leanback.widget.ImageCardView;
import android.support.v17.leanback.widget.ListRow;
import android.support.v17.leanback.widget.ListRowPresenter;
import android.support.v17.leanback.widget.OnItemViewClickedListener;
import android.support.v17.leanback.widget.OnItemViewSelectedListener;
import android.support.v17.leanback.widget.Presenter;
import android.support.v17.leanback.widget.Row;
import android.support.v17.leanback.widget.RowPresenter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class BrowseFragment extends android.support.v17.leanback.app.BrowseFragment {
    private static final String TAG = "leanback.BrowseFragment";

    private static final int NUM_ROWS = 10;
    private ArrayObjectAdapter mRowsAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.i(TAG, "onCreate");
        super.onCreate(savedInstanceState);

        setBadgeDrawable(getActivity().getResources().getDrawable(R.drawable.ic_title));
        setTitle("Leanback Sample App");
        setHeadersState(HEADERS_ENABLED);

        setOnSearchClickedListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });

        setupRows();
        setOnItemViewClickedListener(new ItemViewClickedListener());
        setOnItemViewSelectedListener(new OnItemViewSelectedListener() {
            @Override
            public void onItemSelected(Presenter.ViewHolder itemViewHolder, Object item,
                    RowPresenter.ViewHolder rowViewHolder, Row row) {
                Log.i(TAG, "onItemSelected: " + item + " row " + row);
            }
        });
    }

    private void setupRows() {
        ListRowPresenter lrp = new ListRowPresenter();
        lrp.setRowHeight(CardPresenter.getRowHeight(getActivity()));
        lrp.setExpandedRowHeight(CardPresenter.getExpandedRowHeight(getActivity()));

        mRowsAdapter = new ArrayObjectAdapter(lrp);

        // For good performance, it's important to use a single instance of
        // a card presenter for all rows using that presenter.
        final CardPresenter cardPresenter = new CardPresenter();

        for (int i = 0; i < NUM_ROWS; ++i) {
            ArrayObjectAdapter listRowAdapter = new ArrayObjectAdapter(cardPresenter);
            listRowAdapter.add(new PhotoItem("Hello world", R.drawable.gallery_photo_1));
            listRowAdapter.add(new PhotoItem("This is a test", R.drawable.gallery_photo_2));
            listRowAdapter.add(new PhotoItem("Android TV", R.drawable.gallery_photo_3));
            listRowAdapter.add(new PhotoItem("Leanback", R.drawable.gallery_photo_4));
            listRowAdapter.add(new PhotoItem("Hello world", R.drawable.gallery_photo_5));
            listRowAdapter.add(new PhotoItem("This is a test", R.drawable.gallery_photo_6));
            listRowAdapter.add(new PhotoItem("Android TV", R.drawable.gallery_photo_7));
            listRowAdapter.add(new PhotoItem("Leanback", R.drawable.gallery_photo_8));
            HeaderItem header = new HeaderItem(i, "Row " + i, null);
            mRowsAdapter.add(new ListRow(header, listRowAdapter));
        }

        setAdapter(mRowsAdapter);
    }

    private final class ItemViewClickedListener implements OnItemViewClickedListener {
        @Override
        public void onItemClicked(Presenter.ViewHolder itemViewHolder, Object item,
                RowPresenter.ViewHolder rowViewHolder, Row row) {
            Intent intent = new Intent(getActivity(), DetailsActivity.class);
            intent.putExtra(DetailsActivity.EXTRA_ITEM, (PhotoItem) item);

            Bundle bundle = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    getActivity(),
                    ((ImageCardView)itemViewHolder.view).getMainImageView(),
                    DetailsActivity.SHARED_ELEMENT_NAME).toBundle();
            getActivity().startActivity(intent, bundle);
        }
    }
}
