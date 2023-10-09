package edu.uncc.finalexam;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Nft implements Serializable {
    String nft_image, nft_name;

    public Nft() {
    }

    public Nft(JSONObject jsonObject) {
        this.nft_image = nft_image;
        this.nft_name = nft_name;
    }

    public String getNft_image() {
        return nft_image;
    }

    public void setNft_image(String nft_image) {
        this.nft_image = nft_image;
    }

    public String getNft_name() {
        return nft_name;
    }

    public void setNft_name(String nft_name) {
        this.nft_name = nft_name;
    }
}
