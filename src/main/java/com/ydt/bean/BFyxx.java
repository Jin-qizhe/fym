package com.ydt.bean;

import java.util.Date;

import com.baomidou.mybatisplus.annotation.TableField;

import java.io.Serializable;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author ydt
 * @since 2022-04-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class BFyxx implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String xm;

    private String zjhm;

    private String sjhm;

    private String jkmzt;

    private String jxcs;

    private String instanceName;

    private String xxdz;

    private String ssdq;

    private String gpsX;

    private String gpsY;

    private Date insertTime;


}
