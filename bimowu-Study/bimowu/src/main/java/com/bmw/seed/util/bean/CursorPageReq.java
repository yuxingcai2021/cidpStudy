package com.bmw.seed.util.bean;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class CursorPageReq implements Serializable {
    @NotNull(message = "pageSize不能为空")
    private  Integer  pageSize;
    /**
     * 游标
     */
    @NotNull(message = "cursor不能为空")
    private Integer cursor;

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public void setCursor(Integer cursor) {
        this.cursor = cursor;
    }

    public Integer getCursor() {
        return cursor;
    }
}
