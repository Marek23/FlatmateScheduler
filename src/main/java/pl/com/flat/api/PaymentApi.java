package pl.com.flat.api;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import pl.com.flat.model.Payment;
import pl.com.flat.model.PaymentId;
import pl.com.flat.model.PaymentProjection;
import pl.com.flat.model.Status;
import pl.com.flat.repository.PaymentRepository;
import pl.com.flat.repository.ResidentRepository;
import pl.com.flat.security.IFacade;

import static org.apache.commons.lang3.time.DateFormatUtils.format;

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

		var toPay = payRep.findPaidByResidentIdAndStatus(current, Status.Nierozliczona);
		toPay.forEach(p -> {
			p.setResident(resRep.findById(p.getId().getResidentId()).get());
		});

		model.addAttribute("toPay", toPay);
		return "home/to-pay";
	}

	@ResponseBody
	@GetMapping(
		value    ="/settlement/{id}",
		produces = "application/json; charset=UTF-8"
	)
	public Collection<Payment> paysPerStl(@PathVariable("id") Long id) {
		var payments = payRep.findPaidsForSettlement(id);
		payments.forEach(p -> {
			p.setResident(resRep.findById(p.getId().getResidentId()).get());
		});
		return payments;
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
		PaymentId complexId = new PaymentId(
			resId,
			stlId
		);

		var payment = payRep.findById(complexId);
		if (payment != null)
		{
			payment.setPayed(
				format(new Date(), "yyyy-MM-dd")
			);
			payRep.save(payment);
		}

		return payment;
	}
}
