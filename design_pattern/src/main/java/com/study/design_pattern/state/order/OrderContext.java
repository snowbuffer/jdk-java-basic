package com.study.design_pattern.state.order;
/**
 * 需要放在二方包发布
 */

/**
 * 用于放置客户数据的容器
 *
 * @param <T>
 */
public class OrderContext<T> {

    //业务PayLoad(Entity,DTO.....)
    private T data;

    //接入方code
    private String channelId;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }
}
