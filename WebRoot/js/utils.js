/***
 * 自定义ajax，里面封装了对应的处理请求
 * @param method “GET”或“POST”
 * @param url 请求路径
 * @param isAyn 是否异步加载，默认是true（异步）
 * @param fun(rv) 回调函数，也即请求结束后的回调函数，可以用来执行相应的动作,rv为相应数据
 * @param values 要发送到服务器的数据
 */
function ajaxForMy(method,url,isAyn,fun,values){
	var xmlhttprequest = null;
	if(isAyn == null || isAyn == "undefined") isAyn = true;
	try{
		if(window.XMLHttpRequest){
			xmlhttprequest = new XMLHttpRequest();
		}else{
			xmlhttprequest = new ActiveXObject("Microsoft.XMLHttp");
		}
		xmlhttprequest.open(method, url,isAyn);
		xmlhttprequest.send(values);
		xmlhttprequest.onreadystatechange = function(){
			if(xmlhttprequest.readyState == 4){
				if(xmlhttprequest.status == 200 ){
				    if(fun != null){
				    	var rv = eval("("+xmlhttprequest.responseText+")");
				    	fun(rv);
				    }
				}
			}
		};
	}catch(ex){
		throw Error(ex.message);
	}	
}