package com.ydt.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统字典表
 * </p>
 *
 * @author ydt
 * @since 2021-02-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SDictionary implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String dicName;

    private String dicVal;

    private String dicMemo;

    private Integer sort;

    private Integer pid;

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


}
