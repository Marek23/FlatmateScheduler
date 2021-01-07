package pl.com.flat.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.com.flat.model.Status;
import pl.com.flat.model.Task;
import pl.com.flat.repository.ResidentRepository;
import pl.com.flat.repository.TaskRepository;
import pl.com.flat.repository.TaskTypeRepository;
import pl.com.flat.security.IFacade;

import static pl.com.flat.util.Content.content;
import static pl.com.flat.util.Message.alertSuccess;

import static org.apache.commons.lang3.time.DateFormatUtils.format;

import java.util.Date;
import java.util.Optional;

import static pl.com.flat.util.PageUtil.createPageContent;
@Controller
@RequestMapping("tasks")
public class TaskApi {
	@Autowired IFacade            facade;
	@Autowired ResidentRepository resRep;
	@Autowired TaskRepository     taskRep;
	@Autowired TaskTypeRepository typesRep;
	@Autowired EmailService mail;

	@RequestMapping("/all")
	public String all(Model model, @RequestParam("page") Optional<Integer> number) {
		var page = createPageContent(model, taskRep, number);

		model.addAttribute("tasks", page);

		return content(model, "tasks-all");
	}

	@RequestMapping("/summary")
	public String summary(Model model) {
		var types     = typesRep.findAll();
		var residents = resRep.findAll();

		String [][] summary = new String[(int)typesRep.count()+1][(int)resRep.count()+1];

		int i = 1;
		for (var t : types)
			summary[i++][0] = t.getName();

		i = 1;
		for (var r : residents)
			summary[0][i++] = r.getEmail();
		
		i = 1;
		for (var t : types) {
			int j = 1;
			for (var r : residents) {
				var latest     = taskRep.findFirstByResidentAndStatusAndTypeOrderByExecDateDesc(r, Status.Wykonane, t);
				var latestDate = latest == null ? "brak" : latest.getExecDate();
				var times      = taskRep.countByResidentAndStatusAndType(r, Status.Wykonane, t);
		
				summary[i][j++] = latestDate + " / " + times;
			}
			i++;
		}

		model.addAttribute("summary", summary);

		return content(model, "tasks-summary");
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

		var resident = t.getResident();

		if (t.getType() == null || resident == null)
			return content(model, "tasks-add");

		t.setCreator(logged);
		t.setCreationDate(format(new Date(), "yyyy-MM-dd"));
		t.setStatus(Status.Czeka);
		taskRep.save(t);

		mail.notify(resident, logged.getEmail() + " dodał Ci zadanie do wykonania.");

		alertSuccess(model, "Poprawnie dodano zadanie.");

		return content(model, "tasks-add");
	}

	@RequestMapping(value="/do", method=RequestMethod.POST)
	public String pay(@RequestHeader("Referer") String prev, Long id) {
		var t = taskRep.findById(id).get();
		t.setStatus(Status.Wykonane);
		t.setExecDate(format(new Date(), "yyyy-MM-dd"));
		taskRep.save(t);

		return "redirect:" + prev;
	}
}
