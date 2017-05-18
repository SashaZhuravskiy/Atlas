package com.example.admin.vocabulary1.fragments;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.vocabulary1.DatabaseMyHelper;
import com.example.admin.vocabulary1.R;


public class FragmentMain extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    public FragmentMain() {

    }
    public static FragmentMain newInstance(String param1, String param2) {
        FragmentMain fragment = new FragmentMain();
        return fragment;
    }

    EditText etWord, etTranslate;
    Button btAdd, btShow, btDel, btTest;

    DatabaseMyHelper dbHelper;


    private View myFragmentView;
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        dbHelper= new DatabaseMyHelper(this.getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myFragmentView = inflater.inflate(R.layout.fragment_main, container, false);
        btAdd=(Button)myFragmentView.findViewById(R.id.buttonAdd);
        btAdd.setOnClickListener(this);
        etWord=(EditText)myFragmentView.findViewById(R.id.editText3);
        etTranslate=(EditText)myFragmentView.findViewById(R.id.editText4);
        return myFragmentView;


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


    @Override
    public void onClick(View v) {

        String Word=etWord.getText().toString();
        String Translate=etTranslate.getText().toString();

        SQLiteDatabase database = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

            switch (v.getId())
            {
                case R.id.buttonAdd:
                    contentValues.put(dbHelper.KEY_COUNTRY, Word);
                    contentValues.put(dbHelper.KEY_CAPITAL, Translate);
                    etWord.setText("");
                    etTranslate.setText("");
                    database.insert(dbHelper.TABLE_COUNTRY,null, contentValues);
                    Toast.makeText(FragmentMain.this.getContext(), "Добавлено",Toast.LENGTH_LONG).show();
                    break;
            }
            dbHelper.close();

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
