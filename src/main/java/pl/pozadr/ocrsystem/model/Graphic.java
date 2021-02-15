package pl.pozadr.ocrsystem.model;


import pl.pozadr.ocrsystem.config.AppConstants;

import javax.persistence.*;

@Entity
@Table(name = "graphics")
public class Graphic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "url", length = AppConstants.MAX_DB_URL_LENGTH)
    private String url;
    @Column(name = "content", length = AppConstants.MAX_DB_CONTENT_LENGTH)
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

