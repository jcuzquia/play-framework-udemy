package controllers;

import models.Service;
import play.data.Form;
import play.mvc.Result;
import views.html.services.info;

public class Services extends play.mvc.Controller{

	private static final Form<Service> sServiceForm = Form.form(Service.class);
	public Result list(){
		return TODO;
	}
	
	public Result addService(){
		
		return ok(info.render(sServiceForm));
	}
	
	public Result save(){
		return TODO;
	}
}
