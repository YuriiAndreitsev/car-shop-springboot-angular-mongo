package com.bezkoder.springjwt.search;

import lombok.Data;

@Data
public class SearchRequest {
    private int size;
    private int page;
}
