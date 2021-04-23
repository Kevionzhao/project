package com.zhao.bank1.mapper;

import org.apache.ibatis.annotations.*;

/**
 * @author Admin
 */
@Mapper
public interface AccountInfoMapper{
    /**
     * update account balance
     * @param accountNo
     * @param amount
     * @return
     */
    @Update("update account_info set account_balance = account_balance + #{amount} where account_no = #{accountNo}")
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

    /**
     * if exist
     * @param txNo
     * @return
     */
    @Select("select count(1) from de_duplication where tx_no = #{txNo}")
    int isExistTx(String txNo);

    /**
     * add log
     * @param txNo
     * @return
     */
    @Insert("insert into de_duplication values(#{txNo},now());")
    int addTx(String txNo);
}
