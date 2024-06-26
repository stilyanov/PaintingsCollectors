package com.paintingscollectors.model.dto;

import com.paintingscollectors.model.entity.StyleEnum;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class AddPaintingDTO {

    @NotNull
    @Size(min = 5, max = 40, message = "Name length must be between 5 and 40 characters!")
    private String name;

    @NotNull
    @Size(min = 5, max = 30, message = "Author length must be between 5 and 30 characters")
    private String author;

    @NotBlank(message = "Please enter valid image URL!")
    @Size(max = 150)
    @Pattern(regexp = "https://.*", message = "Please enter valid image URL!")
    private String imageUrl;

    @NotNull(message = "You must select a style!")
    private StyleEnum style;

    public AddPaintingDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public StyleEnum getStyle() {
        return style;
    }

    public void setStyle(StyleEnum style) {
        this.style = style;
    }
}
