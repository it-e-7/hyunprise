package com.hyunprise.backend.domain.coupon.services;

import com.hyunprise.backend.domain.coupon.mappers.CouponMapper;
import com.hyunprise.backend.domain.coupon.mappers.IssuedCouponMapper;
import com.hyunprise.backend.domain.coupon.vo.Coupon;
import com.hyunprise.backend.domain.coupon.vo.CouponDetail;
import com.hyunprise.backend.domain.coupon.vo.CouponSummary;
import com.hyunprise.backend.domain.coupon.vo.IssuedCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    
    private final CouponMapper couponMapper;

    @Override
    public Coupon selectOneCoupon(String couponUUID) {
        return couponMapper.selectOneCoupon(couponUUID);
    }

    @Override
    public Integer createOneCoupon(Coupon coupon_) {

        Map<String, Object> coupon = new HashMap<>();
        coupon.put("sellerUUID", coupon_.getSellerUUID());
        coupon.put("couponName", coupon_.getCouponName());
        coupon.put("couponDescription", coupon_.getCouponDescription());
        coupon.put("discountType", coupon_.getDiscountType());
        coupon.put("discountAmount", coupon_.getDiscountAmount());
        coupon.put("discountLimit", coupon_.getDiscountLimit());
        coupon.put("minimumPurchase", coupon_.getMinimumPurchase());
        coupon.put("retailerLocation", coupon_.getRetailerLocation());
        coupon.put("expirationDays", coupon_.getExpirationDays());
        coupon.put("state", null);

        couponMapper.createOneCoupon(coupon);

        return (Integer) coupon.get("state");
    }

    @Override
    public List<Coupon> selectAllAdminIssuedCoupons(String sellerUUID) {
        return couponMapper.selectAllAdminIssuedCoupons(sellerUUID);
    }
}
