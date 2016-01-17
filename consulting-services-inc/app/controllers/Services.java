package controllers;

import java.util.List;

import models.Service;
import play.data.Form;
import play.libs.Json;
import play.mvc.Result;
import views.html.services.info;

public class Services extends play.mvc.Controller{

	private static final Form<Service> sServiceForm = Form.form(Service.class);
	
	public Result list(){
		List<Service> allServices = Service.findAll();
		
		return ok(Json.toJson(allServices));
	}
	
	public Result addService(){
		
		return ok(info.render(sServiceForm));
	}
	
	public Result save(){
		Form<Service> ourForm = sServiceForm.bindFromRequest();
		Service service = ourForm.get();
		service.save();
		return redirect(routes.Services.addService());
	}
}
