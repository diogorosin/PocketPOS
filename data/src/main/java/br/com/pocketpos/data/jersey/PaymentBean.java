package br.com.pocketpos.data.jersey;

public class PaymentBean {

	private String identifier;

	private String denomination;

	public String getIdentifier() {

		return identifier;

	}

	public void setIdentifier(String identifier) {

		this.identifier = identifier;

	}

	public String getDenomination() {

		return denomination;

	}

	public void setDenomination(String denomination) {

		this.denomination = denomination;

	}

}