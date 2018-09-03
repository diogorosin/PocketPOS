package br.com.pocketpos.data.jersey;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class CompanyBean extends OrganizationBean {

    @JsonIgnore
    private Integer level;

    private String couponTitle;

    private String couponSubtitle;

    public String getCouponTitle() {

        return couponTitle;

    }

    public void setCouponTitle(String couponTitle) {

        this.couponTitle = couponTitle;

    }

    public String getCouponSubtitle() {

        return couponSubtitle;

    }

    public void setCouponSubtitle(String couponSubtitle) {

        this.couponSubtitle = couponSubtitle;

    }

}