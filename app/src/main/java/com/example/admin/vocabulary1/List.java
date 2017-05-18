package com.example.admin.vocabulary1;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import static com.example.admin.vocabulary1.DatabaseMyHelper.TABLE_COUNTRY;


public class List extends AppCompatActivity {

//    ListView MyList;
    DatabaseMyHelper dbHelper;
//    SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        Refresh();
    }

    public void Refresh()
    {
        DatabaseMyHelper dbHelper= new DatabaseMyHelper(this);
        final SQLiteDatabase database = dbHelper.getWritableDatabase();
        ListView MyList = (ListView)findViewById(R.id.ListView);

        registerForContextMenu(MyList);

        Cursor cursor = database.query(TABLE_COUNTRY, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(dbHelper.KEY_ID);
            int wordIndex = cursor.getColumnIndex(dbHelper.KEY_COUNTRY);
            int translateIndex = cursor.getColumnIndex(dbHelper.KEY_CAPITAL);
            Country[] NewWords = new Country[cursor.getCount()];
            int i=0;
            do {
                NewWords[i]= new Country(cursor.getString(wordIndex),cursor.getString(translateIndex), cursor.getInt(idIndex));
                i++;
            } while (cursor.moveToNext());

            MyAdapter adapter = new MyAdapter(this,NewWords);
            MyList.setAdapter(adapter);
        }
        cursor.close();
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo)
    {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(Menu.NONE,1,1,"Удалить");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case 1:
                ContextMenu.ContextMenuInfo menuInfo = item.getMenuInfo();
                ListView lv = (ListView) findViewById(R.id.ListView);
                AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Country obj = (Country) lv.getItemAtPosition(acmi.position);
                SQLiteDatabase db = new DatabaseMyHelper(List.this).getReadableDatabase();
                db.delete(dbHelper.TABLE_COUNTRY,"_id="+obj.id,null);
                db.close();
                Toast.makeText(this,"Удалено", Toast.LENGTH_SHORT).show();
                Refresh();
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }
}
