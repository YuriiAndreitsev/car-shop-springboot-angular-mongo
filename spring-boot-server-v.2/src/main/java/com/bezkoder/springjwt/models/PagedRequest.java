package com.bezkoder.springjwt.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.lang.Nullable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class PagedRequest {
    @Nullable
    private int pageNumber;
    @Nullable
    private int pageSize = 6;
}
