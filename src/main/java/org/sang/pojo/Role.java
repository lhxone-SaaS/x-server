package org.sang.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.models.auth.In;

import java.io.Serializable;

/**
 * Created by sang on 2017/12/17.
 */
@ApiModel
public class Role implements Serializable {

    @ApiModelProperty(name = "id", value = "id", notes = "id", dataType = "java.lang.Integer")
    private Integer id;

    // 角色编码
    @ApiModelProperty(name = "roleCode", value = "角色编码", notes = "角色编码", dataType = "java.lang.String")
    private String roleCode;

    // 角色名称
    @ApiModelProperty(name = "roleName", value = "角色名称", notes = "角色名称", dataType = "java.lang.String")
    private String roleName;

    @ApiModelProperty(name="description", value = "角色描述", notes = "角色描述", dataType = "java.lang.String")
    private String description;

    public Role() {
    }

    public Role(Integer id, String roleCode, String roleName) {
        this.id = id;
        this.roleCode = roleCode;
        this.roleName = roleName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
