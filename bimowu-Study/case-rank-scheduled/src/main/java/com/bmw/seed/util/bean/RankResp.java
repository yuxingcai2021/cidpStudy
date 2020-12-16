package com.bmw.seed.util.bean;


import java.io.Serializable;
import java.math.BigDecimal;

public class RankResp implements Serializable {
    //排名
    private Integer rank;
    //用户手机号
    private String phone;
    //订单金额
    private BigDecimal amount;

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "RankResp{" +
                "rank=" + rank +
                ", phone='" + phone + '\'' +
                ", amount=" + amount +
                '}';
    }
}
