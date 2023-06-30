package com.hyunprise.backend.domain.coupon.mappers;

import com.hyunprise.backend.domain.coupon.vo.Coupon;
import com.hyunprise.backend.domain.coupon.vo.CouponSummary;
import com.hyunprise.backend.domain.coupon.vo.IssuedCoupon;
import com.hyunprise.backend.domain.coupon.vo.CouponDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface IssuedCouponMapper {
    List<CouponSummary> selectAllCouponSummaryOfMemberByStatus(IssuedCoupon issuedCoupon, Boolean available);

    CouponDetail selectOneCouponDetailByIssuedCouponUUID(String issuedCouponUUID);

    Integer createOneIssuedCoupon(IssuedCoupon issuedCoupon);

    Coupon selectOneIssuedCoupon(IssuedCoupon issuedCoupon);
}
