package com.hyunprise.backend.domain.coupon.services;

import com.hyunprise.backend.domain.coupon.mappers.IssuedCouponMapper;
import com.hyunprise.backend.domain.coupon.vo.Coupon;
import com.hyunprise.backend.domain.coupon.vo.CouponSummary;
import com.hyunprise.backend.domain.coupon.vo.IssuedCoupon;
import com.hyunprise.backend.domain.coupon.vo.CouponDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class IssuedCouponServiceImpl implements IssuedCouponService {
    
    private final IssuedCouponMapper issuedCouponMapper;

    @Override
    public List<CouponSummary> selectAllCouponSummaryOfMemberByStatus(IssuedCoupon issuedCoupon, Boolean available) {
        if (issuedCoupon.getMemberUUID() == null) {
            return new ArrayList<>();
        }
        return issuedCouponMapper.selectAllCouponSummaryOfMemberByStatus(issuedCoupon, available);
    }

    @Override
    public CouponDetail selectOneCouponDetailByIssuedCouponUUID(String issuedCouponUUID) {
        return issuedCouponMapper.selectOneCouponDetailByIssuedCouponUUID(issuedCouponUUID);
    }

    @Override
    public String createOneIssuedCoupon(IssuedCoupon issuedCoupon) {

        Map<String, Object> map = new HashMap<>();
        map.put("memberUUID", issuedCoupon.getMemberUUID());
        map.put("couponUUID", issuedCoupon.getCouponUUID());
        map.put("membershipPoint", issuedCoupon.getMembershipPoint());
        map.put("issuedCouponUUID", null);
        issuedCouponMapper.createOneIssuedCoupon(map);

        return map.get("issuedCouponUUID").toString();
    }

    @Override
    public Integer getAvailableIssuedCouponCount(String memberUUID) {
        return issuedCouponMapper.selectAvailableIssuedCouponCount(memberUUID);
    }
}
