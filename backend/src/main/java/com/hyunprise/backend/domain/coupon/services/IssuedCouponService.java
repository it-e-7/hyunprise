package com.hyunprise.backend.domain.coupon.services;

import com.hyunprise.backend.domain.coupon.vo.Coupon;
import com.hyunprise.backend.domain.coupon.vo.CouponSummary;
import com.hyunprise.backend.domain.coupon.vo.IssuedCoupon;
import com.hyunprise.backend.domain.coupon.vo.CouponDetail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface IssuedCouponService {
    List<CouponSummary> selectAllCouponSummaryOfMemberByStatus(IssuedCoupon memberUUID, Boolean available);

    CouponDetail selectOneCouponDetailByIssuedCouponUUID(String issuedCouponUUID);

    String createOneIssuedCoupon(IssuedCoupon issuedCoupon);

}
