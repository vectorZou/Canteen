/***
 * 用于产生tr,附加到table上
 */
function appendHtml2Table(json){
	if(json == null || json == "undefined" || json == "") return false;
	var oftBody = document.getElementById("ftBody");
	oftBody.innerHTML = generatorHTML(json);
	if(json.count != null){
		var config={
				pageId     : "pager",
				totalPage  : json.count,
				currPageNo : 1
			};
		var pageGenerator = new zZBPageGenerator(config);
		pageGenerator.generateHTML();
		pageGenerator.callback = function(currPageNo){
			ajaxForMy("GET","control/info/splmanager_list.do?page="+currPageNo,true,loadHtmlByPageNo);
		};
	};
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
 * 由json拼接字符串
 */
function generatorHTML(json){
	var html = "";
	var list = json.resultList;
	for(var i=0,len=list.length;i<len;i++){
		html += "<tr>";
			html += "<td class='rounded-company'><input type='radio' name='group' value='"+list[i].sId+"' />"+(i+1)+"</td>";
			html += "<td>"+list[i].name+"</td>";
			html += "<td>"+(list[i].gender>0 ? "男":"女")+"</td>";
			html += "<td>"+list[i].telephone+"</td>";
			html += "<td>"+list[i].company+"</td>";
			html += "<td>"+list[i].address+"</td>";
			html += "<td><a href=javascript:loadMoreInfo('"+list[i].sId+"');>详细</a></td>";
		html += "</tr>";
	}
	return html;
}
/***
 * 加载详细信息
 */
function loadMoreInfo(i){
	ajaxForMy("GET","splmanager_loadWholeInfo.do?index="+i,true,success);
	var form = "";
	function success(json){
		form = formGenerator(json,"moreInfo");
		var options = {
		    background: "#000",
            width: "1000px",
            height: "500px",
            resizable: true,
			bgClose: true,
            html: form
		}
		$("body").createModal(options);
	}
}
/***
 * 添加供应商
 */
function save(){
	var form = formGenerator(null,"save");
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
}
/***
 * 更新元素
 */
function update(){
	var i = $("#ftBody :radio:checked").val();
	if(i==null || i=="undefined" || i=="") return false;
	ajaxForMy("GET","splmanager_loadWholeInfo.do?index="+i,true,success);
	function success(json){
		var form = formGenerator(json,"update");
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
	}
}
/***
 * 删除对象
 */
function del(){
	var i = $("#ftBody :radio:checked").val();
	if(i==null || i=="undefined" || i=="") return false;
	ajaxForMy("GET","splmanager_delete.do?index="+i,true,success);
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
			ajaxForMy("GET","splmanager_list.do",true,appendHtml2Table);
		});
	}
}
/***
 * 拼接字符串
 */
function formGenerator(json,method){
	var form = "";
	if(method == "moreInfo"){
		form += "<div class='form_content'>";
			form += "名字：<input disabled='disabled' type='text' value='"+json.name+"'/>";
			form += "性别：<label>"+(json.gender>0 ? "男":"女")+"</label>";
			form += "年龄：<label>"+json.age+"</label>"
			form += "电话: <input disabled='disabled' type='text' value='"+json.telephone+"'/>";
			form += "公司：<input disabled='disabled' type='text' value='"+json.company+"'/>";
			form += "地址：<input disabled='disabled' type='text' value='"+json.address+"'/>";
			form += "Email:<input disabled='disabled' type='text' value='"+json.email+"'/>";
			form += "图片: <img disabled='disabled' alt='' src='"+json.image+"'/>";
			form += "公司简介:<textarea disabled='disabled' cols='25' rows='8'>"+json.company_brief+"</textarea>";
		form += "</div>";
	}else if(method == "save"){
			form += "<div class='form_content'>";
				form += "<form action='splmanager_save.do' enctype='multipart/form-data' method='post'>";
				form += "名字：<input type='text' name='name'/>";
				form += "性别：<input type='radio' value='1' name='gender'/>男 <input type='radio' value='0' name='gender'/>女<br/>";
				form += "年龄：<input type='text' name='age'/>"
				form += "电话: <input  type='text' name='tel'/>";
				form += "公司：<input type='text' name='company'/>";
				form += "地址：<input type='text' name='address'/>";
				form += "Email:<input  type='text' name='email'/>";
				form += "图片: <input type='file' name='image'/>";
				form += "公司简介:<textarea cols='25' rows='8' name='brief'></textarea>";
			form += "</div>";
			form += "<div class='insure-btn-con confirm_foot'>";
				form += "<p class='insure-btn-con' ><span><input type='button' class='sure-btn' value='确定' /></span><span><input type='button' class='cancel-btn modal-close' value='取消' /></span></p>";
			form += "</div>";
			form +="</form>";
	}else if(method == "update"){
			form += "<div class='form_content'>";
				form += "<form action='splmanager_save.do' enctype='multipart/form-data' method='post'>";
				form += "<input type='hidden' name='hidden' value='"+json.sId+"'/>";
				form += "名字：<input type='text' name='name' value='"+json.name+"'/>";
				if(json.gender>0){
					form += "性别：<input type='radio' checked='checked'  value='1' name='gender'/>男 <input type='radio' value='0' name='gender'/>女";
				}else{
					form += "性别：<input type='radio' checked='checked'  value='1' name='gender'/>男<input type='radio' checked='checked' value='0' name='gender'/>女"
				}
				form += "年龄：<input type='text' name='age' value='"+json.age+"'/>"
				form += "电话: <input  type='text' name='tel' value='"+json.telephone+"'/>";
				form += "公司：<input type='text' name='company' value='"+json.company+"' />";
				form += "地址：<input type='text' name='address' value='"+json.address+"'/>";
				form += "Email:<input  type='text' name='email' value='"+json.email+"'/>";
				form += "图片: <img disabled='disabled' alt='' src='"+json.image+"'/><input type='file' name='image'/>";
				form += "公司简介:<textarea cols='25' rows='8' name='brief' >"+json.company_brief+"</textarea>";
			form += "</div>";
			form += "<div class='insure-btn-con confirm_foot'>";
				form += "<p class='insure-btn-con' ><span><input type='button' class='sure-btn' value='确定' /></span><span><input type='button' class='cancel-btn modal-close' value='取消' /></span></p>";
			form += "</div>";
		form +="</form>";
	}
	return form;
}