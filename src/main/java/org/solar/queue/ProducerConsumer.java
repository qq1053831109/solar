package org.solar.queue;


import org.solar.exception.SolarRuntimeException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public interface ProducerConsumer {


    Map<Object, ProducerConsumerImpl> producerMap = new ConcurrentHashMap<Object, ProducerConsumerImpl>();

    static ProducerConsumerImpl getDefault(Consumer consumer) {
        return new ProducerConsumerImpl(consumer);
    }

    static ProducerConsumer getProducerConsumer(Object key, Consumer consumer) {
        ProducerConsumerImpl producerConsumerImpl = producerMap.get(key);
        if (producerConsumerImpl == null) {
            producerConsumerImpl = new ProducerConsumerImpl(consumer);
            producerMap.put(key, producerConsumerImpl);
        }
        return producerConsumerImpl;
    }

    static ProducerConsumer getProducerConsumer(Object key) {
        ProducerConsumerImpl producerConsumerImpl = producerMap.get(key);
        if (producerConsumerImpl == null) {
            throw new SolarRuntimeException("没有此实例key=" + key);
        }
        return producerConsumerImpl;
    }

    boolean produce(Object obj);

    void stopConsumer();

}
