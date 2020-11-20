package com.zdxf.sysmanage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zdxf.common.base.BaseQuery;
import com.zdxf.common.base.BaseServiceImpl;
import com.zdxf.common.enums.CodeType;
import com.zdxf.common.exception.CustomException;
import com.zdxf.common.module.PageResult;
import com.zdxf.common.utils.CommonUtils;
import com.zdxf.common.utils.DateUtils;
import com.zdxf.common.module.ResultJson;
import com.zdxf.sysmanage.Role;
import com.zdxf.sysmanage.dto.UserDto;
import com.zdxf.sysmanage.mapper.RoleMapper;
import com.zdxf.sysmanage.service.UserRoleSerivce;
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
import org.springframework.transaction.annotation.Transactional;
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
    @Autowired
    private UserRoleSerivce userRoleSerivce;
    @Resource
    private RoleMapper roleMapper;

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
        queryWrapper.eq("status",1);
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
                // 用户角色
                String[] rulesList = new String[0];
                if (!StringUtils.isEmpty(item.getRoleIds())) {
                    rulesList = item.getRoleIds().split(",");
                }
                List<Role> roles = roleMapper.selectBatchIds(Arrays.asList(rulesList));
                rulesList = roles.stream().map(Role::getName).toArray(String[]::new);
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
    @Transactional
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
            entity.setPassword(CommonUtils.password(entity.getPassword()));
            entity.setCreateUser(1);
            entity.setCreateTime(DateUtils.now());
        }
        try{
            boolean result = this.saveOrUpdate(entity);
            if (!result) {
                return ResultJson.error("保存数据错误");
            }
            List<UserRole> userRoles = userRoleMapper.selectList(new LambdaQueryWrapper<UserRole>().eq(UserRole::getRoleId, entity.getId()));
            if (!userRoles.isEmpty()){
                // 删除现存的人员角色关系数据
                userRoleMapper.delete(new LambdaQueryWrapper<UserRole>().eq(UserRole::getUserId, entity.getId()));
            }
            // 新增人员角色关系表
            UserRole userRole = new UserRole();
            userRole.setUserId(entity.getId());
            if (!StringUtils.isEmpty(entity.getRoleIds())) {
                String[] ruleId = entity.getRoleIds().split(",");
                for (String role : ruleId) {
                    userRole.setRoleId(role);
                    userRoleMapper.insert(userRole);
                }
            }
            return ResultJson.success("数据保存成功");
        }catch (CustomException e){
            throw new CustomException(ResultJson.error(CodeType.EXCEPTION.getCode(), e.getMessage()));
        }
    }

    /**
     * 删除角色
     *
     * @param
     * @return
     */
    @Override
    public ResultJson deleteById(Integer id) {
        UserRole userRole = new UserRole();
        userRole.setUserId(id);
        try {
            int count = userRoleSerivce.selectCountByCondition(userRole);
            if (count > 0) {
                return ResultJson.error("已分配给角色，删除失败");
            }
            return super.deleteById(id);
        } catch (Exception e) {
            throw new CustomException(ResultJson.error(CodeType.EXCEPTION.getCode(), e.getMessage()));
        }

    }

    /**
     * 重置密码
     *
     * @param userDto 实体对象
     * @return
     */
    @Override
    public ResultJson resetPwd(UserDto userDto) {
        if (userDto.getId() == null || userDto.getId() <= 0) {
            return ResultJson.error("人员ID不能为空");
        }
        User user = (User)this.getInfo(userDto.getId());
        String newPwd = CommonUtils.password(userDto.getNewPwd());
        if (!userDto.getPwd().equals(user.getPassword())){
            return ResultJson.error("旧密码不正确");
        }if(newPwd.equals(user.getPassword())) {
            return ResultJson.error("新密码不能与旧密码相同");
        }
//            // 密码加密如果采用spring security 进行登录校验，那么注册的时候密码采用同样的加密方式
//            BCryptPasswordEncoder encode = new BCryptPasswordEncoder();
//            entity.setPassword(encode.encode(entity.getPassword()));
        //密码加密采用md5加密
        user.setPassword(newPwd);
        user.setUpdateUser(1);
        user.setUpdateTime(DateUtils.now());
        try{
            this.update(user);
            return ResultJson.success("密码修改成功");
        }catch (Exception e){
            throw new CustomException(ResultJson.error(CodeType.EXCEPTION.getCode(), e.getMessage()));
        }
    }
}
