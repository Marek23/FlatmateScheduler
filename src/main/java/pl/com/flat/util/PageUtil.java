package pl.com.flat.util;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.ui.Model;

public class PageUtil {
	public static Page<?> createPageContent(Model model, PagingAndSortingRepository<?, Long> r, Optional<Integer> number) {
		var page = r.findAll(PageRequest.of(number.orElse(1) - 1, 8));

		var totalPages = page.getTotalPages();
        if (totalPages > 0) {
            var numbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
				.collect(Collectors.toList());

            model.addAttribute("pages", numbers);
		}

		return page;
	}

	public static void createPageNumbers(Model model, Page<?> page) {
		var totalPages = page.getTotalPages();
        if (totalPages > 0) {
            var numbers = IntStream.rangeClosed(1, totalPages)
                .boxed()
				.collect(Collectors.toList());

            model.addAttribute("pages", numbers);
		}
	}
}
