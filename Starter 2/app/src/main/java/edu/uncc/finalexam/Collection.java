package edu.uncc.finalexam;

import org.json.JSONObject;

import java.io.Serializable;

public class Collection implements Serializable {
    String coll_name, coll_image;

    public Collection(JSONObject jsonObject) {
        this.coll_name = coll_name;
        this.coll_image = coll_image;
    }

    public Collection() {
    }

    public String getColl_name() {
        return coll_name;
    }

    public void setColl_name(String coll_name) {
        this.coll_name = coll_name;
    }

    public String getColl_image() {
        return coll_image;
    }

    public void setColl_image(String coll_image) {
        this.coll_image = coll_image;
    }
}
