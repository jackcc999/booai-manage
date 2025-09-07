package org.jeecg.modules.system.service;

import java.util.List;
import java.util.Map;

import org.jeecg.modules.system.entity.SysUserRole;

import com.baomidou.mybatisplus.extension.service.IService;
import org.jeecg.modules.system.vo.UserRoleVo;

/**
 * <p>
 * 用户角色表 服务类
 * </p>
 *
 * @Author scott
 * @since 2018-12-21
 */
public interface ISysUserRoleService extends IService<SysUserRole>
{
    public List<String> getSysUserRoleIdsByUserId(String userId);

    public List<UserRoleVo> getSysUserRoleListByUserIds(List<String> userIds);

    public int deleteUserRoleListByUserId(String userId);
}
