package com.zdxf.sysmanage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zdxf.common.base.BaseQuery;
import com.zdxf.common.base.BaseServiceImpl;
import com.zdxf.common.module.PageResult;
import com.zdxf.common.utils.CommonUtils;
import com.zdxf.common.utils.DateUtils;
import com.zdxf.common.module.ResultJson;
import com.zdxf.sysmanage.service.UserService;
import com.zdxf.sysmanage.dto.UserRulesDto;
import com.zdxf.sysmanage.User;
import com.zdxf.sysmanage.UserRole;
import com.zdxf.sysmanage.mapper.UserMapper;
import com.zdxf.login.mapper.UserRolesMapper;
import com.zdxf.sysmanage.query.UserQuery;
import com.zdxf.vo.user.UserInfoVo;
import com.zdxf.vo.user.UserListVo;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.io.Serializable;
import java.util.*;

/**
 * <p>
 * 后台用户管理表 服务实现类
 * </p>
 *
 * @author 鲲鹏
 * @since 2020-03-26
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;
    @Autowired
    private Validator validator;
    @Resource
    private UserRolesMapper userRoleMapper;

    /**
     * 获取数据列表
     *
     * @param query 查询条件
     * @return
     */
    @Override
    public ResultJson getList(BaseQuery query) {
        UserQuery userQuery = (UserQuery) query;
        // 查询条件
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        // 人员姓名
        if (!StringUtils.isEmpty(userQuery.getRealName())) {
            queryWrapper.like("real_name", userQuery.getRealName());
        }
        queryWrapper.eq("deleted", 0);
        queryWrapper.orderByDesc("create_time");

        // 查询数据
        IPage<User> page = new Page<>(userQuery.getPageIndex(), userQuery.getPageSize());
        IPage<User> data = userMapper.selectPage(page, queryWrapper);
        List<User> adminList = data.getRecords();
        List<UserListVo> adminListVoList = new ArrayList<>();
        if (!adminList.isEmpty()) {
            adminList.forEach(item -> {
                UserListVo adminListVo = new UserListVo();
                // 复制属性
                BeanUtils.copyProperties(item, adminListVo);
//                // 头像
//                adminListVo.setAvatar(CommonUtils.getImageURL(item.getAvatar()));
//                // 获取职级
//                Level levelInfo = levelMapper.selectById(item.getLevelId());
//                if (levelInfo != null) {
//                    adminListVo.setLevelName(levelInfo.getName());
//                }
//                // 获取岗位
//                Position positionInfo = positionMapper.selectById(item.getPositionId());
//                if (positionInfo != null) {
//                    adminListVo.setPositionName(positionInfo.getName());
//                }
//                // 获取部门
//                String depName = depService.getDeptName(item.getDeptId());
//                adminListVo.setDeptName(depName);
                // 独立权限
                String[] rulesList = new String[0];
                if (!StringUtils.isEmpty(item.getRules())) {
                    rulesList = item.getRules().split(",");
                }
                adminListVo.setRulesList(rulesList);
                adminListVoList.add(adminListVo);
            });
        }
        //构造返回返回结果
        PageResult<Object> result = PageResult.builder().total(data.getTotal())
                .current(data.getCurrent())
                .size(data.getSize())
                .pages(data.getPages())
                .data(adminListVoList).build();
        return ResultJson.success("操作成功", result);
    }

    /**
     * 获取详情
     *
     * @param id 记录ID
     * @return
     */
    @Override
    public Object getInfo(Serializable id) {
        User entity = (User) super.getInfo(id);
//        // 头像处理
//        if (!StringUtils.isEmpty(entity.getAvatar())) {
//            entity.setAvatar(CommonUtils.getImageURL(entity.getAvatar()));
//        }
        UserInfoVo adminInfoVo = new UserInfoVo();
        // 拷贝属性
        BeanUtils.copyProperties(entity, adminInfoVo);
        return adminInfoVo;
    }

    /**
     * 添加、更新记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public ResultJson edit(User entity) {
        // 字段校验
        Set<ConstraintViolation<User>> violationSet = validator.validate(entity);
        for (ConstraintViolation<User> item : violationSet) {
            return ResultJson.error(item.getMessage());
        }
//        // 头像处理
//        if (!StringUtils.isEmpty(entity.getAvatar()) && entity.getAvatar().contains(CommonConfig.imageURL)) {
//            entity.setAvatar(entity.getAvatar().replaceAll(CommonConfig.imageURL, ""));
//        }
        if (entity.getId() != null && entity.getId() > 0) {
            entity.setUpdateUser(1);
            entity.setUpdateTime(DateUtils.now());
        } else {
//            // 密码加密如果采用spring security 进行登录校验，那么注册的时候密码采用同样的加密方式
//            BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
//            entity.setPassword(encode.encode(entity.getPassword()));
            //密码加密采用md5加密
            entity.setPassword(CommonUtils.password(CommonUtils.password(entity.getPassword())));
            entity.setCreateUser(1);
            entity.setCreateTime(DateUtils.now());
        }
        boolean result = this.saveOrUpdate(entity);
        if (!result) {
            return ResultJson.error();
        }

        // 删除现存的人员角色关系数据
        userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, entity.getId()));
        // 新增人员角色关系表
        if (!StringUtils.isEmpty(entity.getRoleIds())) {
            String[] strings = entity.getRoleIds().split(",");
            for (String string : strings) {
                UserRole userRole = new UserRole();
                userRole.setUserId(entity.getId());
                userRole.setRoleId(string);
                Integer result2 = userRoleMapper.insert(userRole);
            }
        }
        return ResultJson.success();
    }

    /**
     * 删除记录
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public ResultJson delete(User entity) {
        if (entity.getId() == null || entity.getId() == 0) {
            return ResultJson.error("记录ID不能为空");
        }
        entity.setUpdateUser(1);
        entity.setUpdateTime(DateUtils.now());
        return super.delete(entity);
    }

    /**
     * 设置状态
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public ResultJson setStatus(User entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return ResultJson.error("人员ID不能为空");
        }
        if (entity.getStatus() == null) {
            return ResultJson.error("人员状态不能为空");
        }
        return super.setStatus(entity);
    }


    /**
     * 重置密码
     *
     * @param entity 实体对象
     * @return
     */
    @Override
    public ResultJson resetPwd(User entity) {
        if (entity.getId() == null || entity.getId() <= 0) {
            return ResultJson.error("人员ID不能为空");
        }
        entity.setPassword(CommonUtils.password("123456"));
        return this.update(entity);
    }

    /**
     * 设置人员权限
     *
     * @param adminRulesDto 请求参数
     * @return
     */
    @Override
    public ResultJson setRules(UserRulesDto adminRulesDto) {
        // 人员ID
        if (adminRulesDto.getId() == null || adminRulesDto.getId() <= 0) {
            return ResultJson.error("人员ID不能为空");
        }
        // 保存数据
        User entity = new User();
        entity.setId(adminRulesDto.getId());
        entity.setRules(adminRulesDto.getRules());
        boolean result = this.updateById(entity);
        if (!result) {
            return ResultJson.error("权限设置失败");
        }
        return ResultJson.success("权限设置成功");
    }
}
