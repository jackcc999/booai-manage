package org.jeecg.common.system.vo;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jeecgframework.poi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * 角色
 *
 * @author Lion Li
 */

@Data
@NoArgsConstructor
public class SysRoleVO implements Serializable {

    /**
     * 角色ID
     */
    private String id;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色编码
     */
    @Excel(name="角色编码",width=15)
    private String roleCode;

    /**
     * 描述
     */
    @Excel(name="描述",width=60)
    private String description;

}
