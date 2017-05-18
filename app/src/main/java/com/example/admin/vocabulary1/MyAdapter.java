package com.example.admin.vocabulary1;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by User on 13.04.2017.
 */
public class MyAdapter extends BaseAdapter {


    class ViewHolder {
        TextView CountryView;
        TextView CapitalView;
    }

    Context WordContext;
    Country[] NewWords;

    public MyAdapter (Context WordContext, Country[] NewWords){
        this.WordContext=WordContext;
        this.NewWords=NewWords;
    }
    @Override
    public int getCount() {
        return NewWords.length;
    }

    @Override
    public Object getItem(int position) {
        return  NewWords[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            LayoutInflater wordinflater = (LayoutInflater) WordContext.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = wordinflater.inflate(R.layout.single_row,null);

            holder = new ViewHolder();
            holder.CountryView = (TextView) convertView.findViewById(R.id.textView);
            holder.CapitalView = (TextView) convertView.findViewById(R.id.textView2);

            convertView.setTag(holder);
        }
        else{
            holder=(ViewHolder) convertView.getTag();
        }
        Country CurWord = (Country) getItem(position);
        holder.CountryView.setText(CurWord.GetCountry());
        holder.CapitalView.setText(CurWord.GetCapital());
        return convertView;
    }
}
