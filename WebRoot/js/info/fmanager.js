/***
 * 给表格中的增加tr
 */
function appendHtml2Table(json){
	var oftBody = document.getElementById("ftBody");
	oftBody.innerHTML = htmlGenerator(json);
	if(json.count != null){
		var config={
				pageId     : "pager",
				totalPage  : json.count,
				currPageNo : 1
			};
		var pageGenerator = new zZBPageGenerator(config);
		pageGenerator.generateHTML();
		pageGenerator.callback = function(currPageNo){
			ajaxForMy("GET","control/info/fmanager_list.do?page="+currPageNo,true,loadHtmlByPageNo);
		};
	};
}
/***
 * 重写表格的HTML
 * @param json
 */
function loadHtmlByPageNo(json){
	var oTbody = document.getElementById("ftBody");
	oTbody.innerHTML = htmlGenerator(json);
}
/***
 * 根据json内容拼接字符串
 */
function htmlGenerator(json){
	var html = "";
	for(var i=0,len= json.resultList.length;i<len;i++){
		html += "<tr>";
			html += "<td><input type='radio' name='group' value='"+json.resultList[i].ID+"'/>"+(i+1)+"</td>";
			html += "<td>"+json.resultList[i]["name"]+"</td>";
			html += "<td>"+(json.resultList[i].type == null ? "": json.resultList[i].type["name"]) +"</td>";
			html += "<td>"+json.resultList[i].price+"</td>";
			html += "<td><img atl='' src="+json.resultList[i].image+"></td>";
			html += "<td><a href='javascript:loadWholeInfo("+json.resultList[i].ID+");'>详细</a></td>";
		html += "</tr>";
	}
	return html;
}
/***
 * 加载详细信息
 *
 */
function loadWholeInfo(i){
	ajaxForMy("GET","control/info/fmanager_loadWholeInfo.do?index="+i,true,success);
	function success(json){
		var popHtml = "<div id='more_info_div' class='more_info_div'><form>";
			popHtml += "<div>名字:<span>"+json.name+"</span></div>";
			popHtml += "<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类型:<span>"+ (json.type == null ? "":json.type.name) +"</span></div><br/>";
			popHtml += "<div>价格:<span>"+json.price+"</span></div>";
			popHtml += "<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;供应商:<span>"+json.supplier+"</span></div><br/>";
			popHtml += "<div>仓库数量:<span>"+json.store+"</span></div>";
			popHtml += "<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;进货时间:<span><font color='red'>"+json.stockTime+"</font></span></div>";
			popHtml += "<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;保值时间:<span><font color='red'>"+json.days+"</font></span>天</div><br/>";
			popHtml += "<div>食品描述:<textarea cols='50' rows='8' disabled='disabled'>"+json.brief+"</textarea></div><br/>";
			popHtml += "<div>图片:<img alt='' src='"+json.image+"'/></div>";
		popHtml += "</form></div>";
		var options = {title:"详细信息"};
		window.wxc.xcConfirm(popHtml, window.wxc.xcConfirm.typeEnum.custom, options);
	}
}
/***
 * 增加食品
 */
function save(){
	var popHtml = "<div id='more_info_div' class='more_info_div'><form action='fmanager_save.do' enctype='multipart/form-data' method='post'>";
			popHtml += "<div>名字:&nbsp;&nbsp;<input type='text' name='name' /></div>";
			popHtml += "<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类型:&nbsp;&nbsp;<input id='type_select_input' type='text'/><input id='hidden_input' type='hidden' name='type' value=''></div><br/>";
			popHtml += "<div>价格:&nbsp;&nbsp;<input type='text' name='price' /></div>";
			popHtml += "<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;供应商:&nbsp;<input type='text' name='supplier'/></div><br/>";
			popHtml += "<div>仓库数量:<input type='text' name='mount' ></div>";
			popHtml += "<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;进货时间:<input id='datatime_input'  type='text' name='stock'/></div>";
			popHtml += "<div>保值时间:<input type='text' name='day'>天</div><br/>";
			popHtml += "<div>食品描述:<textarea cols='50' rows='8' name='brief'></textarea></div><br/>";
			popHtml += "<div>图片:<input type='file' name='file'></div>";
		popHtml += "</form></div>";
	var options = {
		title:"增加食品",
		btn:parseInt("0011",2),
		onOk:function(){
			oForm = document.getElementById("more_info_div").getElementsByTagName("form")[0];
			oForm.submit();
		},
		onCancel:function(){
			return false;
		}
	};
	window.wxc.xcConfirm(popHtml, window.wxc.xcConfirm.typeEnum.custom, options);
	_innerEventBlind();
}
/***
 * 更新食品
 */
function update(){
	var oRadio = $("#ftBody :radio:checked");
	if(oRadio == null || oRadio.val() == undefined || oRadio.val() == "") return false;
	var i = oRadio.val();
	ajaxForMy("GET","fmanager_loadWholeInfo.do?index="+i,true,success);
	function success(json){
		var popHtml = "<div id='more_info_div' class='more_info_div'><form action='fmanager_update.do' enctype='multipart/form-data' method='post'>";
			popHtml += "<input type='hidden' name='hidden' value='"+i+"' />";
			popHtml += "<div>名字:&nbsp;&nbsp;<input type='text' name='name' value='"+json.name+"'/></div>";
			popHtml += "<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;类型:&nbsp;&nbsp;<input type='text' id='type_select_input' value='"+(json.type == null ? "":json.type.name)+"'/><input id='hidden_input' type='hidden' name='type' value='"+(json.type == null ? "" : json.type.ID)+"'/></div><br/>";
			popHtml += "<div>价格:&nbsp;&nbsp;<input type='text' name='price' value='"+json.price+"' /></div>";
			popHtml += "<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;供应商:&nbsp;<input type='text' name='supplier' value='"+json.supplier+"' /></div><br/>";
			popHtml += "<div>仓库数量:<input type='text' name='mount' value='"+json.store+"' ></div>";
			popHtml += "<div>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;进货时间:<input id='datatime_input' type='text' name='stock' value='"+json.stockTime+"' /></div>";
			popHtml += "<div>保值时间:<input type='text' name='day' value='"+json.days+"'>天</div><br/>";
			popHtml += "<div>食品描述:<textarea cols='50' rows='8' name='brief'>"+json.brief+"</textarea></div><br/>";
			popHtml += "<div>图片:<input type='file' name='file'></div>";
			popHtml += "</form></div>";
		var options = {
			title:"修改食品",
			btn:parseInt("0011",2),
			onOk:function(){
				oForm = document.getElementById("more_info_div").getElementsByTagName("form")[0];
				oForm.submit();
			},
			onCancel:function(){
				return false;
			}
		};
		window.wxc.xcConfirm(popHtml, window.wxc.xcConfirm.typeEnum.custom, options);
		_innerEventBlind();
	}
}
/***
 * 用于绑定弹出框中的类型输入和时间输入框的插件
 */
function _innerEventBlind(){
	$("#datatime_input").click(function(){
			var options = {
			startDate:'%y-%M-01 00:00:00',
			dateFmt:'yyyy-MM-dd HH:mm:ss',
			alwaysUseStartDate:true
		};
		WdatePicker(options);
	});
	$("#type_select_input").focus(function(){
		ajaxForMy("GET","control/info/ftmanager_tree.do",true,success);
		function success(json){
			var html = "<span id='type_span_zzb'></span><div class='tree well'>"
			html += generatorTreeHTML(json);
			html += "</div>";
			window.wxc.xcConfirm(html, window.wxc.xcConfirm.typeEnum.custom);//再加一层弹窗
		}
	});
}

function selectType(n,i){
	$("#type_select_input").val(n);
	$("#hidden_input").val(i);
}
/***
 * 删除食品
 */
function del(){
	var oRadio = $("#ftBody input[type=radio][name=group]:checked");
	if(oRadio == null || oRadio.val() == "" || oRadio.val() == undefined) return false;
	var i = oRadio.val();
	ajaxForMy("get","fmanager_delete.do?index="+i+"&time="+(new Date()).toString(),true,success);
	function success(json){
		var popHtml = "<br/><center><font size='10'>"+json.m+"</font></center>";
		window.wxc.xcConfirm(popHtml, window.wxc.xcConfirm.typeEnum.info);
		ajaxForMy("GET","control/info/fmanager_list.do",true,appendHtml2Table);
	}
}