

package com.todo.controller;




import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import javax.jdo.PersistenceManager;
import javax.servlet.ServletException;
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

import javax.jdo.Query;
import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONObject;
import com.google.apphosting.utils.remoteapi.RemoteApiPb.Response;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.todo.util.*;

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
	 public String validatelogin(HttpServletRequest request,HttpServletResponse response, ModelMap model) throws IOException, ServletException {
	 String email = request.getParameter("email");

	 String password= request.getParameter("password");
	 
	 
	 if(!(email.equals("admin") && password.equals("admin"))){
		 PrintWriter out = response.getWriter(); 
		 response.setContentType("text/html"); 
		 out.println("<script type=\"text/javascript\">"); 
		 out.println("alert('the session did time out, please reconnect');"); 
		 out.println("logout();");
		 out.println("</script>"); 
		 request.getRequestDispatcher("hello").forward(request, response); 
	 }else{
	 

	 System.out.println(email+password);
	 PersistenceManager pm = PMF.get().getPersistenceManager();
	 System.out.println(" name"+password+"email "+email+" password "+password+"");
	 Pojo c = new Pojo();
	
	 c.setemail(email);
	 c.setpassword(password);

	
	 pm.makePersistent(c);
	 return "list";

     
//	 javax.jdo.Query q = pm.newQuery(Pojo.class);
//	 q.setFilter("email == emailParameter && password==passwordParameter");
//	 q.declareParameters("String emailParameter,String passwordParameter");
//	
//	 
//	 List<Pojo> results = (List<Pojo>) q.execute(email,password);
//	 System.out.println("hello"+results);
//	 
//
//if(!results.isEmpty())
//{
//	 if(email.equals("admin@admin.com"))
//	 {
//   System.out.println("not");
//   return "/list.jsp";
//	}
//	 else
//	 {
//		 return "welcome";
//	 }
//}
//else
//{
//	 return "login";
//
//}
	 }
	return null;	 }

	
	 	 @RequestMapping(value = "/addsave", method = RequestMethod.POST)
	 	@ResponseBody	
	 	 public String save(HttpServletRequest request) {

	 			String data = (String)request.getParameter("data");
	 			System.out.println(" data"+data);
	 			
	 			//long first14 =(long) (Math.random() * 100000000000000L);

	 		    //System.out.println("Randon "+first14); 
	 		
	 			
	 			/*ToDo c = new ToDo();
	 			c.setdata(data);
	 			c.setKey(first14);*/
	 			
	 			ToDoList d = new ToDoList();
	 			d.setData(data);
	 			

	 			PersistenceManager pm = PMF.get().getPersistenceManager();
	 			try {
	 			pm.makePersistent(d);
	 			} finally {
	 			pm.close();
	 			}
	         return data;
	 			}

	 	 
	 	 
	 	 @RequestMapping(value = "/retrieve", method = RequestMethod.GET)
	 		@ResponseBody	
	 		 public String retrieve(HttpServletRequest request) {
	 				System.out.println("retreived data");
	 				PersistenceManager pm = PMF.get().getPersistenceManager();
	 				Query q = pm.newQuery(ToDoList.class);
	 				List<ToDoList> results =  (List<ToDoList>) q.execute();
	 				
	 				Gson obj = new Gson();
	 				String ret = obj.toJson(results);
	 				
	 				return ret;
	 				
//	 				try {
//	 					results = (List<ToDoList>) q.execute();
//	 					if (results.size() > 0) {
//	 						// good for listing
//	 						Gson gson = new Gson();
//	 						output = gson.toJson(results);
//	 					}
//	 				}catch(Exception e) {
//	 					e.printStackTrace();
//	 					return output;
//	 				} 
//	 				finally {
//	 					q.closeAll();
//	 					pm.close();
//	 				}
//	 				return output;

	 	 }
	 	 @RequestMapping(value = "/destroy", method = RequestMethod.POST)
	 		public @ResponseBody String delete(@RequestParam long key,
	 				HttpServletRequest request, ModelMap model) {
	               System.out.print("deleting");
	 			PersistenceManager pm = PMF.get().getPersistenceManager();

	 			try {

	 				ToDoList c = pm.getObjectById(ToDoList.class, key);
	 				pm.deletePersistent(c);

	 			} finally {
	 				pm.close();
	 			}

	 			// return to list
	 			return "deleted";

	 		}

	  @RequestMapping(value = "/update", method = RequestMethod.POST)
	 	public String update(HttpServletRequest request, @RequestBody String data) {
	 	 
	 	 System.out.println(data);
	 	 //JSONObject	obj=	new JSONObject(data);	
	 	 Gson gson = new Gson();
	 	 TypeToken<List<ToDoList>> list = new TypeToken<List<ToDoList>>(){};
	 	 
	 	 ToDoList myList = gson.fromJson(data, ToDoList.class);
	 	 System.out.println(myList.getData());

////	 		String key = request.getParameter("data");
	
////	 		String mydata = request.getParameter("mydata");

	 	PersistenceManager pm = PMF.get().getPersistenceManager();
	 	try {

	 			ToDoList c= pm.getObjectById(ToDoList.class, myList.getKey());

	 			c.setData(myList.getData());
	 		pm.makePersistent(c);

	 		} finally {
	 			pm.close();
	 		}
	 		
	 	

	 	// return to list
	 	return "list";

	 	}

	 	 
	 }
	 	 

	 




		
			
