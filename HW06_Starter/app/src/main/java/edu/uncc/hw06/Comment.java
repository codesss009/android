package edu.uncc.hw06;

public class Comment {
    String author, author_id, comment;

    public Comment() {
    }

    public Comment(String author, String author_id, String comment) {
        this.author = author;
        this.author_id = author_id;
        this.comment = comment;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
