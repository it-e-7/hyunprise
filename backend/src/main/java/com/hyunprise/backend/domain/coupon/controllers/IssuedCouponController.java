package com.hyunprise.backend.domain.coupon.controllers;

import com.hyunprise.backend.domain.coupon.vo.Coupon;
import com.hyunprise.backend.domain.coupon.vo.CouponSummary;
import com.hyunprise.backend.domain.coupon.vo.IssuedCoupon;
import com.hyunprise.backend.domain.coupon.services.IssuedCouponService;
import com.hyunprise.backend.domain.coupon.vo.CouponDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issued_coupon")
@RequiredArgsConstructor
public class IssuedCouponController {
    private final IssuedCouponService issuedCouponService;

    @GetMapping("")
    public List<CouponSummary> getAllCouponsOfMemberByStatus(IssuedCoupon issuedCoupon, @RequestParam Boolean available) {
        return issuedCouponService.selectAllCouponSummaryOfMemberByStatus(issuedCoupon, available);
    }
    @GetMapping("/{id}")
    public CouponDetail getOneCouponDetailByIssueUUID(@PathVariable String id) {
        return issuedCouponService.selectOneCouponDetailByIssuedCouponUUID(id);
    }

    @PostMapping("")
    public Coupon getOneCoupons(@RequestBody IssuedCoupon issuedCoupon) {
        return issuedCouponService.selectOneIssuedCoupon(issuedCoupon);
    }
}
