package br.com.pocketpos.data.util;

import br.com.pocketpos.data.jersey.CatalogBean;
import br.com.pocketpos.data.jersey.DatasetBean;
import br.com.pocketpos.data.jersey.MeasureUnitBean;
import br.com.pocketpos.data.jersey.PaymentMethodBean;
import br.com.pocketpos.data.jersey.ReceiptMethodBean;
import br.com.pocketpos.data.jersey.ProductBean;
import br.com.pocketpos.data.jersey.UserBean;
import br.com.pocketpos.data.room.CatalogItemVO;
import br.com.pocketpos.data.room.CatalogVO;
import br.com.pocketpos.data.room.MeasureUnitMeasureUnitVO;
import br.com.pocketpos.data.room.MeasureUnitVO;
import br.com.pocketpos.data.room.PaymentMethodVO;
import br.com.pocketpos.data.room.ReceiptMethodVO;
import br.com.pocketpos.data.room.ProductProductVO;
import br.com.pocketpos.data.room.ProductVO;
import br.com.pocketpos.data.room.UserVO;

public class DBSync {

    private DB database;

    public DBSync(DB database){

        this.database = database;

    }

    public void syncDataset(DatasetBean datasetBean) {

        try {

            database.beginTransaction();

            if (datasetBean.getUsers() != null &&
                    !datasetBean.getUsers().isEmpty()){

                for (UserBean userBean : datasetBean.getUsers()) {

                    UserVO userVO = new UserVO();

                    userVO.setIdentifier(userBean.getIdentifier());

                    userVO.setActive(userBean.getActive());

                    userVO.setLevel(userBean.getLevel());

                    userVO.setName(userBean.getName());

                    userVO.setLogin(userBean.getLogin());

                    userVO.setPassword(userBean.getPassword());

                    if (database.userDAO().exists(userVO.getIdentifier()))

                        database.userDAO().update(userVO);

                    else

                        database.userDAO().create(userVO);

                }

            }

            if (datasetBean.getMeasureUnits() != null &&
                    !datasetBean.getMeasureUnits().isEmpty()) {

                for (MeasureUnitBean measureUnitBean : datasetBean.getMeasureUnits()) {

                    MeasureUnitVO measureUnitVO = new MeasureUnitVO();

                    measureUnitVO.setIdentifier(measureUnitBean.getIdentifier());

                    measureUnitVO.setDenomination(measureUnitBean.getDenomination());

                    measureUnitVO.setAcronym(measureUnitBean.getAcronym());

                    measureUnitVO.setGroup(measureUnitBean.getGroup());

                    if (database.measureUnitDAO().exists(measureUnitVO.getIdentifier()))

                        database.measureUnitDAO().update(measureUnitVO);

                    else

                        database.measureUnitDAO().create(measureUnitVO);

                }

                for (MeasureUnitBean measureUnitBean : datasetBean.getMeasureUnits()) {

                    for (Integer toIdentifier : measureUnitBean.getConversions().keySet()) {

                        MeasureUnitMeasureUnitVO measureUnitMeasureUnitVO = new MeasureUnitMeasureUnitVO();

                        measureUnitMeasureUnitVO.setFromIdentifier(measureUnitBean.getIdentifier());

                        measureUnitMeasureUnitVO.setToIdentifier(toIdentifier);

                        measureUnitMeasureUnitVO.setFactor(measureUnitBean.getConversions().get(toIdentifier).getFactor());

                        if (database.measureUnitMeasureUnitDAO().exists(measureUnitMeasureUnitVO.getFromIdentifier(), measureUnitMeasureUnitVO.getToIdentifier()))

                            database.measureUnitMeasureUnitDAO().update(measureUnitMeasureUnitVO);

                        else

                            database.measureUnitMeasureUnitDAO().create(measureUnitMeasureUnitVO);

                    }

                }

            }

            if (datasetBean.getProducts() != null &&
                    !datasetBean.getProducts().isEmpty()) {

                for (ProductBean productBean : datasetBean.getProducts()) {

                    ProductVO productVO = new ProductVO();

                    productVO.setIdentifier(productBean.getIdentifier());

                    productVO.setActive(productBean.getActive());

                    productVO.setDenomination(productBean.getDenomination());

                    productVO.setWidthValue(productBean.getWidthValue());

                    productVO.setWidthUnit(productBean.getWidthUnit());

                    productVO.setHeightValue(productBean.getHeightValue());

                    productVO.setHeightUnit(productBean.getHeightUnit());

                    productVO.setLengthValue(productBean.getLengthValue());

                    productVO.setLengthUnit(productBean.getLengthUnit());

                    productVO.setContentValue(productBean.getContentValue());

                    productVO.setContentUnit(productBean.getContentUnit());

                    productVO.setGrossWeightValue(productBean.getGrossWeightValue());

                    productVO.setGrossWeightUnit(productBean.getGrossWeightUnit());

                    productVO.setNetWeightValue(productBean.getNetWeightValue());

                    productVO.setNetWeightUnit(productBean.getNetWeightUnit());

                    if (database.productDAO().exists(productVO.getIdentifier()))

                        database.productDAO().update(productVO);

                    else

                        database.productDAO().create(productVO);

                }

                for (ProductBean productBean : datasetBean.getProducts()) {

                    for (Integer partIdentifier : productBean.getParts().keySet()) {

                        ProductProductVO productProductVO = new ProductProductVO();

                        productProductVO.setProductIdentifier(productBean.getIdentifier());

                        productProductVO.setPartIdentifier(partIdentifier);

                        productProductVO.setActive(productBean.getParts().get(partIdentifier).getActive());

                        productProductVO.setQuantity(productBean.getParts().get(partIdentifier).getQuantity());

                        if (database.productProductDAO().exists(productProductVO.getProductIdentifier(), productProductVO.getPartIdentifier()))

                            database.productProductDAO().update(productProductVO);

                        else

                            database.productProductDAO().create(productProductVO);

                    }

                }

            }

            if (datasetBean.getCatalogs() != null &&
                    !datasetBean.getCatalogs().isEmpty()) {

                int x = 0;

                int[] doNotDeleteCatalogs = new int[datasetBean.getCatalogs().size()];

                for (CatalogBean catalogBean : datasetBean.getCatalogs()) {

                    CatalogVO catalogVO = new CatalogVO();

                    catalogVO.setIdentifier(catalogBean.getIdentifier());

                    catalogVO.setPosition(catalogBean.getPosition());

                    catalogVO.setDenomination(catalogBean.getDenomination());

                    if (database.catalogDAO().exists(catalogVO.getIdentifier()))

                        database.catalogDAO().update(catalogVO);

                    else

                        database.catalogDAO().create(catalogVO);

                    int y = 0;

                    int[] doNotDeleteItems = new int[catalogBean.getItems().size()];

                    for (Integer item : catalogBean.getItems().keySet()) {

                        CatalogItemVO catalogItemVO = new CatalogItemVO();

                        catalogItemVO.setCatalog(catalogBean.getIdentifier());

                        catalogItemVO.setItem(item);

                        catalogItemVO.setPosition(catalogBean.getItems().get(item).getPosition());

                        catalogItemVO.setProduct(catalogBean.getItems().get(item).getProduct());

                        catalogItemVO.setCode(catalogBean.getItems().get(item).getCode());

                        catalogItemVO.setDenomination(catalogBean.getItems().get(item).getDenomination());

                        catalogItemVO.setMeasureUnit(catalogBean.getItems().get(item).getMeasureUnit());

                        catalogItemVO.setQuantity(0.0);

                        catalogItemVO.setPrice(catalogBean.getItems().get(item).getPrice());

                        catalogItemVO.setTotal(0.0);

                        if (database.catalogItemDAO().exists(catalogItemVO.getCatalog(), catalogItemVO.getItem()))

                            database.catalogItemDAO().update(catalogItemVO);

                        else

                            database.catalogItemDAO().create(catalogItemVO);

                        doNotDeleteItems[y] = item;

                        y++;

                    }

                    database.catalogItemDAO().deleteCatalogItemsNotIN(catalogBean.getIdentifier(), doNotDeleteItems);

                    doNotDeleteCatalogs[x] = catalogBean.getIdentifier();

                    x++;

                }

                database.catalogDAO().deleteCatalogNotIN(doNotDeleteCatalogs);

            }

            if (datasetBean.getReceiptMethods() != null &&
                    !datasetBean.getReceiptMethods().isEmpty()) {

                for (ReceiptMethodBean receiptMethodBean : datasetBean.getReceiptMethods()) {

                    ReceiptMethodVO receiptMethodVO = new ReceiptMethodVO();

                    receiptMethodVO.setIdentifier(receiptMethodBean.getIdentifier());

                    receiptMethodVO.setDenomination(receiptMethodBean.getDenomination());

                    if (database.receiptMethodDAO().exists(receiptMethodVO.getIdentifier()))

                        database.receiptMethodDAO().update(receiptMethodVO);

                    else

                        database.receiptMethodDAO().create(receiptMethodVO);

                }

            }

            if (datasetBean.getPaymentMethods() != null &&
                    !datasetBean.getPaymentMethods().isEmpty()) {

                for (PaymentMethodBean paymentMethodBean : datasetBean.getPaymentMethods()) {

                    PaymentMethodVO paymentMethodVO = new PaymentMethodVO();

                    paymentMethodVO.setIdentifier(paymentMethodBean.getIdentifier());

                    paymentMethodVO.setDenomination(paymentMethodBean.getDenomination());

                    if (database.paymentMethodDAO().exists(paymentMethodVO.getIdentifier()))

                        database.paymentMethodDAO().update(paymentMethodVO);

                    else

                        database.paymentMethodDAO().create(paymentMethodVO);

                }

            }

            database.setTransactionSuccessful();

        } finally {

            if (database.inTransaction())

                database.endTransaction();

        }

    }

}