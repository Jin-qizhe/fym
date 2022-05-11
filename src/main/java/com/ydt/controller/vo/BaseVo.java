package com.ydt.controller.vo;

/**
 * Created by lvjianqing on 2017/12/25.
 */
public class BaseVo {
    private Integer id;
    //当前页码
    private int current;
    //每页显示条数
    private int size;

    private String jqlxCas;

    private String[] jqlxCasArr = new String[] {};

    public void setJqlxCas(String jqlxCas) {
        this.jqlxCas = jqlxCas;
        if (this.jqlxCas != null) {
            this.jqlxCasArr = this.jqlxCas.split(",");
        }
    }

    public String getJqlxCas() {
        return jqlxCas;
    }

    /**
     * 获取级联选择数据--警情类型type 0 报警  1 反馈
     *
     * @param
     * @return {@link Integer}
     * @author zhao
     * @time 2021/3/9 16:40
     */
    public Integer getCasJqlxType() {
        if (jqlxCas != null && jqlxCasArr.length > 0) {
            return Integer.valueOf(jqlxCasArr[0]);
        }
        return -1;
    }

    /**
     * 获取级联选择数据--报警类别代码
     *
     * @param
     * @return {@link String}
     * @author zhao
     * @time 2021/3/9 16:43
     */
    public String getCasBjlbdm() {
        if (jqlxCas != null && jqlxCasArr.length > 1 && "0".equals(jqlxCasArr[0])) {
            return jqlxCasArr[1];
        }
        return null;
    }

    /**
     * 获取级联选择数据--报警类型代码
     *
     * @param
     * @return {@link String}
     * @author zhao
     * @time 2021/3/9 16:43
     */
    public String getCasBjlxdm() {
        if (jqlxCas != null && jqlxCasArr.length > 2 && "0".equals(jqlxCasArr[0])) {
            return jqlxCasArr[2];
        }
        return null;
    }

    /**
     * 获取级联选择数据--反馈类别代码
     *
     * @param
     * @return {@link String}
     * @author zhao
     * @time 2021/3/9 16:43
     */
    public String getCasFklbdm() {
        if (jqlxCas != null && jqlxCasArr.length > 1 && "1".equals(jqlxCasArr[0])) {
            return jqlxCasArr[1];
        }
        return null;
    }

    /**
     * 获取级联选择数据--反馈类型代码
     *
     * @param
     * @return {@link String}
     * @author zhao
     * @time 2021/3/9 16:43
     */
    public String getCasFklxdm() {
        if (jqlxCas != null && jqlxCasArr.length > 2 && "1".equals(jqlxCasArr[0])) {
            return jqlxCasArr[2];
        }
        return null;
    }

    /**
     * 获取级联选择数据--反馈细类代码
     *
     * @param
     * @return {@link String}
     * @author zhao
     * @time 2021/3/9 16:43
     */
    public String getCasFkxldm() {
        if (jqlxCas != null && jqlxCasArr.length > 3 && "1".equals(jqlxCasArr[0])) {
            return jqlxCasArr[3];
        }
        return null;
    }



    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        this.current = current;
    }

    public int getSize() {
        if(size==0){
            return 10;
        }
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
