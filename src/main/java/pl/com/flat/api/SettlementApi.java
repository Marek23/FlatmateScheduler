package pl.com.flat.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.com.flat.repository.SettlementRepository;
import pl.com.flat.security.IFacade;

@Controller
@RequestMapping("settlements")
public class SettlementApi {
	@Autowired
	IFacade facade;

	@Autowired
	SettlementRepository stlRep;

	@RequestMapping("/all")
	public String settlements(Model model) {
		model.addAttribute("settlements", stlRep.findAll());
		return "home/settlements";
	}
}
