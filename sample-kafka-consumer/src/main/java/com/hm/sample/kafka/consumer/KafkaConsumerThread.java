package com.hm.sample.kafka.consumer;

import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import net.sf.json.JSONObject;
import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.Decoder;
import org.apache.avro.io.DecoderFactory;
import org.apache.avro.specific.SpecificDatumReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Jerry on 2016/1/25 0025.
 */
public class KafkaConsumerThread implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private KafkaStream stream = null;
    private Schema schema = null;
    protected KafkaConsumer kafkaConsumer;

    public KafkaConsumerThread(KafkaStream stream, Schema schema, KafkaConsumer kafkaConsumer) {
        this.stream = stream;
        this.schema = schema;
        this.kafkaConsumer = kafkaConsumer;
    }

    @Override
    public void run() {
        if (null != stream) {
            ConsumerIterator<byte[], byte[]> it = stream.iterator();
            while (it.hasNext()) {
                disposeMessageAvro(it);
            }
        }

    }

    private void disposeMessageAvro(ConsumerIterator<byte[], byte[]> it) {
        try {
            byte[] received_message = it.next().message();
//            logger.debug("received_message:" + new String(received_message));
            DatumReader<GenericRecord> reader = new SpecificDatumReader<>(schema);
            Decoder decoder = DecoderFactory.get().binaryDecoder(received_message, null);
            GenericRecord message = reader.read(null, decoder);
            JSONObject messageJson = JSONObject.fromObject(message.toString());
            consumerService(messageJson);
        } catch (Exception e) {
            logger.error("获取kafka消息失败 , 失败原因: " + e.getMessage(), e);
        }
    }

    public void consumerService(JSONObject messageJson) {

    }

}
