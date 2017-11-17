package org.solar.bean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;


/**
 * 分页信息
 *
 * @author chihiro
 * @version 2.0
 */
public class Pageable implements Serializable {

    private static final long serialVersionUID = -3930180379790344299L;

    /**
     * 默认页码
     */
    private static final long DEFAULT_PAGE_NUMBER = 1;

    /**
     * 默认每页记录数
     */
    private static final long DEFAULT_PAGE_SIZE = 10;

    /**
     * 最大每页记录数
     */
    private static final long MAX_PAGE_SIZE = 10000;

    /**
     * 页码
     */
    private long pageNumber = DEFAULT_PAGE_NUMBER;

    /**
     * 每页记录数
     */
    private long pageSize = DEFAULT_PAGE_SIZE;


    /**
     * 搜索Map
     */
    private Map params;

    /**
     * 排序属性
     */
    private String orderProperty;


    private String orderDirection;

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    /**
     * 初始化一个新创建的Pageable对象
     */
    public Pageable() {
        params=new HashMap();
    }

    /**
     * 初始化一个新创建的Pageable对象
     *
     * @param pageNumber 页码
     * @param pageSize   每页记录数
     */
    public Pageable(Integer pageNumber, Integer pageSize) {
        if (pageNumber != null && pageNumber >= 1) {
            this.pageNumber = pageNumber;
        }
        if (pageSize != null && pageSize >= 1 && pageSize <= MAX_PAGE_SIZE) {
            this.pageSize = pageSize;
        }
    }

    /**
     * 获取页码
     *
     * @return 页码
     */
    public long getPageNumber() {
        return pageNumber;
    }

    /**
     * 设置页码
     *
     * @param pageNumber 页码
     */
    public void setPageNumber(long pageNumber) {
        if (pageNumber < 1) {
            pageNumber = DEFAULT_PAGE_NUMBER;
        }
        this.pageNumber = pageNumber;
    }

    /**
     * 获取每页记录数
     *
     * @return 每页记录数
     */
    public long getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页记录数
     *
     * @param pageSize 每页记录数
     */
    public void setPageSize(long pageSize) {
        if (pageSize < 1 || pageSize > MAX_PAGE_SIZE) {
            pageSize = DEFAULT_PAGE_SIZE;
        }
        this.pageSize = pageSize;
    }

    public Map getParams() {
        return params;
    }

    public void setParams(Map params) {
        this.params = params;
    }

    /**
     * 获取排序属性
     *
     * @return 排序属性
     */
    public String getOrderProperty() {
        return orderProperty;
    }

    /**
     * 设置排序属性
     *
     * @param orderProperty 排序属性
     */
    public void setOrderProperty(String orderProperty) {
        this.orderProperty = orderProperty;
    }


    public static Pageable getPageable(Map<String, String> map) {
        Pageable pageable = new Pageable();
        String pageNumber = map.get("pageNum");
        if (pageNumber != null && !"".equals(pageNumber)) {
            pageable.setPageNumber(Integer.valueOf(pageNumber));
        }
        String pageSize = map.get("pageSize");
        if (pageSize != null && !"".equals(pageSize)) {
            pageable.setPageSize(Integer.valueOf(pageSize));
        }
        String orderProperty = map.get("orderProperty");
        if (orderProperty != null && !"".equals(orderProperty)) {
            pageable.setOrderProperty(orderProperty);
            String orderDirection = map.get("orderDirection");
            if (orderDirection != null && !"".equals(orderDirection)) {
                orderDirection = orderDirection.toLowerCase();
                if ("desc".equals(orderDirection) || "asc".equals(orderDirection)) {
                    pageable.setOrderDirection(orderDirection);
                }
            }else {
                pageable.setOrderDirection("desc");
            }
        }
        pageable.setParams(map);
        return pageable;
    }


    public  Pageable checkPageNum() {
        if (pageNumber>100){
            pageNumber=100;
        }
        return this;
    }


}