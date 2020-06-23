var iframeHeight;
var jqLastSel;
var detailLastSel;
var $dict;
$(function () {
  bulidVue();
  buildTable();
  buildToastr();
  validator();
});

function adddict(){
  $dict.dict = {dictKey:"",
    dictFlag:"",
    detials:[]};
  // $('#dictForm').data('bootstrapValidator').resetForm(true);
  $('#dictEditWin').modal("show");
}

function editdict() {
  var rowid=$("#jqGrid").jqGrid("getGridParam","selrow");
  if (rowid == undefined || rowid == 'norecs') {
    toastr.info("请先选择一行数据！", '提示');
  }else{
    $dict.dict = {dictKey:"",
      dictFlag:"",
      detials:[]};
    var rowData=jQuery("#jqGrid").jqGrid("getRowData",rowid);
    $dict.dict.dictKey = rowData.dictKey;
    $dict.dict.dictFlag = rowData.dictFlag;
    var ids = $('#detailGrid').jqGrid('getDataIDs');
    var details = []
    for(var i=0;i<ids.length;i++){
      details.push($('#detailGrid').jqGrid("getRowData",ids[i]));
    }
    $dict.dict.detials = details
    $('#dictForm').data('bootstrapValidator').resetForm(true);
    $('#dictEditWin').modal("show");
  }
}

function checkDel() {
  var rowid=$("#jqGrid").jqGrid("getGridParam","selrow");
  if (rowid == undefined || rowid == 'norecs') {
    toastr.info("请先选择一行数据！", '提示');
  }else{
    $('#checkMod').modal("show");
  }
}

function deldict(){
  var rowid=$("#jqGrid").jqGrid("getGridParam","selrow");
  var rowData=jQuery("#jqGrid").jqGrid("getRowData",rowid);
  $.ajax({
    type: "POST",
    url: "/sys/dict/delete?_" + $.now(),
    data: rowData.dictFlag,
    dataType: "json",
    contentType : "application/json;charset=utf8",
    success: function (result) {
      if (result.code == 0) {
        toastr.success("成功删除"+result.obj+"条");
        $("#checkMod").modal('hide');
        $("#detailGrid").jqGrid('clearGridData');
        $("#jqGrid").trigger("reloadGrid");
      } else {
        toastr.warning("未知错误！");
      }
    },
  });
}

function loadDetail(dictFlag) {
  $.ajax({
    type: "POST",
    url: "/sys/dict/detail?_" + $.now(),
    data: dictFlag,
    dataType: "json",
    contentType : "application/json;charset=utf8",
    success: function (result) {
      if (result.code == 0) {
        $dict.dict.detials = result.obj;
        var reader = {
           root: function(obj) { return result.obj; },
           page: function(obj) { return result.page; },
           total: function(obj) { return result.total; },
           records: function(obj) { return result.records; }
        }
        $("#detailGrid").setGridParam({data: result.obj, localReader: reader}).trigger('reloadGrid');
      } else {
        toastr.warning("未知错误！");
      }
    },
  });
}

function buildTable() {
  iframeHeight = window.top.$("#main").height();
  $("#jqGrid").jqGrid({
    url: '/sys/dict/list',
    mtype: "POST",
    styleUI : 'Bootstrap',
    datatype: "json",
    autowidth: true, //宽度自适应
    pagerpos:"center",
    pgbuttons:true,
    rownumbers:true,  //显示行号
    postData:{
      sortOrder:'dictFlag'
    },
    // autoScroll: false,
    // shrinkToFit: false,
    colModel: [
      { label: '字典组', name: 'dictKey', width: 150,align:"center"},
      { label: '字典标识', name: 'dictFlag', width: 150,align:"center",key:true },
    ],
    // jsonReader:{
    //   root: "rows",
    //   page: "curPage",
    //   total: "pageNums",
    //   records: "totalRows",
    // },
    viewrecords: false,
    height: iframeHeight - 100,
    rowNum: 20,
    reccount: 20,
    rowlist:[10,20,30],
    sortName:'dictKey',
    pager: "#jqGridPager",
    multiselect:false,
    multiboxonly:false,//checkbox
    onSelectRow: function (rowId, status, e) {
      $("#detailGrid").jqGrid('resetSelection');
      if (rowId == jqLastSel) {
        $(this).jqGrid("resetSelection");
        jqLastSel = undefined;
        status = false;
      } else {
        jqLastSel = rowId;
      }
      var rowData=jQuery("#jqGrid").jqGrid("getRowData",rowId);
      $dict.dict.dictKey = rowData.dictKey;
      $dict.dict.dictFlag = rowData.dictFlag;
      loadDetail(rowData.dictFlag);
    },
    beforeSelectRow: function (rowId, e) {
      $(this).jqGrid("resetSelection");
      return true;
    },
    ondblClickRow: function (rowid,iRow,iCol,e) {
      if (rowid == jqLastSel) {
        $(this).jqGrid("editRow",rowid);
      } else {
        if(!$("#jqGrid").hasClass("ui-state-disabled")){
          jqLastSel = rowid;
          $(this).jqGrid("editRow",rowid);
        }else{
          toastr.info("当前有未保存的数据，请保存后编辑！", '提示');
          return false;
        }
      }
    }
  });
  $("#detailGrid").jqGrid({
    // url: '/sys/dict/detail',
    styleUI : 'Bootstrap',
    datatype: "local",
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
      { label: '字典名', name: 'dictName', width: 150,align:"center" },
      { label: '字典值', name: 'dictValue', width: 150 },
      { label: '描述', name: 'description', width: 150 },
      { label: 'ID', name: 'id',hidden:'true', key: true, width: 75 }
    ],
    jsonReader:{
      root: "rows",
      page: "curPage",
      total: "pageNums",
      records: "totalRows",
    },
    viewrecords: false,
    height: iframeHeight - 100,
    rowNum: -1,
    reccount: 20,
    rowlist:[10,20,30],
    sortName:'id',
    // pager: "#detailGridPager",
    multiselect:false,
    multiboxonly:false,//checkbox
    onSelectRow: function (rowId, status, e) {
      $("#jqGrid").jqGrid('resetSelection');
      if (rowId == detailLastSel) {
        $(this).jqGrid("resetSelection");
        detailLastSel = undefined;
        status = false;
      } else {
        detailLastSel = rowId;
      }
    },
    beforeSelectRow: function (rowId, e) {
      $(this).jqGrid("resetSelection");
      return true;
    },
  });
  $(window).on('resize',function () {
    iframeHeight = window.top.$("#main").height();
    $("#jqGrid").jqGrid("setGridHeight", iframeHeight - 120);
    $("#detailGrid").jqGrid("setGridHeight", iframeHeight - 120);
    $("#jqGrid").setGridWidth($("#gridDiv").width());
    $("#detailGrid").setGridWidth($("#detailGridDiv").width());
  }).resize();
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

function bulidVue(){
  $dict = new Vue({
    el:"#dictEditWin",
    data:{
      dict:{

      }
    },
    methods:{
      saveDict: function () {
        $('#dictForm').bootstrapValidator('validate');
        if($('#dictForm').data('bootstrapValidator').isValid()){
          for(var i=0;i<this.dict.detials.length;i++){
            this.dict.detials[i].dictKey = this.dict.dictKey;
            this.dict.detials[i].dictFlag = this.dict.dictFlag;
          }
          $.ajax({
            type: "PUT",
            url: "/sys/dict/save?_" + $.now(),
            data: JSON.stringify(this.dict.detials),
            dataType: "json",
            contentType : "application/json;charset=utf8",
            success: function (result) {
              if (result.code == 0) {
                toastr.success(result.msg);
              } else {
                toastr.warning(result.msg);
              }
              $("#detailGrid").jqGrid('clearGridData');
              $("#jqGrid").trigger("reloadGrid");
              $("#dictEditWin").modal('hide');
            },
          });
        }
      },
      addRow: function(){
        if($('#dictForm').data('bootstrapValidator').isValid()){
          this.dict.detials.push({id:null,dictName:"",dictValue:""})
          $('#dictKey').val(this.dictKey);
        }
      },
      delRow: function (index) {
        this.dict.detials.shift(index,1);
      },
    }
  });
}
function validator(){
  $('#dictForm').bootstrapValidator({
    message: '未通过验证',
    feedbackIcons: {
      valid: 'glyphicon glyphicon-ok',
      invalid: 'glyphicon glyphicon-remove',
      validating: 'glyphicon glyphicon-refresh'
    },
    fields: {
      dictKey: {
        validators: {
          notEmpty: {
            message: '字典组不可为空'
          },
          stringLength: {
            max: 20,
            message: '字典组长度最长20个字符'
          }
        },
      },
      dictFlag: {
        validators: {
          notEmpty: {
            message: '字典标识不可为空'
          },
          stringLength: {
            max: 20,
            message: '字典标识长度最长20个字符'
          }
        },
      },
      dictName: {
        validators: {
          notEmpty: {
            message: '字典名不可为空'
          },
          stringLength: {
            max: 20,
            message: '字典名长度最长20个字符'
          }
        },
      },
      dictValue: {
        validators: {
          notEmpty: {
            message: '字典值不可为空'
          },
          stringLength: {
            max: 50,
            message: '字典值长度最长50个字符'
          }
        },
      },
    }
  });
}