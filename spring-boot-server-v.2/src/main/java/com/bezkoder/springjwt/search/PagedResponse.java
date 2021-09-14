package com.bezkoder.springjwt.search;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PagedResponse<Object> {
    private List<?> content;
    private long count;
    private long totalCount;
}
