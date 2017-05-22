package com.kayako.sdk.android.k5.messenger.attachmentpreview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.kayako.sdk.android.k5.R;
import com.kayako.sdk.android.k5.activities.KayakoAttachmentPreviewActivity;
import com.kayako.sdk.android.k5.common.utils.ImageUtils;

public class AttachmentPreviewFragment extends Fragment {

    private View mRoot;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mRoot = inflater.inflate(R.layout.ko__fragment_attachment_preview, container, false);
        return mRoot;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRoot.findViewById(R.id.ko__button_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishByExit();
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (!(getActivity() instanceof KayakoAttachmentPreviewActivity)) {
            throw new IllegalStateException("This fragment is meant to be used with KayakoAttachmentPreviewActivity");
        }

        Bundle bundle = getActivity().getIntent().getExtras();
        // TODO: Handle other types later
        String imageUrl = bundle.getString(KayakoAttachmentPreviewActivity.ARG_IMAGE_URL);

        if (imageUrl == null) {
            // TODO: Error Toast?
            finishByExit();
        }

        ImageView imageView = ((ImageView) mRoot.findViewById(R.id.ko__attachment_image));
        ImageUtils.loadUrlAsAttachmentImage(getContext(), imageView, imageUrl, false);

        ImageView exitButton = ((ImageView) mRoot.findViewById(R.id.ko__button_exit));
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishByExit();
            }
        });
    }

    public void finishByExit() {
        if (getActivity() == null) {
            return;
        }

        getActivity().setResult(KayakoAttachmentPreviewActivity.RESULT_EXIT);
        getActivity().onBackPressed(); // not finish() to retain animation
    }
}
