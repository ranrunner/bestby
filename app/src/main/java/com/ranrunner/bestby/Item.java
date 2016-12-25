package com.ranrunner.bestby;

public class Item {

    // database columns
    int _id;
    String _item;
    String _date;

    public Item() {

    }

    public Item(int id, String item, String date) {
        this._id = id;
        this._item = item;
        this._date = date;
    }

    // get-set _id
    public int getID() {
        return this._id;
    }
    public void setID(int id) {
        this._id = id;
    }

    // get-set _item
    public String getItem() { return this._item; }
    public void setItem(String item) {
        this._item = item;
    }

    // get-set _date
    public String getDate() { return this._date; }
    public void setDate(String date) {
        this._date = date;
    }
}
