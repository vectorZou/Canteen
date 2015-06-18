/***
 * 为表格中的数据添加tr元素
 * @param json 为后台响应的数据的json格式数据
 */
function appendHtml2Tbody(json){
	var oTbody = document.getElementById("ftBody");
	oTbody.innerHTML = generatorHTML(json);
	if(json.count != null){
		var config={
				pageId     : "pager",
				totalPage  : json.count,
				currPageNo : 1
			};
		var pageGenerator = new zZBPageGenerator(config);
		pageGenerator.generateHTML();
		pageGenerator.callback = function(currPageNo){
			var qId = document.getElementById("qId").getAttribute("value");
			if(qId == "0") ajaxForMy("get","control/info/ftmanager_list.do?page="+currPageNo,true,loadHtmlByPageNo);
			else ajaxForMy("get","control/info/ftmanager_list.do?page="+currPageNo+"&qId="+qId,true,loadHtmlByPageNo);
		};
	};
}
/***
 * 
 * @param json
 * @returns {String}
 */
function generatorHTML(json){
	var sHtml = "";
	for(var i=0,len= json.resultList.length; i < len; i++){
		sHtml += "<tr id='tr_"+json.resultList[i].ID+"'>";
		sHtml +="<td><input type='radio' name='group_radio' value='"+json.resultList[i].ID+"'/>"+(i+1)+"</td>";
		sHtml +="<td><a href='javascript:updateFdt("+json.resultList[i].ID+");'>修改</a></td>";
		sHtml +="<td>"+json.resultList[i].name+"</td>";
		sHtml +="<td>"+(json.resultList[i].parent == null ? "" : json.resultList[i].parent.name)+"</td>";
		var tmp = json.resultList[i].childs == 0 ? "void(0);" : "loadSubType("+json.resultList[i].ID+")";
		sHtml +="<td><a href='javascript:"+tmp+"'>有<font color='#FF4500'>"+json.resultList[i].childs+"</font>个子类型</a></td>";
		sHtml +="<td><a href='javascript:createSubType("+json.resultList[i].ID+");'>创建子类型</a></td>";
		sHtml +="<td>"+json.resultList[i].note+"</td>";
		sHtml += "</tr>";
	}
	return sHtml;
}
/***
 * 修改当前类型的名称和备注
 * @param pk
 */
function updateFdt(pk){
	var oTr = document.getElementById("tr_"+pk);
	var oTds = oTr.getElementsByTagName("td");
	var popHtml= "名称:<input id='updateFdt_name_input' type='text' style='width:435px;' value='"+oTds[2].innerText+"' /><br/>";
	popHtml += "备注：<textarea id='updateFdt_note_textarea' rows='5' cols='65'>"+oTds[6].innerText+"</textarea>";
	var options = {
		title:"修改页",
		btn:parseInt("0011",2),
		onOk:function(){
			var oInput = document.getElementById("updateFdt_name_input");
			var oTextarea = document.getElementById("updateFdt_note_textarea");
			ajaxForMy("GET", "ftmanager_update.do?pk="+pk+"&name="+oInput.value+"&note="+oTextarea.value, true, success);
			function success(json){
				if(json.toString() == "success"){
					oTds[2].innerText = oInput.value;
					oTds[6].innerText = oTextarea.value;
				}
			};
		},
		onCancel:function(){
			return false;
		}
	};
	window.wxc.xcConfirm(popHtml, window.wxc.xcConfirm.typeEnum.custom,options);
}
/***
 * 在当前父类型中增加子类型
 * @param pd
 */
function createSubType(pd){
	var oTr = document.getElementById("tr_"+pd);
	var oTd = oTr.getElementsByTagName("td")[4];
	var popHtml= "名称:<input id='updateFdt_name_input' type='text' style='width:435px;' value='' /><br/>";
	popHtml += "备注：<textarea id='updateFdt_note_textarea' rows='5' cols='65'></textarea>";
	var options = {
			title:"创建子类型页",
			btn:parseInt("0011",2),
			onOk:function(){
				var oInput = document.getElementById("updateFdt_name_input");
				var oTextarea = document.getElementById("updateFdt_note_textarea");
				ajaxForMy("GET", "ftmanager_saveSub.do?pd="+pd+"&name="+oInput.value+"&note="+oTextarea.value, true,success);
				function success(json){
					if(json!=null){
						oTd.innerHTML = "<a href='javascript:loadSubType("+pd+");'>有<font color='#FF4500'>"+json.count+"</font>个子类型</a>";
					}
				};
			},
			onCancel:function(){
				return false;
			}
		};
		window.wxc.xcConfirm(popHtml, window.wxc.xcConfirm.typeEnum.custom,options);
}
/***
 * 加载查找到的子类
 * @param qId
 */
function loadSubType(qId){
	document.getElementById("qId").setAttribute("value",qId);
	ajaxForMy("GET","ftmanager_list.do?qId="+qId , true, appendHtml2Tbody);
}
/***
 * 重写表格的HTML
 * @param json
 */
function loadHtmlByPageNo(json){
	var oTbody = document.getElementById("ftBody");
	oTbody.innerHTML = generatorHTML(json);
}
/***
 * 创建食品类型树页面
 */
function creatTree(){
	var oIframe = window.top.document.getElementById("subIfram");
	var prefix = oIframe.src.substring(0,oIframe.src.lastIndexOf("/"));
	oIframe.src = prefix+ "/ftmanager_treeUI.do";
}
/***
 * 增加类型
 */
 function save(){
 	var popHtml= "名称:<input id='updateFdt_name_input' type='text' style='width:435px;' value='' /><br/>";
	popHtml += "备注：<textarea id='updateFdt_note_textarea' rows='5' cols='65'></textarea>";
	var options = {
			title:"创建食品类型",
			btn:parseInt("0011",2),
			onOk:function(){
				var oInput = document.getElementById("updateFdt_name_input");
				var oTextarea = document.getElementById("updateFdt_note_textarea");
				ajaxForMy("GET", "ftmanager_save.do?name="+oInput.value+"&note="+oTextarea.value, true,success);
				function success(json){
					if(json!=null){
						window.wxc.xcConfirm(json.o, window.wxc.xcConfirm.typeEnum.info);
						ajaxForMy("get","control/info/ftmanager_list.do",true,appendHtml2Tbody);
					}
				};
			},
			onCancel:function(){
				return false;
			}
		};
	window.wxc.xcConfirm(popHtml, window.wxc.xcConfirm.typeEnum.custom,options);
 }
 /***
  * 更新类型
  */
function update(){
	var oRadio = findCheckedRadio();
 	if(oRadio == null || oRadio == undefined || oRadio == "") return false;
	updateFdt(oRadio);
}
/***
 * 删除类型
 */
 function del(){
 	var oRadio = findCheckedRadio();
 	if(oRadio == null || oRadio == undefined || oRadio == "") return false;
 	ajaxForMy("get","control/info/ftmanager_delete.do?td="+oRadio,true,success);
 	function success(json){
 		if(json != null){
 			if(json.f){
				ajaxForMy("get","control/info/ftmanager_list.do",true,appendHtml2Tbody);
 			}
 			var popHtml = "<br/><center><font size='10'>"+json.o+"</font></center>";
			window.wxc.xcConfirm(popHtml, window.wxc.xcConfirm.typeEnum.info);
 		}
 	}
 }
/***
 * 查找到选中的radio
 */
function findCheckedRadio(){
	var oRadios = document.getElementsByName("group_radio");
	var v = "";
 	for(var i=0,len=oRadios.length;i<len;i++){
 		var oRadio = oRadios[i]; 
 		if(oRadio.checked || oRadio.getAttribute("checked")){
 			v = oRadio.getAttribute("value");
 			break;
 		}
 	}
 	return v;
}