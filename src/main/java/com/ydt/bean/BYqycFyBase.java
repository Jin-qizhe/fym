package com.ydt.bean;

import java.util.Date;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 防疫基础数据表
 * </p>
 *
 * @author ydt
 * @since 2022-04-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BYqycFyBase implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 证件号码
     */
    private String zjhm;

    /**
     * 姓名
     */
    private String xm;

    /**
     * 手机号码
     */
    private String sjhm;

    /**
     * 最新位置省份
     */
    private String sf;

    /**
     * 最新位置地市
     */
    private String ds;

    /**
     * 所属县市区域
     */
    private String area;

    /**
     * 乡镇街道
     */
    private String xzjd;

    /**
     * 详细地址
     */
    private String xxdz;

    /**
     * geo位置代码
     */
    private String geoHash;

    /**
     * 是否核酸:Y/N
     */
    private String sfhs;

    /**
     * 监控名称
     */
    private String channelName;

    /**
     * 监控code
     */
    private String channelCode;

    /**
     * 经度
     */
    private Double gpsX;

    /**
     * 纬度
     */
    private Double gpsY;

    /**
     * 车牌号
     */
    private String plateNum;

    /**
     * 车辆类型
     */
    private String plateType;

    /**
     * 驾驶类型
     */
    private String driveType;

    /**
     * 抓怕时间
     */
    private Date capTime;

    /**
     * 最后落脚点
     */
    private String zhljd;

    /**
     * 现居地址
     */
    private String xjdz;

    private Date insertTime;

    private Date updateTime;

    private String insertId;

    private String updateId;

    private String insertUser;

    private String updateUser;

    /**
     * 0有效，-1无效
     */
    private Integer status;

    /**
     * 0:技侦；1:雪亮;2:防疫
     */
    private Integer type;


}
