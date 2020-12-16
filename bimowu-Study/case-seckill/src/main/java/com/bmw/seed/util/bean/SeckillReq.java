package com.bmw.seed.util.bean;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class SeckillReq implements Serializable {

    @NotNull(message = "产品id 不能为空")
    private Long productId;

    @NotNull(message = "用户id 不能为空")
    private Long userId;
}
