package com.cjlu.crm.domain;

import java.util.Date;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

@Data
@ToString
@Accessors(chain = true)
public class CrmUser {
    /** 主键 */
    private Integer id;

    /** 用户姓名 */
    private String name;

    /** 用户密码 */
    private String password;

    /** 角色编号 */
    private Integer roleId;

    /** 状态:ENABLE、DISABLE */
    private String status;

    /** 创建时间 */
    private Date gmtCreated;

    /** 修改时间 */
    private Date gmtModified;
}