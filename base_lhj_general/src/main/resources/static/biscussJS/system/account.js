var iframeHeight;
var $account;
$(function () {
  buildTable();
  buildToastr();
  bulidVue();
  buildSelect2();
  validator();
});

function addBtn() {//增加
  $account.account = {accountStatus:0};
  $('#accountForm').data('bootstrapValidator').resetForm(true);
  $('#accountEditWin').modal("show");
}

function editBtn() {
  var rowid=$("#jqGrid").jqGrid("getGridParam","selrow");
  if(rowid == undefined || rowid == 'norecs'){
    toastr.info("未选择账户！", '提示');
  }else{
    $account.account = {accountStatus:0};
    var rowData=jQuery("#jqGrid").jqGrid("getRowData",rowid);
    $account.account = rowData;
    console.info(rowData.orgId);
    $("#orgId").select2("data",rowData.orgId);
    $("#orgId").val(rowData.orgId).trigger("change");
    $('#accountForm').bootstrapValidator('validate');
    $('#accountEditWin').modal("show");
  }
}

function delBtn() {
  var rowid=$("#jqGrid").jqGrid("getGridParam","selrow");
  if(rowid == undefined || rowid == 'norecs'){
    toastr.info("未选择账户！", '提示');
  }else{
    $('#checkMod').modal("show");
  }
}

function deleteSel(){
  var rowid=$("#jqGrid").jqGrid("getGridParam","selrow");
  var rowData=jQuery("#jqGrid").jqGrid("getRowData",rowid);
  var id = rowData.id;
  $.ajax({
    type: "POST",
    url: "/sys/account/delete?_" + $.now(),
    data: JSON.stringify(id),
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
      var b=$("#jqGrid").jqGrid("setRowData",rowid,JSON.stringify(this.menu));
      $("#jqGrid").trigger("reloadGrid");
      if(b){
        $("#accountEditWin").modal('hide');
      }
    },
  });
}

function buildTable() {
  iframeHeight = window.top.$("#main").height();
  $("#jqGrid").jqGrid({
    url: '/sys/account/list',
    mtype: "POST",
    styleUI : 'Bootstrap',
    datatype: "json",
    autowidth: true, //宽度自适应
    pagerpos:"center",
    pgbuttons:true,
    rownumbers:true,  //显示行号
    postData:{
      sortOrder:'id'
    },
    // autoScroll: false,
    // shrinkToFit: false,
    colModel: [
      { label: '用户名', name: 'accountName', width: 150,frozen : true },
      { label: '所属机构', name: 'orgName', width: 150,frozen : true },
      { label: '邮箱', name: 'email', width: 200 },
      { label: '电话', name: 'mobile', width: 100 },
      { label: '状态', name: 'status', width: 100,align:"center",editable:true, edittype: 'select', formatter: 'select',editoptions: {value:"0:正常;1:禁用"}},
      { label: '创建时间', name: 'gmtCreate', width: 100 },
      { label: '备注', name: 'remark', width: 100 },
      { label: 'orgId', name: 'orgId',hidden:'true',  width: 75 },
      { label: 'accountPassword', name: 'accountPassword',hidden:'true',  width: 75 },
      { label: 'ID', name: 'id',hidden:'true', key: true, width: 75 }
    ],
    jsonReader:{
      root: "rows",
      page: "curPage",
      total: "pageNums",
      records: "totalRows",
    },
    viewrecords: false,
    height: iframeHeight - 160,
    rowNum: 20,
    reccount: 20,
    rowlist:[10,20,30],
    sortName:'id',
    pager: "#jqGridPager",
    toolbar:[true,"top"],
    //树行表格属性
    // treeGrid: true,
    // treeGridModel: 'adjacency',
    // ExpandColumn: 'menuName',
    // treeReader: {
    //   level_field: "level",
    //   parent_id_field: "parentId",
    //   leaf_field: "leaf",  // 是否叶子节点字段，boolean类型
    //   expanded_field: "expanded" //treeGrid是否展开字段 ，boolean类型
    // },
    //树行表格设置结束
    multiselect:false,
    multiboxonly:false,//checkbox
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
  $("#t_jqGrid").append("&nbsp;<button class='btn btn-primary ml-2' value='增加下级' id='addBtn' onclick='addBtn()'>新增</button>");
  $("#t_jqGrid").append("&nbsp;<button class='btn btn-success ml-2' value='编辑' id='editBtn' onclick='editBtn()'>编辑</button>");
  $("#t_jqGrid").append("&nbsp;<button class='btn btn-danger ml-2' value='删除' id='delBtn' onclick='delBtn()'>删除</button>");
  $(window).on('resize',function () {
    iframeHeight = window.top.$("#main").height();
    $("#jqGrid").jqGrid("setGridHeight", iframeHeight - 160);
    $("#jqGrid").setGridWidth($("#gridDiv").width());
  }).resize();
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

function bulidVue() {
  $account = new Vue({
    el:"#accountEditWin",
    data:{
      account:{
        accountStatus:0
      }
    },
    methods:{
      saveAccount:function(){
        $('#accountForm').bootstrapValidator('validate');
        if($('#accountForm').data('bootstrapValidator').isValid()){
          $.ajax({
            type: "POST",
            url: "/sys/account/save?_" + $.now(),
            data: JSON.stringify(this.account),
            dataType: "json",
            contentType : "application/json;charset=utf8",
            success: function (result) {
              if (result.code == 0) {
                toastr.success(result.msg);
              } else {
                toastr.warning(result.msg);
              }
              var rowid=$("#jqGrid").jqGrid("getGridParam","selrow");
              var b=$("#jqGrid").jqGrid("setRowData",rowid,JSON.stringify(this.menu));
              $("#jqGrid").trigger("reloadGrid");
              if(b){
                $("#accountEditWin").modal('hide');
              }
            },
          });
        }
      }
    }
  });
}

function buildSelect2(){
  var str = '';
  $.ajax({
    url:"/sys/org/selectAllOrg",
    type:'post',
    data:{},
    dataType:'json',
    async:false,
    contentType:'application/json',
    success:function (data) {
      for (var i = 0; i < data.length; i++) {
        str += '<option value="' + data[i].key + '" >' + data[i].value + '</option>';
      }
    },
    error:function(XMLHttpRequest, textStatus, errorThrown) {
      console.log(XMLHttpRequest.status);
      console.log(XMLHttpRequest.readyState);
      console.log(textStatus);
      console.log("请求失败");
    }
  });
  //类型 下拉框
  $("#orgId").select2({
    tags: true,
    width: "100%",
    // minimumResultsForSearch: Infinity, // 隐藏搜索框
    theme: "default", // 样式
    // maximumSelectionLength: 1  //最多能够选择的个数
  });
  $("#orgId").append(str);
  //选择事件
  $("#orgId").on("select2:select",function(e){
    $account.account.orgId = $("#orgId").select2("val");
  });
}

function validator() {
  $('#accountForm').bootstrapValidator({
    message: '未通过验证',
    feedbackIcons: {
      valid: 'glyphicon glyphicon-ok',
      invalid: 'glyphicon glyphicon-remove',
      validating: 'glyphicon glyphicon-refresh'
    },
    fields:{
      accountName:{
        validators: {
          notEmpty: {
            message: '用户名不可为空'
          },
          stringLength: {
            max: 40,
            message: '用户名长度最长40个字符'
          },
        }
      },
      accountPassword:{
        validators: {
          notEmpty: {
            message: '密码不可为空'
          },
          stringLength: {
            max: 50,
            message: '密码长度最长50个字符'
          },
          identical: {
            field: 'confirmPassword',
            message: '两次输入的密码不同'
          },
        }
      },
      confirmPassword:{
        validators: {
          notEmpty: {
            message: '密码不可为空'
          },
          stringLength: {
            max: 50,
            message: '密码长度最长50个字符'
          },
          identical: {
            field: 'accountPassword',
            message: '两次输入的密码不同'
          },
        }
      },
    }
  });
}