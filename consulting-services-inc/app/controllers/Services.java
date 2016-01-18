package controllers;

import java.util.List;

import models.Service;
import play.data.Form;
import play.mvc.Result;
import views.html.services.info;
import views.html.services.list;

public class Services extends play.mvc.Controller{

	private static final Form<Service> sServiceForm = Form.form(Service.class);
	
	public Result list(){
		List<Service> allServices = Service.findAll();
		return ok(list.render(allServices));
	}
	
	public Result addService(){
		
		return ok(info.render(sServiceForm));
	}
	
	public Result save(){
		Form<Service> ourForm = sServiceForm.bindFromRequest();
		if(!ourForm.hasErrors()){
			Service service = ourForm.get();
			service.save();
			return redirect(routes.Services.addService());
		}
		
		return redirect(routes.Services.list());
	}
}
