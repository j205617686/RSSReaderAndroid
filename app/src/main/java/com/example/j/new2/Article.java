package com.example.j.new2;


public class Article {

    String title;
    String link;
    String pubDate;
    String description;
    String category;

    Article(String title,String link,String pubDate,String description,String category)
    {

        this.title=title;
        this.link=link;
        this.pubDate=pubDate;
        this.description=description;
        this.category=category;
    }


    public String getTitle()
    {return title;}
    public String getLink()
    {return link;}
    public String getpubDate()
    {return pubDate;}
    public String getDescription()
    {return description;}
    public String getCategory()
    {return category;}




    public void setTitle(String title) {
        this.title = title;
    }
    public void setLink(String link) {
        this.link = link;
    }
    public void setpubDate(String pubDate) {
        this.pubDate = pubDate;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setCategory(String category) {
        this.category = category;
    }











    public String toString()
    {
        //return "Title: "+getTitle()+"\nLink:"+getLink()+"\npubDate:"+getpubDate()+"\nDescription:"+getDescription()+"\nCategory:"+getCategory()+"\n";
        return title;
    }



}
