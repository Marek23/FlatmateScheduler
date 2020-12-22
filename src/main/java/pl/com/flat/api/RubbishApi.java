package pl.com.flat.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import pl.com.flat.model.Rubbish;
import pl.com.flat.repository.RubbishRepository;
import pl.com.flat.security.IFacade;

import static pl.com.flat.util.Content.content;
import static pl.com.flat.util.Message.alertSuccess;

@Controller
@RequestMapping("rubbishs")
public class RubbishApi {
	@Autowired IFacade            facade;
	@Autowired RubbishRepository  rubRep;

	@RequestMapping("/all")
	public String all(Model model) {
		model.addAttribute("rubbishs", rubRep.findAll());

		return content(model, "rubbishs-all");
	}

	@RequestMapping("/add")
	public String addRubbish(Model model, @ModelAttribute("r") Rubbish r)
	{
		var logged = facade.currentResident();

		model.addAttribute("r", new Rubbish());

		if (r.getDate() == null)
			return content(model, "rubbishs-add");

		r.setResident(logged);

		rubRep.save(r);

		alertSuccess(model, "Poprawnie dodano.");

		return content(model, "rubbishs-add");
	}
}
