package pl.com.flat.api;

import java.math.BigDecimal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.com.flat.model.Payment;
import pl.com.flat.model.Settlement;
import pl.com.flat.model.permissions.StlType;
import pl.com.flat.repository.PaymentRepository;
import pl.com.flat.repository.ResidentRepository;
import pl.com.flat.repository.SettlementRepository;
import pl.com.flat.repository.StlTypeRepository;
import pl.com.flat.security.IFacade;
import pl.com.flat.util.Text;

@Controller
@RequestMapping("settlements")
public class SettlementApi {
	@Autowired
	IFacade facade;

	@Autowired
	ResidentRepository resRep;

	@Autowired
	SettlementRepository stlRep;

	@Autowired
	StlTypeRepository stlTypeRep;

	@Autowired
	PaymentRepository payRep;

	@RequestMapping("/all")
	public String all(Model model) {
		var allowed = new ArrayList<StlType>();

		facade.currentResident().getRoles().forEach((r)-> {
			allowed.addAll(r.getStltypes());
		});

		model.addAttribute("stlTypes", allowed);

		model.addAttribute("settlements", stlRep.findAll());
		model.addAttribute("stl",    new Settlement());
		model.addAttribute("typeId", new Text());

		return "home/all-settlements";
	}

	@RequestMapping("/settled")
	public String settled(Model model) {
		var current = facade.currentResident().getId();
		model.addAttribute("settled", stlRep.findPaidSettlementsForResidentId(current));

		return "home/settled";
	}

	@PostMapping("/add")
	public String add(Model model,
		@ModelAttribute("stl")  Settlement stl,
		@ModelAttribute("type") Text typeId)
	{
		var id   = Long.parseLong(typeId.getValue());
		var type = stlTypeRep.findById(id).get();

		var currentR = facade.currentResident();
		stl.setType(type);
		stl.setResident(currentR);

		var added = stlRep.save(stl);

		var residents   = resRep.findAll();
		var perResident = added.getAmount().divide(
			new BigDecimal(resRep.count())
		);

		var payments = new ArrayList<Payment>();
		residents.forEach(r -> {
			if (r.getId() != currentR.getId())
			{
				payments.add(new Payment(r, added, perResident));
			}
		});

		payRep.saveAll(payments);

		return all(model);
	}
}
