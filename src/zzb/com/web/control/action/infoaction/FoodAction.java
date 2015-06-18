package zzb.com.web.control.action.infoaction;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import zzb.com.base.QueryResult;
import zzb.com.domain.Food;
import zzb.com.domain.FoodType;
import zzb.com.service.FoodService;
import zzb.com.web.utils.WebFileUtils;
import zzb.com.web.utils.ReqParamsEncoding;

@Controller("foodAction")
public class FoodAction {
	@Resource(name="foodService") private FoodService fs;
	private HttpServletResponse response;
	private ReqParamsEncoding reqParams;
	/***
	 *显示: 食品类型管理界面 
	 * @return
	 */
	public String listUI(){		
		return "listUI";
	}
	/***
	 * 罗列所有的食品信息
	 */
	public void list(){
		try{
			response = ServletActionContext.getResponse();
			reqParams = (ReqParamsEncoding) ServletActionContext.getRequest().getAttribute("parameterMap");
			int page = reqParams.getParameter("page")== null ? 1:Integer.parseInt(reqParams.getParameter("page"));
			int maxResult = 10;
			int firstIndex = (page-1)*maxResult;
			QueryResult<Food> qr = fs.getScrollData(Food.class,firstIndex,maxResult);
			qr.setCount((qr.getCount()!=0 && qr.getCount() % maxResult == 0)? qr.getCount()/maxResult : (qr.getCount()/maxResult + 1) );
			response.getWriter().write(JSONObject.fromObject(qr).toString());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	/***
	 * 加载详细信息
	 */
	public void loadWholeInfo(){
		try {
			response = ServletActionContext.getResponse();
			reqParams = (ReqParamsEncoding) ServletActionContext.getRequest().getAttribute("parameterMap");
			int id = Integer.parseInt(reqParams.getParameter("index"));
			Food food = fs.findById(Food.class, id);
			response.getWriter().print(JSONObject.fromObject(food).toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/***
	 * 增加食品录入
	 * @return
	 */
	public String save(){
		Food food = new Food();
		food = recoderFood(food,ServletActionContext.getRequest());
		if(food != null){
			fs.save(food);
		}
		return "listUI";
	}
	/***
	 * 更新Food
	 * @return
	 * @throws FileUploadException 
	 */
	public String update() {
		HttpServletRequest request = ServletActionContext.getRequest();
		Food food = new Food();
		food = recoderFood(food, request);
		if(food != null){
			fs.update(food);
		}
		return "listUI";
	}
	/***
	 * 删除对象
	 */
	public void delete(){
		try {
			response = ServletActionContext.getResponse();
			reqParams = (ReqParamsEncoding) ServletActionContext.getRequest().getAttribute("parameterMap");
			String id = reqParams.getParameter("index");
			fs.delete(Food.class, Integer.parseInt(id));
			response.getWriter().write(JSONObject.fromObject("{\"m\":\"删除成功!\"}").toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	/***
	 * 构造Food对象
	 * @param request
	 * @return
	 */
	private Food recoderFood(Food food,HttpServletRequest request){
		try {
			DiskFileItemFactory diskFileItemFactory = new DiskFileItemFactory();
			ServletFileUpload fileUpload = new ServletFileUpload(diskFileItemFactory);
			@SuppressWarnings("unchecked") 
			/***
			 * 这里不能进行解析，因为在SerCoderFilter.java中就已经解析过了，
			 * 在进行解析的时候其实是解析已经解析过得内容,解析这种内柔就是为null
			 */
			List<FileItem> fileItems = fileUpload.parseRequest(request);
			if(!validate(fileItems)) return null;
			for(FileItem item : fileItems){
				if(item.isFormField()){
					String name = item.getFieldName().trim();
					String filedValue = new String(item.get(),"UTF-8");
					if("hidden".equals(name)){
						if(filedValue != null && !"0".equals(filedValue))
							food = fs.findById(Food.class, Integer.parseInt(filedValue));
					}else if("name".equals(name)) food.setName(filedValue);
					else if("type".equals(name)){
						FoodType type = new FoodType();
						type.setID(Integer.parseInt(filedValue));
						food.setType(type);
					}
					else if("price".equals(name)) food.setPrice(filedValue);
					else if("supplier".equals(name)) food.setSupplier(filedValue);
					else if("mount".equals(name)) food.setStore(Integer.parseInt(filedValue));
					else if("stock".equals(name))food.setStockTime(filedValue);
					else if("day".equals(name)) food.setDays(Integer.parseInt(filedValue));
					else if("brief".equals(name)) food.setBrief(filedValue);
				}else{
					if( item.getSize() > 0){
						String filepath = WebFileUtils.save2File(request, item);
						food.setImage(request.getServletContext().getContextPath()+filepath); // 注意，这里要放相对路径
					}else{
						food.setImage("");
					}
				}
			}
			return food;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private boolean validate(List<FileItem> fileItems){
		for(FileItem item : fileItems){
			if(item.isFormField()){
				String name = item.getFieldName();
				String value = item.getString();
				if("name".equals(name)){
					if(value.isEmpty() || value.length()>20) 
						return false;
				}
				else if("type".equals(name)){
					
				}else if("price".equals(name)){
					if(value.isEmpty()){
						if(!value.matches("^[1-9]{1,5}$"))
							return false;
					}
				}else if("supplier".equals(name)){
					if(value.isEmpty() || value.length()>40)
						return false;
				}else if("mount".equals(name)){
					if(value.isEmpty() || !value.matches("\\d")){
						return false;
					}
				}else if("stock".equals(name)){
					if(value.isEmpty()) return false;
				}else if("day".equals(name)){
					if(value.isEmpty() || !value.matches("\\d"))
						return false;
				}else if("brief".equals(name)){
					if(!value.isEmpty()){
						if(value.length()>200) return false;
					}
				}
			}
		}
		return true;
	}

}
