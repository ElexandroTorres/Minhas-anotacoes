package com.elexandro.minhasanotacoes.model;

public class Note {
    private String _id;
    private String _title;
    private String _description;
    private String _date;

    public String get_id() {
        return _id;
    }

    public String get_title() {
        return _title;
    }

    public void set_title(String _title) {
        this._title = _title;
    }

    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    public String get_date() {
        return _date;
    }

    public void set_date(String _date) {
        this._date = _date;
    }

    @Override
    public String toString() {
        return "Note{" +
                "_id='" + _id + '\'' +
                ", _title='" + _title + '\'' +
                ", _description='" + _description + '\'' +
                ", _date='" + _date + '\'' +
                '}';
    }
}
