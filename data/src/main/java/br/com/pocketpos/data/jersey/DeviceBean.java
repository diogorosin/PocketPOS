package br.com.pocketpos.data.jersey;


public class DeviceBean {

    private Integer identifier;

    private Boolean active;

    private String serialNumber;

    private String manufacturer;

    private String model;

    private String alias;

    public Integer getIdentifier() {

        return identifier;

    }

    public void setIdentifier(Integer identifier) {

        this.identifier = identifier;

    }

    public String getSerialNumber() {

        return serialNumber;

    }

    public Boolean getActive() {

        return active;

    }

    public void setActive(Boolean active) {

        this.active = active;

    }

    public void setSerialNumber(String serialNumber) {

        this.serialNumber = serialNumber;

    }

    public String getManufacturer() {

        return manufacturer;

    }

    public void setManufacturer(String manufacturer) {

        this.manufacturer = manufacturer;

    }

    public String getModel() {

        return model;

    }

    public void setModel(String model) {

        this.model = model;

    }

    public String getAlias() {

        return alias;

    }

    public void setAlias(String alias) {

        this.alias = alias;

    }

    public boolean equals(Object o) {

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceBean that = (DeviceBean) o;
        return identifier != null ? identifier.equals(that.identifier) : that.identifier == null;

    }

    public int hashCode() {

        return identifier != null ? identifier.hashCode() : 0;

    }

}