package edu.uncc.assessment07.models;

import java.io.Serializable;

public class ShoppingList implements Serializable {
    public ShoppingList() {
    }

    public String name, ownerId, docId;

    public ShoppingList(String displayName, String s, String valueOf) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(String ownerId) {
        this.ownerId = ownerId;
    }

    public String getDocId() {
        return docId;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }
}
