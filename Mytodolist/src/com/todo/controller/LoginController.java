
package com.todo.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.todo.util.PMF;

@Controller
public class LoginController {

	@RequestMapping("/hello")
	public String hello(ModelMap model) {
		model.addAttribute("message", "Hello Spring MVC Framework!");

		return "hello";

	}

	
	@RequestMapping(value = "/loginvalidate", method = RequestMethod.POST)
	public String validatelogin(HttpServletRequest request, HttpServletResponse response,ModelMap model)
			throws IOException, ServletException {

		String email = request.getParameter("email");

		String password = request.getParameter("password");
		
	     HttpSession session = request.getSession(true);
		 session.setAttribute("email", email);



		if ( email == null || email.isEmpty() || password == null || password.isEmpty()) {
			
			PrintWriter out1 = response.getWriter();
			
			response.setContentType("text/html");
			out1.println("<script type=\"text/javascript\">");
			out1.println("alert('Enter the login detail');");
			out1.println("logout();");
			out1.println("</script>");
			request.getRequestDispatcher("hello").forward(request, response);
		} else {

			PersistenceManager pm = PMF.get().getPersistenceManager();
			Pojo c = new Pojo();
			
			

			c.setemail(email);
			c.setpassword(password);

			pm.makePersistent(c);

			return "list";
		}
		return null;
	}

	@RequestMapping(value = "/addsave", method = RequestMethod.POST)
	
	public @ResponseBody String save(HttpServletRequest request, HttpServletResponse response,HttpSession session) throws IOException {

		
		
		Gson json = new Gson();
		
		String data = (String) request.getParameter("data");
		System.out.println(" data" + data);
		
		
		
		
	       
	       
		ToDoList d = new ToDoList();
		d.setData(data);

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			
			pm.makePersistent(d);
		} finally {
			pm.close();
		}
		
		System.out.println(json.toJson(d));
	       
	       return json.toJson(d);
	}

	@RequestMapping(value = "/retrieve", method = RequestMethod.GET)
	@ResponseBody
	public String retrieve(HttpServletRequest request) {
		
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query q = pm.newQuery(ToDoList.class);
		List<ToDoList> results = (List<ToDoList>) q.execute();
		
		System.out.println("Todo List  :: "+new Gson().toJson(results));
		
		Gson obj = new Gson();
		String ret = obj.toJson(results);

		return ret;

		

	}

	@RequestMapping(value = "/destroy", method = RequestMethod.POST)
	public @ResponseBody String delete(@RequestParam long key, HttpServletRequest request, ModelMap model) {
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
	public  @ResponseBody String update(HttpServletRequest request, @RequestBody String data) {

		System.out.println(data);
		Gson gson = new Gson();
		TypeToken<List<ToDoList>> list = new TypeToken<List<ToDoList>>() {
		};

		ToDoList myList = gson.fromJson(data, ToDoList.class);
		System.out.println(myList.getData());
		Gson obj2 = new Gson();
		String ret = "";


		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {

			ToDoList c = pm.getObjectById(ToDoList.class, myList.getKey());

			c.setData(myList.getData());
			pm.makePersistent(c);
			ret = obj2.toJson(c);

		} finally {
			pm.close();
		}

		// return to list
		
		

		return ret;

		

	}

	 @RequestMapping(value="/logout", method = RequestMethod.POST)
     public String logout(HttpServletRequest request,HttpSession session ) {

session.removeAttribute("email");
		 System.out.println("sessions");
        session.invalidate();
        return "hello";
     }
}
