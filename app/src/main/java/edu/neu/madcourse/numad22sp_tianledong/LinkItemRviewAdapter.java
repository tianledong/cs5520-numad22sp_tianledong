package edu.neu.madcourse.numad22sp_tianledong;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import edu.neu.numad22sp_tianledong.R;

public class LinkItemRviewAdapter extends RecyclerView.Adapter<LinkItemRviewHolder> {
    private final ArrayList<LinkItemCard> linkItemCards;
    private LinkItemClickListener listener;

    public LinkItemRviewAdapter(ArrayList<LinkItemCard> linkItemCards) {
        this.linkItemCards = linkItemCards;
    }

    public void setOnItemClickListener(LinkItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public LinkItemRviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.link_item_card, parent, false);
        return new LinkItemRviewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull LinkItemRviewHolder holder, int position) {
        LinkItemCard currentItem = linkItemCards.get(position);
        holder.linkName.setText(currentItem.getLinkName());
        holder.linkURL.setText(currentItem.getLinkURL());

    }

    @Override
    public int getItemCount() {
        return linkItemCards.size();
    }
}
