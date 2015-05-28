package com.example.rohanmenhdiratta.newz;

import java.util.ArrayList;

/**
 * Created by rohanmenhdiratta on 5/27/15.
 */
public class News {

    private String section;
    private String subsection;
    private String title;
    private String articleAbstract;
    private String url;
    private String author;
    private String publishedDate;
    private ArrayList<Multimedia> multimedia;

    public News() {

    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getSubsection() {
        return subsection;
    }

    public void setSubsection(String subsection) {
        this.subsection = subsection;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArticleAbstract() {
        return articleAbstract;
    }

    public void setArticleAbstract(String articleAbstract) {
        this.articleAbstract = articleAbstract;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public ArrayList<Multimedia> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(ArrayList<Multimedia> multimedia) {
        this.multimedia = multimedia;
    }

    public void addMultimedia(Multimedia multimedia)
    {
        this.multimedia.add(multimedia);
    }
}
