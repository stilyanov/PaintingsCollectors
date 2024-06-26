package com.paintingscollectors.model.entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "paintings")
public class Painting extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String author;

    @ManyToOne(optional = false)
    @JoinColumn(name = "style_id")
    private Style style;

    @ManyToOne(optional = false)
    @JoinColumn(name = "owner_id")
    private User owner;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private boolean isFavourite;

    @Column(nullable = false)
    private int votes;

    @ManyToMany(mappedBy = "favouritePaintings", fetch = FetchType.EAGER)
    private Set<User> favouriteByUsers;

    @ManyToMany(mappedBy = "ratedPaintings", fetch = FetchType.EAGER)
    private Set<User> ratedByUsers;

    public Painting() {
        this.favouriteByUsers = new HashSet<>();
        this.ratedByUsers = new HashSet<>();
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

    public Style getStyle() {
        return style;
    }

    public void setStyle(Style style) {
        this.style = style;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isFavourite() {
        return isFavourite;
    }

    public void setFavourite(boolean favourite) {
        isFavourite = favourite;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Set<User> getFavouriteByUsers() {
        return favouriteByUsers;
    }

    public void setFavouriteByUsers(Set<User> favouriteByUsers) {
        this.favouriteByUsers = favouriteByUsers;
    }

    public Set<User> getRatedByUsers() {
        return ratedByUsers;
    }

    public void setRatedByUsers(Set<User> ratedByUsers) {
        this.ratedByUsers = ratedByUsers;
    }
}
