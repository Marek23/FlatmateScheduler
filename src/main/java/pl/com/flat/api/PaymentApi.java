package pl.com.flat.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.com.flat.model.Status;
import pl.com.flat.repository.PaymentRepository;
import pl.com.flat.repository.ResidentRepository;
import pl.com.flat.security.IFacade;

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

		var payments = payRep.findPaymentsForResidentId(current);
		payments.forEach(p -> {
			p.setResident(resRep.findById(p.getId().getResidentId()).get());
		});

		model.addAttribute("payments", payments);
		return "home/all-payments";
	}

	@RequestMapping("/to-pay")
	public String toPay(Model model) {
		var current = facade.currentResident().getId();

		var toPay = payRep.findPaidByResidentIdAndStatus(current, Status.NEW);
		toPay.forEach(p -> {
			p.setResident(resRep.findById(p.getId().getResidentId()).get());
		});

		model.addAttribute("toPay", toPay);
		return "home/to-pay";
	}
}
