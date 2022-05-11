package com.ydt.controller.res;

import lombok.Data;

import java.util.Date;

/**
 * 扫码信息返回类
 */
@Data
public class ScanRes {

    //用户id
    private String userId;

    //场所id
    private String instanceCode;

    //经度
    private String gpsX;

    //纬度
    private String gpsY;

    //扫码时间
    private Date insertTime;

}
