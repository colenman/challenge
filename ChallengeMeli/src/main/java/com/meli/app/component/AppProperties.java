package com.meli.app.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppProperties {

	@Value("${app.ip.publica}")
	private String ipPublica;

	public String getIpPublica() {
		return ipPublica;
	}
}
