window.onload = function(){
	ajaxForMy("GET","usersmanager_list.do",true,appendHtml2Table);
};
/***
 * 产生tr
 */
function appendHtml2Table(json){
	if(json.count != null){
		var config={
				pageId     : "pager",
				totalPage  : json.count,
				currPageNo : 1
			};
		var pageGenerator = new zZBPageGenerator(config);
		pageGenerator.generateHTML();
		pageGenerator.callback = function(currPageNo){
			ajaxForMy("GET","control/info/usersmanager_list.do?page="+currPageNo,true,loadHtmlByPageNo);
		};
	};
	loadHtmlByPageNo(json);
}
/***
 * 重写表格的HTML
 * @param json
 */
function loadHtmlByPageNo(json){
	var oTbody = document.getElementById("ftBody");
	oTbody.innerHTML = htmlGenerator(json,"appendHtml2Table");
}
/***
 * 加载当前记录
 */
function loadWholeInfo(index){
	ajaxForMy("GET","usersmanager_loadInfo.do?index="+index,true,success);
	function success(json){
		var popHtml = htmlGenerator(json,"moreInfo");
			var options = {
		    background: "#000",
            width: "1000px",
            height: "500px",
            resizable: true,
			bgClose: true,
            html: popHtml
		}
		$("body").createModal(options);
	}
}
/***
 * 保存
 */
function save(){
	var popHtml = htmlGenerator(null,"save");
	var options = {
		background: "#000",
    	width: "1000px",
        height: "500px",
        resizable: true,
		bgClose: true,
        html: popHtml
		}
	$("body").createModal(options,function(){
		$(".confirm_foot").find(".sure-btn").click(function(){
				document.getElementsByTagName("form")[0].submit();			 
			});
	});
	_innerEventBlind();
}
/***
 * 更新
 */
 function update(){
	var i = $("#ftBody :radio:checked").val();
	if(i==null || i=="undefined" || i=="") return false;
	ajaxForMy("GET","usersmanager_loadInfo.do?index="+i,true,success);
	function success(json){
		var form = htmlGenerator(json,"update");
		var options = {
		    background: "#000",
            width: "1000px",
            height: "500px",
            resizable: true,
			bgClose: true,
            html: form
		}
		$("body").createModal(options,function(){
			$(".confirm_foot").find(".sure-btn").click(function(){
				document.getElementsByTagName("form")[0].submit();			 
			});
		});
		_innerEventBlind();
	}
}
/***
 * 删除
 */
 function del(){
 	var i = $("#ftBody :radio:checked").val();
	if(i==null || i=="undefined" || i=="") return false;
	ajaxForMy("GET","usersmanager_delete.do?index="+i,true,success);
	function success(json){
		var options = {
		    background: "#000",
            width: "600px",
            height: "300px",
            resizable: false,
			bgClose: true,
            html: "<br/><div style='text-align:center;'><font color='#669' size='10'>"+json.o+"</font></div>"
		}
		$("body").createModal(options,function(){
			ajaxForMy("GET","usersmanager_list.do",true,appendHtml2Table);
		});
	}
 }
/***
 * 绑定事件
 */
function _innerEventBlind(){
	$("#datatime_input").click(function(){
			var options = {
			startDate:'%y-%M-01',
			dateFmt:'yyyy-MM-dd',
			alwaysUseStartDate:true
		};
		WdatePicker(options);
	});
}

function htmlGenerator(json,method){
	var html = "";
	if(method == "moreInfo"){
		html += "<div class='form_content'>";
		html += "用户名：<input disabled='disabled' type='text' value='"+json.username+"'/><br/>";
		html += "名字：<input disabled='disabled' type='text' value='"+json.name+"'/><br/>";
		html += "性别：<label>"+(json.gender>0 ? "男":"女")+"</label><br/>";
		html += "出生年月：<label>"+json.birthday+"</label><br/>"
		html += "电话: <input disabled='disabled' type='text' value='"+json.telephone+"'/><br/>";
		html += "单位：<input disabled='disabled' type='text' value='"+json.unit+"'/><br/>";
		html += "图片: <img disabled='disabled' alt='' src='"+json.image+"'/><br/>";
		html += "<div class='insure-btn-con confirm_foot'>";
		html += "<p class='insure-btn-con' ><span><input type='button' class='sure-btn modal-close' value='确定' /></span></p>";
		html += "</div>";
		html += "</div>";
	}else if(method == "save"){
		html += "<div class='form_content'>";
		html += "<form action='usersmanager_save.do' enctype='multipart/form-data' method='post'>";
		html += "用户名：<input type='text' name='username'/>";
		html += "密码：<input type='text' name='password'/>";
		html += "名字：<input type='text' name='name'/>";
		html += "性别：<input type='radio' value='1' name='gender'/>男 <input type='radio' value='0' name='gender'/>女<br/>";
		html += "出生年月：<input id='datatime_input' type='text' name='birthday'/>"
		html += "电话: <input  type='text' name='telephone'/>";
		html += "单位: <input  type='text' name='unit'/>";
		html += "图片: <input type='file' name='image'/>";
		html += "</div>";
		html += "<div class='insure-btn-con confirm_foot'>";
		html += "<p class='insure-btn-con' ><span><input type='button' class='sure-btn' value='确定' /></span><span><input type='button' class='cancel-btn modal-close' value='取消' /></span></p>";
		html += "</div>";
		html +="</form>";
	}else if(method == "update"){
		html += "<div class='form_content'>";
		html += "<form action='usersmanager_save.do' enctype='multipart/form-data' method='post'>";
		html += "<input type='hidden' name='hidden' value='"+json.id+"'/>";
		html += "用户名：<input type='text' name='name' value='"+json.username+"'/>";
		html += "名字：<input type='text' name='name' value='"+json.name+"'/>";
		if(json.gender>0){
			html += "性别：<input type='radio' checked='checked'  value='1' name='gender'/>男 <input type='radio' value='0' name='gender'/>女";
		}else{
			html += "性别：<input type='radio' checked='checked'  value='1' name='gender'/>男<input type='radio' checked='checked' value='0' name='gender'/>女"
		}
		html += "<br/>出生年月：<input type='text' name='birthday' value='"+json.birthday+"'/>"
		html += "电话: <input  type='text' name='tel' value='"+json.telephone+"'/>";
		html += "图片: <img disabled='disabled' alt='' src='"+json.image+"'/><input type='file' name='image'/>";
		html += "</div>";
		html += "<div class='insure-btn-con confirm_foot'>";
		html += "<p class='insure-btn-con' ><span><input type='button' class='sure-btn' value='确定' /></span><span><input type='button' class='cancel-btn modal-close' value='取消' /></span></p>";
		html += "</div>";
		html +="</form>";
	}else if(method == "appendHtml2Table"){
		var list = json.resultList;
		for(var i=0,len=list.length;i<len;i++){
			html += '<tr>';
			html += '<td  class="rounded-company"><input type="radio" name="group" value="'+list[i].id+'">'+(i+1)+'</td>';
			html += '<td>'+list[i].username+'</td>';
			html += '<td>'+list[i]["name"]+'</td>';
			html += '<td>'+( list[i].gender>0 ? "男" : "女" )+'</td>';
			html += '<td>'+list[i].birthday+'</td>';
			html += '<td>'+list[i].telephone+'</td>';
			html += '<td><a href="javascript:loadWholeInfo('+"'"+ list[i].id +"'"+');">详细信息</a></td>';
			html += '</tr>';
		}
	}
	return html;
}