package pl.pozadr.ocrsystem.model;


public class Graphic {
    private String url;
    private String content;

    public Graphic() {
    }

    public Graphic(String url, String content) {
        this.url = url;
        this.content = content;
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
