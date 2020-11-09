package com.zdxf.sysmanage.controller;


import com.zdxf.common.base.BaseController;
import com.zdxf.common.module.ResultJson;
import com.zdxf.sysmanage.OperLog;
import com.zdxf.sysmanage.query.OperLogQuery;
import com.zdxf.sysmanage.service.OperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 操作日志记录 前端控制器
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-03-26
 */
@RestController
@RequestMapping("/operlog")
public class OperLogController extends BaseController {

    @Autowired
    private OperLogService operLogService;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @PostMapping("/list")
    public ResultJson list(@RequestBody OperLogQuery query) {
        return operLogService.getList(query);
    }

    /**
     * 删除日志
     *
     * @param entity 实体对象
     * @return
     */
    @PostMapping("/delete")
    public ResultJson delete(@RequestBody OperLog entity) {
        return operLogService.delete(entity);
    }

}
