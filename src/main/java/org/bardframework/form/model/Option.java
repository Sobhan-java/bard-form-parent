package org.bardframework.form.model;

public class Option {
    private String id;
    private String title;

    public Option() {
    }

    public Option(String id, String title) {
        this.id = id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", title=" + title +
                '}';
    }
}
