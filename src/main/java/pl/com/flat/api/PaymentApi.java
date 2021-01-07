package pl.com.flat.api;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.com.flat.model.PaymentId;
import pl.com.flat.model.PaymentProjection;
import pl.com.flat.model.Status;
import pl.com.flat.repository.PaymentRepository;
import pl.com.flat.repository.ResidentRepository;
import pl.com.flat.security.IFacade;

import static pl.com.flat.util.Content.content;
import static pl.com.flat.util.PageUtil.createPageNumbers;

@Controller
@RequestMapping("payments")
public class PaymentApi {
	@Autowired IFacade facade;
	@Autowired PaymentRepository payRep;
	@Autowired ResidentRepository resRep;
	@Autowired EmailService mail;

	@RequestMapping("/all")
	public String all(Model model, @RequestParam("page") Optional<Integer> number) {
		var current  = facade.currentResident().getId();
		var pageNr   = number.orElse(1) - 1;
		var page     = payRep.findByResidentId(current, PageRequest.of(pageNr, 8));
		createPageNumbers(model, page);

		model.addAttribute("payments", page);
		model.addAttribute("total", payRep.getTotalAmount(facade.currentResident()));

		return content(model, "payments-all");
	}

	@RequestMapping("/to-pay")
	public String toPay(Model model) {
		var current = facade.currentResident().getId();
		var toPay   = payRep.findByResidentIdAndStatus(current, Status.Nieopłacona);

		model.addAttribute("toPay", toPay);

		return content(model, "payments-to-pay");
	}

	public Collection<PaymentProjection> paysProjections() {
		return payRep.findAllProjectedBy();
	}

	@RequestMapping(value="/pay", method=RequestMethod.POST)
	public String pay(
		@RequestHeader("Referer") String prev,
		@ModelAttribute("id") PaymentId id)
	{
		var p = payRep.findById(id);
		p.setPayed();
		payRep.save(p);

		var owner = p.getSettlement().getResident();
		var payer = p.getResident();

		mail.notify(owner, payer.getEmail() + " opłacił Twój wydatek: " + id.getSettlementId());

		return "redirect:" + prev;
	}
}
