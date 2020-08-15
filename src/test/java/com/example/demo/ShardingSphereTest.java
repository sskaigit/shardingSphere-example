package com.example.demo;

import com.example.demo.domain.DataLog;
import com.example.demo.domain.request.DataLogRequest;
import com.example.demo.repository.DataLogRepository;
import com.example.demo.service.DataLogService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes= DemoApplication.class)
public class ShardingSphereTest {

    @Autowired
    private DataLogRepository dataLogRepository;

    @Autowired
    private DataLogService dataLogService;

    @Test
    public void saveTest() throws Exception {
        DataLog log = new DataLog();
//		log.setId(1L);
		log.setCreatorId(3L);
		log.setCreatorName("超级管理员");
		log.setCreateDate(new Date());
		log.setAfterValues("{\"com.qgtech.cloud.trade.domain.OrderCharge\":{\"createDate\":1596517674554,\"enable\":true,\"updateDate\":1596517674554,\"amount\":0.0,\"capital\":200.0,\"fee\":0.0,\"isCharge\":false,\"isCost\":true,\"isDiscount\":false,\"itemCode\":\"POSTAGE\",\"itemName\":\"邮资\",\"order\":{\"chargeFee\":200.0,\"code\":\"SO2008041307541000\",\"colorMarkName\":\"\",\"comment1\":\"\",\"consigneeContact\":\"\",\"consigneeCredentials\":\"\",\"consigneeDetail\":\"\",\"costFee\":0.0,\"createDate\":1596517674302,\"createUserId\":1,\"createUsername\":\"超级管理员\",\"currency\":\"CNY\",\"customerId\":1,\"customerName\":\"林汉彬\",\"discountFee\":0.0,\"employeeId\":12,\"employeeName\":\"Daisy\",\"enable\":true,\"exrateCny\":1.0,\"financialAccountId\":4,\"financialAccountName\":\"Rayla-招商银行\",\"id\":60,\"invoiceName\":\"\",\"logisticsCompanyName\":\"\",\"logisticsType\":\"\",\"orderCharges\":[{\"amount\":200.0,\"capital\":200.0,\"createDate\":1596517674537,\"enable\":true,\"fee\":0.0,\"id\":415,\"isCharge\":true,\"isCost\":false,\"isDiscount\":false,\"itemCode\":\"GOODS_FEE\",\"itemName\":\"货款合计\",\"order\":{\"$ref\":\"$.com.qgtech.cloud.trade.domain.OrderCharge.order\"},\"rate\":100.0,\"updateDate\":1596517674537},{\"amount\":0.0,\"capital\":200.0,\"createDate\":1596517674554,\"enable\":true,\"fee\":0.0,\"id\":416,\"isCharge\":false,\"isCost\":true,\"isDiscount\":false,\"itemCode\":\"POSTAGE\",\"itemName\":\"邮资\",\"order\":{\"$ref\":\"$.com.qgtech.cloud.trade.domain.OrderCharge.order\"},\"rate\":0.0,\"updateDate\":1596517674554},{\"amount\":0.0,\"capital\":200.0,\"fee\":0.0,\"isCharge\":false,\"isCost\":true,\"isDiscount\":false,\"itemCode\":\"TAX1\",\"itemName\":\"关税\",\"order\":{\"$ref\":\"$.com.qgtech.cloud.trade.domain.OrderCharge.order\"},\"rate\":0.0},{\"amount\":0.0,\"capital\":200.0,\"fee\":0.0,\"isCharge\":false,\"isCost\":false,\"isDiscount\":true,\"itemCode\":\"RATE1\",\"itemName\":\"订单折扣\",\"order\":{\"$ref\":\"$.com.qgtech.cloud.trade.domain.OrderCharge.order\"},\"rate\":0.0},{\"amount\":0.0,\"capital\":200.0,\"fee\":0.0,\"isCharge\":true,\"isCost\":false,\"isDiscount\":false,\"itemCode\":\"CUSTOMER_BEAR\",\"itemName\":\"客户承担费用\",\"order\":{\"$ref\":\"$.com.qgtech.cloud.trade.domain.OrderCharge.order\"},\"rate\":0.0},{\"amount\":0.0,\"capital\":200.0,\"fee\":0.0,\"isCharge\":true,\"isCost\":false,\"isDiscount\":false,\"itemCode\":\"OTHER\",\"itemName\":\"其他费用\",\"order\":{\"$ref\":\"$.com.qgtech.cloud.trade.domain.OrderCharge.order\"},\"rate\":0.0}],\"orderEntrys\":[{\"cost\":86.77,\"createDate\":1596517674500,\"discount\":0.0,\"enable\":true,\"id\":237,\"order\":{\"$ref\":\"$.com.qgtech.cloud.trade.domain.OrderCharge.order\"},\"price\":20.0,\"productBarcode\":\"4909978975173\",\"productCode\":\"4909978975173\",\"productId\":483,\"productIsBundle\":false,\"productName\":\"ANESSA 安耐晒极防水清爽低敏UV乳液60ml 粉金瓶新款\",\"quantity\":10,\"settle\":20.0,\"specCode\":\"4909978975173\",\"specId\":478,\"specName\":\"默认\",\"surplusStockQuantity\":10,\"surplusStockUnqualified\":0,\"unqualifiedDiscount\":0.0,\"unqualifiedPrice\":0.0,\"unqualifiedQuantity\":0,\"unqualifiedSettle\":0.0,\"updateDate\":1596517674500}],\"orderFlag\":\"NORMAL\",\"orderSource\":\"MANUAL\",\"orderStatus\":\"PREPARE\",\"orderType\":\"RETAIL\",\"outCode\":\"TEST1234\",\"outPaymentCode\":\"\",\"productCost\":867.7,\"productFee\":200.0,\"productQty\":10,\"settleFee\":200.0,\"settlementType\":\"CREDIT\",\"shopId\":1,\"shopName\":\"Rayla Wholesale HK\",\"totalCost\":867.7,\"totalFee\":200.0,\"updateDate\":1596517674302,\"warehouseId\":3,\"warehouseName\":\"祖姐合作\"},\"rate\":0.0}}");
		log.setOperation("UPDATE");
		log.setEntityId("3");
		log.setEntityName("com.qgtech.cloud.trade.domain.OrderCharge");

		dataLogRepository.save(log);


    }

    @Test
    public void dataLogFacadeTest(){
        DataLogRequest request = new DataLogRequest();
        Page<DataLog> page = dataLogService.findPageByCondition(request, PageRequest.of(0, 20));

        System.out.println(page.getContent());
        System.out.println(page.getTotalElements());
    }
}
