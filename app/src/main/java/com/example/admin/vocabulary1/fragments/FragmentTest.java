package com.example.admin.vocabulary1.fragments;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.admin.vocabulary1.DatabaseMyHelper;
import com.example.admin.vocabulary1.R;
import com.example.admin.vocabulary1.Country;

import java.util.Random;

import static com.example.admin.vocabulary1.DatabaseMyHelper.TABLE_COUNTRY;


public class FragmentTest extends Fragment implements View.OnClickListener {
    private OnFragmentInteractionListener mListener;

    TextView TV,TV4;
    Country[] NewWords;
    Button btCheck;
    EditText edText;
    ImageView IV;
    int i;
    final Random random = new Random();


    public FragmentTest() {

    }
    public static FragmentTest newInstance(String param1, String param2) {
        FragmentTest fragment = new FragmentTest();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        DatabaseMyHelper dbHelper= new DatabaseMyHelper(this.getContext());
        final SQLiteDatabase database = dbHelper.getWritableDatabase();

        Cursor cursor = database.query(TABLE_COUNTRY, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(dbHelper.KEY_ID);
            int wordIndex = cursor.getColumnIndex(dbHelper.KEY_COUNTRY);
            int translateIndex = cursor.getColumnIndex(dbHelper.KEY_CAPITAL);
            NewWords = new Country[cursor.getCount()];
            int i=0;
            do {
                NewWords[i]= new Country(cursor.getString(wordIndex),cursor.getString(translateIndex), cursor.getInt(idIndex));
                i++;
            } while (cursor.moveToNext());
        }
        cursor.close();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_test, container, false);
        TV= (TextView)view.findViewById(R.id.textView3);
        TV4= (TextView)view.findViewById(R.id.textView4);
        IV= (ImageView) view.findViewById(R.id.imageView2);

        btCheck = (Button)view.findViewById(R.id.button4);
        btCheck.setOnClickListener(this);

        edText = (EditText)view.findViewById(R.id.editText);

        i=random.nextInt(NewWords.length);
        TV.setText(NewWords[i].GetCountry());

        return view;
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
        switch (v.getId())
        {
            case R.id.button4:
                //if( edText.getText().equals(NewWords[i].GetWord())) {
                //Toast.makeText(this,NewWords[i].GetWord()+ " - "+ edText.getText(),Toast.LENGTH_SHORT).show();
                if( NewWords[i].GetCapital().contentEquals(edText.getText())) {
                    TV4.setTextColor(Color.GREEN);
                    TV4.setText("ВЕРНО");
                    IV.setImageResource(R.drawable.right3);
                    }
                else {
                    TV4.setTextColor(Color.RED);
                    TV4.setText("НЕВЕРНО");
                    IV.setImageResource(R.drawable.wrong3);
                }

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    public void run() {
                        TV4.setText("");
                        edText.setText("");
                        IV.setImageDrawable(null);
                        i=random.nextInt(NewWords.length);
                        TV.setText(NewWords[i].GetCountry());
                    }
                }, 1500);


                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}
