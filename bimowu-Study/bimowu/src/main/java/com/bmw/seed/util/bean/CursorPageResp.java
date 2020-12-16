package com.bmw.seed.util.bean;

import java.io.Serializable;
import java.util.List;

public class CursorPageResp<T> implements Serializable {
    //数据
    private List<T> list;

    //游标
    private Integer cursor;

    public void setCursor(Integer cursor) {
        this.cursor = cursor;
    }

    public Integer getCursor() {
        return cursor;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public List<T> getList() {
        return list;
    }
}
