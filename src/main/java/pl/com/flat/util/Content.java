package pl.com.flat.util;

import org.springframework.ui.Model;

import lombok.Data;

@Data
public class Content {
	private String value;

	public boolean isEmpty(){return value.isEmpty();}

	public static String content(Model model, String content) {
		var contentName = new Content();
		contentName.setValue(content);
		model.addAttribute("contentName", contentName);

		return "main";
	}
}
