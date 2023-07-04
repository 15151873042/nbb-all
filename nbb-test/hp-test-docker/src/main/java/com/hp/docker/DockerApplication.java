package com.hp.docker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DockerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DockerApplication.class);
    }

    @RequestMapping("/")
    public Object test() {
        return "test";
    }

    @RequestMapping("/form")
    public Object form() {
        return "    <form name=\"punchout_form\" method=\"post\" action=\"https://openapi.alipay.com/gateway.do?charset=GBK&method=alipay.trade.page.pay&sign=tXR6yxB9Uowu7tfbXVPHBoQXeyOqP2JXvo%2Fzmz%2BIA08aWin63h1%2FF7gHNN7I2K%2FnTV3ZYtvPkILu%2FS8uPmzpsAv1SJev0UNN2AbLDIGvbd%2BWn5neyIUqKcu5ySP1S8Bt4fbnvkMgnlijR25obVi5aFbj99JBZDrpeJ5cB9uq7Ccx7nX54%2F7coNv26PFD%2FPirlIKVVr2avD2w%3D%3D&return_url=http%3A%2F%2Fwww.shanshiwangluo.com%2F%23%2FpayNotify&notify_url=http%3A%2F%2Fwww.shanshiwangluo.com%2Fssmall%2Fportal%2Forder%2Fpay%2FaliCallback&version=1.0&app_id=2018062260383877&sign_type=RSA2&timestamp=2018-12-15+17%3A13%3A32&alipay_sdk=alipay-sdk-java-dynamicVersionNo&format=json\">\n" +
                "        <input type=\"hidden\" name=\"biz_content\" value=\"{    &quot;out_trade_no&quot;:&quot;1812141933252566&quot;,    &quot;product_code&quot;:&quot;FAST_INSTANT_TRADE_PAY&quot;,    &quot;total_amount&quot;:0.02,    &quot;subject&quot;:&quot; 订单：1812141933252566&quot;,    &quot;extend_params&quot;:{    &quot;sys_service_provider_id&quot;:&quot;2018062211454921&quot;    }  }\">\n" +
                "        <input type=\"submit\" value=\"立即支付\" style=\"display:none\" >\n" +
                "    </form>\n" +
                "    <script>document.forms[0].submit();</script>";
    }
}
