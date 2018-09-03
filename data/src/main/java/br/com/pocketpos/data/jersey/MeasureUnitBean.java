package br.com.pocketpos.data.jersey;

import java.util.Map;

public class MeasureUnitBean {

	private Integer identifier;

	private String denomination;

	private String acronym;

	private Integer group;

	private Map<Integer, ConversionBean> conversions;

	public Integer getIdentifier() {

		return identifier;

	}

	public void setIdentifier(Integer identifier) {

		this.identifier = identifier;

	}

	public String getDenomination() {

		return denomination;

	}

	public void setDenomination(String denomination) {

		this.denomination = denomination;

	}

	public String getAcronym() {

		return acronym;

	}

	public void setAcronym(String acronym) {

		this.acronym = acronym;

	}

    public Integer getGroup() {

        return group;

    }

    public void setGroup(Integer group) {

        this.group = group;

    }

	public Map<Integer, ConversionBean> getConversions() {

		return conversions;

	}

	public void setConversions(Map<Integer, ConversionBean> conversions) {

		this.conversions = conversions;

	}

}
