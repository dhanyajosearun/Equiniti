/*
 *  PANCREDIT SYSTEMS LTD
 *  (C) Copyright PanCredit Systems Ltd 2021
 *
 *  COPYRIGHT NOTICE
 *  ---------------------------------
 *  The contents of this file are protected by copyright. Any unauthorised
 *  copying, duplication of its contents are in breach of the copyright.
 *
 *  Last Checked In By: $Author$
 *  Date Checked In:    $Date$
 *  Name and Version:   $Id$
 *
 *  Log messages:       $Log$
 * 
 */
package com.pancredit.api;

//Import required java libraries
import java.io.*;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.servlet.*;
import javax.servlet.http.*;
import com.google.gson.Gson;
import com.pancredit.model.PanCredit;
import com.pancredit.service.PanCreditService;

public class ApiServlet extends HttpServlet {  

	private String id; 
	private int	applicationId; 
	private String type; 
	private String summary; 
	private float amount; 
	private String postingDate; 
	private boolean isCleared;  
	private String clearedDate;
	private boolean invalidAppIdFlag;
	private boolean invalidAmountFlag;
	private Gson gson=new Gson();
	private List <PanCredit> panCreditLst=new ArrayList<>();
	private String pancreditJSON;
	private PanCreditService panCreditService=new PanCreditService();

	public void init(ServletConfig servletConfig) throws ServletException {

		this.id  = servletConfig.getInitParameter("Id");
		this.applicationId  = Integer.parseInt(servletConfig.getInitParameter("ApplicationId"));//servletConfig.getInitParameter("ApplicationId");
		this.type  = servletConfig.getInitParameter("Type");
		this.summary  = servletConfig.getInitParameter("Summary");
		this.amount  = Float.parseFloat(servletConfig.getInitParameter("Amount"));
		this.postingDate  = servletConfig.getInitParameter("PostingDate");
		this.isCleared  =  Boolean.parseBoolean(servletConfig.getInitParameter("IsCleared"));
		this.clearedDate  = servletConfig.getInitParameter("ClearedDate");
	}

	/*
	 * Display json file
	 * if Id is provided shows just that record
	 * if id is not available shows entire file
	 * In case of invalid Id shows invalid id message 
	 * */
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		response.setContentType("text/html");	// Set response content type
		getParamFromUrl(request,response);	//getting parameters from URL
		PrintWriter out = response.getWriter();
		getPanCreditJSONStr();
		if(id==null) {
			out.println( "Enter ID in Url: ");
			out.println("<h1>" + pancreditJSON + "</h1>");
		}else {
			boolean idAvailable=false;
			for (PanCredit p:panCreditLst) {
				if(p.getId().equals(id)) {
					String pancredit=gson.toJson(p);
					idAvailable=true;
					out.println("<h1>" + pancredit + "</h1>");
				}
			}
			if(!idAvailable) {
				out.println("Invalid ID: Enter the correct ID");
				out.println("<h1>" + pancreditJSON + "</h1>");
			}
		}
		out.close();
	}

	/*
	 * Delete json file based on their ID
	 * 
	 * */
	public void doDelete(HttpServletRequest pReq, HttpServletResponse pResp) throws ServletException, IOException
	{
		PrintWriter out = pResp.getWriter();
		getParamFromUrl(pReq,pResp);		
		pResp.setContentType("text/html");	// Set response content type
		getPanCreditJSONStr();
		if(id==null) {
			out.println( "Enter an ID to delete the record: ");
			out.println("<h1>" + pancreditJSON + "</h1>");
		}else {
			boolean idAvailable=false;
			for (PanCredit p:panCreditLst) {
				if(p.getId().equals(id)) {
					String pancredit=gson.toJson(p);
					panCreditLst.remove(p);
					idAvailable=true;
					out.println("<h1> Removed Record :" + pancredit + "</h1>");
					pancreditJSON=gson.toJson(panCreditLst);
					panCreditService.setPanCreditLst(pancreditJSON);
				}
			}
			if(!idAvailable) {
				out.println("Invalid ID: Enter the correct ID");
			}
		}
		out.close();
	}

	/*
	 * Update json file based on their ID
	 * 
	 * */
	public void doPut(HttpServletRequest pReq, HttpServletResponse pResp) throws ServletException, IOException
	{
		PrintWriter out = pResp.getWriter();
		getParamFromUrl(pReq,pResp);		
		pResp.setContentType("text/html");	// Set response content type
		getPanCreditJSONStr();
		if(id==null) {
			out.println( "Enter a valid ID to update the record: ");
		}else {
			boolean idAvailable=false;
			numberFormatValidator(pResp); //Checks ApplicationId and Amount is in respective formats
			for (PanCredit p:panCreditLst) {
				if(p.getId().equals(id)) {
					int index = panCreditLst.indexOf(p);
					if(applicationId != 0)
						p.setApplicationId(applicationId);
					if(type!=null)
						p.setType(type);
					if(summary!=null)
						p.setSummary(summary);
					if(amount!=0.0)
						p.setAmount(amount);
					if(postingDate!=null)
						p.setPostingDate(postingDate);
					if(isCleared!=p.isIsCleared())
						p.setIsCleared(isCleared);
					if(clearedDate!=null)
						p.setClearedDate(clearedDate);
					String pancredit=gson.toJson(p);
					panCreditLst.set(index, p);
					idAvailable=true;
					out.println("<h1> Updated Record :" + pancredit + "</h1>");
					pancreditJSON=gson.toJson(panCreditLst);
					panCreditService.setPanCreditLst(pancreditJSON);
				}
			}
			if(!idAvailable) {				
				out.println("Invalid ID: Enter the correct ID");
			}
		}	
		out.close();
	}

	/*
	 * Insert new record to joson file
	 * 
	 * */
	public void doPost(HttpServletRequest pReq, HttpServletResponse pResp) throws ServletException, IOException
	{
		PrintWriter out = pResp.getWriter();
		getParamFromUrl(pReq,pResp);
		pResp.setContentType("text/html");	// Set response content type
		PanCredit newPanCredit=new PanCredit();
		numberFormatValidator(pResp); //Checks ApplicationId and Amount is in respective formats
		getPanCreditJSONStr();
		boolean idExist=false;
		if(id!=null && id!="") {
			newPanCredit.setId(id);
			for (PanCredit p:panCreditLst) {
				if(p.getId().equals(id)) {
					idExist=true;
					out.println("<h1> Record already exist for this ID. Please update </h1>");
				}

			}
		}else {
			String randId = UUID.randomUUID().toString();
			newPanCredit.setId(randId);
		}
		if(!idExist) {
			if(!(applicationId==0&&summary==null&&amount==0.0&&postingDate==null&&isCleared==false&&clearedDate==null&&type==null)) {
			newPanCredit.setApplicationId(applicationId);
			newPanCredit.setType(type);
			newPanCredit.setSummary(summary);
			newPanCredit.setAmount(amount);
			newPanCredit.setPostingDate(postingDate);
			newPanCredit.setIsCleared(isCleared);
			newPanCredit.setClearedDate(clearedDate);	

			panCreditLst.add(newPanCredit);
			String pancreditJSON=gson.toJson(panCreditLst);
			String pancredit=gson.toJson(newPanCredit);
			out.println("<h1> New Record :" + pancredit + "</h1>");
			panCreditService.setPanCreditLst(pancreditJSON);
			}else {
				out.println("<h1> Provide details for New Record :</h1>");
			}
		}
		out.close();
	}


	private void getParamFromUrl(HttpServletRequest req, HttpServletResponse resp) {
		Enumeration params = req.getParameterNames();
		String paramName = null;
		String[] paramValues = null;
		id  = null;
		applicationId  = 0;
		type  = null;
		summary  = null;
		amount  = 0.0f;
		postingDate  = null;
		clearedDate  = null;
		invalidAppIdFlag =false;
		invalidAmountFlag=false;
		if(params!=null)//!Objects.isNull(params)
		while (params.hasMoreElements()) {
			paramName = (String) params.nextElement();
			paramValues = req.getParameterValues(paramName);

			for (int i = 0; i < paramValues.length; i++) {
				if(paramName.equals("Id")) {			   
					id=paramValues[i].toString();
				}
				else if(paramName.equals("ApplicationId")) {		
					try {
						applicationId=Integer.parseInt(paramValues[i].toString());
					}catch (NumberFormatException e) {
						invalidAppIdFlag=true;
						applicationId=0;
					} 
				}
				else if(paramName.equals("Type")) {			   
					type=paramValues[i].toString();
				}
				else if(paramName.equals("Summary")) {			   
					summary=paramValues[i].toString();
				}
				else if(paramName.equals("Amount")) {
					try {
						amount=Float.parseFloat(paramValues[i].toString());
					}catch (NumberFormatException e) {
						invalidAmountFlag=true;
						amount=0.0f;
					} 

				}
				else if(paramName.equals("PostingDate")) {			   
					postingDate=paramValues[i].toString();
				}
				else if(paramName.equals("IsCleared")) {			   
					isCleared= Boolean.parseBoolean(paramValues[i].toString());
				}
				else if(paramName.equals("ClearedDate")) {			   
					clearedDate=paramValues[i].toString();
				}
				else {
					System.out.println("Unknown Parameter "+paramName+ " with value "+paramValues[i].toString());
				}
			}
		}
	}
	private void getPanCreditJSONStr() {
		panCreditLst=panCreditService.getPanCreditLst();
		pancreditJSON=gson.toJson(panCreditLst);

	}
	private void numberFormatValidator(HttpServletResponse pResp) throws IOException {
		PrintWriter out = pResp.getWriter();
		if(invalidAppIdFlag)
			out.println("Incorrect Application ID Format: Enter the valid id in Number format");
		if(invalidAmountFlag)
			out.println("Incorrect Amount Format: Enter the valid amount");;
	}
}
