package com.example.finalexam;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Item implements Serializable {
    String id, thumbnail, photo_description, photo_creation_date, owner_name, owner_icon;
    String photo_small_image, owner_profile_account_url ;

    public Item(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getString("id");
        this.thumbnail = jsonObject.getJSONObject("urls").getString("thumb");
        this.photo_description = jsonObject.getString("description");
        this.photo_creation_date = jsonObject.getString("created_at");
        this.owner_name = jsonObject.getJSONObject("user").getString("name");
        this.owner_icon = jsonObject.getJSONObject("user").getJSONObject("profile_image").getString("small");
        this.photo_small_image = jsonObject.getJSONObject("urls").getString("small");
        this.owner_profile_account_url = jsonObject.getJSONObject("user").getJSONObject("links").getString("html");
    }

    public Item() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getPhoto_description() {
        return photo_description;
    }

    public void setPhoto_description(String photo_description) {
        this.photo_description = photo_description;
    }

    public String getPhoto_creation_date() {
        return photo_creation_date;
    }

    public void setPhoto_creation_date(String photo_creation_date) {
        this.photo_creation_date = photo_creation_date;
    }

    public String getOwner_name() {
        return owner_name;
    }

    public void setOwner_name(String owner_name) {
        this.owner_name = owner_name;
    }

    public String getOwner_icon() {
        return owner_icon;
    }

    public void setOwner_icon(String owner_icon) {
        this.owner_icon = owner_icon;
    }

    public String getPhoto_small_image() {
        return photo_small_image;
    }

    public void setPhoto_small_image(String photo_small_image) {
        this.photo_small_image = photo_small_image;
    }

    public String getOwner_profile_account_url() {
        return owner_profile_account_url;
    }

    public void setOwner_profile_account_url(String owner_profile_account_url) {
        this.owner_profile_account_url = owner_profile_account_url;
    }
}
