package pl.pozadr.ocrsystem.model;


import pl.pozadr.ocrsystem.config.AppConstants;

import javax.persistence.*;

public class Graphic {
    private Long id;
    private String url;
    private String content;

    public Graphic() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

