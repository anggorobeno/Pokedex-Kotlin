package com.example.pokedex.data.remote.response;

import com.google.gson.annotations.SerializedName;

public class ResultsItem{

	@SerializedName("name")
	private String name;

	@SerializedName("url")
	private String url;

	public String getName(){
		return name;
	}

	public String getUrl(){
		return url;
	}
}