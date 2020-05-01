package com.example.myapplication;

public class FirstAidCard {
    int id;
    String title,content;
    String imageId;

    FirstAidCard(int id,String title,String content,String imageId)
    {
        this.title = title;
        this.id=id;
        this.content = content;
        this.imageId = imageId;
    }
}
