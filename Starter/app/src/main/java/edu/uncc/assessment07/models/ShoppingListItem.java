package edu.uncc.assessment07.models;

public class ShoppingListItem {
    public String name, docId;
    public Integer quantity;

    public ShoppingListItem() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDocId() {
        return docId;
    }

    public ShoppingListItem(String name, String docId, Integer quantity) {
        this.name = name;
        this.docId = docId;
        this.quantity = quantity;
    }

    public void setDocId(String docId) {
        this.docId = docId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
