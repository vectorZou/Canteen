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
import zzb.com.domain.Users;
import zzb.com.service.UsersService;
import zzb.com.web.utils.ReqParamsEncoding;
import zzb.com.web.utils.WebFileUtils;

@Controller("usersAction")
public class UsersAction {
	@Resource(name="usersService") private UsersService usersService;
	private HttpServletResponse response;
	private ReqParamsEncoding reqParams;
	/***
	 * 页面导航
	 * @return
	 */
	public String listUI(){
		return "listUI";
	}
	/***
	 * 加载数据和分页
	 */
	public void list(){		
		try {
			this.response = ServletActionContext.getResponse();
			this.reqParams = (ReqParamsEncoding) ServletActionContext.getRequest().getAttribute("parameterMap");
			int page = reqParams.getParameter("page")== null ? 1:Integer.parseInt(reqParams.getParameter("page"));
			int maxResult = 10;
			int firstIndex = (page-1)*maxResult;
			QueryResult< Users> qr = usersService.getScrollData(Users.class,firstIndex,maxResult);
			qr.setCount(qr.getCount()!=0 && qr.getCount()% maxResult == 0 ? qr.getCount()/maxResult : (qr.getCount()/maxResult+1));
			this.response.getWriter().write(JSONObject.fromObject(qr).toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/***
	 * 加载记录，更加id
	 */
	public void loadInfo(){
		try {
			this.response = ServletActionContext.getResponse();
			this.reqParams = (ReqParamsEncoding) ServletActionContext.getRequest().getAttribute("parameterMap");
			String id = this.reqParams.getParameter("index");
			Users user = usersService.findById(Users.class, id);
			response.getWriter().write(JSONObject.fromObject(user).toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/***
	 * 保存数据
	 */
	public String save(){
		try{
			response = ServletActionContext.getResponse();
			HttpServletRequest request = ServletActionContext.getRequest();
			Users user = new Users();
			user.setImage("");
			user = record2Supplier(user,request);
			if(user != null){
				if(user.getId() != null && !"".equals(user.getId())){
					usersService.update(user);
				}else{
					user.setId(UUID.randomUUID().toString());
					usersService.save(user);
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
			String id= reqParams.getParameter("index");
			usersService.delete(Users.class, id);
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
	private Users record2Supplier(Users user,HttpServletRequest request){
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
						if(value !=null && !"".equals(value))	user = usersService.findById(Users.class, value);
					}else if("username".equals(name))	user.setUsername(value);
					else if("password".equals(name))	user.setPassword(value);
					else if("name".equals(name))	user.setName(value);
					else if("gender".equals(name)) 	user.setGender(Integer.parseInt(value));
					else if("birthday".equals(name)) 	user.setBirthday(value);
					else if("telephone".equals(name)) 	user.setTelephone(value);
					else if("unit".equals(name)) 	user.setUnit(value);
				}else{
					if(fileItem.getSize()>0)	user.setImage(WebFileUtils.save2File(request, fileItem));
				}
			}
			return user;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	private boolean validate(List<FileItem>  fileItems){
		for(FileItem item : fileItems){
			if(item.isFormField()){
				String name = item.getFieldName();
				String value = item.getString();
				if("name".equals(name) || "username".equals(name) || "birthday".equals(name)) {
					if (value.isEmpty() || "".equals(value) ) 
						return false;
				}else if("gender".equals(name)) {
					if(value.isEmpty() || !value.matches("^[01]$"))
						return false;
				}else if("telephone".equals(name)) {
					if(value.isEmpty() || !value.matches("^\\d{8,11}$"))
						return false;
				}
			}
		}
		return true;
	}
}
