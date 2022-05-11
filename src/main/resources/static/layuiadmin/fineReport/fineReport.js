// 永康警情月报
var ykjqyb = frBaseUrl + "report?viewlet=YK%252FJQYB%252FYKJQ-YB-20190001.cpt&op=view";
// 永康警情周报
var ykjqzb = frBaseUrl + "report?viewlet=YK%252FJQYB%252FYKJQ-ZB-20190001.cpt&op=view";
//开屏播报(今日简报)
var jrjb = frBaseUrl + "form?viewlet=YK%252FJRJB%252FJRJB.frm";
var jrjb1920 = frBaseUrl + "form?viewlet=YK%252FJRJB%252FJRJB1920.frm";//适配1920*1080分辨率
//永康派出所日报告
var xxpcs = frBaseUrl+'report?viewlet=YK%252FPCS%252Fxxrb.cpt&op=view';
var cqpcs = frBaseUrl+'report?viewlet=YK%252FPCS%252Fcsrb.cpt&op=view';
//永康派出所周报告
var xxzb = frBaseUrl+'report?viewlet=YK%252FPCS%252Fxxzb.cpt&op=view';
var cqzb = frBaseUrl+'report?viewlet=YK%252FPCS%252Fcszb.cpt&op=view';
//永康派出所月报告
var xxyb = frBaseUrl+'report?viewlet=YK%252FPCS%252Fxxyb.cpt&op=view';
var cqyb = frBaseUrl+'report?viewlet=YK%252FPCS%252Fcsyb.cpt&op=view';
//永康队周报告
var fkbzb = frBaseUrl+'report?viewlet=YK%252FFKB%252Fajfx_z.cpt&op=view';

//永康标签月报
var ykbqyb= frBaseUrl+'report?viewlet=YK%2FBQFX%2Fbqfx.cpt&op=view';
//永康纠纷警情(公安版)
var ykjfjq1= frBaseUrl+'report?viewlet=YK%2FJFFX%2Fjffx.cpt&op=view';
//永康求助警情
var ykqzjq= frBaseUrl+'report?viewlet=YK%2FQZFX%2Fqzfx.cpt&op=view';
//永康盗窃警情
var ykdqjq= frBaseUrl+'report?viewlet=YK%2FDQFX%2Fdqfx.cpt&op=view';
//永康诈骗警情
var ykzpjq= frBaseUrl+'report?viewlet=YK%2Fwlzpfx%2Ftxwlzpfx.cpt&op=view';
//永康纠纷警情(政府版)
var ykjfjq2= frBaseUrl+'report?viewlet=YK%252FZFJFFX%252Fjffx.cpt&op=view';

//秋滨派出所日报告
var qbpcsrb = frBaseUrl+'report?viewlet=QBPCS%252Fxxrb.cpt&op=view';
//秋滨派出所周报告
var qbpcszb = frBaseUrl+'report?viewlet=QBPCS%252Fxxzb.cpt&op=view';
//秋滨派出所月报告
var qbpcsyb = frBaseUrl+'report?viewlet=QBPCS%252Fxxyb.cpt&op=view';

//交通警情报告
var jtjqbg = frBaseUrl+'report?viewlet=YK%252FJQYB%252FYKJTJQ-BB.cpt&op=view';

/**
 * 打开治安简报
 * @param yhmc 当前登入用户名称
 * @param AutoClose 是否自动关闭页面：true-自动关闭
 * @param sfzh 身份证号码
 */
function openJrjb(yhmc, sfzh, AutoClose){
    //获取当前登入用户的单位ID
    $.ajax({
        url: "/yh/getYhDwid",
        async: false,
        success: function (d) {
            var DWDM = d;
            if(d.length==8){
                CLDWDM=d.substring(0,6);
            }else{
                CLDWDM=d;
            }
            //新窗口打开治安播报页面
            if(window.screen.width==1920 && window.screen.height==1080){
                //1920*1080分辨率
                window.open(jrjb1920+"&DWID="+DWDM+"&CLDWID="+CLDWDM+"&yhmc="
                    +yhmc+'&AutoClose='+AutoClose+"&sfzh="+sfzh, "_blank");
            }else {
                //其他分辨率
                window.open(jrjb+"&DWID="+DWDM+"&CLDWID="+CLDWDM+"&yhmc="
                    +yhmc+'&AutoClose='+AutoClose+"&sfzh="+sfzh, "_blank");
            }

        }
    });
}