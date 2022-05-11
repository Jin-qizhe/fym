package com.ydt.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统权限表
 * </p>
 *
 * @author ydt
 * @since 2021-02-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SPower implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 上级id
     */
    private Integer pid;

    /**
     * 菜单标题
     */
    private String title;

    /**
     * 类型：0一级菜单，1二级菜单，2三级菜单，99按钮
     */
    private Integer type;

    /**
     * 菜单地址
     */
    private String url;

    /**
     * 图标
     */
    private String icon;

    /**
     * 按钮标志
     */
    private String btn;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Date insertTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 状态：0有效，-1无效
     */
    private Integer status;

    private int open;
}
