package com.example.varun.ToDoList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by varun on 10/2/2016.
 */

public class MyAdapter extends ArrayAdapter<String[]> {
    public MyAdapter(Context context, ArrayList<String[]> resource) {
        super(context, R.layout.mylinearlayout, resource);
    }

    CheckBox cb;
    int index;

    @Override
    public View getView(final int index, View row, ViewGroup parent) {

        LayoutInflater minflater = LayoutInflater.from(getContext());
        row = minflater.inflate(R.layout.mylinearlayout, parent, false);
        String[] task = getItem(index);
        TextView Text1 = (TextView) row.findViewById(R.id.Text1);
        TextView Text2 = (TextView) row.findViewById(R.id.Text2);
        CheckBox checkbox = (CheckBox)row.findViewById(R.id.checkbox);
        checkbox.setFocusable(false);
        checkbox.setFocusableInTouchMode(false);
        int no=index+1;
        Text1.setText("Task "+no+":"+task[0]);
        Text2.setText(task[1]);

        checkbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] deletable_task = getItem(index);
                remove(deletable_task);

            }
        });

        return row;
    }
}
