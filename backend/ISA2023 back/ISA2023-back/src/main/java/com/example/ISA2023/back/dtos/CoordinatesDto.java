package com.example.ISA2023.back.dtos;

public class CoordinatesDto {
    public double lang;
    public double lat;

    @Override
    public String toString() {
        return
                "lang=" + lang +
                ", lat=" + lat;
    }
}
