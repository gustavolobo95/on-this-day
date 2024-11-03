package com.lobo.on_this_day;

import org.springframework.boot.SpringApplication;

public class TestOnThisDayApplication {

	public static void main(String[] args) {
		SpringApplication.from(OnThisDayApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
