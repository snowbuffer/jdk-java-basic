package com.snowbuffer.study.java.common.excel.support;

/**
 * Created by helei3669 on 18/11/23 下午8:18
 */
public class BasePageQuery {

    Integer offset;

    private static int DEFAULT_PAGE = 1;

    Integer pageSize = 20;

    boolean needTotalSize = false;

    public boolean isNeedTotalSize() {
        return needTotalSize;
    }

    public BasePageQuery setNeedTotalSize(boolean needTotalSize) {
        this.needTotalSize = needTotalSize;
        return this;
    }

    public Integer getOffset() {
        return offset;
    }

    /**
     * 设置page即同步关联offset。
     *
     * @param page
     * @return
     */
    private Integer getOffsetByPage(Integer page, Integer pageSize) {
        if (page == null) {
            page = DEFAULT_PAGE;
        }
        //设置page最小边界
        page = page < 1 ? 1 : page;
        //转换为offset，
        return (page - 1) * pageSize;
    }

    /**
     * 要么调用这个，传递过来page和pagesize，要么调用其他的，传递过来
     *
     * @param page
     * @param pageSize
     * @return
     */
    public BasePageQuery buildPageInfoByPageAndSize(Integer page, Integer pageSize) {
        this.pageSize = pageSize;
        this.offset = getOffsetByPage(page, pageSize);
        return this;
    }
//    public BasePageQuery setOffsetInfo(Integer offset,Integer pageSize){
//        this.offset=offset;
//        this.pageSize=pageSize;
//        return this;
//    }

    /**
     * 建议用page，如果真要用的话，不要和page混用
     *
     * @param offset
     * @return
     */
    public BasePageQuery setOffset(Integer offset) {
        this.offset = offset;
        return this;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public BasePageQuery setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
        return this;
    }


}
