package com.hm.sample.avro;

import com.hm.sample.jgit.JGitRepository;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.BinaryEncoder;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.io.EncoderFactory;
import org.apache.avro.specific.SpecificDatumWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by Jerry on 2016/1/23 0023.
 */
@Configuration
@EnableConfigurationProperties(AvroBean.class)
public class AvroMessage {
    @Autowired
    private AvroBean avroBean;

    @Autowired
    JGitRepository jGitRepository;

    public byte[] getCreateSmsAvroMessage(String mobile, String msg) throws Exception {
        Schema schema = this.getSendSmsSchema();
        GenericRecord record = new GenericData.Record(schema);
        record.put("mobile", mobile);
        record.put("message", msg);

        return this.getMessageOutputStream(this.getSendSmsSchema(), record);
    }

    @Bean
    public Schema getSendSmsSchema() {
        return new Schema.Parser().parse(jGitRepository.findOne(avroBean.getCreateSmsAvsc(), "master"));
    }

    public byte[] getMessageOutputStream(Schema schema, GenericRecord genericRecord) throws IOException {
        DatumWriter<GenericRecord> writer = new SpecificDatumWriter<>(schema);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        BinaryEncoder encoder = EncoderFactory.get().binaryEncoder(out, null);
        writer.write(genericRecord, encoder);
        encoder.flush();
        out.close();
        return out.toByteArray();
    }

}
