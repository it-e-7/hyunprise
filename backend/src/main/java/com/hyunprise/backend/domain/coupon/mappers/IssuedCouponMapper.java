package com.hyunprise.backend.domain.coupon.mappers;

import com.hyunprise.backend.domain.coupon.vo.CouponSummary;
import com.hyunprise.backend.domain.coupon.vo.IssuedCoupon;
import com.hyunprise.backend.domain.coupon.vo.CouponDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface IssuedCouponMapper {
    List<CouponSummary> selectAllCouponSummaryOfMemberByStatus(IssuedCoupon issuedCoupon);

    CouponDetail selectOneCouponDetailByIssuedCouponUUID(String issuedCouponUUID);
}
