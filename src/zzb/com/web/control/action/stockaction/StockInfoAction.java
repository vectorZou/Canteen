package zzb.com.web.control.action.stockaction;

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
import zzb.com.domain.StockInfo;
import zzb.com.service.StockInfoService;
import zzb.com.web.utils.ReqParamsEncoding;

@Controller("stockInfoAction")
public class StockInfoAction {
	@Resource(name="stockInfoService") private StockInfoService stiService;
	private HttpServletResponse response;
	private ReqParamsEncoding reqParams;
	/***
	 * 进货信息导航
	 * @return
	 */
	public String listUI(){
		return "listUI";
	}
	/***
	 * 加载信息
	 */
	public void list(){
		 try {
			response = ServletActionContext.getResponse();
			reqParams = (ReqParamsEncoding) ServletActionContext.getRequest().getAttribute("parameterMap");
			int page = reqParams.getParameter("page")== null ? 1:Integer.parseInt(reqParams.getParameter("page"));
			int maxResult = 10;
			int firstIndex = (page-1)*maxResult;
			QueryResult<StockInfo> qr = stiService.getScrollData(StockInfo.class,firstIndex,maxResult);
			qr.setCount(qr.getCount()!=0 && qr.getCount()% maxResult == 0 ? qr.getCount()/maxResult : (qr.getCount()/maxResult+1));
			response.getWriter().write(JSONObject.fromObject(qr).toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	/***
	 * 加载某条记录
	 */
	public void loadInfo(){
		try{
			response = ServletActionContext.getResponse();
			reqParams = (ReqParamsEncoding) ServletActionContext.getRequest().getAttribute("parameterMap");
			String sId= reqParams.getParameter("index");
			StockInfo stock = stiService.findById(StockInfo.class, sId);
			response.getWriter().write(JSONObject.fromObject(stock).toString());
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
			StockInfo stock = new StockInfo();
			stock = record2Supplier(stock,request);
			if(stock != null){
				if(stock.getId() != null && !"".equals(stock.getId())){
					stiService.update(stock);
				}else{
					stock.setId(UUID.randomUUID().toString());
					stiService.save(stock);
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
			stiService.delete(StockInfo.class, sId);
			response.getWriter().write(JSONObject.fromObject("{\"o\":\"删除成功！\"}").toString());
		}catch(Exception e){
			throw new RuntimeException(e);
		}
	}
	/***
	 * 将request中的数据封装到Supplier对象中
	 * @param stock
	 * @param request
	 * @return
	 */
	private StockInfo record2Supplier(StockInfo stock,HttpServletRequest request){
		try {
			DiskFileItemFactory disk = new DiskFileItemFactory();
			ServletFileUpload fileUpload = new ServletFileUpload(disk);
			@SuppressWarnings("unchecked")
			List<FileItem>  fileItems = fileUpload.parseRequest(request);
//			if(!validate(fileItems)) return null;
			for(FileItem fileItem : fileItems){
				if(fileItem.isFormField()){
					String name = fileItem.getFieldName();
					String value =new String(fileItem.getString().getBytes("ISO-8859-1"),"UTF-8");
					if("hidden".equals(name)){
						if(value !=null && !"".equals(value))	stock = stiService.findById(StockInfo.class, value);
					}else if("name".equals(name))	stock.setName(value);
					else if("price".equals(name)) 	stock.setPrice(Double.parseDouble(value));
					else if("count".equals(name)) 	stock.setCount(Integer.parseInt(value));
					else if("totalPrice".equals(name)) 	stock.setTotalPrice(Double.parseDouble(value));
					else if("stockTime".equals(name)) 	stock.setStockTime(value);
					else if("purchaser".equals(name)) 	stock.setPurchaser(value);
					else if("checkPerson".equals(name)) 	stock.setCheckPerson(value);
				}
			}
			return stock;
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
}
