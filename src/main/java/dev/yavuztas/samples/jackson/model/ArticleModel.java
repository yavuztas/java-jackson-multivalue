package dev.yavuztas.samples.jackson.model;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import dev.yavuztas.samples.jackson.SingleAwareListDeserializer;

/**
 * Model class for response handling comments with generic deserializer by
 * {@link SingleAwareListDeserializer}
 * 
 * @author Yavuz Tas
 *
 */
public class ArticleModel {

    private Long id;

    private String name;

    @JsonDeserialize(using = SingleAwareListDeserializer.class)
    // @JsonFormat(with = Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY)
    private List<CommentModel> comments;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CommentModel> getComments() {
        return comments;
    }

    public void setComments(List<CommentModel> comments) {
        this.comments = comments;
    }

}