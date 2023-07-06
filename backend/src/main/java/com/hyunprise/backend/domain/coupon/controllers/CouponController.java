package com.hyunprise.backend.domain.coupon.controllers;

import com.hyunprise.backend.domain.coupon.services.CouponService;
import com.hyunprise.backend.domain.coupon.services.IssuedCouponService;
import com.hyunprise.backend.domain.coupon.vo.Coupon;
import com.hyunprise.backend.domain.coupon.vo.CouponDetail;
import com.hyunprise.backend.domain.coupon.vo.CouponSummary;
import com.hyunprise.backend.domain.coupon.vo.IssuedCoupon;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.List;

@RestController
@RequestMapping("/coupon")
@RequiredArgsConstructor
public class CouponController {
    private final CouponService couponService;

    @PostMapping("")
    public Integer postOneCoupon(@RequestBody Coupon coupon) {
        System.out.println("coupon = " + coupon);
        return couponService.createOneCoupon(coupon);
    }

    @GetMapping("/{couponUUID}")
    public Coupon getOneCoupon(@PathVariable String couponUUID) {
        return couponService.selectOneCoupon(couponUUID);
    }

    @GetMapping("/admin/{sellerUUID}")
    public List<CouponSummary> getAllAdminIssuedCoupons(@PathVariable String sellerUUID) {
        return couponService.selectAllAdminIssuedCoupons(sellerUUID);
    }

    @GetMapping("/admin/detail/{couponUUID}")
    public CouponDetail getOneAdminCouponDetailByCouponUUID(@PathVariable String couponUUID) {
        return couponService.selectOneAdminCouponDetailByCouponUUID(couponUUID);
    }
}
