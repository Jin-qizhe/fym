package com.ydt.bean;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统操作日志
 * </p>
 *
 * @author ydt
 * @since 2021-02-01
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 功能名称
     */
    private String name;

    /**
     * url
     */
    private String url;

    /**
     * 操作用户ID
     */
    private Integer userId;

    /**
     * 操作用户名称
     */
    private String userName;

    /**
     * 请求来源地址
     */
    private String host;

    /**
     * 日志打印时间
     */
    private Date logTime;

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

    public SLog() {
    }

    public SLog(String name, String url, Integer userId, String userName, String host, Date logTime) {
        this.name = name;
        this.url = url;
        this.userId = userId;
        this.userName = userName;
        this.host = host;
        this.logTime = logTime;
    }
}
