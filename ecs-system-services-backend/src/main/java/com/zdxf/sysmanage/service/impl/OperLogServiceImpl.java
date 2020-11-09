package com.zdxf.sysmanage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zdxf.common.base.BaseQuery;
import com.zdxf.common.base.BaseServiceImpl;
import com.zdxf.common.module.PageResult;
import com.zdxf.common.module.ResultJson;
import com.zdxf.common.utils.StringUtils;
import com.zdxf.sysmanage.OperLog;
import com.zdxf.sysmanage.mapper.OperLogMapper;
import com.zdxf.sysmanage.query.OperLogQuery;
import com.zdxf.sysmanage.service.OperLogService;
import com.zdxf.vo.operlog.OperLogListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 操作日志记录 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-03-26
 */
@Service
public class OperLogServiceImpl extends BaseServiceImpl<OperLogMapper, OperLog> implements OperLogService {

    @Autowired
    private OperLogMapper operLogMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public ResultJson getList(BaseQuery query) {
        OperLogQuery operLogQuery = (OperLogQuery) query;
        // 查询条件
        QueryWrapper<OperLog> queryWrapper = new QueryWrapper<>();
        // 日志标题
        if (!StringUtils.isEmpty(operLogQuery.getTitle())) {
            queryWrapper.like("title", operLogQuery.getTitle());
        }
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("create_time");

        // 查询数据
        IPage<OperLog> page = new Page<>(operLogQuery.getPageIndex(), operLogQuery.getPageSize());
        IPage<OperLog> data = operLogMapper.selectPage(page, queryWrapper);
        List<OperLog> operLogList = data.getRecords();
        List<OperLogListVo> operLogListVoList = new ArrayList<>();
        if (!operLogList.isEmpty()) {
            operLogList.forEach(item -> {
                OperLogListVo operLogListVo = new OperLogListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, operLogListVo);
                operLogListVoList.add(operLogListVo);
            });
        }
        //构造返回返回结果
        PageResult<Object> result = PageResult.builder().total(data.getTotal())
                .current(data.getCurrent())
                .size(data.getSize())
                .pages(data.getPages())
                .data(operLogListVoList).build();
        return ResultJson.success("操作成功", result);
    }

    /**
     * 删除日志
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public ResultJson delete(OperLog entity) {
        if (entity.getId() == null || entity.getId() == 0) {
            return ResultJson.error("记录ID不能为空");
        }
        return super.delete(entity);
    }

    /**
     * 创建系统操作日志
     *
     * @param operLog 操作日志对象
     */
    @Override
    public void insertOperlog(OperLog operLog) {
          this.add(operLog);
    }
}
