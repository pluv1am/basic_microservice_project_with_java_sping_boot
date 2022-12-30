package com.hava.hava_durumu;

import ch.qos.logback.core.net.SyslogOutputStream;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Locale;

@SpringBootApplication
@RestController
public class HavaDurumuApplication {

	public static void main(String[] args) {
		SpringApplication.run(HavaDurumuApplication.class, args);

		try {
			Document doc = Jsoup.connect("https://yandex.com.tr/hava/ankara").get();

			Elements sicaklik = doc.select("body > div.b-page__container > div.content.content_compressed.i-bem > div.content__top > div.fact.card.card_size_big > div.fact__temp-wrap > a > div > div.temp.fact__temp.fact__temp_size_s > span");
			Elements hava = doc.select("body > div.b-page__container > div.content.content_compressed.i-bem > div.content__top > div.fact.card.card_size_big > div.fact__temp-wrap > a > div > div.link__feelings.fact__feelings > div.link__condition.day-anchor.i-bem");
			Elements hissedilen = doc.select("body > div.b-page__container > div.content.content_compressed.i-bem > div.content__top > div.fact.card.card_size_big > div.fact__temp-wrap > a > div > div.link__feelings.fact__feelings > div.term.term_orient_h.fact__feels-like > div.term__value > div > span");
			//Elements sicaklik = doc.select("");

			String sicaklik_s = sicaklik.text();
			sicaklik_s = sicaklik_s.replaceAll("<[^>]*>", "");
			String hava_s = hava.text();
			hava_s = hava_s.replaceAll("<[^>]*>", "");
			String hissedilen_s = hissedilen.text();
			hissedilen_s = hissedilen_s.replaceAll("<[^>]*>", "");

			System.out.println("ANKARA HAVA DURUMU\n");
			System.out.println("Sıcaklık: " + sicaklik_s + "°C");
			System.out.println("Hava: " + hava_s);
			System.out.println("Hissedilen sıcaklık: " + hissedilen_s + "°C");


		} catch (IOException e) {
			throw new RuntimeException(e);
		}


	}

	@GetMapping("/hava")
	public String hava(@RequestParam(value= "sehir", defaultValue = "ankara") String sehir){

		sehir = sehir.toLowerCase();

		Document doc = null;
		try {
			doc = Jsoup.connect("https://yandex.com.tr/hava/" + sehir).get();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		Elements sicaklik = doc.select("body > div.b-page__container > div.content.content_compressed.i-bem > div.content__top > div.fact.card.card_size_big > div.fact__temp-wrap > a > div > div.temp.fact__temp.fact__temp_size_s > span");
		Elements hava = doc.select("body > div.b-page__container > div.content.content_compressed.i-bem > div.content__top > div.fact.card.card_size_big > div.fact__temp-wrap > a > div > div.link__feelings.fact__feelings > div.link__condition.day-anchor.i-bem");
		Elements hissedilen = doc.select("body > div.b-page__container > div.content.content_compressed.i-bem > div.content__top > div.fact.card.card_size_big > div.fact__temp-wrap > a > div > div.link__feelings.fact__feelings > div.term.term_orient_h.fact__feels-like > div.term__value > div > span");
		//Elements sicaklik = doc.select("");

		String sicaklik_s = sicaklik.text();
		sicaklik_s = sicaklik_s.replaceAll("<[^>]*>", "");
		String hava_s = hava.text();
		hava_s = hava_s.replaceAll("<[^>]*>", "");
		String hissedilen_s = hissedilen.text();
		hissedilen_s = hissedilen_s.replaceAll("<[^>]*>", "");

		String hava_durumu = sehir.toUpperCase() + " HAVA DURUMU<br><br>" + "Sıcaklık: " + sicaklik_s + "°C<br>" + "Hava: " + hava_s + "<br>Hissedilen sıcaklık: " + hissedilen_s + "°C";
		//System.out.println("ANKARA HAVA DURUMU\n");
		//System.out.println("Sıcaklık: " + sicaklik_s + "°C");
		//System.out.println("Hava: " + hava_s);
		//System.out.println("Hissedilen sıcaklık: " + hissedilen_s + "°C");

		return hava_durumu;

	}

}
