/***
 * 本插件由邹志兵制作，所有版权归邹志兵个人所有。
 */
function zZBPageGenerator(init){
	/***
	 * 插件基本配置
	 */
	var config = { // 记住 ||的短路问题，如果init为非0则是true，也就是config为init。null、undefined、""、0都是false；
		pageId     : "pager",
		totalPage  : 12,
		currPageNo : 1,
		lang:{ //用于显示的中文字符常量
			beginPage : "首页",
			lastPage  : "上一页",
			nextPage  : "下一页",
			endPage   : "尾页",
			totalText : "当前第",
			page      : "页",
			SplitStr  : "/",
			totalPage : "共",
			btnText   : "确定",
			equiv     : "..."  
		}
	};
	/***
	 * 产生PageDiv来显示
	 */
	this.generateHTML = function(){ 
		_extends(init);
		var oPageDiv = document.getElementById(config.pageId);
		oPageDiv.setAttribute("class","pageContent");
		var html = "<div>";
			html += _generateFirstSpan();
			html += _generateAfterSpan();
			html += _generateIsolation();
		html += "</div>";
		oPageDiv.innerHTML = html;
		_events(this);
	};
	this.callback = function(){};
	/***
	 * 点击确定按钮，执行函数。由用户自定义
	 */
	this.confirmBtn = function(){};
	/***
	 * 产生前部分的Span
	 */
	function _generateFirstSpan(){
		var html = '<span  class="pageBtnWrap">';
			html += '<span id="a_wraps_span_head">';
				html += '<a  href="javascript:void(0);" class="curr" >'+config.lang.beginPage+'</a>';
				html += '<a href="javascript:void(0);" class="disabled">'+config.lang.lastPage+'</a>';
			html += '</span>';
			html += '<span id="a_wraps_span">';
				html += _generateALinks();
			html += '</span>';
			html +='<span id="a_wraps_span_end">';
				html += '<a href="javascript:void(0);" class="curr">'+config.lang.nextPage+'</a>';
				html += '<a href="javascript:void(0);" class="curr">'+config.lang.endPage+'</a>';
			html += '</span>';
		html += '</span>';
		return html;
	}
	/***
	 * 产生页码的a标签
	 */
	function _generateALinks(){
		var html="";
		for(var i=1;i<=config.totalPage;i++){
			if(i==config.currPageNo){
				html += '<a href="javascript:void(0);" class="currPageNum" style="background:#31ACFF;color:#FFF">'+i+'</a>';
			}else{
				if(config.totalPage <=10){
					html += '<a href="javascript:void(0);" class="curr">'+i+'</a>';
				}else{
					if(i<5 || i>config.totalPage-3) html += '<a href="javascript:void(0);" class="curr">'+i+'</a>';
					else if(i == 6) html += '<a href="javascript:void(0);" class="curr">'+config.lang.equiv+'</a>';
				}
			}
		}
		return html;
	};
	/***
	 * 产生后部分的Span
	 */
	function _generateAfterSpan(){
		var html = '<span class="infoTextAndGoPageBtnWrap">';
			html += '<span class="totalText">'+config.lang.totalText+'';
				html += '<span id="currPageNum_span_after" class="currPageNum">'+config.currPageNo+'</span>'+config.lang.page+'';
				html += '<span class="totalInfoSplitStr">'+config.lang.SplitStr+'</span>'+config.lang.totalPage+'';
				html += '<span class="totalPageNum">'+config.totalPage+'</span>'+config.lang.page+'';
			html += '</span>';
			html += '<span>';
				html += '<span class="pager_gopage_wrap">';
					html += '<input id="pager_btn_go" type="button" class="pager_btn_go"  value="'+config.lang.btnText+'">';
					html += '<input id="pager_btn_go_input" type="text" class="pager_btn_go_input" value="1">';
				html += '</span>页';
			html += '</span>';
		html += '</span>';
		return html;
	}
	/***
	 * 为后续Div清除inherit样式
	 */
	function _generateIsolation(){
		return '<div style="clear:both;"></div>';
	}
	/***
	 * 通过外部对象进行合并相同属性的值
	 */
	function _extends(inits){
		if(inits == null || inits == "undefined" || inits == "") return false;
		for(var prop in inits){
			if(config.hasOwnProperty){
				config[prop] = inits[prop];
			}
		}
	}
	/***
	 * 添加事件为input
	 */
	function _events(context){
		var oBtn = document.getElementById("pager_btn_go");
		var oText = document.getElementById("pager_btn_go_input");
		var aA_sh = document.getElementById("a_wraps_span_head").getElementsByTagName("a");
		var aA = document.getElementById("a_wraps_span").getElementsByTagName("a");
		var aA_se = document.getElementById("a_wraps_span_end").getElementsByTagName("a");
		/***
		 * 首页、下一页。绑定onclick事件
		 */
		for(var i=0,len=aA_sh.length;i<len;i++){
			var oA = aA_sh[i];
			oA.onclick = function(){
				var currNo = this.innerText;
				if(currNo == config.lang.beginPage) currNo = 1; 	 
				else if(currNo == config.lang.lastPage) currNo = 'last';
				_zZBBtnALink(currNo,context);	
			};
		}
		/***
		 * 尾页、上一页。绑定onclick事件
		 */
		for(var i=0,len=aA_se.length;i<len;i++){
			var oA = aA_se[i];
			oA.onclick = function(){
				var currNo = this.innerText;
				if(currNo == config.lang.endPage) currNo = config.totalPage; 	 
				else if(currNo == config.lang.nextPage) currNo = 'next';
				_zZBBtnALink(currNo,context);	
			};
		}
		/***
		 * 给1、2、3...页。绑定onclick事件
		 */
		 for(var i=0,len=aA.length;i<len;i++){
		 	var oA = aA[i];
		 	if(oA.innerText != config.lang.equiv ){
			 	oA.onclick = function(){
			 		var currNo = this.innerText;
			 		_zZBBtnALink( parseInt(currNo),context);
			 	};
		 	}
		 }
		/***
		 * 给文本输入框增加focus事件
		 */
		oText.onfocus = function(){
			oText.setAttribute("class","pager_btn_go_focus");
			oBtn.setAttribute("style","display: inline-block; left: 40px;");
		};
		/***
		 * 为文本输入框增加blur事件
		 */
		oText.onblur = function(){
			oText.setAttribute("class","pager_btn_go_input");
			var timeoutId = window.setTimeout(function(){
				oBtn.setAttribute("style","");
				window.clearTimeout(timeoutId);
			},3000);
		};
		/***
		 * 给确定按钮增加点击事件
		 */
		oBtn.onclick = function(){
			var page = oText.value;
			if(page - config.totalPage <=0){
				_zZBBtnALink(page,context);
			}
			if(context.confirmBtn!=null) context.confirmBtn(oText.value);
		};
	}
	/***
	 * 根据config.currPageNo自动变动缩略
	 */
	function _generateALinksByNum(context){
		var oSpan = document.getElementById("a_wraps_span");
		var html="";
		for(var i=1;i<=config.totalPage;i++){
			if(i==config.currPageNo){
				html += '<a href="javascript:zZBBtnALink('+config.currPageNo+');" class="currPageNum" style="background:#31ACFF;color:#FFF">'+i+'</a>';
			}else{
				if(config.currPageNo < 8){
					if(i< config.currPageNo+2 || i> config.totalPage-3) html += '<a href="javascript:void(0);" class="curr">'+i+'</a>';
					else if(i == config.currPageNo+2) html += '<a href="javascript:void(0);" class="curr">'+config.lang.equiv+'</a>';
				}else{
					if(i< 5 || i> config.totalPage-3 || (i >=config.currPageNo && i < config.currPageNo+2) ) html += '<a href="javascript:void(0);" class="curr">'+i+'</a>';
					else if(i == config.currPageNo-2 || i == config.currPageNo+2) html += '<a href="javascript:void(0);" class="curr">'+config.lang.equiv+'</a>';
				}
			}
		}
		oSpan.innerHTML = html;
		var aA = oSpan.getElementsByTagName("a");
		/***
		 * 给1、2、3...页。绑定onclick事件
		 */
		 for(var i=0,len=aA.length;i<len;i++){
		 	var oA = aA[i];
		 	if(oA.innerText != config.lang.equiv ){
			 	oA.onclick = function(){
			 		var currNo = this.innerText;
			 		_zZBBtnALink( parseInt(currNo),context);
			 	};
		 	}
		 }
	}
	/***
	 * 点击a标签时的链接函数，用于控制分页插件的显示和后天显示；
	 */
	_zZBBtnALink = function(curNo,context){
		if(curNo == "last"){ 
			config.currPageNo = (config.currPageNo == 1? 1 : config.currPageNo-1);
		}else if(curNo == "next"){
			config.currPageNo = (config.currPageNo == config.totalPage ?  config.totalPage : config.currPageNo + 1);
		}else{
			config.currPageNo =  parseInt(curNo);
		}
		var aA = document.getElementById("a_wraps_span").getElementsByTagName("a");
		var oCurPageNum = document.getElementById("currPageNum_span_after");
		var bFlag = false; // 用于表示config.currPageNo还在初始产生的a标签范围内
		for(var i=1,len=aA.length;i<=len;i++){
			var oA = aA[i-1];
			if(oA.getAttribute("class") == "currPageNum"){
				if(oA.innerText != config.currPageNo){ 
					oA.removeAttribute("class");
					oA.removeAttribute("style");
				}else{
					bFlag = true;
				}
			}else{
				if(oA.innerText == config.currPageNo){
					oA.setAttribute("class","currPageNum");
					oA.setAttribute("style","background:#31ACFF;color:#FFF");
					bFlag = true;
				}
			}
		}
		oCurPageNum.innerHTML = config.currPageNo;
		if(!bFlag) _generateALinksByNum(context);
		if(context!=null && context.callback!=null) context.callback(config.currPageNo);
	};	
}