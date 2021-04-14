package com.example.mymiwokapp;

import android.app.Activity;
import android.content.Context;
import android.provider.UserDictionary;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
       private int scolorResourceId;
    public WordAdapter(Activity context, ArrayList<Word> words, int colorResourceId) {

        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, words);
        scolorResourceId=colorResourceId;
    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Word currentword = getItem(position);

        // Find the TextView in the list_item.xml layout with the ID version_name
        TextView sanskritTextView = (TextView) listItemView.findViewById(R.id.l1);
        // Get the version name from the current AndroidFlavor object and
        // set this text on the name TextView
        sanskritTextView.setText(currentword.getmSanskritTranslation());

        // Find the TextView in the list_item.xml layout with the ID version_number
        TextView defaultTextView = (TextView) listItemView.findViewById(R.id.l11);
        // Get the version number from the current AndroidFlavor object and
        // set this text on the number TextView
        defaultTextView.setText(currentword.getmDefaultTranslation());

        //Find the ImageView in the list_item.xml layout with the ID version_number
        ImageView imageView=(ImageView)listItemView.findViewById(R.id.img_view);

        //Get the version number from the current AnroidFlavor object and
        // set this image on the ImageView

        if(currentword.hasImage())
        {
            imageView.setImageResource(currentword.getmResourceid());

            imageView.setVisibility(View.VISIBLE); //Visible means view will be visible to the user
        }
        else{
            imageView.setVisibility(View.GONE);  //Gone means view will not be visible to the user and it does not take any space in the way
        }
        // set the theme color for the list item
        View textContainer= listItemView.findViewById(R.id.textContainer);

        //find the color that the resource id maps to
        int color= ContextCompat.getColor(getContext(), scolorResourceId);

        //set the background color of text container view
        textContainer.setBackgroundColor(color);

        // Return the whole list item layout (containing 2 TextViews and an ImageView)
        // so that it can be shown in the ListView
        return listItemView;
    }
}
