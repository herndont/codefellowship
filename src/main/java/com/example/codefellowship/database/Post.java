package com.example.codefellowship.database;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public class Post {
    @Id
    @GeneratedValue
    public long id;

    public String username;
    public String content;
    public Date timeStamp;
    public long userID;
}
