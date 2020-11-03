package com.zdxf.common.base;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zdxf.common.module.ResultJson;

import java.io.Serializable;
import java.util.List;


/**
 * @author Admin
 */
public interface IBaseService<T> extends IService<T> {

    /**
     * 根据查询条件获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    ResultJson getList(BaseQuery query);

    /**
     * 根据ID获取记录信息
     *
     * @param id 记录ID
     * @return
     */
    ResultJson info(Integer id);

    /**
     * 根据ID获取记录信息
     *
     * @param id 记录ID
     * @return
     */

    Object getInfo(Serializable id);

    /**
     * 根据实体对象添加记录
     *
     * @param entity 实体对象
     * @return
     */
    ResultJson add(T entity);

    /**
     * 根据实体对象更新记录
     *
     * @param entity 实体对象
     * @return
     */
    ResultJson update(T entity);

    /**
     * 根据实体对象添加、编辑记录
     *
     * @param entity 实体对象
     * @return
     */
    ResultJson edit(T entity);

    /**
     * 删除记录
     *
     * @param entity 实体对象
     * @return
     */
    ResultJson delete(T entity);

    /**
     * 根据ID删除记录
     *
     * @param id 记录ID
     * @return
     */
    ResultJson deleteById(Integer id);

    /**
     * 根据ID删除记录
     *
     * @param ids 记录ID
     * @return
     */
    ResultJson deleteByIds(String ids);

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    ResultJson setStatus(T entity);

    /**
     * 获取基础参数
     *
     * @return
     */
    ResultJson getParamList();

    /**
     * 导出Excel
     *
     * @return
     */
    List<T> exportExcel();

}
