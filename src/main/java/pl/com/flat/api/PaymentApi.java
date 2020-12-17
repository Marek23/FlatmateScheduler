package pl.com.flat.api;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.com.flat.model.Payment;
import pl.com.flat.model.PaymentProjection;
import pl.com.flat.model.Status;
import pl.com.flat.repository.PaymentRepository;
import pl.com.flat.repository.ResidentRepository;
import pl.com.flat.security.IFacade;

import static pl.com.flat.util.Content.content;

@Controller
@RequestMapping("payments")
public class PaymentApi {
	@Autowired
	IFacade facade;

	@Autowired
	PaymentRepository payRep;

	@Autowired
	ResidentRepository resRep;

	@RequestMapping("/all")
	public String all(Model model) {
		var current  = facade.currentResident().getId();
		var payments = payRep.findByResidentId(current);

		model.addAttribute("payments", payments);

		return content(model, "payments-all");
	}

	@RequestMapping("/to-pay")
	public String toPay(Model model) {
		var current = facade.currentResident().getId();
		var toPay   = payRep.findByResidentIdAndStatus(current, Status.Nieopłacona);

		model.addAttribute("toPay", toPay);

		return content(model, "payments-to-pay");
	}

	@ResponseBody
	@GetMapping(
		value    ="/settlement/{id}",
		produces = "application/json; charset=UTF-8"
	)
	public Collection<Payment> settlementPayments(@PathVariable("id") Long id) {
		return payRep.findBySettlementId(id);
	}

	@ResponseBody
	@GetMapping(
		value    ="/projections",
		produces = "application/json; charset=UTF-8"
	)
	public Collection<PaymentProjection> paysProjections() {
		return payRep.findAllProjectedBy();
	}

	@ResponseBody
	@GetMapping(
		value    ="/pay/{resId}/{stlId}",
		produces = "application/json; charset=UTF-8"
	)
	public Payment pay(@PathVariable("resId") Long resId, @PathVariable("stlId") Long stlId) {

		var payment = payRep.findByResidentIdAndSettlementId(resId, stlId);
		if (payment != null)
		{
			payment.setPayed();
			payRep.save(payment);
		}

		return payment;
	}
}
