var iframeHeight;
var $employee;
$(function () {
  buildTable();
  buildToastr();
  bulidVue();
  // buildSelect2();
  validator();
});

function addBtn() {//增加
  $employee.employee = {empStatus:2,empNature:0};
  $('#empForm').data('bootstrapValidator').resetForm(true);
  $('#empEditWin').modal("show");
}

function editBtn() {
  var rowid=$("#jqGrid").jqGrid("getGridParam","selrow");
  if(rowid == undefined || rowid == 'norecs'){
    toastr.info("未选择员工！", '提示');
  }else{
    // $employee.employee = {empStatus:0};
    var rowData=jQuery("#jqGrid").jqGrid("getRowData",rowid);
    $employee.employee = rowData;
    // console.info($employee.employee.empGender);
    // $("#empGender").select2("data",rowData.empGender);
    $('#empForm').bootstrapValidator('validate');
    $('#empEditWin').modal("show");
  }
}

function delBtn() {
  var rowid=$("#jqGrid").jqGrid("getGridParam","selrow");
  if(rowid == undefined || rowid == 'norecs'){
    toastr.info("未选择员工！", '提示');
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
    url: "/sys/employee/delete?_" + $.now(),
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
        $("#empEditWin").modal('hide');
      }
    },
  });
}

function buildTable() {
  iframeHeight = window.top.$("#main").height();
  $("#jqGrid").jqGrid({
    url: '/sys/employee/list',
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
      { label: '员工号', name: 'empId', width: 200,frozen : true },
      { label: '姓名', name: 'empName', width: 150,frozen : true },
      { label: '性别', name: 'empGender', width: 100 ,editable:true, edittype: 'select', formatter: 'select',editoptions: {value:"0:男;1:女"}},
      { label: '身份证号', name: 'empIdNum', width: 280 },
      { label: '社保号', name: 'empInsuranceNum', width: 280 },
      { label: '电话', name: 'empTel', width: 200 },
      { label: '邮箱', name: 'empEmail', width: 200 },
      { label: '学历', name: 'empEducation', width: 200 },
      { label: '毕业院校', name: 'empUniversity', width: 200 },
      // { label: '员工性质', name: 'empNature', width: 150 },
      { label: '员工性质', name: 'empNature', width: 150,align:"center",editable:true, edittype: 'select', formatter: 'select',editoptions: {value:"0:试用期;1:转正;2:离职"}},
      { label: '状态', name: 'empStatus', width: 150,align:"center",editable:true, edittype: 'select', formatter: 'select',editoptions: {value:"0:休假;1:休假;2:空闲"}},
      { label: '创建者', name: 'createdBy',hidden:'true', width: 100 },
      { label: '创建时间', name: 'createdTime',hidden:'true', width: 100 },
      { label: '修改时间', name: 'updatedTime',hidden:'true', width: 100 },
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
  $("#t_jqGrid").append("&nbsp;<button class='btn btn-primary ml-2' value='增加' id='addBtn' onclick='addBtn()'>新增</button>");
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
  $employee = new Vue({
    el:"#empEditWin",
    data:{
      employee:{
        employeeStatus:0
      }
    },
    methods:{
      saveEmployee:function(){
        $('#empForm').bootstrapValidator('validate');
        if($('#empForm').data('bootstrapValidator').isValid()){
          $.ajax({
            type: "POST",
            url: "/sys/employee/save?_" + $.now(),
            data: JSON.stringify(this.employee),
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
                $("#empEditWin").modal('hide');
              }
            },
          });
        }
      }
    }
  });
}

// function buildSelect2(){
//   //类型 下拉框
//   $("#empGender").select2({
//     tags: true,
//     width: "100%",
//     // minimumResultsForSearch: Infinity, // 隐藏搜索框
//     theme: "default", // 样式
//     // maximumSelectionLength: 1  //最多能够选择的个数
//   });
//   $("#empGender").append('<option value="0">女</option>');
//   $("#empGender").append('<option value="1">男</option>');
//   //选择事件
//   $("#empGender").on("select2:select",function(e){
//     $employee.employee.empGender = $("#empGender").select2("val");
//   });
// }

function validator() {
  $('#empForm').bootstrapValidator({
    message: '未通过验证',
    feedbackIcons: {
      valid: 'glyphicon glyphicon-ok',
      invalid: 'glyphicon glyphicon-remove',
      validating: 'glyphicon glyphicon-refresh'
    },
    fields:{
      empName:{
        validators: {
          notEmpty: {
            message: '姓名不可为空'
          },
          stringLength: {
            max: 40,
            message: '姓名长度最长40个字符'
          },
        }
      },
      empGender:{
        validators: {
          notEmpty: {
            message: '性别不可为空'
          },
        }
      },
      empEmail:{
        validators: {
          notEmpty: {
            message: '邮箱不可为空'
          },
        }
      },
    }
  });
}