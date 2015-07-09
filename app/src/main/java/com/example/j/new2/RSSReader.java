package com.example.j.new2;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;
import java.net.*;
import java.net.URLEncoder;
import java.net.MalformedURLException;


public class RSSReader {


    private String geturl;

    public RSSReader(String geturl) {
        this.geturl = geturl;
    }

    public List<Article> getitem() throws IOException
    {

        List<Article> article =new ArrayList<Article>();


        try {
            //URL url = new URL(geturl);


            geturl = "http" + geturl.substring(geturl.indexOf(":"));

            Document xmlDoc = Jsoup.connect(geturl)
                    .ignoreContentType(true)

                    .parser(Parser.xmlParser()) // <-- add this
                    .get();
            //Jsoup.parse(xml, "", Parser.xmlParser());



            Elements elements = xmlDoc.select("item");

            /*
                Elements title = xmlDoc.select("title"); //要解析的tag元素為title
                Elements link = xmlDoc.select("link");  //要解析的tag元素為td
                Elements pubDate = xmlDoc.select("pubDate");
                Elements description = xmlDoc.select("description");
                Elements category = xmlDoc.select("category");
*/


            //int i=0;
            for(Element e:elements) {


                String titleS = e.getElementsByTag("title").text();
                String linkS = e.getElementsByTag("link").text();
                String pubDateS = e.getElementsByTag("pubDate").text();
                String descriptionS = e.getElementsByTag("description").text();
                String categoryS=e.getElementsByTag("category").text();



                article.add(new Article(titleS,linkS,pubDateS,descriptionS,categoryS));


            }

        }
        catch (MalformedURLException e)
        {e.printStackTrace();}


        return article;
    }

}
