/**
*
* @license Guriddo jqGrid JS - v5.4.0 
* Copyright(c) 2008, Tony Tomov, tony@trirand.com
* 
* License: http://guriddo.net/?page_id=103334
*/
!function (a) {
  "use strict";
  "function" == typeof define && define.amd ? define(["jquery", "./grid.base"], a) : a(jQuery)
}(function (a) {
  "use strict";
  a.jgrid.extend({
    setTreeNode: function (b, c) {
      return this.each(function () {
        var d = this;
        if (d.grid && d.p.treeGrid) {
          var e, f, g, h, i, j, k, l, m = d.p.expColInd, n = d.p.treeReader.expanded_field,
            o = d.p.treeReader.leaf_field, p = d.p.treeReader.level_field, q = d.p.treeReader.icon_field,
            r = d.p.treeReader.loaded, s = a.jgrid.styleUI[d.p.styleUI || "jQueryUI"].common, t = b;
          for (a(d).triggerHandler("jqGridBeforeSetTreeNode", [t, c]), a.isFunction(d.p.beforeSetTreeNode) && d.p.beforeSetTreeNode.call(d, t, c); b < c;) {
            var u = a.jgrid.stripPref(d.p.idPrefix, d.rows[b].id), v = d.p._index[u];
            k = d.p.data[v], "nested" === d.p.treeGridModel && (k[o] || (e = parseInt(k[d.p.treeReader.left_field], 10), f = parseInt(k[d.p.treeReader.right_field], 10), k[o] = f === e + 1 ? "true" : "false", d.rows[b].cells[d.p._treeleafpos].innerHTML = k[o])), g = parseInt(k[p], 10), 0 === d.p.tree_root_level ? (h = g + 1, i = g) : (h = g, i = g - 1), j = "<div class='tree-wrap tree-wrap-" + d.p.direction + "' style='width:" + 18 * h + "px;'>", j += "<div style='" + ("rtl" === d.p.direction ? "right:" : "left:") + 18 * i + "px;' class='" + s.icon_base + " ", void 0 !== k[r] && ("true" === k[r] || !0 === k[r] ? k[r] = !0 : k[r] = !1), "true" === k[o] || !0 === k[o] ? (j += (void 0 !== k[q] && "" !== k[q] ? k[q] : d.p.treeIcons.leaf) + " tree-leaf treeclick", k[o] = !0, l = "leaf") : (k[o] = !1, l = ""), k[n] = ("true" === k[n] || !0 === k[n]) && (k[r] || void 0 === k[r]), !1 === k[n] ? j += !0 === k[o] ? "'" : d.p.treeIcons.plus + " tree-plus treeclick'" : j += !0 === k[o] ? "'" : d.p.treeIcons.minus + " tree-minus treeclick'", j += "></div></div>", a(d.rows[b].cells[m]).wrapInner("<span class='cell-wrapper" + l + "'></span>").prepend(j), g !== parseInt(d.p.tree_root_level, 10) && (a(d).jqGrid("isVisibleNode", k) || a(d.rows[b]).css("display", "none")), a(d.rows[b].cells[m]).find("div.treeclick").on("click", function (b) {
              var c = b.target || b.srcElement,
                e = a.jgrid.stripPref(d.p.idPrefix, a(c, d.rows).closest("tr.jqgrow")[0].id), f = d.p._index[e];
              return d.p.data[f][o] || (d.p.data[f][n] ? (a(d).jqGrid("collapseRow", d.p.data[f]), a(d).jqGrid("collapseNode", d.p.data[f])) : (a(d).jqGrid("expandRow", d.p.data[f]), a(d).jqGrid("expandNode", d.p.data[f]))), !1
            }), !0 === d.p.ExpandColClick && a(d.rows[b].cells[m]).find("span.cell-wrapper").css("cursor", "pointer").on("click", function (b) {
              var c = b.target || b.srcElement,
                e = a.jgrid.stripPref(d.p.idPrefix, a(c, d.rows).closest("tr.jqgrow")[0].id), f = d.p._index[e];
              return d.p.data[f][o] || (d.p.data[f][n] ? (a(d).jqGrid("collapseRow", d.p.data[f]), a(d).jqGrid("collapseNode", d.p.data[f])) : (a(d).jqGrid("expandRow", d.p.data[f]), a(d).jqGrid("expandNode", d.p.data[f]))), a(d).jqGrid("setSelection", e), !1
            }), b++
          }
          a(d).triggerHandler("jqGridAfterSetTreeNode", [t, c]), a.isFunction(d.p.afterSetTreeNode) && d.p.afterSetTreeNode.call(d, t, c)
        }
      })
    }, setTreeGrid: function () {
      return this.each(function () {
        var b, c, d, e, f = this, g = 0, h = !1, i = [], j = a.jgrid.styleUI[f.p.styleUI || "jQueryUI"].treegrid;
        if (f.p.treeGrid) {
          f.p.treedatatype || a.extend(f.p, {treedatatype: f.p.datatype}), f.p.loadonce && (f.p.treedatatype = "local"), f.p.subGrid = !1, f.p.altRows = !1, f.p.treeGrid_bigData || (f.p.pgbuttons = !1, f.p.pginput = !1, f.p.rowList = []), f.p.gridview = !0, null !== f.p.rowTotal || f.p.treeGrid_bigData || (f.p.rowNum = 1e4), f.p.multiselect = !1, f.p.expColInd = 0, b = j.icon_plus, "jQueryUI" === f.p.styleUI && (b += "rtl" === f.p.direction ? "w" : "e"), f.p.treeIcons = a.extend({
            plus: b,
            minus: j.icon_minus,
            leaf: j.icon_leaf
          }, f.p.treeIcons || {}), "nested" === f.p.treeGridModel ? f.p.treeReader = a.extend({
            level_field: "level",
            left_field: "lft",
            right_field: "rgt",
            leaf_field: "isLeaf",
            expanded_field: "expanded",
            loaded: "loaded",
            icon_field: "icon"
          }, f.p.treeReader) : "adjacency" === f.p.treeGridModel && (f.p.treeReader = a.extend({
            level_field: "level",
            parent_id_field: "parent",
            leaf_field: "isLeaf",
            expanded_field: "expanded",
            loaded: "loaded",
            icon_field: "icon"
          }, f.p.treeReader));
          for (d in f.p.colModel) if (f.p.colModel.hasOwnProperty(d)) {
            c = f.p.colModel[d].name, c !== f.p.ExpandColumn || h || (h = !0, f.p.expColInd = g), g++, c !== f.p.treeReader.level_field && c !== f.p.treeReader.left_field && c !== f.p.treeReader.right_field || (f.p.colModel[d].sorttype = "integer");
            for (e in f.p.treeReader) f.p.treeReader.hasOwnProperty(e) && f.p.treeReader[e] === c && i.push(c)
          }
          a.each(f.p.treeReader, function (b, c) {
            c && -1 === a.inArray(c, i) && ("leaf_field" === b && (f.p._treeleafpos = g), g++, f.p.colNames.push(c), f.p.colModel.push({
              name: c,
              width: 1,
              hidden: !0,
              sortable: !1,
              resizable: !1,
              hidedlg: !0,
              editable: !0,
              search: !1
            }))
          })
        }
      })
    }, expandRow: function (b) {
      this.each(function () {
        var c = this;
        if (!c.p.treeGrid_bigData) var d = c.p.lastpage;
        if (c.grid && c.p.treeGrid) {
          var e = a(c).jqGrid("getNodeChildren", b), f = c.p.treeReader.expanded_field, g = b[c.p.localReader.id],
            h = a(c).triggerHandler("jqGridBeforeExpandTreeGridRow", [g, b, e]);
          void 0 === h && (h = !0), h && a.isFunction(c.p.beforeExpandTreeGridRow) && (h = c.p.beforeExpandTreeGridRow.call(c, g, b, e)), !1 !== h && (a(e).each(function () {
            var b = c.p.idPrefix + a.jgrid.getAccessor(this, c.p.localReader.id);
            a(a(c).jqGrid("getGridRowById", b)).css("display", ""), this[f] && a(c).jqGrid("expandRow", this)
          }), a(c).triggerHandler("jqGridAfterExpandTreeGridRow", [g, b, e]), a.isFunction(c.p.afterExpandTreeGridRow) && c.p.afterExpandTreeGridRow.call(c, g, b, e), c.p.treeGrid_bigData || (c.p.lastpage = d))
        }
      })
    }, collapseRow: function (b) {
      this.each(function () {
        var c = this;
        if (c.grid && c.p.treeGrid) {
          var d = a(c).jqGrid("getNodeChildren", b), e = c.p.treeReader.expanded_field, f = b[c.p.localReader.id],
            g = a(c).triggerHandler("jqGridBeforeCollapseTreeGridRow", [f, b, d]);
          void 0 === g && (g = !0), g && a.isFunction(c.p.beforeCollapseTreeGridRow) && (g = c.p.beforeCollapseTreeGridRow.call(c, f, b, d)), !1 !== g && (a(d).each(function () {
            var b = c.p.idPrefix + a.jgrid.getAccessor(this, c.p.localReader.id);
            a(a(c).jqGrid("getGridRowById", b)).css("display", "none"), this[e] && a(c).jqGrid("collapseRow", this)
          }), a(c).triggerHandler("jqGridAfterCollapseTreeGridRow", [f, b, d]), a.isFunction(c.p.afterCollapseTreeGridRow) && c.p.afterCollapseTreeGridRow.call(c, f, b, d))
        }
      })
    }, getRootNodes: function () {
      var b = [];
      return this.each(function () {
        var c, d, e = this, f = e.p.data;
        if (e.grid && e.p.treeGrid) switch (e.p.treeGridModel) {
          case"nested":
            c = e.p.treeReader.level_field, a(f).each(function () {
              parseInt(this[c], 10) === parseInt(e.p.tree_root_level, 10) && b.push(this)
            });
            break;
          case"adjacency":
            d = e.p.treeReader.parent_id_field, a(f).each(function () {
              null !== this[d] && "null" !== String(this[d]).toLowerCase() || b.push(this)
            })
        }
      }), b
    }, getNodeDepth: function (b) {
      var c = null;
      return this.each(function () {
        if (this.grid && this.p.treeGrid) {
          var d = this;
          switch (d.p.treeGridModel) {
            case"nested":
              var e = d.p.treeReader.level_field;
              c = parseInt(b[e], 10) - parseInt(d.p.tree_root_level, 10);
              break;
            case"adjacency":
              c = a(d).jqGrid("getNodeAncestors", b).length
          }
        }
      }), c
    }, getNodeParent: function (b) {
      var c = null;
      return this.each(function () {
        var d = this;
        if (d.grid && d.p.treeGrid) switch (d.p.treeGridModel) {
          case"nested":
            var e = d.p.treeReader.left_field, f = d.p.treeReader.right_field, g = d.p.treeReader.level_field,
              h = parseInt(b[e], 10), i = parseInt(b[f], 10), j = parseInt(b[g], 10);
            a(this.p.data).each(function () {
              if (parseInt(this[g], 10) === j - 1 && parseInt(this[e], 10) < h && parseInt(this[f], 10) > i) return c = this, !1
            });
            break;
          case"adjacency":
            for (var k = d.p.treeReader.parent_id_field, l = d.p.localReader.id, m = b[l], n = d.p._index[m]; n--;) if (String(d.p.data[n][l]) === String(a.jgrid.stripPref(d.p.idPrefix, b[k]))) {
              c = d.p.data[n];
              break
            }
        }
      }), c
    }, getNodeChildren: function (b) {
      var c = [];
      return this.each(function () {
        var d = this;
        if (d.grid && d.p.treeGrid) {
          var e, f, g = this.p.data.length;
          switch (d.p.treeGridModel) {
            case"nested":
              var h = d.p.treeReader.left_field, i = d.p.treeReader.right_field, j = d.p.treeReader.level_field,
                k = parseInt(b[h], 10), l = parseInt(b[i], 10), m = parseInt(b[j], 10);
              for (e = 0; e < g; e++) (f = d.p.data[e]) && parseInt(f[j], 10) === m + 1 && parseInt(f[h], 10) > k && parseInt(f[i], 10) < l && c.push(f);
              break;
            case"adjacency":
              var n = d.p.treeReader.parent_id_field, o = d.p.localReader.id;
              for (e = 0; e < g; e++) (f = d.p.data[e]) && String(f[n]) === String(a.jgrid.stripPref(d.p.idPrefix, b[o])) && c.push(f)
          }
        }
      }), c
    }, getFullTreeNode: function (b, c) {
      var d = [];
      return this.each(function () {
        var e, f = this, g = f.p.treeReader.expanded_field;
        if (f.grid && f.p.treeGrid) switch (null != c && "boolean" == typeof c || (c = !1), f.p.treeGridModel) {
          case"nested":
            var h = f.p.treeReader.left_field, i = f.p.treeReader.right_field, j = f.p.treeReader.level_field,
              k = parseInt(b[h], 10), l = parseInt(b[i], 10), m = parseInt(b[j], 10);
            a(this.p.data).each(function () {
              parseInt(this[j], 10) >= m && parseInt(this[h], 10) >= k && parseInt(this[h], 10) <= l && (c && (this[g] = !0), d.push(this))
            });
            break;
          case"adjacency":
            if (b) {
              d.push(b);
              var n = f.p.treeReader.parent_id_field, o = f.p.localReader.id;
              a(this.p.data).each(function (b) {
                for (e = d.length, b = 0; b < e; b++) if (String(a.jgrid.stripPref(f.p.idPrefix, d[b][o])) === String(this[n])) {
                  c && (this[g] = !0), d.push(this);
                  break
                }
              })
            }
        }
      }), d
    }, getNodeAncestors: function (b, c, d) {
      var e = [];
      return void 0 === c && (c = !1), this.each(function () {
        if (this.grid && this.p.treeGrid) {
          d = void 0 !== d && this.p.treeReader.expanded_field;
          for (var f = a(this).jqGrid("getNodeParent", b); f;) {
            if (d) try {
              f[d] = !0
            } catch (a) {
            }
            c ? e.unshift(f) : e.push(f), f = a(this).jqGrid("getNodeParent", f)
          }
        }
      }), e
    }, isVisibleNode: function (b) {
      var c = !0;
      return this.each(function () {
        var d = this;
        if (d.grid && d.p.treeGrid) {
          var e = a(d).jqGrid("getNodeAncestors", b), f = d.p.treeReader.expanded_field;
          a(e).each(function () {
            if (!(c = c && this[f])) return !1
          })
        }
      }), c
    }, isNodeLoaded: function (b) {
      var c;
      return this.each(function () {
        var d = this;
        if (d.grid && d.p.treeGrid) {
          var e = d.p.treeReader.leaf_field, f = d.p.treeReader.loaded;
          c = void 0 !== b && (void 0 !== b[f] ? b[f] : !!(b[e] || a(d).jqGrid("getNodeChildren", b).length > 0))
        }
      }), c
    }, setLeaf: function (b, c, d) {
      return this.each(function () {
        var e = a.jgrid.getAccessor(b, this.p.localReader.id), f = a("#" + e, this.grid.bDiv)[0],
          g = this.p.treeReader.leaf_field;
        try {
          var h = this.p._index[e];
          null != h && (this.p.data[h][g] = c)
        } catch (a) {
        }
        if (!0 === c) a("div.treeclick", f).removeClass(this.p.treeIcons.minus + " tree-minus " + this.p.treeIcons.plus + " tree-plus").addClass(this.p.treeIcons.leaf + " tree-leaf"); else if (!1 === c) {
          var i = this.p.treeIcons.minus + " tree-minus";
          d && (i = this.p.treeIcons.plus + " tree-plus"), a("div.treeclick", f).removeClass(this.p.treeIcons.leaf + " tree-leaf").addClass(i)
        }
      })
    }, reloadNode: function (b, c) {
      return this.each(function () {
        if (this.grid && this.p.treeGrid) {
          var d = this.p.localReader.id, e = this.p.selrow;
          a(this).jqGrid("delChildren", b[d]), void 0 === c && (c = !1), c || jQuery._data(this, "events").jqGridAfterSetTreeNode || a(this).on("jqGridAfterSetTreeNode.reloadNode", function () {
            var b = this.p.treeReader.leaf_field;
            if (this.p.reloadnode) {
              var c = this.p.reloadnode, d = a(this).jqGrid("getNodeChildren", c);
              c[b] && d.length ? a(this).jqGrid("setLeaf", c, !1) : c[b] || 0 !== d.length || a(this).jqGrid("setLeaf", c, !0)
            }
            this.p.reloadnode = !1
          });
          var f = this.p.treeReader.expanded_field, g = this.p.treeReader.parent_id_field, h = this.p.treeReader.loaded,
            i = this.p.treeReader.level_field, j = this.p.treeReader.leaf_field, k = this.p.treeReader.left_field,
            l = this.p.treeReader.right_field, m = a.jgrid.getAccessor(b, this.p.localReader.id),
            n = a("#" + m, this.grid.bDiv)[0];
          b[f] = !0, b[j] || a("div.treeclick", n).removeClass(this.p.treeIcons.plus + " tree-plus").addClass(this.p.treeIcons.minus + " tree-minus"), this.p.treeANode = n.rowIndex, this.p.datatype = this.p.treedatatype, this.p.reloadnode = b, c && (this.p.treeANode = n.rowIndex > 0 ? n.rowIndex - 1 : 1, a(this).jqGrid("delRowData", m)), "nested" === this.p.treeGridModel ? a(this).jqGrid("setGridParam", {
            postData: {
              nodeid: m,
              n_left: b[k],
              n_right: b[l],
              n_level: b[i]
            }
          }) : a(this).jqGrid("setGridParam", {
            postData: {
              nodeid: m,
              parentid: b[g],
              n_level: b[i]
            }
          }), a(this).trigger("reloadGrid"), b[h] = !0, "nested" === this.p.treeGridModel ? a(this).jqGrid("setGridParam", {
            selrow: e,
            postData: {nodeid: "", n_left: "", n_right: "", n_level: ""}
          }) : a(this).jqGrid("setGridParam", {selrow: e, postData: {nodeid: "", parentid: "", n_level: ""}})
        }
      })
    }, expandNode: function (b) {
      return this.each(function () {
        if (this.grid && this.p.treeGrid) {
          var c = this, d = this.p.treeReader.expanded_field, e = this.p.treeReader.parent_id_field,
            f = this.p.treeReader.loaded, g = this.p.treeReader.level_field, h = this.p.treeReader.left_field,
            i = this.p.treeReader.right_field;
          if (!b[d]) {
            var j = a.jgrid.getAccessor(b, this.p.localReader.id),
              k = a("#" + this.p.idPrefix + a.jgrid.jqID(j), this.grid.bDiv)[0], l = this.p._index[j],
              m = a(c).triggerHandler("jqGridBeforeExpandTreeGridNode", [j, b]);
            if (void 0 === m && (m = !0), m && a.isFunction(this.p.beforeExpandTreeGridNode) && (m = this.p.beforeExpandTreeGridNode.call(this, j, b)), !1 === m) return;
            a(this).jqGrid("isNodeLoaded", this.p.data[l]) ? (b[d] = !0, a("div.treeclick", k).removeClass(this.p.treeIcons.plus + " tree-plus").addClass(this.p.treeIcons.minus + " tree-minus")) : this.grid.hDiv.loading || (b[d] = !0, a("div.treeclick", k).removeClass(this.p.treeIcons.plus + " tree-plus").addClass(this.p.treeIcons.minus + " tree-minus"), this.p.treeANode = k.rowIndex, this.p.datatype = this.p.treedatatype, "nested" === this.p.treeGridModel ? a(this).jqGrid("setGridParam", {
              postData: {
                nodeid: j,
                n_left: b[h],
                n_right: b[i],
                n_level: b[g]
              }
            }) : a(this).jqGrid("setGridParam", {
              postData: {
                nodeid: j,
                parentid: b[e],
                n_level: b[g]
              }
            }), a(this).trigger("reloadGrid"), b[f] = !0, "nested" === this.p.treeGridModel ? a(this).jqGrid("setGridParam", {
              postData: {
                nodeid: "",
                n_left: "",
                n_right: "",
                n_level: ""
              }
            }) : a(this).jqGrid("setGridParam", {
              postData: {
                nodeid: "",
                parentid: "",
                n_level: ""
              }
            })), a(c).triggerHandler("jqGridAfterExpandTreeGridNode", [j, b]), a.isFunction(this.p.afterExpandTreeGridNode) && this.p.afterExpandTreeGridNode.call(this, j, b)
          }
        }
      })
    }, collapseNode: function (b) {
      return this.each(function () {
        if (this.grid && this.p.treeGrid) {
          var c = this.p.treeReader.expanded_field, d = this;
          if (b[c]) {
            var e = a.jgrid.getAccessor(b, this.p.localReader.id),
              f = a("#" + this.p.idPrefix + a.jgrid.jqID(e), this.grid.bDiv)[0],
              g = a(d).triggerHandler("jqGridBeforeCollapseTreeGridNode", [e, b]);
            if (void 0 === g && (g = !0), g && a.isFunction(this.p.beforeCollapseTreeGridNode) && (g = this.p.beforeCollapseTreeGridNode.call(this, e, b)), b[c] = !1, !1 === g) return;
            a("div.treeclick", f).removeClass(this.p.treeIcons.minus + " tree-minus").addClass(this.p.treeIcons.plus + " tree-plus"), a(d).triggerHandler("jqGridAfterCollapseTreeGridNode", [e, b]), a.isFunction(this.p.afterCollapseTreeGridNode) && this.p.afterCollapseTreeGridNode.call(this, e, b)
          }
        }
      })
    }, SortTree: function (b, c, d, e) {
      return this.each(function () {
        if (this.grid && this.p.treeGrid) {
          var f, g, h, i, j, k = [], l = this, m = a(this).jqGrid("getRootNodes", l.p.search);
          if (i = a.jgrid.from.call(this, m), Boolean(l.p.sortTreeByNodeType)) {
            var n = l.p.sortTreeNodeOrder && "desc" === l.p.sortTreeNodeOrder.toLowerCase() ? "d" : "a";
            i.orderBy(l.p.treeReader.leaf_field, n, d, e)
          }
          for (i.orderBy(b, c, d, e), j = i.select(), f = 0, g = j.length; f < g; f++) h = j[f], k.push(h), a(this).jqGrid("collectChildrenSortTree", k, h, b, c, d, e);
          var o = a(this).jqGrid("getDataIDs"), p = 1;
          a.each(k, function (b) {
            var c = a.jgrid.getAccessor(this, l.p.localReader.id);
            -1 !== a.inArray(c, o) && (a("#" + a.jgrid.jqID(l.p.id) + " tbody tr:eq(" + p + ")").after(a("#" + a.jgrid.jqID(l.p.id) + " tbody tr#" + a.jgrid.jqID(c))), p++)
          }), i = null, j = null, k = null
        }
      })
    }, searchTree: function (b) {
      var c, d, e, f, g, h, i, j = b.length || 0, k = [], l = [], m = [];
      return this.each(function () {
        if (this.grid && this.p.treeGrid && j) for (c = this.p.localReader.id, i = 0; i < j; i++) {
          if (k = a(this).jqGrid("getNodeAncestors", b[i], !0, !0), Boolean(this.p.FullTreeSearchResult)) {
            var n = a(this).jqGrid("getFullTreeNode", b[i], !0);
            k = k.concat(n)
          } else k.push(b[i]);
          if (d = k[0][c], -1 === a.inArray(d, l)) l.push(d), m = m.concat(k); else for (g = 0, e = k.length; g < e; g++) {
            var o = !1;
            for (h = 0, f = m.length; h < f; h++) if (k[g][c] === m[h][c]) {
              o = !0;
              break
            }
            o || m.push(k[g])
          }
        }
      }), m
    }, collectChildrenSortTree: function (b, c, d, e, f, g) {
      return this.each(function () {
        if (this.grid && this.p.treeGrid) {
          var h, i, j, k, l, m;
          for (k = a(this).jqGrid("getNodeChildren", c, this.p.search), l = a.jgrid.from.call(this, k), l.orderBy(d, e, f, g), m = l.select(), h = 0, i = m.length; h < i; h++) j = m[h], b.push(j), a(this).jqGrid("collectChildrenSortTree", b, j, d, e, f, g)
        }
      })
    }, setTreeRow: function (b, c) {
      var d = !1;
      return this.each(function () {
        var e = this;
        e.grid && e.p.treeGrid && (d = a(e).jqGrid("setRowData", b, c))
      }), d
    }, delTreeNode: function (b) {
      return this.each(function () {
        var c, d, e, f, g, h = this, i = h.p.localReader.id, j = h.p.treeReader.left_field,
          k = h.p.treeReader.right_field;
        if (h.grid && h.p.treeGrid) {
          b = a.jgrid.stripPref(h.p.idPrefix, b);
          var l = h.p._index[b];
          if (void 0 !== l) {
            d = parseInt(h.p.data[l][k], 10), e = d - parseInt(h.p.data[l][j], 10) + 1;
            var m = a(h).jqGrid("getFullTreeNode", h.p.data[l]);
            if (m.length > 0) for (c = 0; c < m.length; c++) a(h).jqGrid("delRowData", h.p.idPrefix + m[c][i]);
            if ("nested" === h.p.treeGridModel) {
              if (f = a.jgrid.from.call(h, h.p.data).greater(j, d, {stype: "integer"}).select(), f.length) for (g in f) f.hasOwnProperty(g) && (f[g][j] = parseInt(f[g][j], 10) - e);
              if (f = a.jgrid.from.call(h, h.p.data).greater(k, d, {stype: "integer"}).select(), f.length) for (g in f) f.hasOwnProperty(g) && (f[g][k] = parseInt(f[g][k], 10) - e)
            }
          }
        }
      })
    }, delChildren: function (b) {
      return this.each(function () {
        var c, d, e, f, g = this, h = g.p.localReader.id, i = g.p.treeReader.left_field, j = g.p.treeReader.right_field;
        if (g.grid && g.p.treeGrid) {
          b = a.jgrid.stripPref(g.p.idPrefix, b);
          var k = g.p._index[b];
          if (void 0 !== k) {
            c = parseInt(g.p.data[k][j], 10), d = c - parseInt(g.p.data[k][i], 10) + 1;
            var l = a(g).jqGrid("getFullTreeNode", g.p.data[k]);
            if (l.length > 0) for (var m = 0; m < l.length; m++) l[m][h] !== b && a(g).jqGrid("delRowData", g.p.idPrefix + l[m][h]);
            if ("nested" === g.p.treeGridModel) {
              if (e = a.jgrid.from(g.p.data).greater(i, c, {stype: "integer"}).select(), e.length) for (f in e) e.hasOwnProperty(f) && (e[f][i] = parseInt(e[f][i], 10) - d);
              if (e = a.jgrid.from(g.p.data).greater(j, c, {stype: "integer"}).select(), e.length) for (f in e) e.hasOwnProperty(f) && (e[f][j] = parseInt(e[f][j], 10) - d)
            }
          }
        }
      })
    }, addChildNode: function (b, c, d, e) {
      var f = this[0];
      if (d) {
        var g, h, i, j, k, l, m, n, o = f.p.treeReader.expanded_field, p = f.p.treeReader.leaf_field,
          q = f.p.treeReader.level_field, r = f.p.treeReader.parent_id_field, s = f.p.treeReader.left_field,
          t = f.p.treeReader.right_field, u = f.p.treeReader.loaded, v = 0, w = c;
        if (void 0 === e && (e = !1), null == b) {
          if ((k = f.p.data.length - 1) >= 0) for (; k >= 0;) v = Math.max(v, parseInt(f.p.data[k][f.p.localReader.id], 10)), k--;
          b = v + 1
        }
        var x = a(f).jqGrid("getInd", c);
        if (m = !1, void 0 === c || null === c || "" === c) c = null, w = null, g = "last", j = f.p.tree_root_level, k = f.p.data.length + 1; else {
          g = "after";
          var y = a.jgrid.stripPref(f.p.idPrefix, c);
          h = f.p._index[y], i = f.p.data[h], c = i[f.p.localReader.id], j = parseInt(i[q], 10) + 1;
          var z = a(f).jqGrid("getFullTreeNode", i);
          if (z.length ? (k = z[z.length - 1][f.p.localReader.id], w = k, k = a(f).jqGrid("getInd", f.p.idPrefix + w)) : k = a(f).jqGrid("getInd", f.p.idPrefix + c), i[p] && (m = !0, i[o] = !0, a(f.rows[x]).find("span.cell-wrapperleaf").removeClass("cell-wrapperleaf").addClass("cell-wrapper").end().find("div.tree-leaf").removeClass(f.p.treeIcons.leaf + " tree-leaf").addClass(f.p.treeIcons.minus + " tree-minus"), f.p.data[h][p] = !1, i[u] = !0), !1 === k) throw"Parent item with id: " + w + " (" + c + ") can't be found";
          k++
        }
        if (l = k + 1, void 0 === d[o] && (d[o] = !1), void 0 === d[u] && (d[u] = !1), d[q] = j, void 0 === d[p] && (d[p] = !0), "adjacency" === f.p.treeGridModel && (d[r] = c), "nested" === f.p.treeGridModel) {
          var A, B, C;
          if (null !== c) {
            if (n = parseInt(i[t], 10), A = a.jgrid.from.call(f, f.p.data), A = A.greaterOrEquals(t, n, {stype: "integer"}), B = A.select(), B.length) for (C in B) B.hasOwnProperty(C) && (B[C][s] = B[C][s] > n ? parseInt(B[C][s], 10) + 2 : B[C][s], B[C][t] = B[C][t] >= n ? parseInt(B[C][t], 10) + 2 : B[C][t]);
            d[s] = n, d[t] = n + 1
          } else {
            if (n = parseInt(a(f).jqGrid("getCol", t, !1, "max"), 10), B = a.jgrid.from.call(f, f.p.data).greater(s, n, {stype: "integer"}).select(), B.length) for (C in B) B.hasOwnProperty(C) && (B[C][s] = parseInt(B[C][s], 10) + 2);
            if (B = a.jgrid.from.call(f, f.p.data).greater(t, n, {stype: "integer"}).select(), B.length) for (C in B) B.hasOwnProperty(C) && (B[C][t] = parseInt(B[C][t], 10) + 2);
            d[s] = n + 1, d[t] = n + 2
          }
        }
        (null === c || a(f).jqGrid("isNodeLoaded", i) || m) && (a(f).jqGrid("addRowData", b, d, g, f.p.idPrefix + w), a(f).jqGrid("setTreeNode", k, l)), i && !i[o] && e && a(f.rows[x]).find("div.treeclick").click()
      }
    }
  })
});