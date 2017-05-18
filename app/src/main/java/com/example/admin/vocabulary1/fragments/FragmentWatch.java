package com.example.admin.vocabulary1.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.admin.vocabulary1.DatabaseMyHelper;
import com.example.admin.vocabulary1.MyAdapter;
import com.example.admin.vocabulary1.R;
import com.example.admin.vocabulary1.Country;

import static com.example.admin.vocabulary1.DatabaseMyHelper.TABLE_COUNTRY;

public class FragmentWatch extends Fragment {
    private OnFragmentInteractionListener mListener;

    public FragmentWatch() {
    }
    public static FragmentWatch newInstance(String param1, String param2) {
        FragmentWatch fragment = new FragmentWatch();
        return fragment;
    }

    ListView MyList;
    DatabaseMyHelper dbHelper;
    View MyView;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }


    public void Refresh()
    {
        DatabaseMyHelper dbHelper= new DatabaseMyHelper(this.getContext());
        final SQLiteDatabase database = dbHelper.getWritableDatabase();


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

            MyAdapter adapter = new MyAdapter(this.getContext(),NewWords);
            MyList.setAdapter(adapter);
        }
        cursor.close();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        MyView =inflater.inflate(R.layout.fragment_watch, container, false);
        MyList = (ListView)MyView.findViewById(R.id.ListView1);
        Refresh();
        return MyView;
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
                AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
                Country obj = (Country) MyList.getItemAtPosition(acmi.position);
                SQLiteDatabase db = new DatabaseMyHelper(FragmentWatch.this.getContext()).getReadableDatabase();
                db.delete(dbHelper.TABLE_COUNTRY,"_id="+obj.getId(),null);
                db.close();
                Toast.makeText(this.getContext(),"Удалено", Toast.LENGTH_SHORT).show();
                Refresh();
                break;
            default:
                return super.onContextItemSelected(item);
        }
        return true;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }
    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
