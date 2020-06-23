var iframeHeight;
var $role;
$(function () {
  buildTable();
  buildToastr();
  bulidVue();
  buildSelect2();
  buildZtree();
  validator();
});

function buildTable() {
  iframeHeight = window.top.$("#main").height();
  $("#jqGrid").jqGrid({
    url: '/sys/role/list',
    mtype: "POST",
    styleUI : 'Bootstrap',
    datatype: "json",
    autowidth: true, //宽度自适应
    pagerpos:"center",
    pgbuttons:true,
    rownumbers:true,  //显示行号
    postData:{
      sortOrder:'roleId'
    },
    // autoScroll: false,
    // shrinkToFit: true,//宽度自适应
    colModel: [
      { label: '编号', name: 'roleId', width: 100,key: true,align:"center" },
      { label: '角色名称', name: 'roleName', width: 200,align:"center" },
      { label: '角色标识', name: 'roleSign', width: 150 },
      { label: '所属机构', name: 'orgName', width: 150 },
      { label: '备注', name: 'remark', width: 200 },
      { label: '创建时间', name: 'gmtCreate', width: 150 },
      { label:'orgId',hidden:'true', name: 'orgId', width: 150 }
    ],
    jsonReader:{
      root: "rows",
      page: "curPage",
      total: "pageNums",
      records: "totalRows",
    },
    viewrecords: false,
    height: iframeHeight - 130,
    rowNum: 20,
    reccount: 20,
    rowlist:[10,20,30],
    sortName:'id',
    pager: "#jqGridPager",
    toolbar:[true,"top"],
    multiselect:false,
    multiboxonly:true,//checkbox
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
  $("#t_jqGrid").append("&nbsp;<button class='btn btn-default ml-2' value='操作权限' id='operatingBtn' onclick='operatingBtn()'>操作权限</button>");
  $(window).on('resize',function () {
    iframeHeight = window.top.$("#main").height();
    $("#jqGrid").jqGrid("setGridHeight", iframeHeight - 130);
    $("#jqGrid").setGridWidth($("#gridDiv").width());
  }).resize();
}

function addBtn() {
  $role.role = {};
  // $('#roleForm').data('bootstrapValidator').resetForm(true);
  $('#roleEditWin').modal("show");
}

function editBtn() {
  var rowid=$("#jqGrid").jqGrid("getGridParam","selrow");
  if(rowid == undefined || rowid == 'norecs'){
    toastr.info("未选择角色！", '提示');
  }else{
    $role.role = {};
    $('#roleForm').data('bootstrapValidator').resetForm(true);
    var rowid=$("#jqGrid").jqGrid("getGridParam","selrow");
    var rowData=jQuery("#jqGrid").jqGrid("getRowData",rowid);
    $("#orgId").select2("data",rowData.orgId);
    $("#orgId").val(rowData.orgId).trigger("change");
    $role.role = rowData;
    $('#roleEditWin').modal("show");
  }
}

function delBtn() {
  var rowid=$("#jqGrid").jqGrid("getGridParam","selrow");
  if(rowid == undefined || rowid == 'norecs'){
    toastr.info("未选择角色！", '提示');
  }else{
    $('#checkMod').modal("show");
  }
}

function deleteSel(){
  var roleId=$("#jqGrid").jqGrid("getGridParam","selrow");
  $.ajax({
    type: "POST",
    url: "/sys/role/delete?_" + $.now(),
    data: JSON.stringify(roleId),
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
      $("#jqGrid").trigger("reloadGrid");
    },
  });
}

function operatingBtn() {
  var rowid=$("#jqGrid").jqGrid("getGridParam","selrow");
  if(rowid == undefined || rowid == 'norecs'){
    toastr.info("未选择角色！", '提示');
  }else{
    $.ajax({
      type: "POST",
      url: "/sys/menu/listByRoleId",
      data: JSON.stringify(rowid),
      dataType: "json",
      contentType : "application/json;charset=utf8",
      success: function (result) {
        var tree = $.fn.zTree.getZTreeObj("menuTree");
        if (result.code == 0) {
          for(var i=0;i<result.obj.length;i++){
            tree.checkNode( tree.getNodeByParam( "id",result.obj[i] ), true );
          }
          $('#menusWin').modal("show");
        } else {
          toastr.warning(result.msg);
        }
      },
    });
  }
}

function saveMenuRole() {
  var treeObj = $.fn.zTree.getZTreeObj("menuTree");
  var nodes = treeObj.getCheckedNodes(true);
  var roleId = $("#jqGrid").jqGrid("getGridParam","selrow");
  var menuRoles = [];
  for(var i=0;i<nodes.length;i++){
    var id = {
      menuId:nodes[i].id,
      roleId:roleId
    }
    menuRoles.push(id);
  }
  $.ajax({
    type: "PUT",
    url: "/sys/menu/saveMenuRole?_" + $.now(),
    data: JSON.stringify(menuRoles),
    dataType: "json",
    contentType : "application/json;charset=utf8",
    success: function (result) {
      if (result.code == 0) {
        toastr.success(result.msg);
      } else {
        toastr.warning(result.msg);
      }
      $("#jqGrid").trigger("reloadGrid");
      $("#menusWin").modal('hide');
    },
  });
}

function buildToastr() {
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
    $role.role.orgId = $("#orgId").select2("val");
  });
}

function buildZtree() {
  var setting = {
    view: {
      selectedMulti : true,//设置是否允许同时选中多个节点。

    },
    check: {
      enable: true,
      chkboxType : {"Y": "ps", "N": "ps"},//勾选 checkbox 对于父子节点的关联关系。[setting.check.enable = true 且 setting.check.chkStyle = "checkbox" 时生效]
      chkStyle : "checkbox",//勾选框类型(checkbox 或 radio）[setting.check.enable = true 时生效]
    },
    data: {
      key: {
        name : "menuName",
        title : "menuName",
      },
      simpleData: {
        enable: true,
        idKey : "id",
        pIdKey : "parentId",
        rootPId : null
      }
    }
  }
  $.ajax({
    type: "POST",
    url: "/sys/menu/treeListAll?_" + $.now(),
    data: {},
    dataType: "json",
    contentType : "application/json;charset=utf8",
    success: function (result) {
      var zNodes = result.rows;
      if (result.rows.length > 0) {
        $.fn.zTree.init($("#menuTree"), setting, zNodes);
      }
    }
  });
}

function bulidVue() {
  $role = new Vue({
      el:"#roleEditWin",
      data:{
        role:{
        }
      },
      methods:{
        saveRole:function(){
          $('#roleForm').bootstrapValidator('validate');
          if($('#roleForm').data('bootstrapValidator').isValid()){
            $.ajax({
              type: "POST",
              url: "/sys/role/save?_" + $.now(),
              data: JSON.stringify(this.role),
              dataType: "json",
              contentType : "application/json;charset=utf8",
              success: function (result) {
                if (result.code == 0) {
                  toastr.success(result.msg);
                } else {
                  toastr.warning(result.msg);
                }
                var rowid=$("#jqGrid").jqGrid("getGridParam","selrow");
                var b=$("#jqGrid").jqGrid("setRowData",rowid,JSON.stringify(this.role));
                $("#jqGrid").trigger("reloadGrid");
                if(b){
                  $("#roleEditWin").modal('hide');
                }
              },
            });
          }
        }
      }
    });
}
function validator() {
$('#roleForm').bootstrapValidator({
  message: '未通过验证',
  feedbackIcons: {
    valid: 'glyphicon glyphicon-ok',
    invalid: 'glyphicon glyphicon-remove',
    validating: 'glyphicon glyphicon-refresh'
  },
  fields:{
    roleName:{
      validators: {
        notEmpty: {
          message: '角色名称不能为空！'
        }
      }
    },
    roleSign:{
      validators: {
        notEmpty: {
          message: '角色标识不能为空！'
        }
      }
    },
  }
});
}