package pl.com.flat;

import java.math.BigDecimal;
import java.util.Date;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pl.com.flat.model.Payment;
import pl.com.flat.model.Resident;
import pl.com.flat.model.Settlement;
import pl.com.flat.model.permissions.Role;
import pl.com.flat.model.permissions.StlType;
import pl.com.flat.model.permissions.TaskType;
import pl.com.flat.repository.*;

import static org.apache.commons.lang3.time.DateFormatUtils.format;

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
			SettlementRepository sr,
			PaymentRepository    pr) {
		return args -> {
			System.out.println("START");

			var um = new Resident("marek",   "password");
			var ua = new Resident("adam", "password");
			var uk = new Resident("kamil", "password");

			ur.save(um);
			ur.save(ua);
			ur.save(uk);

			var webRole  = new Role("WEBADM____");
			var roomRole = new Role("ROOMOWNER_");
			rr.save(roomRole);
			rr.save(webRole);

			var st1 = new StlType("Opłata miesięczna za internet");
			var st2 = new StlType("Zakupy");

			var tt2 = new TaskType("Sprzątanie łazienki");
			var tt3 = new TaskType("Sprzątanie kuchni");

			st1.setRole(webRole);

			st2.setRole(roomRole);
			tt2.setRole(roomRole);
			tt3.setRole(roomRole);

			str.save(st1);
			str.save(st2);

			ttr.save(tt2);
			ttr.save(tt3);

			var s = new Settlement();
			s.setAmount(new BigDecimal(3f));
			s.setDate(format(new Date(), "yyyy-MM-dd"));
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

			var p = new Payment(
				ur.findByEmail("marek"),
				sr.findAll().iterator().next(),
				new BigDecimal(9)
			);

			pr.save(p);

			System.out.println("STOP");
		};
	};
}

