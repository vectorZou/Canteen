window.onload=function(){
	ajaxForMy("GET","stockmanager_list.do",true,append2Table);
};
/***
 * 附加html
 */		
function append2Table(json){
	if(json.count != null){
		var config={
				pageId     : "pager",
				totalPage  : json.count,
				currPageNo : 1
			};
		var pageGenerator = new zZBPageGenerator(config);
		pageGenerator.generateHTML();
		pageGenerator.callback = function(currPageNo){
			ajaxForMy("GET","control/stock/stockmanager_list.do?page="+currPageNo,true,loadHtmlByPageNo);
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
	oTbody.innerHTML = htmlGenerator(json,"append2Table");
}
/***
 * 加载当前记录
 */
function loadWholeInfo(index){
	ajaxForMy("GET","stockmanager_loadInfo.do?index="+index,true,success);
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
		$("#stock_total_price").click(function(){
			var price = $(this).prev().prev().val();
			var count = $(this).prev().val();
			if(price == undefined || price=="") return false;
			if(count == undefined || count=="") return false;
			$(this).val(parseFloat(price)*parseInt(count));
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
	ajaxForMy("GET","stockmanager_loadInfo.do?index="+i,true,success);
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
			$("#stock_total_price").click(function(){
				var price = $(this).prev().prev().val();
				var count = $(this).prev().val();
				if(price == undefined || price=="") return false;
				if(count == undefined || count=="") return false;
				$(this).val(parseFloat(price)*parseInt(count));
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
	ajaxForMy("GET","stockmanager_delete.do?index="+i,true,success);
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
			ajaxForMy("GET","stockmanager_list.do",true,append2Table);
		});
	}
 }
/***
 * 绑定事件
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
}

function htmlGenerator(json,method){
	var html = "";
	if(method == "moreInfo"){
		html += "<div class='form_content'>";
		html += "货品名称：<input disabled='disabled' type='text' value='"+json.name+"'/><br/>";
		html += "价格：<input disabled='disabled' type='text' value='"+json.price+"'/><br/>";
		html += "数量：<input disabled='disabled' type='text' value='"+json.count+"'/><br/>";
		html += "总价：<input disabled='disabled' type='text' value='"+json.totalPrice+"'/><br/>"
		html += "进货时间: <input disabled='disabled' type='text' value='"+json.stockTime+"'/><br/>";
		html += "进货人：<input disabled='disabled' type='text' value='"+json.purchaser+"'/><br/>";
		html += "检验员: <img disabled='disabled' alt='' src='"+json.checkPerson+"'/><br/>";
		html += "<div class='insure-btn-con confirm_foot'>";
		html += "<p class='insure-btn-con' ><span><input type='button' class='sure-btn modal-close' value='确定' /></span></p>";
		html += "</div>";
		html += "</div>";
	}else if(method == "save"){
		html += "<div class='form_content'>";
		html += "<form action='stockmanager_save.do' enctype='multipart/form-data' method='post'>";
		html += "货品名称：<input type='text' name='name'/>";
		html += "价格：<input type='text' name='price'/>";
		html += "数量：<input type='text' name='count'/>";
		html += "总价：<input id='stock_total_price' type='text' name='totalPrice'/>";
		html += "进货时间：<input id='datatime_input' type='text' name='stockTime'/>"
		html += "进货人: <input  type='text' name='purchaser'/>";
		html += "检验员: <input  type='text' name='checkPerson'/>";
		html += "</div>";
		html += "<div class='insure-btn-con confirm_foot'>";
		html += "<p class='insure-btn-con' ><span><input type='button' class='sure-btn' value='确定' /></span><span><input type='button' class='cancel-btn modal-close' value='取消' /></span></p>";
		html += "</div>";
		html +="</form>";
	}else if(method == "update"){
		html += "<div class='form_content'>";
		html += "<form action='stockmanager_save.do' enctype='multipart/form-data' method='post'>";
		html += "<input type='hidden' name='hidden' value='"+json.id+"'/>";
		html += "货品名称：<input type='text' name='name' value='"+json.name+"'/>";
		html += "价格：<input type='text' name='price' value='"+json.price+"'/>";
		html += "数量：<input type='text' name='count' value='"+json.count+"'/>";
		html += "总价：<input id='stock_total_price' type='text' name='totalPrice' value='"+json.totalPrice+"'/>"
		html += "进货时间:<input id='datatime_input' type='text' name='stockTime' value='"+json.stockTime+"'/>";
		html += "进货人: <input  type='text' name='purchaser' value='"+json.purchaser+"'/>";
		html += "检验员: <input  type='text' name='checkPerson' value='"+json.checkPerson+"'/>";
		html += "</div>";
		html += "<div class='insure-btn-con confirm_foot'>";
		html += "<p class='insure-btn-con' ><span><input type='button' class='sure-btn' value='确定' /></span><span><input type='button' class='cancel-btn modal-close' value='取消' /></span></p>";
		html += "</div>";
		html +="</form>";
	}else if(method == "append2Table"){
		var list = json.resultList;
		for(var i=0,len=list.length;i<len;i++){
			html += '<tr>';
			html += '<td  class="rounded-company"><input type="radio" name="group" value="'+list[i].id+'">'+(i+1)+'</td>';
			html += '<td>'+list[i]["name"]+'</td>';
			html += '<td>'+list[i]["price"]+'</td>';
			html += '<td>'+list[i]["count"]+'</td>';
			html += '<td>'+list[i]["totalPrice"]+'</td>';
			html += '<td>'+list[i]["stockTime"]+'</td>';
			html += '<td><a href="javascript:void(0);">'+list[i]["purchaser"]+'</a></td>';
			html += '<td><a href="javascript:void(0);">'+list[i]["checkPerson"]+'</a></td>';
			html += '</tr>';
		}
	}
	return html;
}