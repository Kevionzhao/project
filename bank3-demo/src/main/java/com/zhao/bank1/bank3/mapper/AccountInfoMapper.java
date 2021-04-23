package com.zhao.bank1.bank3.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * @author Admin
 */
@Mapper
public interface AccountInfoMapper extends BaseMapper<AccountInfoMapper> {
    /**
     * 更新账户金额
     * @param accountNo
     * @param amount
     * @return
     */
    @Update("UPDATE account_info SET account_balance = account_balance + #{amount} WHERE account_no = #{accountNo}")
    int balanceUpdate(@Param("accountNo") String accountNo, @Param("amount") Double amount);
}
