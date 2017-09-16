package com.example.win10_pc.whichwish;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    WishListAdapter wishListAdapter = new WishListAdapter();
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);
        //객체 선언 끝
        final View header = getLayoutInflater().inflate(R.layout.wishlistview_header, null, false);
        header.findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "ADD", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent();
                //intent add Activity
            }
        });
        listView.addHeaderView(header);
        //리스트뷰 헤더
        readData();
        listView.setAdapter(wishListAdapter);
        //리스트뷰
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = getIntent();
        if (intent != null) {
            wishListAdapter.addItem(intent.getExtras().getString("title"), intent.getExtras().getString("context"));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveDate();
    }

    public void saveDate() {
        try {
            FileOutputStream fos = new FileOutputStream("wish.tmp");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(wishListAdapter.getListViewItems());
            oos.close();
        } catch (FileNotFoundException e) {
            Log.i("save", "FILE NOT FOUND EXCEPTION");
        } catch (IOException e) {
            Log.i("save", "IOEXCEPTION");
        }
    }

    public void readData() {
        try {
            FileInputStream fis = new FileInputStream("wish.tmp");
            ObjectInputStream ois = new ObjectInputStream(fis);
            ArrayList<WishListViewItem> wishListarraylist = (ArrayList<WishListViewItem>) ois.readObject();
            wishListAdapter.setArrayList(wishListarraylist);
            ois.close();
        } catch (FileNotFoundException e) {
            Log.i("read", "FILE NOT FOUND EXCEPTION");
        } catch (IOException e) {
            Log.i("read", "IOEXCEPTION");
        } catch (ClassNotFoundException e) {
            Log.i("read", "CLASS NOT FOUND EXCEPTION");
        }
    }
}
