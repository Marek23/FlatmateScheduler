package pl.com.flat.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.com.flat.model.Settlement;
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
	SettlementRepository stlRep;

	@Autowired
	StlTypeRepository stlTypeRep;

	@RequestMapping("/all")
	public String settlements(Model model) {
		model.addAttribute("settlements", stlRep.findAll());
		model.addAttribute("stlTypes",    stlTypeRep.findAll());

		model.addAttribute("stl",    new Settlement());
		model.addAttribute("typeId", new Text());

		return "home/settlements";
	}

	@PostMapping("/add")
	public String greetingSubmit(Model model,
		@ModelAttribute("stl")  Settlement stl,
		@ModelAttribute("type") Text typeId)
	{
		var id   = Long.parseLong(typeId.getValue());
		var type = stlTypeRep.findById(id).get();

		stl.setType(type);
		stl.setResident(facade.currentResident());

		stlRep.save(stl);

		return settlements(model);
	}
}
