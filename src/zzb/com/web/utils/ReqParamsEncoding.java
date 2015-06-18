package zzb.com.web.utils;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public final class ReqParamsEncoding {
	private Map<String,String[]> parametersMap;
	private String sCoder;
	private String dCoder;
	
	public ReqParamsEncoding(Map<String, String[]> parametersMap, String sCoder,String dCoder) {
		this.sCoder = sCoder;
		this.dCoder = dCoder;
		this.parametersMap = this.Params2Encoding(parametersMap, sCoder, dCoder);
	}

	public String getsCoder() {
		return sCoder;
	}

	public String getdCoder() {
		return dCoder;
	}

	public String getParameter(String key){
		String value = null;
		if(this.parametersMap.get(key) != null)
			value = this.parametersMap.get(key)[0];
		return value;
	}
	
	public String[] getParameterValues(String key){
		return this.parametersMap.get(key);
	}

	public Map<String, String[]> getParametersMap() {
		return parametersMap;
	}

	private  Map<String,String[]> Params2Encoding(Map<String,String[]> params,String sCoder,String dCoder) {
		Map<String,String[]> dParams = new HashMap<String,String[]>();
		try {
			for(Map.Entry<String, String[]> entry : params.entrySet()){
				String key = new String(entry.getKey().getBytes(sCoder),dCoder);
				String[] values = entry.getValue();
				for(int i=0, len=values.length;i<len;i++){
					values[i] = new String(values[i].getBytes(sCoder),dCoder);
				}
				dParams.put(key, values);
			}
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		return dParams;
	}
}
