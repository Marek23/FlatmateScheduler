package pl.com.flat.api;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.com.flat.model.Payment;
import pl.com.flat.model.Settlement;
import pl.com.flat.repository.PaymentRepository;
import pl.com.flat.repository.ResidentRepository;
import pl.com.flat.repository.SettlementRepository;
import pl.com.flat.repository.StlTypeRepository;
import pl.com.flat.security.IFacade;

import static pl.com.flat.util.Content.content;
import static pl.com.flat.util.Message.alertSuccess;

@Controller
@RequestMapping("settlements")
public class SettlementApi {
	@Autowired IFacade              facade;
	@Autowired ResidentRepository   resRep;
	@Autowired SettlementRepository stlRep;
	@Autowired StlTypeRepository    typesRep;
	@Autowired PaymentRepository    payRep;

	@RequestMapping("/all")
	public String all(Model model) {
		model.addAttribute("settlements", stlRep.findAll());

		return content(model, "settlements-all");
	}

	@RequestMapping("/add")
	public String addSettlement(Model model, @ModelAttribute("s") Settlement s)
	{
		var logged       = facade.currentResident();
		var loggedId     = logged.getId();
		var allowedTypes = typesRep.findByRoleIn(logged.getRoles());

		model.addAttribute("types", allowedTypes);
		model.addAttribute("s",     new Settlement());

		if (s.getAmount() == null || s.getType() == null)
			return content(model, "settlements-add");

		s.setResident(logged);

		var settlement     = stlRep.save(s);
		var residents      = resRep.findAll();
		var payments       = new ArrayList<Payment>();
		var payPerResident = settlement.getAmount().divide(
			new BigDecimal(resRep.count()),
			2, RoundingMode.CEILING
		);
		residents.forEach(r -> {
			var p = new Payment(r, settlement, payPerResident);
			if (r.getId() == loggedId)
				p.setPayed();
			payments.add(p);
		});

		payRep.saveAll(payments);

		alertSuccess(model, "Poprawnie dodano wydatek");

		return content(model, "settlements-add");
	}
}
