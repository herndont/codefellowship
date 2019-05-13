package com.example.codefellowship.controllers;

import com.example.codefellowship.database.Post;
import com.example.codefellowship.database.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Calendar;
import java.util.Date;


@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    PostRepository repoPost;

    @PostMapping("/create")
    public ModelAndView createPost(
            @RequestParam String username,
            @RequestParam String content,
            @RequestParam String userID
    ) {
        Post post = new Post();
        post.username = username;
        post.content = content;
        post.timeStamp = new Date();
        post.userID = Long.parseLong(userID);

        repoPost.save(post);
        return new ModelAndView("redirect:/userInfo/" + userID);

    }
}
