package com.example.j.new2;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;

import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;



import java.util.List;


/**
 * Created by J on 2015/4/15.
 */
public class ListListener implements AdapterView.OnItemClickListener {

    List<Article> Article;
    Activity activity;


    public ListListener(List<Article> Article,Activity activity)
    {
        this.Article = Article;
        this.activity = activity;

    }

    public void onItemClick(AdapterView<?> parent,View view,int pos,long is)
    {
        Intent i =new Intent(Intent.ACTION_VIEW);

        if(Article.get(pos).getLink().substring(0,9).equals("<![CDATA[")) {
            i.setData(Uri.parse(Article.get(pos).getLink().substring(9,Article.get(pos).getLink().length()-3)));
        }
        else
        {
            i.setData(Uri.parse(Article.get(pos).getLink()));
        }
        activity.startActivity((i));

    }







}


