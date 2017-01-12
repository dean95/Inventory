package com.example.dean.inventory;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.dean.inventory.data.ProductContract.ProductEntry;
import com.example.dean.inventory.data.ProductDbHelper;

public class MainActivity extends AppCompatActivity {

    private ProductDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new ProductDbHelper(this);

        displayDatabaseInfo();
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayDatabaseInfo();
    }

    private void displayDatabaseInfo() {

        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + ProductEntry.TABLE_NAME, null);

        try {
            TextView displayText = (TextView) findViewById(R.id.text_view_product);
            displayText.setText("Number of rows in product database table: " + cursor.getCount());
        } finally {
            cursor.close();
        }
    }

    private void insertPet() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(ProductEntry.COLUMN_PRODUCT_NAME, "Sugar");
        values.put(ProductEntry.COLUMN_PRODUCT_PRICE, "12");
        values.put(ProductEntry.COLUMN_PRODUCT_QUANTITY, 2);
        values.put(ProductEntry.COLUMN_PRODUCT_SUPPLIER, "Sugar company d.o.o");

        long newRowId = db.insert(ProductEntry.TABLE_NAME, null, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertPet();
                displayDatabaseInfo();
                return true;
            case R.id.action_delete_all_entries:
                return true;
        }

        return super.onOptionsItemSelected(menuItem);
    }
}
