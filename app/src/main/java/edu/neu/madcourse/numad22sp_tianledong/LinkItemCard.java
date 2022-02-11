package edu.neu.madcourse.numad22sp_tianledong;

public class LinkItemCard implements LinkItemClickListener {
    private final String linkName;
    private final String linkURL;


    public LinkItemCard(String linkName, String linkURL) {
        this.linkName = linkName;
        this.linkURL = linkURL;
    }

    public String getLinkName() {
        return linkName;
    }

    public String getLinkURL() {
        return linkURL;
    }

    @Override
    public void onItemClick(int position) {

    }
}
