package com.southwind.vo;

import lombok.Data;

import java.util.List;

@Data
public class PageVO {
    private Long currentPage;
    private Long totalPage;
    private List data;
}
