package com.example.pokedex.data.local.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "list_pokemon_table")
public class PokemonEntity {
    public boolean isCaught() {
        return caught;
    }
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public PokemonEntity(int id, boolean caught, String name, String url) {
        this.id = id;
        this.caught = caught;
        this.name = name;
        this.url = url;
    }

    @ColumnInfo(name = "caught")
    private boolean caught = false;

    public void setCaught(boolean caught) {
        this.caught = caught;
    }

    private String name;

    private String url;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


}
