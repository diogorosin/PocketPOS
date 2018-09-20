// REFERENCIA DE SQL COM DATAS
// http://androidkt.com/datetime-datatype-sqlite-using-room/

package br.com.pocketpos.data.room;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class SaleModel implements Serializable {

    private Integer identifier;

    private Date dateTime;

    private UserVO user;

    private List<SaleItemModel> items;

    public Integer getIdentifier() {

        return identifier;

    }

    public void setIdentifier(Integer identifier) {

        this.identifier = identifier;

    }

    public Date getDateTime() {

        return dateTime;

    }

    public void setDateTime(Date dateTime) {

        this.dateTime = dateTime;

    }

    public UserVO getUser() {

        return user;

    }

    public void setUser(UserVO user) {

        this.user = user;

    }

    public List<SaleItemModel> getItems() {

        return items;

    }

    public void setItems(List<SaleItemModel> items) {

        this.items = items;

    }

}