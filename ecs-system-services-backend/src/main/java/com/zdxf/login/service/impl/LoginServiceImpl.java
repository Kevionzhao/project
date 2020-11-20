package com.zdxf.login.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zdxf.common.config.CommonConfig;
import com.zdxf.common.enums.CodeType;
import com.zdxf.common.enums.Constants;
import com.zdxf.common.enums.OperatorType;
import com.zdxf.common.exception.CustomException;
import com.zdxf.common.module.ResultJson;
import com.zdxf.common.utils.SpringSecurityUtils;
import com.zdxf.login.dto.LoginDto;
import com.zdxf.login.service.LoginService;
import com.zdxf.sysmanage.Menu;
import com.zdxf.sysmanage.User;
import com.zdxf.sysmanage.UserDetail;
import com.zdxf.sysmanage.asyncmanage.AsyncFactory;
import com.zdxf.sysmanage.asyncmanage.AsyncManager;
import com.zdxf.sysmanage.mapper.MenuMapper;
import com.zdxf.sysmanage.mapper.RoleMenuMapper;
import com.zdxf.sysmanage.mapper.UserMapper;
import com.zdxf.login.mapper.UserRolesMapper;
import com.zdxf.utils.JwtUtils;
import com.zdxf.utils.ResponseUserToken;
import com.zdxf.vo.menu.MenuListVo;
import org.apache.commons.beanutils.ConvertUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Admin
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMenuMapper roleMenuMapper;
    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private MenuMapper menuMapper;
    @Resource
    private AuthenticationManager authenticationManager;

    @Override
    public ResponseUserToken login(LoginDto longDto) {
        //用户验证
        final Authentication authentication = authenticate(longDto.getUserName(), longDto.getPassWord());
        //存储认证信息
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //生成token,查看源代码会发现调用getPrincipal()方法会返回一个实现了`UserDetails`接口的对象
        final UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        //通过工具类生成token
        final String token = "Bearer "+jwtUtils.generateToken(userDetail);

        //存储token
        jwtUtils.putToken(userDetail.getUsername(),token);
        // 学习 测试用,把用户的信息也返回了
        return new ResponseUserToken(token, userDetail);

    }


    private Authentication authenticate(String username, String password) {
        try {
            //该方法会去调用userDetailsService.loadUserByUsername()去验证用户名和密码，
            // 如果正确，则存储该用户名密码到“security的context中”
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException | BadCredentialsException e) {
            throw new CustomException(ResultJson.error(CodeType.LOGIN_ERROR.getCode(), e.getMessage()));
        }
    }

    /**
     * 获取菜单列表
     *
     * @return
     */
    @Override
    public ResultJson getMenuList() {
        // 获取用户信息
        //User user = userMapper.findByUsername(SpringSecurityUtils.getCurrentUserName());

        User user = userMapper.findByUsername("18000000006");
        // 用户角色
        String[] roleList = user.getRoleIds().split(",");
        // 用户独立权限
        //String[] ruleList = user.getRules().split(",");
        Map<String, Object> map = new HashMap<>();
        map.put("roleList", (Integer[]) ConvertUtils.convert(roleList, Integer.class));
        //map.put("ruleList", (Integer[]) ConvertUtils.convert(ruleList, Integer.class));
        List<Menu> menuList = roleMenuMapper.getMenuList(map);
        List<MenuListVo> menuListVoList = new ArrayList<>();
        if (!menuList.isEmpty()) {
            menuList.forEach(item -> {
                MenuListVo menuListVo = new MenuListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, menuListVo);
                // 获取类型名称
                menuListVo.setTypeName(CommonConfig.MENU_TYPE_LIST.get(item.getType()));
                // 获取子级
                menuListVo.setChildren(this.getChildMenuList(item.getId()));
                menuListVoList.add(menuListVo);
            });
        }
        return ResultJson.success("操作成功", menuListVoList);
    }

    /**
     * 根据父级ID获取子级菜单
     *
     * @param pid 父级ID
     * @return
     */
    private List<MenuListVo> getChildMenuList(Integer pid) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("p_id", pid);
        queryWrapper.eq("deleted",0);
        queryWrapper.orderByAsc("sort");

        List<MenuListVo> menuListVoList = new ArrayList<>();
        List<Menu> menuList = menuMapper.selectList(queryWrapper);
        if (!menuList.isEmpty()) {
            menuList.forEach(item -> {
                // 菜单列表Vo
                MenuListVo menuListVo = new MenuListVo();
                // 拷贝属性
                BeanUtils.copyProperties(item, menuListVo);
                // 获取类型名称
                menuListVo.setTypeName(CommonConfig.MENU_TYPE_LIST.get(item.getType()));
                // 获取子级
                List<MenuListVo> childrenMenuList = this.getChildMenuList(item.getId());
                if (childrenMenuList != null) {
                    menuListVo.setChildren(childrenMenuList);
                }
                menuListVoList.add(menuListVo);
            });
        }
        return menuListVoList;
    }

    /**
     * 退出登录
     *
     * @return
     */
    @Override
    public ResultJson logout() {
        // 获取当前登录人信息
        UserDetail userDetail = (UserDetail) SpringSecurityUtils.getUserDetail();
        // 记录用户退出日志
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(userDetail.getUsername(), Constants.LOGOUT, "退出成功"));
        // 退出登录
//        ShiroUtils.logout();
        return ResultJson.success("注销成功");
    }
}
