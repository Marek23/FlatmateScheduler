package pl.com.flat;

import java.math.BigDecimal;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pl.com.flat.model.Resident;
import pl.com.flat.model.Settlement;
import pl.com.flat.model.permissions.Role;
import pl.com.flat.model.permissions.StlType;
import pl.com.flat.repository.*;

@SpringBootApplication
public class FlatApplication {
	public static void main(String[] args) {
		SpringApplication.run(FlatApplication.class, args);
	}

	@Bean
	ApplicationRunner init(
			ResidentRepository ur,
			RoleRepository rr,
			StlTypeRepository tr,
			SettlementRepository sr) {
		return args -> {
			System.out.println("START");

			var u = new Resident("email", "password");
			ur.save(u);

			var webRole  = new Role("WEBADM____");
			var roomRole = new Role("ROOMOWNER_");
			rr.save(roomRole);
			rr.save(webRole);

			var t1 = new StlType("WEBBILL___", "Opłata miesięczna za internet");
			var t2 = new StlType("RUBBISH___", "Wynoszenie śmieci");
			var t3 = new StlType("CLEANBATHR", "Sprzątanie łazienki");
			var t4 = new StlType("CLEANKITCH", "Sprzątanie kuchni");

			t1.setRole(webRole);
			t2.setRole(roomRole);
			t3.setRole(roomRole);
			t4.setRole(roomRole);

			tr.save(t1);
			tr.save(t2);
			tr.save(t3);
			tr.save(t4);

			var s = new Settlement();
			s.setAmount(new BigDecimal(3f));
			s.setDate("20200101");
			s.setType(t1);
			s.setResident(u);
			sr.save(s);

			var update = ur.findByEmail("email");
			update.addRole(webRole);
			ur.save(update);

			System.out.println("STOP");
		};
	};
}

