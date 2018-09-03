package br.com.pocketpos.data.jersey;

public class AccountBean {

    private DeviceBean device;

    private CompanyBean company;

    private UserBean user;

    public DeviceBean getDevice() {

        return device;

    }

    public void setDevice(DeviceBean device) {

        this.device = device;

    }

    public CompanyBean getCompany() {

        return company;

    }

    public void setCompany(CompanyBean company) {

        this.company = company;

    }

    public UserBean getUser() {

        return user;

    }

    public void setUser(UserBean user) {

        this.user = user;

    }

}