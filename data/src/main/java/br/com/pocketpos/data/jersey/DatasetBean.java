package br.com.pocketpos.data.jersey;

import java.util.List;

public class DatasetBean {

    private CompanyBean company;

    private List<DeviceBean> devices;

    private List<OrganizationBean> organizations;

    private List<IndividualBean> individuals;

    private List<UserBean> users;

    private List<MeasureUnitBean> measureUnits;

    private List<ProductBean> products;

    private List<CatalogBean> catalogs;

    private List<PaymentBean> payments;

    public CompanyBean getCompany() {

        return company;

    }

    public void setCompany(CompanyBean company) {

        this.company = company;

    }

    public List<DeviceBean> getDevices() {

        return devices;

    }

    public void setDevices(List<DeviceBean> devices) {

        this.devices = devices;

    }

    public List<UserBean> getUsers() {

        return users;

    }

    public void setUsers(List<UserBean> users) {

        this.users = users;

    }

    public List<OrganizationBean> getOrganizations() {

        return organizations;

    }

    public void setOrganizations(List<OrganizationBean> organizations) {

        this.organizations = organizations;

    }

    public List<IndividualBean> getIndividuals() {

        return individuals;

    }

    public void setIndividuals(List<IndividualBean> individuals) {

        this.individuals = individuals;

    }

    public List<MeasureUnitBean> getMeasureUnits() {

        return measureUnits;

    }

    public void setMeasureUnits(List<MeasureUnitBean> measureUnits) {

        this.measureUnits = measureUnits;

    }

    public List<ProductBean> getProducts() {

        return products;

    }

    public void setProducts(List<ProductBean> products) {

        this.products = products;

    }

    public List<CatalogBean> getCatalogs() {

        return catalogs;

    }

    public void setCatalogs(List<CatalogBean> catalogs) {

        this.catalogs = catalogs;

    }

    public List<PaymentBean> getPayments() {

        return payments;

    }

    public void setPayments(List<PaymentBean> payments) {

        this.payments = payments;

    }

}