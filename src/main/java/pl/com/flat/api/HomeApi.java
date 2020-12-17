package pl.com.flat.api;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static pl.com.flat.util.Content.content;

@Controller
class HomeApi {
	@RequestMapping("/") String index(Model model) {
		return content(model, "none");
	}
}
