package models;

import java.util.Date;
import java.util.List;

import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.avaje.ebean.Expr;
import com.avaje.ebean.Model;

import play.data.validation.Constraints;
import play.data.format.Formats;

public class Invoice extends Model{

	@Id
	public Long id;
	
	public String invoiceId;
	
	@Constraints.Required(message = "select.message")
	@ManyToOne(optional = false, fetch = FetchType.LAZY)
	@JoinColumn(name = "customer_id")
	public Customer customer;
	
	@Constraints.Required(message = "required.message")
	@Temporal(TemporalType.DATE)
	@Formats.DateTime(pattern = "dd/MM/yyyy")
	public Date date;
	
	
	
	private static Finder<String, Invoice> find = new Model.Finder<String, Invoice>(Invoice.class);
	
	public static List<Invoice> findAll(){
		return find.fetch("customer").orderBy("id").findList();
	}
	
	public static List<Invoice> findAll(String pattern){
		return find.fetch("customer").where().or(
				Expr.ilike("invoiceId", "%" + pattern + "%"), 
				Expr.ilike("customer.name", "%" + pattern + "%")).findList();
	}
	
	public static Invoice retrive(Long id){
		return find.byId(id.toString());
	}
	
	public static boolean exists(Long id){
		return(find.where().eq("id", id).findRowCount() == 1) ? true : false;
	}
}
