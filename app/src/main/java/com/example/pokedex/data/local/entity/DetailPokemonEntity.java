package com.example.pokedex.data.local.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;


@Entity(tableName = "detail_pokemon_table")
public class DetailPokemonEntity {
    @ColumnInfo(name = "caught")
    private boolean caught = false;

    @PrimaryKey
    @NonNull
    private String id;

    private String name;

    private String nickname;

    private int height;

    private int weight;

    private String url;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isCaught() {
        return caught;
    }

    public void setCaught(boolean caught) {
        this.caught = caught;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public DetailPokemonEntity(boolean caught, @NonNull String id, String name, int height, int weight, String url) {
        this.caught = caught;
        this.id = id;
        this.name = name;
        this.height = height;
        this.weight = weight;
        this.url = url;
    }
}
