package edu.neu.madcourse.numad22sp_tianledong;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import edu.neu.numad22sp_tianledong.R;

public class LinkItemRviewHolder extends RecyclerView.ViewHolder {
    public TextView linkName;
    public TextView linkURL;
    public LinkItemRviewHolder(View itemView, final LinkItemClickListener listener) {
        super(itemView);
        linkURL = itemView.findViewById(R.id.linkUrl);
        linkName = itemView.findViewById(R.id.linkName);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int position = getLayoutPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(position);
                    }
                }
            }
        });
    }


}
