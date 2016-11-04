
package com.todo.controller;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
public class Pojo {
	
	
@PrimaryKey
@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
private Key key;


@Persistent
private String password;


@Persistent
private String passwordagain;




@Persistent
private String name;
@Persistent
private String email;


public String getpassword(){
	
return password;
}
public void setpassword(String password){
this.password=password;
}

public String getemail(){
return email;

}
public void setemail(String email){
this.email=email;
}


public String getname(){
	
return password;
}
public void setname(String name){
this.password=password;
}


public String getpasswordagain(){
	
return password;
}
public void setpasswordagain(String password){
this.password=password;
}
}
















