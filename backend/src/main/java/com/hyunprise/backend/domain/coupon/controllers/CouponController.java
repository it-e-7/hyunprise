package com.hyunprise.backend.domain.coupon.controllers;

import com.hyunprise.backend.domain.coupon.services.CouponService;
import com.hyunprise.backend.domain.coupon.services.IssuedCouponService;
import com.hyunprise.backend.domain.coupon.vo.Coupon;
import com.hyunprise.backend.domain.coupon.vo.CouponDetail;
import com.hyunprise.backend.domain.coupon.vo.CouponSummary;
import com.hyunprise.backend.domain.coupon.vo.IssuedCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @GetMapping("/{couponUUID}")
    public Coupon getOneCoupon(@PathVariable String couponUUID) {
        return couponService.selectOneCoupon(couponUUID);
    }
}
