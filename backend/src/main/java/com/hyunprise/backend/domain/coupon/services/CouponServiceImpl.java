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
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {
    
    private final CouponMapper couponMapper;

    @Override
    public Coupon selectOneCoupon(String couponUUID) {
        return couponMapper.selectOneCoupon(couponUUID);
    }
}
