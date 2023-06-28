package com.hyunprise.backend.domain.coupon.services;

import com.hyunprise.backend.domain.coupon.mappers.IssuedCouponMapper;
import com.hyunprise.backend.domain.coupon.vo.CouponSummary;
import com.hyunprise.backend.domain.coupon.vo.IssuedCoupon;
import com.hyunprise.backend.domain.coupon.vo.CouponDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class IssuedCouponServiceImpl implements IssuedCouponService {
    
    private final IssuedCouponMapper issuedCouponMapper;

    @Override
    public List<CouponSummary> selectAllCouponSummaryOfMemberByStatus(IssuedCoupon issuedCoupon) {
        if (issuedCoupon.getMemberUUID() == null) {
            return new ArrayList<>();
        };
        return issuedCouponMapper.selectAllCouponSummaryOfMemberByStatus(issuedCoupon);
    }

    @Override
    public CouponDetail selectOneCouponDetailByIssueUUID(String issuedCouponUUID) {
        return issuedCouponMapper.selectOneCouponDetailByIssueUUID(issuedCouponUUID);
    }
}
