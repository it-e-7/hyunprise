package com.hyunprise.backend.domain.coupon.services;

import com.hyunprise.backend.domain.coupon.vo.Coupon;
import com.hyunprise.backend.domain.coupon.vo.CouponDetail;
import com.hyunprise.backend.domain.coupon.vo.CouponSummary;
import com.hyunprise.backend.domain.coupon.vo.IssuedCoupon;

import java.util.List;

public interface CouponService {
    Coupon selectOneCoupon(String couponUUID);
    Integer createOneCoupon(Coupon coupon);
    List<Coupon> selectAllAdminIssuedCoupons(String sellerUUID);
}
