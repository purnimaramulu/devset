

package com.todo.controller;




import java.io.IOException;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.jdo.PersistenceManager;
import javax.servlet.http.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.appengine.api.datastore.Query;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.apphosting.utils.remoteapi.RemoteApiPb.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

//import com.todo.jdo.ToDoList;

//
//
@Controller
public class LoginController {
//
//
//	 
	 @RequestMapping("/hello")
   public String hello(ModelMap model) 
		{
	      model.addAttribute("message", "Hello Spring MVC Framework!");

	      return "hello";
	      
   }
	 @RequestMapping(value = "/loginvalidate", method = RequestMethod.POST)
	 public String validatelogin(HttpServletRequest request, ModelMap model) {

	 String email = request.getParameter("email");

	 String password= request.getParameter("password");

	 System.out.println(email+password);
	 PersistenceManager pm = PMF.get().getPersistenceManager();
	 System.out.println(" name"+password+"email "+email+" password "+password+"");
	 Pojo c = new Pojo();
	
	 c.setemail(email);
	 c.setpassword(password);

	
	 pm.makePersistent(c);

     
	 javax.jdo.Query q = pm.newQuery(Pojo.class);
	 q.setFilter("email == emailParameter && password==passwordParameter");
	 q.declareParameters("String emailParameter,String passwordParameter");
	
	 
	List<Pojo> results = (List<Pojo>) q.execute(email,password);
	 System.out.println("hello"+results);
	 
	 if(results.isEmpty())
		
	 {

	 System.out.println("purnima");
	 return "hello";
	 }
	 else
	 {
	 System.out.println("not");
	 return "hello";
	 
	 }}}

		
			
