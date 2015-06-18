function generatorTreeHTML(json){
	var html = "<ul>";
		html += "<li><span><i class='icon-folder-open'></i><label style='float:right;'>食品类型</label></span>";
			for(var key in json){
				html += "<ul>";
					if(json[key] == "[object Object]"){
						var v = "";
						for(var k in json[key]) v = k;
						html += "<li><span><i class='icon-folder-open'></i><a href=javascript:selectType('"+key+"','"+v+"');>"+key+"</a>";
						html += generatorTreeChildHTML(json[key][k]);
						html += "</li>";
					}else{
						html += "<li><span><i class='icon-folder-open'></i><a href=javascript:selectType('"+key+"','"+json[key]+"');>"+key+"</a></li>";
					}
				html +="</ul>"
			}
		html += "</li>"
	html += "</ul>";
	return html;
}

function generatorTreeChildHTML(json){
	var html = "<ul>";
		for(var key in json){
			if(json[key] == "[object Object]"){
					var v = "";
						for(var k in json[key]) v = k;
						html += "<li><span><i class='icon-minus-sign'></i><a href=javascript:selectType('"+key+"','"+v+"');>"+key+"</a>";
						html += generatorTreeChildHTML(json[key][k]);
						html += "</li>";
			}else{
				html += "<li><span><i class='icon-leaf'></i><a href=javascript:selectType('"+key+"','"+json[key]+"');>"+key+"</a></li>";
			}
		}
	html += "</ul>";
	return html;
}