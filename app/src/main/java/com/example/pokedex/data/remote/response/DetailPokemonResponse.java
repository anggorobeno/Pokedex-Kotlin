package com.example.pokedex.data.remote.response;

import com.google.gson.annotations.SerializedName;

public class DetailPokemonResponse {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("height")
    private int height;

    @SerializedName("weight")
    private int weight;

    @SerializedName("url")
    private String url;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getHeight() {
        return height;
    }

    public int getWeight() {
        return weight;
    }

    public String getUrl() {
        return url;
    }
}
