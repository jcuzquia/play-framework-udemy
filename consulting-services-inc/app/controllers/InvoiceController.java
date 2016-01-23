package controllers;

import java.util.List;

import constants.ModeConst;
import models.Invoice;
import models.InvoiceDetail;
import models.InvoiceSearchForm;
import play.Logger;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.invoice.list;
import views.html.invoice.info;
import views.html.invoice.list_detail;

public class InvoiceController extends Controller {
	
	private static Logger.ALogger logger = Logger.of(InvoiceController.class);
	
	public Result list(){
		List<Invoice> invoiceList = Invoice.findAll();
		Form<InvoiceSearchForm> myForm = Form.form(InvoiceSearchForm.class);
		return ok(list.render(invoiceList, myForm));
	}
	
	public Result search(){
		Form<InvoiceSearchForm> myForm = Form.form(InvoiceSearchForm.class).bindFromRequest();
		if(myForm.hasErrors()){
			List<Invoice> invoiceList = Invoice.findAll();
			return badRequest(list.render(invoiceList, myForm));
		}
		
		InvoiceSearchForm search = myForm.get();
		if(search.name != null && search.name.length() > 0){
			List<Invoice> invoiceList = Invoice.findAll(search.name);
			return ok(list.render(invoiceList, myForm));
		} else {
			List<Invoice> invoiceList = Invoice.findAll();
			return ok(list.render(invoiceList, myForm));
		}
	}
	
	public Result addInvoice(){
		Form<Invoice> myForm = Form.form(Invoice.class);
		return ok(info.render(myForm, ModeConst.ADD));
	}
	
	public Result save(String mode) {
        Form<Invoice> myForm = Form.form(Invoice.class).bindFromRequest();
        if(myForm.hasErrors()) {
            return badRequest(info.render(myForm, mode));
        }

        Invoice invoice = myForm.get();
        if(invoice != null) {
            if(ModeConst.ADD.equals(mode)) {
                invoice.save();
                invoice.invoiceId = invoice.id.toString();
                invoice.update();
                return redirect(routes.InvoiceController.editInvoice(invoice.id));
            } else if(ModeConst.EDIT.equals(mode)) {
                invoice.update();
                return redirect(routes.InvoiceController.editInvoice(invoice.id));
            }
        }

        return badRequest(info.render(myForm, mode));
    }
	
	public Result editInvoice(Long id){
		Invoice invoice = Invoice.retrieve(id);
		Form<Invoice> myForm = Form.form(Invoice.class).fill(invoice);
		return ok(info.render(myForm,  ModeConst.EDIT));
	}
	
	public Result delete(Long id){
		Invoice invoice = Invoice.retrieve(id);
		if(invoice !=null){
			invoice.delete();
		}
		return redirect(routes.InvoiceController.list());
	}
	
	public Result listInvoiceDetail(Long invoiceId){
		List<InvoiceDetail> detailList = InvoiceDetail.findAllByInvoice(invoiceId);
		return ok(list_detail.render(detailList, invoiceId));
	}
	
	public Result addInvoiceDetail(Long invoiceId){
		Form<InvoiceDetail> myForm = Form.form(InvoiceDetail.class);
		return ok(info_detail.render(myForm, invoiceId, ModeConst.ADD));
	}
	
	public Result editInvoiceDetail(Long invoiceId, Long invoiceDetailId){
		InvoiceDetail detail = InvoiceDetail.retrieve(invoiceDetailId);
		Form<InvoiceDetail> myForm = Form.form(InvoiceDetail.class).fill(detail);
		return ok(info_detail.render(myForm, invoiceId, ModeConst.EDIT));
	}
	
	public Result saveInvoiceDetail(Long invoiceId, String mode){
		Form<InvoiceDetail> myForm = Form.form(InvoiceDetail.class).bindFromRequest();
		if(myForm.hasErrors()){
			return badRequest(info_detail.render(myForm, invoiceId, mode));
		}
		
		InvoiceDetail detail = myForm.get();
		if(detail!=null){
			if(ModeConst.ADD.equals(mode)){
				detail.invoice = Invoice.retrieve(invoiceId);
				detail.save();
				return redirect(routes.InvoiceController.addInvoiceDetail(invoiceId));
			} else if (ModeConst.EDIT.equals(mode)){
				detail.invoice = Invoice.retrieve(invoiceId);
				detail.update();
				return redirect(routes.InvoiceController.editInvoiceDetail(invoiceId, detail.id));
			}
		}
		
		return badRequest(info_detail.render(myForm, invoiceId, mode));
	}
	
	public Result deleteInvoiceDetail(Long invoiceId, Long invoiceDetailId){
		InvoiceDetail detail = InvoiceDetail.retrieve(invoiceDetailId);
		
		if(detail != null){
			detail.delete();
		}
		return redirect(routes.InvoiceController.listInvoiceDetail(invoiceId));
	}
}
