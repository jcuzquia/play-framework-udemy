package controllers;

import play.mvc.Result;
import views.html.services.info;

public class Services extends play.mvc.Controller{

	public Result list(){
		return TODO;
	}
	
	public Result addService(){
		return ok(info.render());
	}
	
	public Result save(){
		return TODO;
	}
}
