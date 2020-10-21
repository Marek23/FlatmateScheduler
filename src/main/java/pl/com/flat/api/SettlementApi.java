package pl.com.flat.api;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import pl.com.flat.repository.SettlementRepository;
import pl.com.flat.security.IFacade;

@RestController
public class SettlementApi {
	@Autowired
	IFacade facade;

	@Autowired
	SettlementRepository stlRep;

	@GetMapping("/settlements")
	public String settlements() {
		var out = new JSONArray();

		stlRep.findByResident(facade.currentResident()).forEach(s -> {
			var date   = s.getDate();
			var amount = s.getAmount();
			System.out.println("w lambdzie");
			out.put(new JSONObject()
				.put("date",   date)
				.put("amount", amount)
			);
		});
		
		return out.toString();
	}
}
