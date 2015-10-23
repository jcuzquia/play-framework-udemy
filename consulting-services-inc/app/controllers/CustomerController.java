package controllers;

import java.util.List;

import constants.ModeConst;
import models.Customer;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class CustomerController extends Controller{

	private static Logger.ALogger logger = Logger.of(CustomerController.class);
	
	public Result list(){
		List<Customer> customerList = Customer.findAll();
		return ok(list.render(customerList));
	}
	
	public Result addCustomer() {
		Form<Customer> form = Form.form(Customer.class);
		return ok(info.render(form, ModeConst.ADD));
	}
	
	public Result save(String mode){
		Form<Customer> myForm = Form.form(Customer.class).bindFromRequest();
		if(myForm.hasErrors()){
			logger.debug("failed Validation");
			return badRequest(info.render(myForm, mode));
		}
		Customer customer = myForm.get();
		if(customer != null){
			if(ModeConst.ADD.equals(mode)){
				customer.save();
			}else if (mode.startsWith(ModeConst.EDIT)){
				customer.update();
				return redirect(routes.CustomerController.editCustomer(customer.id));
			}
		}
		return badRequest(info.render(myForm, mode));
	}
}
