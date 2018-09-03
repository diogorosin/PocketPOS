package br.com.pocketpos.data.jersey;

import java.util.HashMap;
import java.util.Map;

public class CatalogBean {

	private Integer identifier;

	private Integer position;

	private String denomination;

	private Map<Integer, CatalogItemBean> items;

	public Integer getIdentifier() {

		return identifier;

	}

	public void setIdentifier(Integer identifier) {

		this.identifier = identifier;

	}

	public Integer getPosition() {

		return position;

	}

	public void setPosition(Integer position) {

		this.position = position;

	}

	public String getDenomination() {

		return denomination;

	}

	public void setDenomination(String denomination) {

		this.denomination = denomination;

	}

	public Map<Integer, CatalogItemBean> getItems() {

		return items;

	}

	public void setItems(Map<Integer, CatalogItemBean> items) {

		this.items = items;

	}

}