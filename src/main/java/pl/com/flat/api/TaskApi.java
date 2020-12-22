package pl.com.flat.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import pl.com.flat.model.Status;
import pl.com.flat.model.Task;
import pl.com.flat.repository.ResidentRepository;
import pl.com.flat.repository.TaskRepository;
import pl.com.flat.repository.TaskTypeRepository;
import pl.com.flat.security.IFacade;

import static pl.com.flat.util.Content.content;
import static pl.com.flat.util.Message.alertSuccess;

@Controller
@RequestMapping("tasks")
public class TaskApi {
	@Autowired IFacade            facade;
	@Autowired ResidentRepository resRep;
	@Autowired TaskRepository     taskRep;
	@Autowired TaskTypeRepository typesRep;

	@RequestMapping("/all")
	public String all(Model model) {
		model.addAttribute("tasks", taskRep.findAll());

		return content(model, "tasks-all");
	}

	@RequestMapping("/todo")
	public String todo(Model model) {
		var current = facade.currentResident().getId();
		model.addAttribute("tasks", taskRep.findByResidentIdAndStatus(current, Status.Czeka));

		return content(model, "tasks-todo");
	}

	@RequestMapping("/add")
	public String addTask(Model model, @ModelAttribute("t") Task t)
	{
		var logged       = facade.currentResident();
		var allowedTypes = typesRep.findByRoleIn(logged.getRoles());

		model.addAttribute("types",     allowedTypes);
		model.addAttribute("residents", resRep.findAll());
		model.addAttribute("t",         new Task());

		if (t.getDate() == null || t.getType() == null || t.getResident() == null)
			return content(model, "tasks-add");

		t.setCreator(logged);
		t.setStatus(Status.Czeka);
		taskRep.save(t);

		alertSuccess(model, "Poprawnie dodano zadanie.");

		return content(model, "tasks-add");
	}

	@RequestMapping(value="/do", method=RequestMethod.POST)
	public String pay(@RequestHeader("Referer") String prev, Long id) {
		var t = taskRep.findById(id).get();
		t.setStatus(Status.Wykonane);
		taskRep.save(t);

		return "redirect:" + prev;
	}
}
