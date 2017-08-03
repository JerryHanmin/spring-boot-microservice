package com.hm.sample.avro;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Created by Jerry on 2016/1/23 0023.
 */

@ConfigurationProperties("avro.schema")
public class AvroBean {
    private String createSmsAvsc = "avro/send_sms.avsc";

    public String getCreateSmsAvsc() {
        return createSmsAvsc;
    }

    public void setCreateSmsAvsc(String createSmsAvsc) {
        this.createSmsAvsc = createSmsAvsc;
    }
}
