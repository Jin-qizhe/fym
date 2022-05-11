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

  //区块轮播切换
  layui.use(['admin', 'carousel'], function(){
    var $ = layui.$
    ,admin = layui.admin
    ,carousel = layui.carousel
    ,element = layui.element
    ,device = layui.device();

    //轮播切换
    $('.layadmin-carousel').each(function(){
      var othis = $(this);
      carousel.render({
        elem: this
        ,width: '100%'
        ,arrow: 'none'
        ,interval: othis.data('interval')
        ,autoplay: othis.data('autoplay') === true
        ,trigger: (device.ios || device.android) ? 'click' : 'hover'
        ,anim: othis.data('anim')
      });
    });
    
    element.render('progress');
    
  });

  //图表模块
  layui.use(['admin', 'carousel', 'echarts'], function(){
    var $ = layui.$
    ,admin = layui.admin
    ,carousel = layui.carousel
    ,echarts = layui.echarts;

    $.ajax({
      type: "GET",
      url: "/jfyjs/homepageEcharts",
      success: function (data) {
        var days = data.days
        var jqs = data.jqs
        var dbs = data.dbs
        var dws = data.dws
        var dwPie = data.dwPie
        var bqbq = data.bqbq
        var sqbq = data.sqbq
        var max = data.max
        var echartsApp = [], options = [
          //今日流量趋势
          {
            title: {
              text: '近30日警情',
              x: 'center',
              textStyle: {
                fontSize: 14
              }
            },
            tooltip : {
              trigger: 'axis'
            },
            legend: {
              data:['','']
            },
            xAxis : [{
              type : 'category',
              boundaryGap : false,
              data: days
            }],
            yAxis : [{
              type : 'value'
            }],
            series : [{
              name:'警情数',
              type:'line',
              smooth:true,
              itemStyle: {normal: {areaStyle: {type: 'default'}}},
              data: jqs
            },{
              name:'已打标警情数',
              type:'line',
              smooth:true,
              itemStyle: {normal: {areaStyle: {type: 'default'}}},
              data:dbs
            }]
          },

          //访客浏览器分布
          {
            title : {
              text: '各单位警情占比',
              x: 'center',
              textStyle: {
                fontSize: 14
              }
            },
            tooltip : {
              trigger: 'item',
              formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
              orient : 'vertical',
              x : 'left',
              data:dws
            },
            series : [{
              name:'单位',
              type:'pie',
              radius : '55%',
              center: ['50%', '50%'],
              data:dwPie
            }]
          },

          //完全实况球员数据
          {
            title : {
              subtext: '周标签完成情况',
              textStyle: {
                fontSize: 14
              }
            },
            tooltip : {
              trigger: 'axis'
            },
            legend: {
              x : 'left',
              data:['本期','上期']
            },
            polar : [
              {
                indicator : [
                  {text : '打标数', max  : max},
                  {text : '未打标数', max  : max},
                  {text : '审核通过数', max  : max},
                  {text : '审核未通过数', max  : max}
                ],
                radius : 130
              }
            ],
            series : [
              {
                type: 'radar',
                center : ['50%', '50%'],
                itemStyle: {
                  normal: {
                    areaStyle: {
                      type: 'default'
                    }
                  }
                },
                data:[
                  {value : bqbq, name : '本期'},
                  {value : sqbq, name : '上期'}
                ]
              }
            ]
          }
        ]
            ,elemDataView = $('#LAY-index-dataview').children('div')
            ,renderDataView = function(index){
          echartsApp[index] = echarts.init(elemDataView[index], layui.echartsTheme);
          echartsApp[index].setOption(options[index]);
          //window.onresize = echartsApp[index].resize;
          admin.resize(function(){
            echartsApp[index].resize();
          });
        };

        //没找到DOM，终止执行
        if(!elemDataView[0]) return;

        renderDataView(0);

        //监听数据概览轮播
        var carouselIndex = 0;
        carousel.on('change(LAY-index-dataview)', function(obj){
          renderDataView(carouselIndex = obj.index);
        });

        //监听侧边伸缩
        layui.admin.on('side', function(){
          setTimeout(function(){
            renderDataView(carouselIndex);
          }, 300);
        });

        //监听路由
        layui.admin.on('hash(tab)', function(){
          layui.router().path.join('') || renderDataView(carouselIndex);
        });
      }
    })

  });

  layui.use(['admin', 'echarts'], function(){
    var $ = layui.$
        ,admin = layui.admin
        ,echarts = layui.echarts;

    $.ajax({
      type: "GET",
      url: "/jfyjs/jqlbPie",
      success: function (data) {
        var echartsApp = [], options = [
          //各类警情占比
          {
            tooltip : {
              trigger: 'item',
              formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
              orient : 'horizontal',
              y : 'top',
              data:data.nameList
            },
            series : [{
              name:'警情类别',
              type:'pie',
              radius : '40%',
              center: ['50%', '80%'],
              data:data.m
            }]
          }
        ]
            ,elemDataView = $('#gljqzbPie').children('div')
            ,renderDataView = function(index){
          echartsApp[index] = echarts.init(elemDataView[index], layui.echartsTheme);
          echartsApp[index].setOption(options[index]);
          //window.onresize = echartsApp[index].resize;
          admin.resize(function(){
            echartsApp[index].resize();
          });
        };

        //没找到DOM，终止执行
        if(!elemDataView[0]) return;

        renderDataView(0);
      }
    })

  });

  //列表模块
  layui.use('table', function(){
    var $ = layui.$
    ,table = layui.table;

    var date = $("#date").val();
    //今日警情
    table.render({
      elem: '#LAY-index-topSearch'
      ,url: '/jfyjs/jqgl/jjddata'
      ,page: true
      ,where:{"bjsjqj":date}
      ,cols: [[
        {title: '序号', type: 'numbers'}
        , {
          field: 'bjsj',
          title: '报警时间',
          align: 'center',
          width: 170,
          templet: '<div>{{date2str(d.bjsj)}}</div>'
        }
        ,{field: 'jqlxName', title: '警情类型', minWidth: 120}
        ,{field: 'bjnr', title: '报警内容'}
      ]]
      ,skin: 'line'
    });
    
    //今日热贴
    table.render({
      elem: '#LAY-index-topCard'
      ,url: layui.setter.base + 'json/console/top-card.js' //模拟接口
      ,page: true
      ,cellMinWidth: 120
      ,cols: [[
        {type: 'numbers', fixed: 'left'}
        ,{field: 'title', title: '标题', minWidth: 300, templet: '<div><a href="{{ d.href }}" target="_blank" class="layui-table-link">{{ d.title }}</div>'}
        ,{field: 'username', title: '发帖者'}
        ,{field: 'channel', title: '类别'}
        ,{field: 'crt', title: '点击率', sort: true}
      ]]
      ,skin: 'line'
    });
  });
  
  exports('console', {})
});