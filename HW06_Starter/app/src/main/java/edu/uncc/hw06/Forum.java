package edu.uncc.hw06;

import java.io.Serializable;
import java.util.ArrayList;

public class Forum implements Serializable {
    String author;
    String title;

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public Forum() {
    }

    String description;
    String auth_id;

    Integer likes;

    ArrayList<Comment> comments = new ArrayList<>();
    String doc_id;
    String created_at;

    public Forum(String author, String title, String description, String auth_id, Integer likes, ArrayList<Comment> comments, String doc_id, String created_at) {
        this.author = author;
        this.title = title;
        this.description = description;
        this.auth_id = auth_id;
        this.likes = likes;
        this.comments = comments;
        this.doc_id = doc_id;
        this.created_at = created_at;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAuth_id() {
        return auth_id;
    }

    public void setAuth_id(String auth_id) {
        this.auth_id = auth_id;
    }

    public String getDoc_id() {
        return doc_id;
    }

    public void setDoc_id(String doc_id) {
        this.doc_id = doc_id;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public void setComments(ArrayList<Comment> comments) {
        this.comments = comments;
    }
}
