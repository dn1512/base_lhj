var vm;
(function ($){
  $(window).on('resize',function () {
    $("iframe").height($(this).innerHeight() - $(".main-header").height() - $(".content-header").height() -20);
  }).resize();
  // window.onbeforeunload = function(e) {
  //   var dialogText = '是否退出本次登录';
  //   e.returnValue = dialogText;
  //   return dialogText
  // };
    $.lhjtab = {
        requestFullScreen: function () {
            var de = document.documentElement;
            if (de.requestFullscreen) {
                de.requestFullscreen();
            } else if (de.mozRequestFullScreen) {
                de.mozRequestFullScreen();
            } else if (de.webkitRequestFullScreen) {
                de.webkitRequestFullScreen();
            }
        },
        exitFullscreen: function () {
            var de = document;
            if (de.exitFullscreen) {
                de.exitFullscreen();
            } else if (de.mozCancelFullScreen) {
                de.mozCancelFullScreen();
            } else if (de.webkitCancelFullScreen) {
                de.webkitCancelFullScreen();
            }
        },
        refreshTab: function () {
            var currentId = $('.page-tabs-content').find('.active').attr('data-id');
            var target = $('.LHJ_iframe[data-id="' + currentId + '"]');
            var url = target.attr('src');
            target.attr('src', url).load(function () {

            });
        },
        activeTab: function () {
            var currentId = $(this).data('id');
            if (!$(this).hasClass('active')) {
                $('.container-fluid .LHJ_iframe').each(function () {
                    if ($(this).data('id') == currentId) {
                        $(this).show().siblings('.LHJ_iframe').hide();
                        return false;
                    }
                });
                $(this).addClass('active').siblings('.nav-item.nav-link').removeClass('active');
                $.lhjtab.scrollToTab(this);
            }
        },
        closeTab: function () {
            var closeTabId = $(this).parents('.nav-item.nav-link').data('id');
            // var currentWidth = $(this).parents('.nav-item.nav-link').width();
            if ($(this).parents('.nav-item.nav-link').hasClass('active')) {
                if($(this).parents('.nav-item.nav-link').next('.nav-item.nav-link').length > 0){
                  if ($(this).parents('.nav-item.nav-link').next('.nav-item.nav-link').length) {
                    var activeId = $(this).parents('.nav-item.nav-link').next('.nav-item.nav-link:eq(0)').data('id');
                    $(this).parents('.nav-item.nav-link').next('.nav-item.nav-link:eq(0)').addClass('active');

                    $('.container-fluid .LHJ_iframe').each(function () {
                      if ($(this).data('id') == activeId) {
                        $(this).show().siblings('.LHJ_iframe').hide();
                        return false;
                      }
                    });
                    var marginLeftVal = parseInt($('.page-tabs-content').css('margin-left'));
                    if (marginLeftVal < 0) {
                      $('.page-tabs-content').animate({
                        marginLeft: (marginLeftVal + currentWidth) + 'px'
                      }, "fast");
                    }
                    $(this).parents('.nav-item.nav-link').remove();
                    $('.container-fluid .LHJ_iframe').each(function () {
                      if ($(this).data('id') == closeTabId) {
                        $(this).remove();
                        return false;
                      }
                    });
                  }
                }
                if ($(this).parents('.nav-item.nav-link').prev('.nav-item.nav-link').length > 0) {
                    var activeId = $(this).parents('.nav-item.nav-link').prev('.nav-item.nav-link:last').data('id');
                    $(this).parents('.nav-item.nav-link').prev('.nav-item.nav-link:last').addClass('active');
                    $('.container-fluid .LHJ_iframe').each(function () {
                        if ($(this).data('id') == activeId) {
                            $(this).show().siblings('.LHJ_iframe').hide();
                            return false;
                        }
                    });
                    $(this).parents('.nav-item.nav-link').remove();
                    $('.container-fluid .LHJ_iframe').each(function () {
                        if ($(this).data('id') == closeTabId) {
                            $(this).remove();
                            return false;
                        }
                    });
                }
            }
            else {
                $(this).parents('.nav-item.nav-link').remove();
                $('.container-fluid .LHJ_iframe').each(function () {
                    if ($(this).data('id') == closeTabId) {
                        $(this).remove();
                        return false;
                    }
                });
                $.lhjtab.scrollToTab($('.nav-item.nav-link.active'));
            }
            return false;
        },
        addTab: function () {
            // $(".navbar-custom-menu>ul>li.open").removeClass("open");
          var dataId = $(this).attr('data-id');
          var dataUrl = $(this).attr('href');
          var menuName = $.trim($(this).text());
          // vm.main = dataUrl;
          var flag = true;
          if (dataUrl == 'null' || dataUrl == undefined || $.trim(dataUrl).length == 0) {
              return false;
          }
          $('.nav-item.nav-link').each(function () {
            // alert($(this).text());
              if ($(this).data('id') == dataUrl) {
                  if (!$(this).hasClass('active')) {
                      $(this).addClass('active').siblings('.nav-item.nav-link').removeClass('active');
                      // $.lhjtab.scrollToTab(this);
                      $('.LHJ_iframe').each(function () {
                          if ($(this).data('id') == dataUrl) {
                              $(this).show().siblings('.LHJ_iframe').hide();
                              return false;
                          }
                      });
                  }
                  flag = false;
                  return false;
              }
          });
          if (flag) {
              var str = "<a href='javascript:;' class='active nav-item nav-link' style='position: relative' data-id='" + dataUrl + "'><button type='button' aria-hidden='true' class='close' style='position: absolute; top: -4px;right: -1px;'>×</button>" + menuName + " </a>";
              $('.nav-item.nav-link').removeClass('active');
              var str1 = '<iframe class="LHJ_iframe" id="iframe' + dataId + '" name="iframe' + dataId + '"  width="100%" height="100%" src="' + dataUrl + '" frameborder="0" data-id="' + dataUrl + '" seamless></iframe>';
              $('.container-fluid').find('iframe.LHJ_iframe').hide();
              $('.container-fluid').append(str1);
              // $('.container-fluid iframe:visible').load(function () {
              //
              // });
              $("#tab_label").append(str);
            // $(str).appendTo($("#tab_label"));
              // $.lhjtab.scrollToTab($('.nav-link.active'));
            $('.nav-item.nav-link>.close').on('click', $.lhjtab.closeTab);
            $('.nav-item.nav-link').on('click', $.lhjtab.activeTab);
            $("iframe").height(window.innerHeight - $(".main-header").height() - $(".content-header").height()  - 20);
          }
          return false;
        },
        scrollToTab: function (element) {
            var marginLeftVal = $.lhjtab.calSumWidth($(element).prevAll()),
                marginRightVal = $.lhjtab.calSumWidth($(element).nextAll());
            var tabOuterWidth = $.lhjtab.calSumWidth($("#tab_label").children().not(".nav-item.nav-link"));
            var visibleWidth = $(".content-tabs").outerWidth(true) - tabOuterWidth;
            var scrollVal = 0;
            if ($(".page-tabs-content").outerWidth() < visibleWidth) {
                scrollVal = 0;
            } else if (marginRightVal <= (visibleWidth - $(element).outerWidth(true) - $(element).next().outerWidth(true))) {
                if ((visibleWidth - $(element).next().outerWidth(true)) > marginRightVal) {
                    scrollVal = marginLeftVal;
                    var tabElement = element;
                    while ((scrollVal - $(tabElement).outerWidth()) > ($(".page-tabs-content").outerWidth() - visibleWidth)) {
                        scrollVal -= $(tabElement).prev().outerWidth();
                        tabElement = $(tabElement).prev();
                    }
                }
            } else if (marginLeftVal > (visibleWidth - $(element).outerWidth(true) - $(element).prev().outerWidth(true))) {
                scrollVal = marginLeftVal - $(element).prev().outerWidth(true);
            }
            $('.page-tabs-content').animate({
                marginLeft: 0 - scrollVal + 'px'
            }, "fast");
        },
        calSumWidth: function (element) {
            var width = 0;
            $(element).each(function () {
                width += $(this).outerWidth(true);
            });
            return width;
        },
        calSumHeight: function (element) {
            var height = 0;
            $(element).each(function () {
                height += $(this).outerHeight(true)
            });
            return height;
        },
        init: function () {
          $('.nav-link.menuItem').on('click', $.lhjtab.addTab);
            // $('.tabLeft').on('click', $.lhjtab.scrollTabLeft);
            // $('.tabRight').on('click', $.lhjtab.scrollTabRight);
            // $('.tabReload').on('click', $.lhjtab.refreshTab);
            // $('.tabCloseAll').on('click', function () {
            //     $('.page-tabs-content').children("[data-id]").find('.fa-remove').each(function () {
            //         $('.LHJ_iframe[data-id="' + $(this).data('id') + '"]').remove();
            //         $(this).parents('a').remove();
            //     });
            //     $('.page-tabs-content').children("[data-id]:first").each(function () {
            //         $('.LHJ_iframe[data-id="' + $(this).data('id') + '"]').show();
            //         $(this).addClass("active");
            //     });
            //     $('.page-tabs-content').css("margin-left", "0");
            // });
            // $('.tabCloseOther').on('click', $.lhjtab.closeOtherTabs);
            $("#sidebar-menu li a").click(function () {
                var d = $(this), e = d.next();
                if (e.is(".treeview-menu") && e.is(":visible")) {
                    e.slideUp(500, function () {
                        e.removeClass("menu-open")
                    }),
                        e.parent("li").removeClass("active")
                } else if (e.is(".treeview-menu") && !e.is(":visible")) {
                    var f = d.parents("ul").first(),
                        g = f.find("ul:visible").slideUp('fast');
                    g.removeClass("menu-open");
                    var h = d.parent("li");
                    var _height3 = $.lhjtab.calSumHeight($("#sidebar-menu>li"));
                    e.slideDown('fast', function () {
                        e.addClass("menu-open"),
                            f.find("li.active").removeClass("active"),
                            h.addClass("active");

                    })
                }
                e.is(".treeview-menu");
            });
        }
    };
    $.lhjindex = {
        load: function () {
          $("body").removeClass("hold-transition")
          $("#content-wrapper").find('.container-fluid').height($(window).height() - 133);
          $("#sidebar-menu").height($(window).height() - 50);
          $(window).resize(function (e) {
            $("iframe").height($(".content-wrapper").height() - $(".content-header").height());
            $("#content-wrapper").find('.container-fluid').height($(window).height() - 133);
            $("#sidebar-menu").height($(window).height() - 50);
          });
        },
        jsonWhere: function (data, action) {
            if (action == null) return;
            var reval = new Array();
            $(data).each(function (i, v) {
                if (action(v)) {
                    reval.push(v);
                }
            })
            return reval;
        },
        loadMenu: function (data) {
            var _html = "";
            $.each(data, function (i) {
                var row = data[i];
                _html += '<ul class="nav nav-pills nav-sidebar flex-column nav-child-indent" data-widget="treeview" role="menu" data-accordion="false">';
                _html += '<li class="nav-item has-treeview';
                if(row.expanded == 1){
                  _html += ' menu-open';
                }
                _html += '">';
                if(row.type == 0) {
                    _html += '<a href="#" class="nav-link">';
                    if (row.icon != null) {
                        _html += '<i class="nav-icon '+row.icon+'"></i>' +
                            '<p>'+ row.menuName +
                            '<i class="right fas fa-angle-left"></i></p>' +
                            '</a>';
                    }
                    _html += '<ul class="nav nav-treeview">';
                    _html += $.lhjindex.getChild(row.list);
                    _html += '</ul>';
                }
                if(row.type == 1) {
                    if(row.icon == null){
                        row.icon = "fa fa-circle-o";
                    }
                    _html += "<a href='" + row.url + "' data-id='" + row.id + "' class='menuItem'><i class='" + row.icon + "'></i> " + row.menuName + "</a>";
                }
                _html += "</li>";
                _html += "</ul>";
            });
            $("#sidebar-menu").empty().append(_html);
          $.lhjtab.init();
        },
        getChild: function(data) {
            var _html = "";
            $.each(data, function (i) {
                var row = data[i];
                _html += '<li class="nav-item">';
                if(row.type == 0) {
                    _html += '<a href="javascript:;" class="nav-link">';
                    if (row.icon != null) {
                        _html += '<i class="nav-icon '+row.icon+'"></i>' +
                            '<p>'+ row.menuName +
                            '<i class="right fas fa-angle-left"></i>' ;
                        _html += '</p></a>';
                    }
                    _html += '<ul class="nav nav-treeview">';
                    _html += $.lhjindex.getChild(row.list);
                    _html += '</ul>';
                }
                if(row.type == 1) {
                    if(row.icon == null){
                        row.icon = "fa fa-circle-o";
                    }
                    _html += "<a href='" + row.url + "' data-id='" + row.id + "' class='nav-link menuItem'><i class='" + row.icon + " nav-icon'></i><p>" + row.menuName + "</p> </a>";
                }
                _html += "</li>";
            });
            return _html;
        }
    };
})(jQuery);

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

vm = new Vue({
    el: '#lhjLTE',
    data: {
        employee: {},
        menuList: {},
        main: "pages/examples/main.html",
        pswd: null,
        newPswd: null
    },
    methods: {
        hideMenu: function () {
            if (!$("body").hasClass("sidebar-collapse")) {
                $("body").addClass("sidebar-collapse");
                removeScroll();
            } else {
                $("body").removeClass("sidebar-collapse");
                setScroll();
            }
        },
        fullScreen: function () {
            if (!$('#fullscreen').attr('fullscreen')) {
                $('#fullscreen').attr('fullscreen', 'true');
                $.lhjtab.requestFullScreen();
            } else {
                $('#fullscreen').removeAttr('fullscreen')
                $.lhjtab.exitFullscreen();
            }
        },
        tabCloseCurrent: function() {
            $('.page-tabs-content').find('.active i').trigger("click");
        },
        getMenuList: function (event) {
            $.getJSON("sys/menu/account?_" + $.now(), function (r) {
                this.menuList = r.menuList;
                $.lhjindex.loadMenu(r.menuList);
            });
        },
        getEmployee: function () {
          this.employee.eplName = localStorage.getItem("accountName");
          console.info(localStorage.getItem("accountName"));
          $('#employeeName').val(this.employee.eplName);
        },
        openUPSDWin:function(){
          $("#oldPsd").val("");
          $("#newPsd").val("");
          $("#reenterPsd").val("");
          $("#psdWin").modal('show');
        },
        openLogoutWin:function(){
          $("#logoutWin").modal('show');
        },
        updatePassword: function () {
          if($('#oldPsd').val() == ''){
            toastr.warning("原密码不能为空！");
            return;
          }
          if($('#newPsd').val() == ''){
            toastr.warning("新密码不能为空！");
            return;
          }
          if($('#reenterPsd').val() == ''){
            toastr.warning("请重复新密码！");
            return;
          }
          if($('#reenterPsd').val() != $('#newPsd').val()){
            toastr.warning("重复新密码错误！");
            return;
          }
                    var data = "pswd=" + $('#oldPsd').val() + "&newPswd="
                        + $('#reenterPsd').val();
                    $.ajax({
                        type: "POST",
                        url: "/sys/account/updatePswd?_" + $.now(),
                        data: data,
                        dataType: "json",
                        success: function (result) {
                            if (result.code == 0) {
                              toastr.success(result.msg);
                            } else {
                              toastr.warning(result.msg);
                            }
                          $("#psdWin").modal('hide');
                        }
                    });
            //     }
            // });
        },
        logout: function () {
            localStorage.removeItem("token");
            toUrl('/login.html');
        }
    },
    created: function () {
        this.getMenuList();
        // this.getPermList();
        this.getEmployee();
        // $("#lhjLTE").show();
    },
    updated: function () {
        // $.lhjindex.load();
        // if(this.menuList.length == undefined){
        //     this.getMenuList();
        // }
        // $.lhjindex.loadMenu(this.menuList);
        // $.lhjtab.init();
        // setScroll();
    }
});

function removeScroll() {
    $('.sidebar').append($('#sidebar-menu'));
    $('#sidebar-menu').removeAttr('style');
    $('.slimScrollDiv').remove();
}

/**
 * 进入个人中心
 */
function gotoPersonalCenter() {
  var employeeId = vm.employee.id;
}

/**
 *
 */
function openPageInMain(url) {

}
/**
 *
 */
function checkToken() {
    var data = {
      token:localStorage.getItem("token"),
      accountId:localStorage.getItem("accountId"),
    }
    $.ajax({
        type : "POST",
        url : "sys/checkLogin",
        data : data,
        dataType : "json",
        success : function(result) {
            if(result.code == 0){
                //toastr.success(result.msg)
            }else{
                // Toast.fire({
                //     type: 'warning',
                //     title: result.msg
                // })
                window.location.href = "login.html";
            }
        },
        error : function (result) {
            console.log(result);
        },
        fail:function(e){
            alert("fail");
        }
    })
}