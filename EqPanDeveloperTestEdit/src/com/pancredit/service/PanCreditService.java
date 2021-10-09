package com.pancredit.service;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.util.List;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pancredit.model.PanCredit;
/**
 *
 * @author DhanyaJ
 */
public class PanCreditService {

	public List<PanCredit> getPanCreditLst(){
		try{
			ObjectMapper mapper = new ObjectMapper();
			mapper.findAndRegisterModules();
			InputStream inputStream=this.getClass().getClassLoader().getResourceAsStream("/data.json");
			TypeReference <List<PanCredit>> typeReference=new TypeReference<List<PanCredit>>() {};
			List<PanCredit> panCreditLst=mapper.readValue(inputStream, typeReference);
			return panCreditLst;
		}catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (StreamReadException e) {
			e.printStackTrace();
			return null;
		} catch (DatabindException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} 

	}
	public void setPanCreditLst(String updatedJSON){
		String url=this.getClass().getClassLoader().getResource("/data.json").getPath();
		String UrlDecoded=URLDecoder.decode(url);//new String(url.getBytes("UTF-8"),"ASCII");
		try (FileWriter file = new FileWriter(UrlDecoded) )
		{
			file.write(updatedJSON);
			System.out.println("Successfully updated json object to file...!!");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
