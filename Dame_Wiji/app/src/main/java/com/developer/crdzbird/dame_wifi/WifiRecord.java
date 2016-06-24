package com.developer.crdzbird.dame_wifi;

/**
 * Created by crdzbird on 06-23-16.
 */

public class WifiRecord {

	private String ssid;
	private String secret;
	private String security;
	private boolean encrypted = false;

	public String getSsid() {
		return ssid;
	}

	public void setSsid(String ssid) {
		this.ssid = ssid;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getSecurity() {
		return security;
	}

	public void setSecurity(String security) {
		this.security = security;
	}

	public boolean isEncrypted() {
		return encrypted;
	}

	public void setEncrypted(boolean encrypted) {
		this.encrypted = encrypted;
	}

	@Override
	public String toString() {
		return "WifiRecord [ssid=" + ssid + ", secret=" + secret
				+ ", security=" + security + ", encrypted=" + encrypted +"]";
	}
}
