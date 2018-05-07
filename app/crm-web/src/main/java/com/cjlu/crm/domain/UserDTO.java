package com.cjlu.crm.domain;

import lombok.Data;
import lombok.ToString;

import java.util.Date;

/**
 * @author minghui.y
 * @create 2018-05-07 15:04
 **/
@Data
@ToString
public class UserDTO {
    /** 主键 */
    private Integer id;

    /** 用户姓名 */
    private String name;

    /** 用户密码 */
    private String password;

    /** 角色编号 */
    private Integer roleId;
    private String roleName;

    /** 状态:ENABLE、DISABLE */
    private String status;

    /** 创建时间 */
    private Date gmtCreated;

    /** 修改时间 */
    private Date gmtModified;
}
