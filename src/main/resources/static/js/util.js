/**获取浏览器滚动条宽度**/
function getScrollWidth() {
    var noScroll, scroll, oDiv = document.createElement("DIV");
    oDiv.style.cssText = "position:absolute; top:-1000px; width:100px; height:100px; overflow:hidden;";
    noScroll = document.body.appendChild(oDiv).clientWidth;
    oDiv.style.overflowY = "scroll";
    scroll = oDiv.clientWidth;
    document.body.removeChild(oDiv);
    return noScroll - scroll;
}

Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
}

/**java Date类型转换成字符串**/
function date2str(timestamp) {
    if(!timestamp){
        return "";
    }
    var date = "";
    if(timestamp){
        date = new Date(timestamp).format("yyyy-MM-dd hh:mm:ss");
    }
    return date;
}

/**java Date类型转换成字符串**/
function date2str2(timestamp) {
    if(!timestamp){
        return "";
    }
    var date = "";
    if(timestamp){
        date = new Date(timestamp).format("yyyy-MM-dd");
    }
    return date;
}

/**java Date类型转换成字符串**/
function date2Month(timestamp) {
    if(!timestamp){
        return "";
    }
    var date = "";
    if(timestamp){
        date = new Date(timestamp).format("yyyy-MM");
    }
    return date.split("-")[0]+'年'+date.split("-")[1]+'月';
}

function gx(txt){
    if(txt && txt==0){
        return "无版本更新";
    }else if(txt && txt==1){
        return "常规更新";
    }else if(txt && txt==2){
        return "强制更新";
    }else{
        return "";
    }
}

/**
 * 导出excel0
 * @param data
 */
function downloadExcel(url, data) {
    var query = "?size=300000";
    if(data){
        jQuery.each(data.where, function (i, val) {
            if (val) {
                query += "&" + i + "=" + val;
            }
        });
    }
    window.open(url + query);
}

//获取表单参数导出
function downloadExcel1(url, data) {
    var query = "?size=300000&";
    query+=data;
    window.open(url + query);
}

/*
 * 验证身份证号码是否合法
 */
var cityArr = { 11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江", 31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖北", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西藏", 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏", 65: "新疆" }//, 71: "台湾", 81: "香港", 82: "澳门", 91: "国外"
function CheckIdentityCode(code) {
    var info = "";
    var flag=true;
    if (code.length == 0 || code == null || code == undefined) {
        flag=false;
    }
    reg = /(^\d{15}$)|(^\d{17}([0-9]|X)$)/i;
    if (!reg.test(code)) {
        flag=false;
    }
    code = code.replace(/x$/i, "a");
    if (cityArr[parseInt(code.substr(0, 2))] == null) {
        flag=false;
    }
    birthDay = code.substr(6, 4) + "-" + Number(code.substr(10, 2)) + "-" + Number(code.substr(12, 2));
    var d = new Date(birthDay.replace(/-/g, "/"));
    if (birthDay != (d.getFullYear() + "-" + (d.getMonth() + 1) + "-" + d.getDate())) {
        flag=false;
    }
    return flag;
}
/**java Date类型转换成字符串**/
function date3str(timestamp) {
    if(!timestamp){
        return "";
    }
    var date = "";
    if(timestamp){
        date = new Date(timestamp).format("yyyy-MM-dd hh:mm:ss");
    }
    return date;
}

/**
 * 空值处理
 * @param t 判断空值对象
 * @param d 默认返回值
 * @returns {string}
 */
function nullDefVal(t, d) {
    return t ? t : d ? d : "";
}