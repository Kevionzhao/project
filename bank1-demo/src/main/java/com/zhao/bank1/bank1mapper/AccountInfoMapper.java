package com.zhao.bank1.bank1mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhao.bank1.entity.AccountInfo;
import org.apache.ibatis.annotations.*;

/**
 * @author Admin
 */
@Mapper
public interface AccountInfoMapper extends BaseMapper<AccountInfo> {
    /**
     * update account balance
     * @param accountNo
     * @param amount
     * @return
     */
    @Update("update account_info set account_balance = #{amount} where account_no = #{accountNo}")
    int updateAccountBalance(@Param("accountNo") String accountNo, @Param("amount") double amount);

    /**
     * huo qu yu e
     * @param accountNo
     * @return
     */
    @Select("select account_balance from account_info where account_no = #{accountNo}")
    int getAccountBalance(@Param("accountNo") String accountNo);

    /**
     * cha ru
     * @return
     */
    @Insert("insert into account_info (`id`,`account_name`,`account_no`,`account_password`,`account_balance`) values (3,'张三23','1','123','11')")
    int insertAccountPassword();
}
