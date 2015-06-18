package zzb.com.web.control.action.infoaction;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import zzb.com.base.QueryResult;
import zzb.com.domain.Supplier;
import zzb.com.service.SupplierService;
import zzb.com.web.utils.ReqParamsEncoding;
import zzb.com.web.utils.WebFileUtils;

@Controller("supplierAction")
public class SupplierAction {
	@Resource(name="supplierService") private SupplierService supplierService;
	private HttpServletResponse response;
	private ReqParamsEncoding reqParams;
	/***
	 * 界面导航
	 * @return
	 */
	public String listUI(){
		return "listUI";
	}
	/***
	 * 加载页面内容及分页
	 */
	public void list(){
		try{
			response = ServletActionContext.getResponse();
			reqParams = (ReqParamsEncoding) ServletActionContext.getRequest().getAttribute("parameterMap");
			int page = reqParams.getParameter("page")== null ? 1:Integer.parseInt(reqParams.getParameter("page"));
			int maxResult = 10;
			int firstIndex = (page-1)*maxResult;
			QueryResult<Supplier> qr = supplierService.getScrollData(Supplier.class,firstIndex,maxResult);
			qr.setCount(qr.getCount()!=0 && qr.getCount()% maxResult == 0 ? qr.getCount()/maxResult : (qr.getCount()/maxResult+1));
			response.getWriter().write(JSONObject.fromObject(qr).toString());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	/***
	 * 加载某条记录
	 */
	public void loadWholeInfo(){
		try{
			response = ServletActionContext.getResponse();
			reqParams = (ReqParamsEncoding) ServletActionContext.getRequest().getAttribute("parameterMap");
			String sId= reqParams.getParameter("index");
			Supplier supplier = supplierService.findById(Supplier.class, sId);
			response.getWriter().write(JSONObject.fromObject(supplier).toString());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	/***
	 * 保存对象
	 * @return
	 */
	public String save(){
		try{
			response = ServletActionContext.getResponse();
			HttpServletRequest request = ServletActionContext.getRequest();
			Supplier supplier = new Supplier();
			supplier.setImage("");
			supplier = record2Supplier(supplier,request);
			if(supplier != null){
				if(supplier.getsId() != null && !"".equals(supplier.getsId())){
					supplierService.update(supplier);
				}else{
					supplier.setsId(UUID.randomUUID().toString());
					supplierService.save(supplier);
				}
			}
			return "listUI";
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	/***
	 * 删除对象
	 */
	public void delete(){
		try{
			response = ServletActionContext.getResponse();
			reqParams = (ReqParamsEncoding) ServletActionContext.getRequest().getAttribute("parameterMap");
			String sId= reqParams.getParameter("index");
			supplierService.delete(Supplier.class, sId);
			response.getWriter().write(JSONObject.fromObject("{\"o\":\"删除成功！\"}").toString());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	/***
	 * 将request中的数据封装到Supplier对象中
	 * @param supplier
	 * @param request
	 * @return
	 */
	private Supplier record2Supplier(Supplier supplier,HttpServletRequest request){
		try {
			DiskFileItemFactory disk = new DiskFileItemFactory();
			ServletFileUpload fileUpload = new ServletFileUpload(disk);
			@SuppressWarnings("unchecked")
			List<FileItem>  fileItems = fileUpload.parseRequest(request);
			if(!validate(fileItems)) return null;
			for(FileItem fileItem : fileItems){
				if(fileItem.isFormField()){
					String name = fileItem.getFieldName();
					String value =new String(fileItem.getString().getBytes("ISO-8859-1"),"UTF-8");
					if("hidden".equals(name)){
						if(value !=null && !"".equals(value))	supplier = supplierService.findById(Supplier.class, value);
					}else if("name".equals(name))	supplier.setName(value);
					else if("gender".equals(name)) 	supplier.setGender(Integer.parseInt(value));
					else if("age".equals(name)) 	supplier.setAge(Integer.parseInt(value));
					else if("tel".equals(name)) 	supplier.setTelephone(value);
					else if("address".equals(name)) 	supplier.setAddress(value);
					else if("company".equals(name)) 	supplier.setCompany(value);
					else if("email".equals(name)) 	supplier.setEmail(value);
					else if("brief".equals(name)) 	supplier.setCompany_brief(value);
				}else{
					if(fileItem.getSize()>0)	supplier.setImage(WebFileUtils.save2File(request, fileItem));
				}
			}
			return supplier;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	private boolean validate(List<FileItem>  fileItems){
		for(FileItem item : fileItems){
			if(item.isFormField()){
				String name = item.getFieldName();
				String value = item.getString();
				if("name".equals(name)) {
					if (value.isEmpty() || "".equals(value) ) 
						return false;
				}else if("gender".equals(name)) {
					if(value.isEmpty() || !value.matches("^[01]$"))
						return false;
				}else if("age".equals(name)) {
					if(value.isEmpty() || !value.matches("^[1-9]{2}$"))
						return false;
				}else if("tel".equals(name)) {
					if(value.isEmpty() || !value.matches("^\\d{8,11}$"))
						return false;
				}else if("address".equals(name)){
					if(value.isEmpty() || value.length()>120) return false;
				}else if("email".equals(name)) {
					if(!value.isEmpty()){
						if(!value.matches("^([a-zA-Z0-9]{6,12}@[a-zA-Z0-9]{2,12}\\.com)(.cn)?$"))
							return false;
					}
				}else if ("company".equals(name)){
					if(!value.isEmpty()){
						if(value.length() >40) return false;
					}
				}else if("brief".equals(name)) {
					if(value.isEmpty() || value.length()>200){
						return false;
					}
				}
			}
		}
		return true;
	}
}
