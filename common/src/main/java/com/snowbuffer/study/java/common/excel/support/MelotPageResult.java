package com.snowbuffer.study.java.common.excel.support;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * Created by helei3669 on 18/11/24 下午3:27.
 */
public class MelotPageResult<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 数据列表,初始化list不为null
     */
    private List<T> list = Collections.emptyList();

    /**
     * 当前页
     */
    private int page;

    /**
     * 总页数
     */
    private int pageCount;

    /**
     * 每页数量
     */
    private int pageSize;

    /**
     * 总记录条数
     */
    private int total;

    public List<T> getList() {
        return list;
    }

    public MelotPageResult<T> setList(List<T> list) {
        this.list = list;
        return this;
    }

    /**
     * list不会为null
     *
     * @param list
     * @return
     */
    public MelotPageResult<T> setNotNullList(List<T> list) {
        if (list == null) {
            this.list = Collections.emptyList();
        } else {
            this.list = list;
        }
        return this;
    }

    public int getPage() {
        return page;
    }

    public MelotPageResult<T> setPage(int page) {
        this.page = page;
        return this;
    }

    public int getPageCount() {
        if (total == 0) {
            return 0;
        }
        if (pageSize < 1) {
            return 0;
        }
        int p = total / pageSize;
        if (total % pageSize == 0) {
            return p;
        } else {
            return p + 1;
        }
    }

    public MelotPageResult<T> setPageCount(int pageCount) {
        this.pageCount = pageCount;
        return this;
    }

    public int getPageSize() {
        return pageSize;
    }

    public MelotPageResult<T> setPageSize(int pageSize) {
        this.pageSize = pageSize;
        return this;
    }

    public int getTotal() {
        return total;
    }

    public MelotPageResult<T> setTotal(int total) {
        this.total = total;
        return this;
    }


    @Override
    public String toString() {
        return "MelotPageResult{" +
                "list=" + list +
                ", page=" + page +
                ", pageCount=" + pageCount +
                ", pageSize=" + pageSize +
                ", total=" + total +
                '}';
    }


}
