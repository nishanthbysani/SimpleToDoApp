package com.codeproject.nishanthb.simpletodo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    List<String> items;

    Button btnAdd;
    EditText etItem;
    RecyclerView rvitems;
    ItemsAdapter itemsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdd = findViewById(R.id.btnAdd);
        etItem = findViewById(R.id.etItem);
        rvitems = findViewById(R.id.rvItems);


        loadItems();

        ItemsAdapter.OnLongClickListener onLongClickListener = new ItemsAdapter.OnLongClickListener() {
            @Override
            public void onItemLongClicked(int position) {
                //Delete the item from the model
                items.remove(position);
//                Notify the Adapter
                itemsAdapter.notifyItemRemoved(position);
                Toast.makeText(getApplicationContext(),"Item Removed!", Toast.LENGTH_SHORT).show();
                saveItems();

            }
        };
        itemsAdapter = new ItemsAdapter(items,onLongClickListener);
        rvitems.setAdapter(itemsAdapter);
        rvitems.setLayoutManager(new LinearLayoutManager(this));

        //Adding an onclick listener
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Add the item to the list
                String todoItem  = etItem.getText().toString();
                items.add(todoItem);
                //Notifying the adapter about the list update.
                itemsAdapter.notifyItemInserted(items.size()-1);
                etItem.setText("");
                Toast.makeText(getApplicationContext(),"Item Added!", Toast.LENGTH_SHORT).show();
                saveItems();
            }
        });
    }

    private File getDataFile(){
        return new File(getFilesDir(), "data.txt");
    }

//    Read Lines from the file
    private void loadItems(){
        try {
            items = new ArrayList<>(FileUtils.readLines(getDataFile(), Charset.defaultCharset()));
        } catch (IOException e) {
            Log.e("MainActivity","Error reading items from the file",e);
            items = new ArrayList<>();
        }
    }
//    Write lines to the file
    private void saveItems(){
        try {
            FileUtils.writeLines(getDataFile(), items);
        } catch (IOException e) {
            Log.e("MainActivity","Error writing items to the file",e);
        }
    }
}