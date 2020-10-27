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
import pl.com.flat.model.permissions.TaskType;
import pl.com.flat.repository.*;

@SpringBootApplication
public class FlatApplication {
	public static void main(String[] args) {
		SpringApplication.run(FlatApplication.class, args);
	}

	@Bean
	ApplicationRunner init(
			ResidentRepository   ur,
			RoleRepository       rr,
			StlTypeRepository    str,
			TaskTypeRepository   ttr,
			SettlementRepository sr) {
		return args -> {
			System.out.println("START");

			var um = new Resident("marek", "password");
			var ua = new Resident("adam",  "password");
			var uk = new Resident("kamil", "password");

			ur.save(um);
			ur.save(ua);
			ur.save(uk);

			var webRole  = new Role("WEBADM____");
			var roomRole = new Role("ROOMOWNER_");
			rr.save(roomRole);
			rr.save(webRole);

			var st1 = new StlType("WEBBILL___", "Opłata miesięczna za internet");
			var st2 = new StlType("SHOPPING__", "Zakupy");

			var tt1 = new TaskType("RUBBISH___", "Wynoszenie śmieci");
			var tt2 = new TaskType("CLEANBATHR", "Sprzątanie łazienki");
			var tt3 = new TaskType("CLEANKITCH", "Sprzątanie kuchni");

			st1.setRole(webRole);

			st2.setRole(roomRole);
			tt1.setRole(roomRole);
			tt2.setRole(roomRole);
			tt3.setRole(roomRole);

			str.save(st1);
			str.save(st2);

			ttr.save(tt1);
			ttr.save(tt2);
			ttr.save(tt3);

			var s = new Settlement();
			s.setAmount(new BigDecimal(3f));
			s.setDate("20200101");
			s.setType(st1);
			s.setResident(um);
			sr.save(s);

			var updatem = ur.findByEmail("marek");
			var updatea = ur.findByEmail("adam");
			var updatek = ur.findByEmail("kamil");

			updatem.addRole(roomRole);
			updatea.addRole(roomRole);

			updatek.addRole(roomRole);
			updatek.addRole(webRole);

			ur.save(updatem);
			ur.save(updatea);
			ur.save(updatek);

			System.out.println("STOP");
		};
	};
}

