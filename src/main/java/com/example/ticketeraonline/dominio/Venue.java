package com.example.ticketeraonline.dominio;

import lombok.Data;

@Data
public class Venue {

    private Long id;
    private String name;
    private String address;
    private int capacity;

}
