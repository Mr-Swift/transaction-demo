package com.apple.dev.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.math.BigDecimal;

@Mapper
public interface SysUserMapper {
    /**
     * 增加余额
     * @param userId
     * @param addBalance
     * @return
     */
    int addBalance(@Param("userId") Long userId, @Param("addBalance") BigDecimal addBalance);

    /**
     * 扣减余额
     * @param userId
     * @param subBalance
     * @return
     */
    int subBalance(@Param("userId") Long userId,@Param("subBalance") BigDecimal subBalance);
}
