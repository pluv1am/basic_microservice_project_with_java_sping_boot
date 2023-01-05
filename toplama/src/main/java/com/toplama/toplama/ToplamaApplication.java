package com.toplama.toplama;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class ToplamaApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToplamaApplication.class, args);
	}

	@GetMapping("/hesap")
	public String topla(@RequestParam(value = "num1", defaultValue = "13") int num1, @RequestParam(value="num2", defaultValue = "57") int num2, @RequestParam(value = "carpma", defaultValue = "carpma") String islem) {

		if(islem.equals("toplama")){
			return String.format("%d %s %d = %d", num1,"+", num2, num1+num2);
		}
		else if(islem.equals("cikarma")){
			return String.format("%d %s %d = %d", num1,"-", num2, num1-num2);
		}
		else if(islem.equals("carpma")){
			return String.format("%d %s %d = %d", num1,"*", num2, num1*num2);
		}
		else{
			return String.format("%d %s %d = %.2f", num1,"/", num2, (float)num1/(float)num2);
		}

	}


}
