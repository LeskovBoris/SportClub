package com.example.sportclub;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class MemberCursorAdapter  extends CursorAdapter {
    public MemberCursorAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.member_items, parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView firstNameTextView = view.findViewById(R.id.firstNameTextView);
        TextView lastNameTextView = view.findViewById(R.id.lastNameTextView);
        TextView sportTextView = view.findViewById(R.id.sportTextView);

        String firstName = cursor.getString(cursor.getColumnIndexOrThrow("firstName"));
        String lastName = cursor.getString(cursor.getColumnIndexOrThrow("lastName"));
        String sport = cursor.getString(cursor.getColumnIndexOrThrow("sport"));

        firstNameTextView.setText(firstName);
        lastNameTextView.setText(lastName);
        sportTextView.setText(sport);

    }


}
