var iframeHeight;
var $org;
$(function () {
  buildTable();
  buildVue();
  // 图标可以点击选中 icp-auto 操作图标元素
  buildIconPicker();
  buildSelect2()
  buildToastr();
  validator();
});

function buildTable() {
  iframeHeight = window.top.$("#main").height();
  $("#jqGrid").jqGrid({
    url: '/sys/org/treeList',
    mtype: "POST",
    styleUI : 'Bootstrap',
    datatype: "json",
    autowidth: true, //宽度自适应
    pagerpos:"center",
    pgbuttons:true,
    // rownumbers:true,  //显示行号
    postData:{
      sortOrder:'orgId'
    },
    // autoScroll: false,
    // shrinkToFit: false,
    colModel: [
      { label: '编号', name: 'orgId', width: 150,key: true,},
      { label: '名称', name: 'name', width: 200 },
      { label: '机构编码', name: 'code', width: 150},
      { label: '上级机构', name: 'parentName', width: 150 },
      { label: '可用', name: 'status', width: 150 },
      { label:'排序号', name: 'orderNum', width: 100 },
    ],
    jsonReader:{
      root: "rows",
      page: "curPage",
      total: "pageNums",
      records: "totalRows",
    },
    viewrecords: true,
    emptyrecords:"",
    height: iframeHeight - 100,
    // rowNum: 20,
    // reccount: 20,
    // rowlist:[10,20,30],
    // sortName:'orderNum',
    // pager: "#jqGridPager",
    toolbar:[true,"top"],
    //树行表格属性
    treeGrid: true,
    treeGridModel: 'adjacency',
    ExpandColumn: 'orgId',
    ExpandColClick: true,
    treeReader: {
      level_field: "level",
      parent_id_field: "parentId",
      leaf_field: "leaf",  // 是否叶子节点字段，boolean类型
      expanded_field: "expanded" //treeGrid是否展开字段 ，boolean类型
    },
    //树行表格设置结束
    multiselect:true,
    multiboxonly:true,
    onSelectRow: function (rowId, status, e) {
      var lastSel;
      if (rowId == lastSel) {
        $(this).jqGrid("resetSelection");
        lastSel = undefined;
        status = false;
      } else {
        lastSel = rowId;
      }
    },
    beforeSelectRow: function (rowId, e) {
      $(this).jqGrid("resetSelection");
      return true;
    },
  });
  // $("#jqGrid").jqGrid('setFrozenColumns')
  $("#t_jqGrid").append("&nbsp;<button class='btn btn-primary ml-2' value='增加平级' id='addBtn' onclick='addBtn()'>增加平级</button>");
  $("#t_jqGrid").append("&nbsp;<button class='btn btn-primary ml-2' value='增加下级' id='addchildBtn' onclick='addchildBtn()'>增加下级</button>");
  $("#t_jqGrid").append("&nbsp;<button class='btn btn-success ml-2' value='编辑' id='editBtn' onclick='editBtn()'>编辑</button>");
  $("#t_jqGrid").append("&nbsp;<button class='btn btn-danger ml-2' value='删除' id='delBtn' onclick='delBtn()'>删除</button>");
  $(window).on('resize',function () {
    iframeHeight = window.top.$("#main").height();
    $("#jqGrid").jqGrid("setGridHeight", iframeHeight - 100);
    $("#jqGrid").setGridWidth($("#gridDiv").width());
  }).resize();
}

function addBtn() {//增加平级
  var rowid=$("#jqGrid").jqGrid("getGridParam","selrow");
  if (rowid == undefined || rowid == 'norecs') {
    toastr.info("请先选择一行数据！", '提示');
  }else {
    $org.org = {};
    $('#orgForm').data('bootstrapValidator').resetForm(true);
    var rowData=jQuery("#jqGrid").jqGrid("getRowData",rowid);
    var orgItem = {
      orgId:null,
      parentId:rowData.parentId,
      level:rowData.level,
      // isLeaf:rowData.isLeaf,
      expanded:rowData.expanded,
      name:"",
      url:"",
      icon:"",
      status:"",
      orderNum:Number(rowData.orderNum)+1,
    };
    $org.org = orgItem;
    $('#orgAddWin').modal("show");
  }

}

function addchildBtn() {
  var rowid=$("#jqGrid").jqGrid("getGridParam","selrow");
  if(rowid == undefined || rowid == 'norecs'){

  }else{
    $org.org = {};
    $('#orgForm').data('bootstrapValidator').resetForm(true);
    var rowData=jQuery("#jqGrid").jqGrid("getRowData",rowid);
    var orgItem = {
      orgId:null,
      parentId:rowData.orgId,
      level:Number(rowData.level)+1,
      // leaf:rowData.isLeaf,
      expanded:rowData.expanded,
      name:"",
      code:"",
      orderNum:0,
    };
    $org.org = orgItem;
    $('#orgAddWin').modal("show");
  }
}

function editBtn() {
  var rowid=$("#jqGrid").jqGrid("getGridParam","selrow");
  if(rowid == undefined || rowid == 'norecs'){
    toastr.info("未选择菜单！", '提示');
  }else{
    $org.org = {};
    $('#orgForm').data('bootstrapValidator').resetForm(true);
    var rowid=$("#jqGrid").jqGrid("getGridParam","selrow");
    var rowData=jQuery("#jqGrid").jqGrid("getRowData",rowid);
    $org.org = rowData;
    $('#orgAddWin').modal("show");
    console.info($org.org);
  }
}
function delBtn() {
  var rowid=$("#jqGrid").jqGrid("getGridParam","selrow");
  if(rowid == undefined || rowid == 'norecs'){
    toastr.info("未选择菜单！", '提示');
  }else{
    $('#checkMod').modal("show");
  }
}

function deleteSel(){
  var rowid=$("#jqGrid").jqGrid("getGridParam","selrow");
  var rowData=jQuery("#jqGrid").jqGrid("getRowData",rowid);
  var orgId = rowData.orgId;
  $.ajax({
    type: "POST",
    url: "/sys/org/delete?_" + $.now(),
    data: JSON.stringify(orgId),
    dataType: "json",
    contentType : "application/json;charset=utf8",
    success: function (result) {
      if (result.code == 0) {
        toastr.success(result.msg);
        $("#checkMod").modal('hide');
      } else {
        toastr.warning(result.msg);
      }
      var rowid=$("#jqGrid").jqGrid("getGridParam","selrow");
      var b=$("#jqGrid").jqGrid("setRowData",rowid,JSON.stringify(this.org));
      $("#jqGrid").trigger("reloadGrid");
      if(b){
        $("#orgAddWin").modal('hide');
      }
    },
  });
}

function buildSelect2(){
  //类型 下拉框
  $("#type").select2({
    tags: true,
    width: "100%",
    minimumResultsForSearch: Infinity, // 隐藏搜索框
    theme: "default", // 样式
    // maximumSelectionLength: 1  //最多能够选择的个数
  });
  //选择事件
  // $("#type").on("select2:select",function(e){
  //   console.info("select2:select", e);
  // });
  //是否展开 下拉框
  $("#expanded").select2({
    tags: true,
    width: "100%",
    minimumResultsForSearch: Infinity, // 隐藏搜索框
    theme: "default", // 样式
    // maximumSelectionLength: 1  //最多能够选择的个数
  });
  //选择事件
  // $("#expanded").on("select2:select",function(e){
  //   console.info("select2:select", e);
  // });
}

//初始化提示框
function buildToastr(){
  toastr.options = {
    closeButton: true,
    debug: false,
    progressBar: false,
    positionClass: "toast-top-center",
    onclick: null,
    showDuration: "300",
    hideDuration: "1000",
    timeOut: "3000",
    extendedTimeOut: "1000",
    showEasing: "swing",
    hideEasing: "linear",
    showMethod: "fadeIn",
    hideMethod: "fadeOut"
  };
}

function validator() {
  $('#orgForm').bootstrapValidator({
    message: '未通过验证',
    feedbackIcons: {
      valid: 'glyphicon glyphicon-ok',
      invalid: 'glyphicon glyphicon-remove',
      validating: 'glyphicon glyphicon-refresh'
    },
    fields:{
      name:{
        validators: {
          notEmpty: {
            message: '机构名称不可为空！'
          }
        }
      },
    }
  });
}

function buildVue() {
  $org = new Vue({
    el: '#orgAddWin',
    data: {
      org:{
      }
    },
    methods:{
      bindIcon: function(event) {
        this.org.icon = $("#icon").val();
      },
      blur: function(event){
        if(event.target.value == ""){
          $("#"+event.target.id).focus();
        }
      },
      saveOrg: function () {
        $('#orgForm').bootstrapValidator('validate');
        // console.info(JSON.stringify(this.org));
        if($('#orgForm').data('bootstrapValidator').isValid()){
          $.ajax({
            type: "POST",
            url: "/sys/org/save?_" + $.now(),
            data: JSON.stringify(this.org),
            dataType: "json",
            contentType : "application/json;charset=utf8",
            success: function (result) {
              if (result.code == 0) {
                toastr.success(result.msg);
              } else {
                toastr.warning(result.msg);
              }
              var rowid=$("#jqGrid").jqGrid("getGridParam","selrow");
              var b=$("#jqGrid").jqGrid("setRowData",rowid,JSON.stringify(this.org));
              $("#jqGrid").trigger("reloadGrid");
              if(b){
                $("#orgAddWin").modal('hide');
              }
            },
          });
        }
      }
    }
  });
}

function buildIconPicker() {
  $('.icp-auto').iconpicker({
    theme:'fip-bootstrap',
    title: '请选择一个图标',
    //  指定图标
    //icons:['fa-github', 'fa-heart', 'fa-html5', 'fa-css3'],
    // 添加其他图标 加入 bootstrap  glyphicon 字体图标
    icons: $.merge(['fa-github'], $.iconpicker.defaultOptions.icons),
    fullClassFormatter: function(val){
      if(val.match(/^fa-/)){
        return 'fa '+val;
      }else{
        return 'glyphicon '+val;
      }
    },
    component: '.input-group-addon', // 图标存放容器
    /* Placements: inline topLeftCorner topLeft top topRight topRightCorner rightTop right rightBottom bottomRightCorner bottomRight bottom bottomLeft bottomLeftCorner leftBottom left leftTop*/
    placement:'right',  // 图标容器位置
  });
}