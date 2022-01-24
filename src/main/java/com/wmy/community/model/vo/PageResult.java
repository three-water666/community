package com.wmy.community.model.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * @Description: 分页结果
 * @Author: 三水
 * @Date: 2022/1/23 0:18
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
public class PageResult<T> {
    private Integer totalPage;
    private List<T> list;

    public PageResult(Integer totalPage, List<T> list) {
        this.totalPage = totalPage;
        this.list = list;
    }
}
