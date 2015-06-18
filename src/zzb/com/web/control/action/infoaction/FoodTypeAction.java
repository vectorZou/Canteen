package zzb.com.web.control.action.infoaction;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import zzb.com.base.QueryResult;
import zzb.com.domain.FoodType;
import zzb.com.service.FoodTypeService;
import zzb.com.web.utils.ReqParamsEncoding;
import zzb.com.web.utils.TypeTree;

@Controller("foodTypeAction")
public class FoodTypeAction {
	@Resource(name="foodTypeService")  private FoodTypeService fts;
	private HttpServletResponse response ;
	private ReqParamsEncoding reqParams;
	/***
	 *显示: 食品类型管理界面 
	 * @return
	 */
	public String listUI(){		
		return "listUI";
	}
	/***
	 * 罗列: 数据库中所有的食品数据类型，有前台ajax请求过来，同时将数据封装成json格式回写到前台页面
	 */
	public void list(){
		try {
			response = ServletActionContext.getResponse();
			reqParams = (ReqParamsEncoding) ServletActionContext.getRequest().getAttribute("parameterMap");
			String qId = reqParams.getParameter("qId");
			int page = reqParams.getParameter("page")== null ? 1:Integer.parseInt(reqParams.getParameter("page"));
			
			String whereSql = "";
			if(qId == null) whereSql = " where o.parent is null";
			else whereSql = " where o.parent.ID="+qId;
			
			int maxResult = 10;
			int firstIndex = (page-1)*maxResult;
			QueryResult<FoodType> qr = fts.getScrollData(FoodType.class,firstIndex,maxResult,whereSql, null);
			if(page == 1)
				qr.setCount(qr.getCount()!=0 && qr.getCount() % maxResult == 0? qr.getCount()/maxResult : (qr.getCount()/maxResult + 1) );
			String jsonText= JSONObject.fromObject(qr).toString();
			response.getWriter().write(jsonText);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}			
	}
	/***
	 * 显示生成树页面
	 * @return
	 */
	public String treeUI(){
		return "treeUI";
	}
	/***
	 * 生成类型树
	 */
	public void tree(){
		try{
			response = ServletActionContext.getResponse();
			TypeTree tt = new TypeTree(fts);
			response.getWriter().write(JSONObject.fromObject(tt.getTreeMap()).toString());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		
	}
	/***
	 * 修改: 名称和备注
	 */
	public void update(){
		try {
			reqParams = (ReqParamsEncoding) ServletActionContext.getRequest().getAttribute("parameterMap");
			response = ServletActionContext.getResponse();
			String id = reqParams.getParameter("pk");
			String name = reqParams.getParameter("name");
			String note = reqParams.getParameter("note");
			FoodType foodType= fts.findById(FoodType.class, Integer.parseInt(id));
			foodType.setName(name);
			foodType.setNote(note);
			fts.update(foodType);
			response.getWriter().write(JSONUtils.valueToString("success"));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/***
	 * 保存食品子类型
	 */
	public void saveSub(){
		try {
			response = ServletActionContext.getResponse();
			reqParams = (ReqParamsEncoding) ServletActionContext.getRequest().getAttribute("parameterMap");
			String parentid =reqParams.getParameter("pd");
			String name = reqParams.getParameter("name");
			String note = reqParams.getParameter("note");
			if(parentid !=null && !"".equals(parentid.trim())){
				FoodType parent = fts.findById(FoodType.class, Integer.parseInt(parentid));
				parent.setChilds(parent.getChilds()+1);
				FoodType foodType = new FoodType(name,note);
				foodType.setParent(parent);
				fts.save(foodType);
				fts.update(parent);
				int count = parent.getChilds();
				response.getWriter().write("{\"count\":"+count+"}");
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	/***
	 * 保存食品类型
	 */
	public void save(){
		try {
			response = ServletActionContext.getResponse();
			reqParams = (ReqParamsEncoding) ServletActionContext.getRequest().getAttribute("parameterMap");
			String name = reqParams.getParameter("name");
			if(name != null && !"".equals(name)){
				String note = reqParams.getParameter("note");
				FoodType foodType = new FoodType(name,note);
				fts.save(foodType);
				response.getWriter().write("{\"o\":\"保存成功\"}");
			}else{
				response.getWriter().write("{\"o\":\"食品类型不能为空\"}");
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	/***
	 * 删除类型
	 */
	public void delete(){
		try {
			response = ServletActionContext.getResponse();
			reqParams = (ReqParamsEncoding) ServletActionContext.getRequest().getAttribute("parameterMap");
			String sId = reqParams.getParameter("td");
			int id = (sId != null && !"".equals(sId))? Integer.parseInt(sId) : 0;
			if(fts.findById(FoodType.class, id).getChilds()>0){
				response.getWriter().write("{\"o\":\"不能删除具有子类型的食品类型！\",\"f\":\"false\"}");
				return;
			}
			fts.delete(FoodType.class, id);
			response.getWriter().write("{\"o\":\"删除成功！\",\"f\":\"true\"}");
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
