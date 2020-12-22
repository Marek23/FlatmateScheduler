package pl.com.flat.util;

import org.springframework.ui.Model;

public class Message {
	public static void alertSuccess(Model model, String text) {
		model.addAttribute("message",   text);
		model.addAttribute("alertType", "alert-success");
	}
}
