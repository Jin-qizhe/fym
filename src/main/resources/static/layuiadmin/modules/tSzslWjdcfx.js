/**

 @Name：layuiAdmin 主页控制台
 @Author：贤心
 @Site：http://www.layui.com/admin/
 @License：GPL-2
    
 */


layui.define(function(exports){
  
  /*
    下面通过 layui.use 分段加载不同的模块，实现不同区域的同时渲染，从而保证视图的快速呈现
  */
  //数据概览
  layui.use(['admin', 'carousel', 'echarts'], function(){
    var $ = layui.$
    ,admin = layui.admin
    // ,carousel = layui.carousel
    ,echarts = layui.echarts;

    $.ajax({
      type: "GET",
      url: "/tSzslWj/hyzkdata",
      success: function (res) {
        var data = res;
        //layer.alert(JSON.stringify(data));
        var echartsApp = [], options = [
          {
            tooltip : {
              trigger: 'item',
              formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
              orient : 'vertical',
              x : 'left',
              data:['未婚','已婚','离异']
            },
            series : [{
              name:'婚姻状况',
              type:'pie',
              radius : '55%',
              center: ['50%', '50%'],
              data:data
            }]
          }
        ]
            ,elemDataView = $('#hyzk').children('div')
            ,renderDataView = function(index){
          echartsApp[index] = echarts.init(elemDataView[index], layui.echartsTheme);
          echartsApp[index].setOption(options[index]);
          window.onresize = echartsApp[index].resize;
          admin.resize(function(){
            echartsApp[index].resize();
          });
        };
        //没找到DOM，终止执行
        if(!elemDataView[0]) return;
        renderDataView(0);
      }
    })

    $.ajax({
      type: "GET",
      url: "/tSzslWj/gwxgdata",
      success: function (res) {
        var data = res;
        //layer.alert(JSON.stringify(data));
        var echartsApp = [], options = [
          {
            tooltip : {
              trigger: 'item',
              formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
              orient : 'vertical',
              x : 'left',
              data:['否','是']
            },
            series : [{
              name:'购物习惯',
              type:'pie',
              radius : '55%',
              center: ['50%', '50%'],
              data:data
            }]
          }
        ]
            ,elemDataView = $('#gwxg').children('div')
            ,renderDataView = function(index){
          echartsApp[index] = echarts.init(elemDataView[index], layui.echartsTheme);
          echartsApp[index].setOption(options[index]);
          window.onresize = echartsApp[index].resize;
          admin.resize(function(){
            echartsApp[index].resize();
          });
        };
        //没找到DOM，终止执行
        if(!elemDataView[0]) return;
        renderDataView(0);
      }
    })


    $.ajax({
      type: "GET",
      url: "/tSzslWj/yxqkdata",
      success: function (res) {
        var data = res;
        //layer.alert(JSON.stringify(data));
        var echartsApp = [], options = [
          {
            tooltip : {
              trigger: 'item',
              formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
              orient : 'vertical',
              x : 'left',
              data:['不玩','玩']
            },
            series : [{
              name:'游戏情况',
              type:'pie',
              radius : '55%',
              center: ['50%', '50%'],
              data:data
            }]
          }
        ]
            ,elemDataView = $('#yxqk').children('div')
            ,renderDataView = function(index){
          echartsApp[index] = echarts.init(elemDataView[index], layui.echartsTheme);
          echartsApp[index].setOption(options[index]);
          window.onresize = echartsApp[index].resize;
          admin.resize(function(){
            echartsApp[index].resize();
          });
        };
        //没找到DOM，终止执行
        if(!elemDataView[0]) return;
        renderDataView(0);
      }
    })

    $.ajax({
      type: "GET",
      url: "/tSzslWj/jzhjdata",
      success: function (res) {
        var data = res;
        //layer.alert(JSON.stringify(data));
        var echartsApp = [], options = [
          {
            tooltip : {
              trigger: 'item',
              formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
              orient : 'vertical',
              x : 'left',
              data:['出租房','自建房','商品房']
            },
            series : [{
              name:'居住环境',
              type:'pie',
              radius : '55%',
              center: ['50%', '50%'],
              data:data
            }]
          }
        ]
            ,elemDataView = $('#jzhj').children('div')
            ,renderDataView = function(index){
          echartsApp[index] = echarts.init(elemDataView[index], layui.echartsTheme);
          echartsApp[index].setOption(options[index]);
          window.onresize = echartsApp[index].resize;
          admin.resize(function(){
            echartsApp[index].resize();
          });
        };
        //没找到DOM，终止执行
        if(!elemDataView[0]) return;
        renderDataView(0);
      }
    })

    $.ajax({
      type: "GET",
      url: "/tSzslWj/gdgzdata",
      success: function (res) {
        var data = res;
        //layer.alert(JSON.stringify(data));
        var echartsApp = [], options = [
          {
            tooltip : {
              trigger: 'item',
              formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
              orient : 'vertical',
              x : 'left',
              data:['有','无']
            },
            series : [{
              name:'固定工作',
              type:'pie',
              radius : '55%',
              center: ['50%', '50%'],
              data:data
            }]
          }
        ]
            ,elemDataView = $('#gdgz').children('div')
            ,renderDataView = function(index){
          echartsApp[index] = echarts.init(elemDataView[index], layui.echartsTheme);
          echartsApp[index].setOption(options[index]);
          window.onresize = echartsApp[index].resize;
          admin.resize(function(){
            echartsApp[index].resize();
          });
        };
        //没找到DOM，终止执行
        if(!elemDataView[0]) return;
        renderDataView(0);
      }
    })

    $.ajax({
      type: "GET",
      url: "/tSzslWj/wllcdata",
      success: function (res) {
        var data = res;
        //layer.alert(JSON.stringify(data));
        var echartsApp = [], options = [
          {
            tooltip : {
              trigger: 'item',
              formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
              orient : 'vertical',
              x : 'left',
              data:['否','是']
            },
            series : [{
              name:'网络理财',
              type:'pie',
              radius : '55%',
              center: ['50%', '50%'],
              data:data
            }]
          }
        ]
            ,elemDataView = $('#wllc').children('div')
            ,renderDataView = function(index){
          echartsApp[index] = echarts.init(elemDataView[index], layui.echartsTheme);
          echartsApp[index].setOption(options[index]);
          window.onresize = echartsApp[index].resize;
          admin.resize(function(){
            echartsApp[index].resize();
          });
        };
        //没找到DOM，终止执行
        if(!elemDataView[0]) return;
        renderDataView(0);
      }
    })


    $.ajax({
      type: "GET",
      url: "/tSzslWj/ywbpdata",
      success: function (res) {
        var data = res;
        //layer.alert(JSON.stringify(data));
        var echartsApp = [], options = [
          {
            tooltip : {
              trigger: 'item',
              formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
              orient : 'vertical',
              x : 'left',
              data:['无','有']
            },
            series : [{
              name:'有无被骗',
              type:'pie',
              radius : '55%',
              center: ['50%', '50%'],
              data:data
            }]
          }
        ]
            ,elemDataView = $('#ywbp').children('div')
            ,renderDataView = function(index){
          echartsApp[index] = echarts.init(elemDataView[index], layui.echartsTheme);
          echartsApp[index].setOption(options[index]);
          window.onresize = echartsApp[index].resize;
          admin.resize(function(){
            echartsApp[index].resize();
          });
        };
        //没找到DOM，终止执行
        if(!elemDataView[0]) return;
        renderDataView(0);
      }
    })

    //监听路由
    layui.admin.on('hash(tab)', function(){
      layui.router().path.join('') || renderDataView(carouselIndex);
    });

  });

  
  exports('tSzslWjdcfx', {})
});