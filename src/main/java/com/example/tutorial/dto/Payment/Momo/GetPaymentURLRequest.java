package com.example.tutorial.dto.Payment.Momo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.digest.HmacAlgorithms;
import org.apache.commons.codec.digest.HmacUtils;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPaymentURLRequest {
    private String partnerCode;
    private String partnerName;
    private String storeId;
    private String requestType;
    private String ipnUrl;
    private String redirectUrl;
    private String orderId;
    private int amount;
    private String lang;
    private String orderInfo;
    private String requestId;
    private String extraData;
    private String signature;

    public void encodeSignature(String secretKey, String accessKey) {
        StringBuilder builder = new StringBuilder();
        builder.append("accessKey=").append(accessKey).append("&");
        builder.append("amount").append(this.amount).append("&");
        builder.append("extraData").append(this.extraData).append("&");
        builder.append("ipnUrl").append(this.ipnUrl).append("&");
        builder.append("orderId").append(this.orderId).append("&");
        builder.append("orderInfo").append(this.orderInfo).append("&");
        builder.append("partnerCode").append(this.partnerCode).append("&");
        builder.append("redirectUrl").append(this.redirectUrl).append("&");
        builder.append("requestId").append(this.requestId).append("&");
        builder.append("requestType").append(this.requestType);

        String payload = builder.toString();
        this.signature = new HmacUtils(HmacAlgorithms.HMAC_SHA_256, secretKey).hmacHex(payload);
    }
}
