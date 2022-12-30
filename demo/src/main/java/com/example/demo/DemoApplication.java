package com.example.demo;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
@RestController
public class DemoApplication {

	@Bean
	public RestTemplate getRestTemplate(){
		return new RestTemplate();
	}

	@Autowired
	private RestTemplate restTemplate;


	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@RequestMapping("/")
	public ResponseEntity<String> index() throws IOException {
		String html = new String(Files.readAllBytes(Paths.get("src/main/resources/templates/index.html")));
		return new ResponseEntity<>(html, HttpStatus.OK);
	}

	@RequestMapping("/calculate")
	public String submitForm(@RequestParam("number1") String value1, @RequestParam("number2") String value2, @RequestParam("islem") String islem) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("message", restTemplate.exchange(("http://localhost:8081/hesap?num1=" + value1 + "&num2=" + value2 + "&islem=" + islem).toString() , HttpMethod.GET, null, String.class).getBody());
				return jsonObject.get("message").toString();
	}

	@RequestMapping("/hava")
	public String hava(@RequestParam(value= "sehir", defaultValue = "ankara") String sehir){
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("message", restTemplate.exchange(("http://localhost:8082/hava?sehir=" + sehir).toString() , HttpMethod.GET, null, String.class).getBody());
		return jsonObject.get("message").toString();
	}





}
