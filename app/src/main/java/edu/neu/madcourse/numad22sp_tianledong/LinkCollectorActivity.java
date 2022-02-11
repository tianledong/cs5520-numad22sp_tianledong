package edu.neu.madcourse.numad22sp_tianledong;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.URL;
import java.util.ArrayList;

import edu.neu.numad22sp_tianledong.R;

public class LinkCollectorActivity extends AppCompatActivity {
    private ArrayList<LinkItemCard> linkItemCards = new ArrayList<>();

    private RecyclerView recyclerView;
    private LinkItemRviewAdapter linkItemRviewAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private FloatingActionButton addButton;

    private static final String KEY_OF_INSTANCE = "KEY_OF_INSTANCE";
    private static final String NUMBER_OF_ITEMS = "NUMBER_OF_ITEMS";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_collector);

        init(savedInstanceState);

        addButton = findViewById(R.id.addLinkButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int pos = 0;
                addItem(pos);
            }
        });

        //Specify what action a specific gesture performs, in this case swiping right or left deletes the entry
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                Toast.makeText(LinkCollectorActivity.this, "Delete an item", Toast.LENGTH_SHORT).show();
                int position = viewHolder.getLayoutPosition();
                linkItemCards.remove(position);

                linkItemRviewAdapter.notifyItemRemoved(position);

            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    // Handling Orientation Changes on Android
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {


        int size = linkItemCards == null ? 0 : linkItemCards.size();
        outState.putInt(NUMBER_OF_ITEMS, size);

        // Need to generate unique key for each item
        // This is only a possible way to do, please find your own way to generate the key
        for (int i = 0; i < size; i++) {
            // put link name information into instance
            outState.putString(KEY_OF_INSTANCE + i + "0", linkItemCards.get(i).getLinkName());
            // put link URL information into instance
            outState.putString(KEY_OF_INSTANCE + i + "1", linkItemCards.get(i).getLinkURL());
        }
        super.onSaveInstanceState(outState);

    }

    private void init(Bundle savedInstanceState) {

        initialItemData(savedInstanceState);
        createRecyclerView();
    }

    private void initialItemData(Bundle savedInstanceState) {
        if (savedInstanceState != null && savedInstanceState.containsKey(NUMBER_OF_ITEMS)) {
            if (linkItemCards == null || linkItemCards.size() == 0) {
                int size = savedInstanceState.getInt(NUMBER_OF_ITEMS);

                // Retrieve keys we stored in the instance
                for (int i = 0; i < size; i++) {
                    String linkName = savedInstanceState.getString(KEY_OF_INSTANCE + i + "0");
                    String linkUrl = savedInstanceState.getString(KEY_OF_INSTANCE + i + "1");
                    LinkItemCard linkItemCard = new LinkItemCard(linkName, linkUrl);
                    linkItemCards.add(linkItemCard);
                }
            }
        }
    }

    private void createRecyclerView() {
        layoutManager = new LinearLayoutManager(this);

        recyclerView = findViewById(R.id.linkCollectorRecyclerView);
        recyclerView.setHasFixedSize(true);

        linkItemRviewAdapter = new LinkItemRviewAdapter(linkItemCards);
        LinkItemClickListener linkItemClickListener = new LinkItemClickListener() {
            @Override
            public void onItemClick(int position) {
                linkItemCards.get(position).onItemClick(position);
                String url = linkItemCards.get(position).getLinkURL();
                if (isValidUrl(url)) {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                } else {
                    Toast.makeText(LinkCollectorActivity.this, "Invalid URL",
                            Toast.LENGTH_SHORT).show();
                }
                linkItemRviewAdapter.notifyItemChanged(position);
            }
        };

        linkItemRviewAdapter.setOnItemClickListener(linkItemClickListener);
        recyclerView.setAdapter(linkItemRviewAdapter);
        recyclerView.setLayoutManager(layoutManager);
    }

    private static boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void addItem(int position) {
        linkItemCards.add(position, new LinkItemCard("", ""));
        Toast.makeText(LinkCollectorActivity.this, "Add an item", Toast.LENGTH_SHORT).show();

        linkItemRviewAdapter.notifyItemInserted(position);
    }


}
