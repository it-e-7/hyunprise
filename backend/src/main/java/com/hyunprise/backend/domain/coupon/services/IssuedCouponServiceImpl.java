package com.hyunprise.backend.domain.coupon.services;

import com.hyunprise.backend.domain.coupon.mappers.IssuedCouponMapper;
import com.hyunprise.backend.domain.coupon.vo.Coupon;
import com.hyunprise.backend.domain.coupon.vo.CouponSummary;
import com.hyunprise.backend.domain.coupon.vo.IssuedCoupon;
import com.hyunprise.backend.domain.coupon.vo.CouponDetail;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.ArrayList;
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
        };
        return issuedCouponMapper.selectAllCouponSummaryOfMemberByStatus(issuedCoupon, available);
    }

    @Override
    public CouponDetail selectOneCouponDetailByIssuedCouponUUID(String issuedCouponUUID) {
        return issuedCouponMapper.selectOneCouponDetailByIssuedCouponUUID(issuedCouponUUID);
    }

    @Override
    public Integer createOneIssuedCoupon(IssuedCoupon issuedCoupon) {
        return issuedCouponMapper.createOneIssuedCoupon(issuedCoupon);
    }

    @Override
    public Coupon selectOneIssuedCoupon(IssuedCoupon issuedCoupon) {
        return issuedCouponMapper.selectOneIssuedCoupon(issuedCoupon);
    }
}
