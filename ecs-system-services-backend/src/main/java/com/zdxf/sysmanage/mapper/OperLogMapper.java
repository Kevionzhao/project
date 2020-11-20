package com.zdxf.sysmanage.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zdxf.sysmanage.OperLog;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 操作日志记录 Mapper 接口
 * </p>
 *
 * @author Admin
 */
public interface OperLogMapper extends BaseMapper<OperLog> {

    /**
     * 创建系统操作日志
     *
     * @param operLog 操作日志对象
     */
//    void insertOperlog(OperLog operLog);

}
