package dev.yavuztas.samples.jackson.model;

/**
 * Model class for comment response structure
 * 
 * @author Yavuz Tas
 *
 */
public class CommentModel {

    private Long id;

    private String text;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

}
