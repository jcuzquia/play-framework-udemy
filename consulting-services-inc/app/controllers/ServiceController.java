package controllers;

import java.util.List;

import constants.ModeConst;
import models.Service;
import play.Logger;
import play.data.Form;
import play.mvc.Result;
import views.html.services.info;
import views.html.services.list;

public class ServiceController extends play.mvc.Controller{

	private static Logger.ALogger logger = Logger.of(ServiceController.class);
	
	private static final Form<Service> sServiceForm = Form.form(Service.class);
	
	public Result list(){
		List<Service> allServices = Service.findAll();
		return ok(list.render(allServices));
	}
	
	public Result addService(){
		List<Service> allServices = Service.findAll();
		return ok(info.render(sServiceForm, ModeConst.ADD));
	}
	
	public Result save(String mode){
		Form<Service> ourForm = Form.form(Service.class).bindFromRequest();
		Logger.debug("ERRORS: " + ourForm.errors().toString());
		if(ourForm.hasErrors()){
			logger.debug("Error");
			return badRequest(info.render(ourForm, ModeConst.ADD));
		}
			
		Service service = ourForm.get();
		if(service != null) {
			if(ModeConst.ADD.equals(mode)){
				if(Service.exists(service.code)){
					flash("errorsFound", "Service code already exists");
					return badRequest(info.render(ourForm, mode));
				}
				service.save();
				return redirect(routes.ServiceController.addService());
			} else if (mode.startsWith(ModeConst.EDIT)){
				service.update();
				return redirect(routes.ServiceController.editService(service.code));
			}
			
		}
		return badRequest(info.render(ourForm, mode));
	} 
	
	
	public Result editService (String code){
		Service service = Service.retrieve(code);
		Form<Service> ourForm = Form.form(Service.class).fill(service);
		return ok(info.render(ourForm, ModeConst.EDIT + ": " + service.code));
	}
	
	
	public Result delete(String code){
		Service service = Service.retrieve(code);
		Logger.debug("Executing deletion");
		if(service == null){
			return notFound(code + " is not on file");
		}
		service.delete();
		return redirect(routes.ServiceController.list());
	}
}