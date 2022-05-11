package com.ydt.controller;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.ydt.bean.BFyxx;
import com.ydt.bean.BYqycFyBase;
import com.ydt.config.UnAuthority;
import com.ydt.controller.res.ScanRes;
import com.ydt.controller.res.InstanceRes;
import com.ydt.controller.res.MyRes;
import com.ydt.controller.res.UserRes;
import com.ydt.controller.vo.BYqycFyBaseVo;
import com.ydt.service.BFyxxService;
import com.ydt.service.BYqycFyBaseService;
import com.ydt.util.MyBigExcelUtil;
import com.ydt.util.StringUtil;
import com.ydt.util.excel.Excel;
import com.ydt.util.excel.ExcelColumn;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.List;


/**
 * <p>
 * 防疫基础数据表 前端控制器
 * </p>
 *
 * @author ydt
 * @since 2022-04-19
 */
@Controller
@RequestMapping("/bYqycFyBase")
public class BYqycFyBaseController extends BaseController {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private BYqycFyBaseService bYqycFyBaseService;
    @Autowired
    private BFyxxService bFyxxService;

    /**
     * 打开页面
     *
     * @return
     */
    @RequestMapping("/page")
//    @UnAuthority
    public String page() {
        return "/yqwy_list";
    }

    /**
     * 数据获取
     *
     * @param vo
     * @return
     */
    @RequestMapping("/pageData")
    @ResponseBody
    @UnAuthority
    public String pageData(BYqycFyBaseVo vo) {
        Page<BYqycFyBase> page = new Page<>(vo.getCurrent(), vo.getSize());
        QueryWrapper<BYqycFyBase> qw = new QueryWrapper<>();
        page = bYqycFyBaseService.page(page, qw);
        return getPageData(page);
    }

    /**
     * 数据获取
     *
     * @param vo
     * @return
     */
    @RequestMapping("/data")
    @ResponseBody
    @UnAuthority
    public String data(BYqycFyBaseVo vo) {
        Page<BFyxx> page = new Page<>(vo.getCurrent(), vo.getSize());
        QueryWrapper<BFyxx> qw = new QueryWrapper<>();
        page = bFyxxService.page(page, qw);
        return getPageData(page);
    }

    /**
     * 数据导入
     *
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping("/dataImport")
    @ResponseBody
    @UnAuthority
    public MyRes dataImport(MultipartFile file) throws Exception {
        bYqycFyBaseService.deleteAllfy();
        MyRes myRes = new MyRes();
        JSONObject object = new JSONObject();
        object.put("code", 0);
        Map<String, String> map = new HashMap<>();
        map.put("姓名", "xm");
        map.put("证件号码", "zjhm");
        map.put("手机号码", "sjhm");
        List<BYqycFyBase> read = MyBigExcelUtil.read(file.getInputStream(), 0, BYqycFyBase.class, map);
        int size = read.size();
        for (int i = 0; i < size; i += 900) {
            List<BYqycFyBase> subList = read.subList(i, i + 900 <= size ? i + 900 : size);
            subList.stream().forEach(l -> {
                if (StringUtil.isNotEmpty(l.getZjhm())) {
                    l.setZjhm(l.getZjhm().toUpperCase());
                }
            });
            bYqycFyBaseService.saveBatch(subList);
        }
        object.put("msg", "ok");
        System.out.println("插入成功!");
        myRes.setSuccess(object.toString());
        return myRes;
    }

    /**
     * 数据导出
     *
     * @param request
     * @param response
     * @param vo
     * @return
     */
    @RequestMapping("/excel")
    @ResponseBody
    @UnAuthority
    public MyRes excel(HttpServletRequest request, HttpServletResponse response, BYqycFyBaseVo vo) {
        MyRes myRes = new MyRes();
        List<ExcelColumn> columns = new ArrayList<>();
        columns.add(new ExcelColumn("xm", "姓名"));
        columns.add(new ExcelColumn("sjhm", "手机号码"));
        columns.add(new ExcelColumn("zjhm", "证件号码"));
        columns.add(new ExcelColumn("jkmzt", "健康码状态"));
        columns.add(new ExcelColumn("jxcs", "接种次数"));
        columns.add(new ExcelColumn("gpsX", "经度"));
        columns.add(new ExcelColumn("gpsY", "纬度"));
        columns.add(new ExcelColumn("instanceName", "场所名称"));
        columns.add(new ExcelColumn("xxdz", "详细地址"));
        columns.add(new ExcelColumn("ssdq", "所属地区"));
        columns.add(new ExcelColumn("insertTime", "扫码时间", "date"));
        vo.setSize(-1);
        Excel excel = new Excel(data(vo), columns);
        excel.writeExcel(response, "防疫码比对数据结果");
        myRes.setSuccess("导出成功!");
        return myRes;
    }

    /**
     * 数据比对
     *
     * @return
     */
    @RequestMapping("/sjbd")
    @ResponseBody
    @UnAuthority
    public MyRes sjbd() {
        MyRes myRes = new MyRes();
        List<BYqycFyBase> list = bYqycFyBaseService.list();
        if (list.size() > 0) {
            list.stream().forEach(l -> {
                try {
                    String param = JSONObject.toJSONString(l, SerializerFeature.WriteNullStringAsEmpty);
                    JSONObject object = doPost("http://192.168.105.170:8080/bYqycFyBase/sjhq", param);
                    BFyxx bFyxx = JSON.toJavaObject(object, BFyxx.class);
                    if (StringUtil.isEmpty(l.getZjhm())) {
                        l.setZjhm(bFyxx.getZjhm());
                    }
                    if (StringUtil.isEmpty(l.getSjhm())) {
                        l.setSjhm(bFyxx.getSjhm());
                    }
                    if (StringUtil.isEmpty(l.getXm())) {
                        l.setXm(bFyxx.getXm());
                    }
                    if (null != bFyxx.getInsertTime()) {
                        bFyxx.setInsertTime(DateUtil.offsetHour(bFyxx.getInsertTime(), -8));
                    }
                    l.setStatus(-1);
                    bYqycFyBaseService.updateById(l);
                    if (StringUtil.isNotEmpty(bFyxx.getZjhm())) {
                        bFyxxService.save(bFyxx);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }
        myRes.setSuccess("成功!");
        return myRes;
    }

    /**
     * 接口调用(信息获取)
     *
     * @param param
     * @return
     */
    @RequestMapping("/sjhq")
    @ResponseBody
    @UnAuthority
    public BFyxx sjhq(@RequestBody BYqycFyBase param) {
        BFyxx bFyxx = new BFyxx();
        UserRes userRes = bYqycFyBaseService.getUser(param.getZjhm(), param.getSjhm());//获取用户信息
        if (userRes != null) {
            if (StringUtil.isNotEmpty(userRes.getXm())) {
                bFyxx.setXm(userRes.getXm());
            }
            if (StringUtil.isNotEmpty(userRes.getZjhm())) {
                bFyxx.setZjhm(userRes.getZjhm());
            }
            if (StringUtil.isNotEmpty(userRes.getSjhm())) {
                bFyxx.setSjhm(userRes.getSjhm());
            }
            if (StringUtil.isNotEmpty(userRes.getJkmzt())) {
                bFyxx.setJkmzt(userRes.getJkmzt());
            }
            if (StringUtil.isNotEmpty(userRes.getJxcs())) {
                bFyxx.setJxcs(userRes.getJxcs());
            }
            //fyxxRes = bYqycFyBaseService.getFyxx(userRes.getCertNo());
            ScanRes scanRes = bYqycFyBaseService.getScanxx(userRes.getUserId());//获取最新的扫码记录
            if (scanRes != null) {
                if (StringUtil.isNotEmpty(scanRes.getGpsX())) {
                    bFyxx.setGpsX(scanRes.getGpsX());
                }
                if (StringUtil.isNotEmpty(scanRes.getGpsY())) {
                    bFyxx.setGpsY(scanRes.getGpsY());
                }
                if (null != scanRes.getInsertTime()) {
                    bFyxx.setInsertTime(scanRes.getInsertTime());
                }
                if (StringUtil.isNotEmpty(scanRes.getInstanceCode())) {
                    InstanceRes instanceRes = bYqycFyBaseService.getInstancexx(scanRes.getInstanceCode());//获取地址信息
                    if (instanceRes != null) {
                        if (StringUtil.isNotEmpty(instanceRes.getInstanceName())) {
                            bFyxx.setInstanceName(instanceRes.getInstanceName());
                        }
                        if (StringUtil.isNotEmpty(instanceRes.getXxdz())) {
                            bFyxx.setXxdz(instanceRes.getXxdz());
                        }
                        if (StringUtil.isNotEmpty(instanceRes.getAddrId())) {
                            String ssdq = bYqycFyBaseService.getSsdq(instanceRes.getAddrId());
                            if (StringUtil.isNotEmpty(ssdq)) {
                                bFyxx.setSsdq(ssdq);
                            }
                        }
                    }
                }
            }
        }
        log.info(String.valueOf(DateUtil.offsetSecond(new Date(), 5)) + "调用一次接口,入参:{zjhm:" + param.getZjhm() + "sjhm:" + param.getSjhm() + "},返回数据为:" + bFyxx);
        return bFyxx;
    }

    /**
     * @return
     */
    @RequestMapping("/sfbdwc")
    @ResponseBody
    @UnAuthority
    public MyRes sfbdwc() {
        MyRes myRes = new MyRes();
        List<BYqycFyBase> bYqycFyBases = bYqycFyBaseService.list(new QueryWrapper<BYqycFyBase>().eq("status", "0"));
        if (bYqycFyBases.size() > 0) {
            myRes.setFailed(-1, "数据未比对完成,请等待...");
        } else {
            myRes.setSuccess("成功!");
        }
        return myRes;
    }

    /**
     * 接口调用(信息获取)
     *
     * @param param
     * @return
     */
    @RequestMapping("/getScanBycode")
    @ResponseBody
    @UnAuthority
    public List<BFyxx> getScanBycode(@RequestBody BYqycFyBaseVo param) {
        List<BFyxx> bFyxxes = new ArrayList<>();
        if (StringUtil.isNotEmpty(param.getYsxh())) {
            Date date = DateUtil.offsetDay(new Date(), -1);
            List<ScanRes> scanRes = bYqycFyBaseService.getScanList(param.getYsxh(), date);
            if (scanRes.size() > 0) {
                scanRes.stream().forEach(s -> {
                    BFyxx bFyxx = Convert.convert(BFyxx.class, s);
                    if (StringUtil.isNotEmpty(s.getUserId())) {
                        UserRes userRes = bYqycFyBaseService.getUserById(s.getUserId());//获取用户信息
                        if (userRes != null) {
                            bFyxx.setZjhm(userRes.getZjhm());
                            bFyxx.setSjhm(userRes.getSjhm());
                            bFyxx.setXm(userRes.getXm());
                            bFyxx.setJkmzt(userRes.getJkmzt());
                            bFyxx.setJxcs(userRes.getJxcs());
                        }
                    }
                    if (StringUtil.isNotEmpty(s.getInstanceCode())) {
                        InstanceRes instanceRes = bYqycFyBaseService.getInstancexx(s.getInstanceCode());//获取地址信息
                        if (null != instanceRes) {
                            bFyxx.setInstanceName(instanceRes.getInstanceName());
                            bFyxx.setXxdz(instanceRes.getXxdz());
                            if (StringUtil.isNotEmpty(instanceRes.getAddrId())) {
                                String ssdq = bYqycFyBaseService.getSsdq(instanceRes.getAddrId());
                                bFyxx.setSsdq(ssdq);
                            }
                        }
                    }
                    bFyxxes.add(bFyxx);
                });
            }
        }
        return bFyxxes;
    }

    /**
     * 接口调用(信息获取)
     *
     * @param param
     * @return
     */
    @RequestMapping("/getScanBycode1")
    @ResponseBody
    @UnAuthority
    public List<BFyxx> getScanBycode1(@RequestBody BYqycFyBaseVo param) {
        List<BFyxx> bFyxxes = bFyxxService.list();

        return bFyxxes;
    }

    @RequestMapping("/yscx")
    @ResponseBody
    @UnAuthority
    public MyRes yscx(String ysxh) {
        MyRes myRes = new MyRes();
        bYqycFyBaseService.deleteAllfy();
        BYqycFyBaseVo vo = new BYqycFyBaseVo();
        vo.setYsxh(ysxh);
        String param = JSONObject.toJSONString(vo, SerializerFeature.WriteNullStringAsEmpty);
        String object = doPost1("http://192.168.105.170:8080/bYqycFyBase/getScanBycode", param);
        System.out.println(object);
        List<BFyxx> bFyxxes = JSONObject.parseArray(object,BFyxx.class);
        if (bFyxxes.size() > 0) {
            bFyxxes.stream().forEach(b->{
                BYqycFyBase bYqycFyBase = new BYqycFyBase();
                bYqycFyBase.setZjhm(b.getZjhm());
                bYqycFyBase.setSjhm(b.getSjhm());
                bYqycFyBase.setXm(b.getXm());
                bYqycFyBase.setStatus(-1);
                bYqycFyBaseService.save(bYqycFyBase);
                if (StringUtil.isNotEmpty(b.getZjhm())) {
                    bFyxxService.save(b);
                }
            });
        }
//        Boolean flag = bFyxxService.saveBatch(bFyxxes);
//        if (flag) {
//            myRes.setSuccess("成功");
//        } else {
//            myRes.setFailed(-1, "失败");
//        }
        myRes.setSuccess("成功");
        return myRes;
    }

    /**
     * post请求
     *
     * @param httpUrl
     * @param param
     * @return
     */
    public static JSONObject doPost(String httpUrl, String param) {
        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null;
        JSONObject object = new JSONObject();
        try {
            URL url = new URL(httpUrl);
            // 通过远程url连接对象打开连接
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接请求方式
            connection.setRequestMethod("POST");
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(1500000);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setReadTimeout(6000000);

            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoOutput(true);
            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setDoInput(true);
            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty("Content-Type", "application/json");
            // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
            connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 通过连接对象获取一个输出流
            os = connection.getOutputStream();
            // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
            os.write(param.getBytes());
            // 通过连接对象获取一个输入流，向远程读取
            if (connection.getResponseCode() == 200) {

                is = connection.getInputStream();
                // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                StringBuffer sbf = new StringBuffer();
                String temp = null;
                // 循环遍历一行一行读取数据
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
                object = JSONObject.parseObject(result);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 断开与远程地址url的连接
            connection.disconnect();
        }
        return object;
    }

    /**
     * post请求
     *
     * @param httpUrl
     * @param param
     * @return
     */
    public static String doPost1(String httpUrl, String param) {
        HttpURLConnection connection = null;
        InputStream is = null;
        OutputStream os = null;
        BufferedReader br = null;
        String result = null;
        JSONArray array = new JSONArray();
        try {
            URL url = new URL(httpUrl);
            // 通过远程url连接对象打开连接
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接请求方式
            connection.setRequestMethod("POST");
            // 设置连接主机服务器超时时间：15000毫秒
            connection.setConnectTimeout(1500000);
            // 设置读取主机服务器返回数据超时时间：60000毫秒
            connection.setReadTimeout(6000000);

            // 默认值为：false，当向远程服务器传送数据/写数据时，需要设置为true
            connection.setDoOutput(true);
            // 默认值为：true，当前向远程服务读取数据时，设置为true，该参数可有可无
            connection.setDoInput(true);
            // 设置传入参数的格式:请求参数应该是 name1=value1&name2=value2 的形式。
            connection.setRequestProperty("Content-Type", "application/json");
            // 设置鉴权信息：Authorization: Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0
            connection.setRequestProperty("Authorization", "Bearer da3efcbf-0845-4fe3-8aba-ee040be542c0");
            // 通过连接对象获取一个输出流
            os = connection.getOutputStream();
            // 通过输出流对象将参数写出去/传输出去,它是通过字节数组写出的
            os.write(param.getBytes());
            // 通过连接对象获取一个输入流，向远程读取
            if (connection.getResponseCode() == 200) {

                is = connection.getInputStream();
                // 对输入流对象进行包装:charset根据工作项目组的要求来设置
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));

                StringBuffer sbf = new StringBuffer();
                String temp = null;
                // 循环遍历一行一行读取数据
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
//                System.out.println(result);
//                array = new JSONArray(Collections.singletonList(result));
//                System.out.println(array);
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            // 断开与远程地址url的连接
            connection.disconnect();
        }
        return result;
    }

    /**
     * get请求
     *
     * @param httpurl
     * @return
     */
    public static String doGet(String httpurl) {
        HttpURLConnection connection = null;
        InputStream is = null;
        BufferedReader br = null;
        String result = null;// 返回结果字符串
        try {
            // 创建远程url连接对象
            URL url = new URL(httpurl);
            // 通过远程url连接对象打开一个连接，强转成httpURLConnection类
            connection = (HttpURLConnection) url.openConnection();
            // 设置连接方式：get
            connection.setRequestMethod("GET");
            // 设置连接主机服务器的超时时间：15000毫秒
            connection.setConnectTimeout(15000);
            // 设置读取远程返回的数据时间：60000毫秒
            connection.setReadTimeout(60000);
            // 发送请求
            connection.connect();
            // 通过connection连接，获取输入流
            if (connection.getResponseCode() == 200) {
                is = connection.getInputStream();
                // 封装输入流is，并指定字符集
                br = new BufferedReader(new InputStreamReader(is, "UTF-8"));
                // 存放数据
                StringBuffer sbf = new StringBuffer();
                String temp = null;
                while ((temp = br.readLine()) != null) {
                    sbf.append(temp);
                    sbf.append("\r\n");
                }
                result = sbf.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭资源
            if (null != br) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            connection.disconnect();// 关闭远程连接
        }

        return result;
    }

    /**
     * 数据比对
     *
     * @return
     */
    @RequestMapping("/sjbd1")
    @ResponseBody
    @UnAuthority
    public MyRes sjbd1() {
        MyRes myRes = new MyRes();
        List<BYqycFyBase> list = bYqycFyBaseService.list();
        try {
            //非线程延时一秒执行(一分钟最多执行60次)
            list.forEach(l -> {
                Robot r = null;
                try {
                    r = new Robot();
                } catch (AWTException e) {
                    e.printStackTrace();
                }
                r.delay(1000);
                String param = JSONObject.toJSONString(l, SerializerFeature.WriteNullStringAsEmpty);
                JSONObject object = doPost("http://192.168.105.170:8080/bYqycFyBase/sjhq", param);
                BFyxx bFyxx = JSON.toJavaObject(object, BFyxx.class);
                if (StringUtil.isNotEmpty(bFyxx.getZjhm())) {
                    bFyxxService.save(bFyxx);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        myRes.setSuccess("成功!");
        return myRes;
    }


}
