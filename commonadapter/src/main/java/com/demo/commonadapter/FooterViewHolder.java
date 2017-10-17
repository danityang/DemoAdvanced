package com.demo.commonadapter;

import android.view.View;
import android.widget.TextView;

/**
 * Created by yangdi on 2017/10/13.
 */

public class FooterViewHolder extends BaseViewHolder {
    TextView text_tips;

    public FooterViewHolder(View itemView) {
        super(itemView);
        text_tips = (TextView) itemView.findViewById(R.id.text_tips);
    }
}
