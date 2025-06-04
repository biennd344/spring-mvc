package vn.hoidanit.laptopshop;

import org.springframework.context.ApplicationContext;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LaptopshopApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaptopshopApplication.class, args);
		// ApplicationContext bin = SpringApplication.run(LaptopshopApplication.class,
		// args);
		// for (String s : bin.getBeanDefinitionNames()) {
		// System.out.println(s);
		// }

	}

}
