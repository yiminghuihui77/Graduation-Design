package com.cjlu.crm.domain;

import java.util.Date;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class CrmRole {
    /** 主键 */
    private Integer id;

    /** 角色名称 */
    private String roleName;

    /** 角色描述 */
    private String roleDesc;

    /** 角色权限编号 */
    private String rightUrls;

    /** 创建时间 */
    private Date gmtCreated;

    /** 修改时间 */
    private Date gmtModified;
}