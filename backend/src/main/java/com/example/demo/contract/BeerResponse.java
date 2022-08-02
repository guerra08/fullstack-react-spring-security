package com.example.demo.contract;

public record BeerResponse (
    int id,
    String uid,
    String brand,
    String name,
    String style,
    String hop,
    String yeast,
    String malts,
    String ibu,
    String alcohol,
    String blg
) { }
