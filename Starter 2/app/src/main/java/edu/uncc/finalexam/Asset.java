package edu.uncc.finalexam;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Asset implements Serializable {
        private JSONObject nft;
        private JSONObject collection;

        String nft_name, nft_image, collection_name, collection_image, id;

        public Asset() {
        }

        public JSONObject getNft() {
                return nft;
        }

        public void setNft(JSONObject nft) {
                this.nft = nft;
        }

        public JSONObject getCollection() {
                return collection;
        }

        public void setCollection(JSONObject collection) {
                this.collection = collection;
        }

        public String getNft_name() {
                return nft_name;
        }

        public void setNft_name(String nft_name) {
                this.nft_name = nft_name;
        }

        public String getNft_image() {
                return nft_image;
        }

        public void setNft_image(String nft_image) {
                this.nft_image = nft_image;
        }

        public String getCollection_name() {
                return collection_name;
        }

        public void setCollection_name(String collection_name) {
                this.collection_name = collection_name;
        }

        public String getCollection_image() {
                return collection_image;
        }

        public void setCollection_image(String collection_image) {
                this.collection_image = collection_image;
        }

        @Override
        public String toString() {
                return "Asset{" +
                        "nft=" + nft +
                        ", collection=" + collection +
                        ", nft_name='" + nft_name + '\'' +
                        ", nft_image='" + nft_image + '\'' +
                        ", collection_name='" + collection_name + '\'' +
                        ", collection_image='" + collection_image + '\'' +
                        ", id='" + id + '\'' +
                        '}';
        }

        public Asset(JSONObject nft, JSONObject collection) throws JSONException {
                this.nft_image = nft.getString("image_thumbnail_url");
                this.nft_name = nft.getString("name");
                this.collection_image = collection.getString("banner_image_url");
                this.collection_name = collection.getString("name");
                this.id = nft.getString("id");
        }
}
