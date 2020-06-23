/**
 * 编辑-js
 */
var vm = new Vue({
	el:'#dpLTE',
	data: {
		tableConf: {
			id: 0

		}
	},
	methods : {
		setForm: function() {
			$.get('/common/interface/dic_data', {type_id: 1001},function(result) {
				if(result.code == 0){
					var yw = result.rows;
					var selDom = $("#ywlx");
					for(var i = 0 ;i<yw.length;i++){
						selDom.append("<option value="+yw[i].value+">"+yw[i].name+"</option>");
					}
				}
			}),
			$.get('/common/interfac