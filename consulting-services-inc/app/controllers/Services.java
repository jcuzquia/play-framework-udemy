package controllers;

import java.util.List;

import models.Service;
import play.Logger;
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
		
		return ok(info.render(sServiceForm, "Adding"));
	}
	
//	public Result save(){
//		Form<Service> ourForm = sServiceForm.bindFromRequest();
//		Logger.debug("ERRORS: " + ourForm.errors().toString());
//		if(!ourForm.hasErrors()){
//			Service service = ourForm.get();
//			if(Service.exists(service.code)){
//				service.update();
//			} else {
//				service.save();
//			}
//			return redirect(routes.Services.addService());
//		}else{
//			//we have an error, show it on the screen
//			flash("errors found", "Please fix the errors on the page");
//			return badRequest(info.render(ourForm));
//		}
//	}
	
	public Result save(String mode){
		Logger.debug("Mode is " + mode);
		Form<Service> ourForm = sServiceForm.bindFromRequest();
		Logger.debug("ERRORS: " + ourForm.errors().toString());
		if(!ourForm.hasErrors()){
			Service service = ourForm.get();
			if(mode.equalsIgnoreCase("Adding")){
				Logger.debug("CODE IS [" + service.code + "]");
				if(service.code.equals("") || Service.exists(service.code)){
					flash("errorsFound", "Empty primary key or primary key already exists");
					return badRequest(info.render(ourForm, "Adding"));
				}
				service.save();
			}else {
				Logger.debug("code: " + service.code + " amount " + service.amount + " desc " + service.description);
				service.update();
			}
			return redirect(routes.Services.addService());
		} else {
			//show errors on the webpage
			flash("errorsFound", "Please fix the errors on the page");
			return badRequest(info.render(ourForm,mode));
		}
	}
	
	public Result info(String code){
		Service service = Service.retrieve(code);
		if(service == null){
			return notFound(code + " is not on file");
		}
		
		Form<Service> fillForm = sServiceForm.fill(service);
		return ok(info.render(fillForm, "Editing: " + service.description));
		
	}
}
