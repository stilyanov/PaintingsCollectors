package com.paintingscollectors.model.dto;

import com.paintingscollectors.model.entity.Painting;

public class PaintingDTO {

    private long userId;

    private long paintingId;

    private String username;

    private String imageUrl;

    private String author;

    private String name;

    private String style;

    public PaintingDTO(Painting painting) {
        this.userId = painting.getOwner().getId();
        this.paintingId = painting.getId();
        this.username = painting.getOwner().getUsername();
        this.imageUrl = painting.getImageUrl();
        this.author = painting.getAuthor();
        this.name = painting.getName();
        this.style = String.valueOf(painting.getStyle().getName());
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getPaintingId() {
        return paintingId;
    }

    public void setPaintingId(long paintingId) {
        this.paintingId = paintingId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStyle() {
        return style;
    }

    public void setStyle(String style) {
        this.style = style;
    }
}
