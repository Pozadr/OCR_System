package pl.pozadr.ocrsystem.model;


import javax.persistence.*;

@Entity
@Table(name = "graphics")
public class Graphic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "url", length = 10000)
    private String url;
    @Column(name = "content", length = 10000)
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

