package com.bkash.bookmanagement.dto;

import java.util.List;

public class GetBooksRequest {
    private int offset;
    private int limit;
    private List<Integer> genreList;
    private List<Integer> authorList;
    private String prefixName;
}
