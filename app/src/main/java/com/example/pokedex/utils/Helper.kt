package com.example.pokedex.utils;

public class Helper {
    public static String getIdFromUrl(String url) {
        return url.substring(url.lastIndexOf('/') - 2, url.lastIndexOf('/'));
    }
    public static String getWeight(double weight){
        return weight/10 + " Kg";
    }
    public static String getHeight(double height){
        return height/10 + " m";
    }
    public static String idConverter(int id){
        return String.format("%03d", id);
    }
}
