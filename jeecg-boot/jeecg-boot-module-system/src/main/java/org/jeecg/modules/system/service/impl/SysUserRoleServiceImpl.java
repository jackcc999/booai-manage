package org.jeecg.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.jeecg.modules.system.entity.SysUserRole;
import org.jeecg.modules.system.mapper.SysUserRoleMapper;
import org.jeecg.modules.system.service.ISysUserRoleService;
import org.jeecg.modules.system.vo.UserRoleVo;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @Author scott
 * @since 2018-12-21
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements ISysUserRoleService
{
    @Override
    public List<String> getSysUserRoleIdsByUserId(String userId)
    {
        return baseMapper.getSysUserRoleIdsByUserId(userId);
    }

    @Override
    public List<UserRoleVo> getSysUserRoleListByUserIds(List<String> userIds)
    {
        return baseMapper.getSysUserRoleListByUserIds(userIds);
    }

    @Override
    public int deleteUserRoleListByUserId(String userId)
    {
        return baseMapper.deleteUserRoleListByUserId(userId);
    }
}
