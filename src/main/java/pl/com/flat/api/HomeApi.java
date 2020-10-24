package pl.com.flat.api;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
class HomeApi {
	@RequestMapping("/") String index() {return "home/home";}
}
