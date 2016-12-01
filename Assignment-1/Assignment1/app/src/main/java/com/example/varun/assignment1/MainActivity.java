package com.example.varun.assignment1;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;


public class MainActivity extends AppCompatActivity {
    Button button;
    ListView listview;
    ArrayList<String[]> list = new ArrayList<>();
    EditText editText1, editText2;
    String title_key = "title_key";
    String desc_key = "desc_key";
    @Override
    public void onPause() {
        super.onPause(); // always call super
        SharedPreferences.Editor editor = getPreferences(MODE_PRIVATE).edit();
        editor.putString("killed", "yes");

        System.out.println("paused");
    }
    @Override
    public void onResume() {
        super.onResume(); // always call super
        System.out.println("rseumed");
    }
@Override
    public void onStop() {
        super.onStop(); // always call super
    String filename = "myfile.txt";

    FileOutputStream outputStream;

    try {
        outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
        if(list.size()==0) {
           deleteFile(filename);
        }
        for(int i=0;i<list.size();i++) {
            String[] temp=list.get(i);
            String temp1=temp[0];
            String temp2=temp[1];
            String nline="\n";
            String tline="\t";
            outputStream.write(temp1.getBytes());
            outputStream.write(tline.getBytes());
            outputStream.write(temp2.getBytes());
            outputStream.write(nline.getBytes());
        }
        outputStream.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
    System.out.println("stopped");
    }
    @Override
    public void onStart() {
         // always call super

        try {
            super.onStart();
            InputStream inputStream = openFileInput("myfile.txt");
            System.out.println("filefound");
            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                //StringBuilder stringBuilder = new StringBuilder();

                while ((receiveString = bufferedReader.readLine()) != null) {
                    String temp = receiveString.toString();
                    String[] pieces=temp.split("\t");
                    list.add(pieces);
                }
                inputStream.close();
            }
            initiate(list);
        }

        catch (FileNotFoundException e) {
            super.onStart();
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }
        System.out.println("started");
    }
    @Override
    public void onRestart() {
        super.onRestart(); // always call super
        System.out.println("restarted");
    }
@Override
    public void onDestroy() {
    super.onDestroy(); // always call super
    android.os.Debug.stopMethodTracing();
    File f = getFileStreamPath("myfile.txt");
    if (f.length() == 0) {
deleteFile("myfile.txt");
    }

    System.out.println("destroyed");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (!isTaskRoot()
                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
                && getIntent().getAction() != null
                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {

            finish();
            return;
        }


        button = (Button) findViewById(R.id.button);
        listview = (ListView) findViewById(R.id.listview);
        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);

        listview.setOnItemLongClickListener(
                new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> l,
                                                   View row,
                                                   int index,
                                                   long rowID) {

                        list.remove(index);
                        initiate(list);
                        return false;
                    }

                }
        );
    }

    public void click(View view) throws IOException {
        String title = editText1.getText().toString();
        String description = editText2.getText().toString();
        String[] task = {title, description};
        if (title.equals("")) {
            Toast.makeText(this, "Please enter Task Title", Toast.LENGTH_SHORT).show();
        } else if (description.equals("")) {
            Toast.makeText(this, "Please enter Task Description", Toast.LENGTH_SHORT).show();
        } else {
            list.add(task);
            initiate(list);
            editText1.setText("");
            editText2.setText("");
            editText1.setHint("Input Task Title");
            editText2.setHint("Input Task Description");
        }
    }

public void initiate(ArrayList<String[]> list)
{
    ListAdapter myAdapter = new MyAdapter(this, list);
    listview.setAdapter(myAdapter);

    String filename = "myfile.txt";

    FileOutputStream outputStream;

    try {
        outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
        for(int i=0;i<list.size();i++) {
            String[] temp=list.get(i);
            String temp1=temp[0];
            String temp2=temp[1];
            String nline="\n";
            String tline="\t";
            outputStream.write(temp1.getBytes());
            outputStream.write(tline.getBytes());
            outputStream.write(temp2.getBytes());
            outputStream.write(nline.getBytes());
        }
        outputStream.close();
    } catch (Exception e) {
        e.printStackTrace();
    }
}
    }

