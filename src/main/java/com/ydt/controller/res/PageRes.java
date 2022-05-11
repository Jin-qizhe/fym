package com.ydt.controller.res;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class PageRes<T> extends MyRes {
    public PageRes(long total, long pages, List<T> data) {
        this.total = total;
        this.pages = pages;
        this.data = data;
    }

    /**
     * 分页总条数
     */
    private long total;
    /**
     * 分页页码
     */
    private long pages;

    /**
     * 分页数据集合
     */
    private List<T> data;
}
