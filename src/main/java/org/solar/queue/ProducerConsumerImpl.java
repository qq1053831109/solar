package org.solar.queue;

import java.util.concurrent.LinkedBlockingQueue;

class ProducerConsumerImpl implements Runnable, ProducerConsumer {
    private boolean start = true;
    private LinkedBlockingQueue queue = new LinkedBlockingQueue();
    private Consumer consumer;

    public ProducerConsumerImpl(Consumer consumer) {
        this.consumer = consumer;
        new Thread(this).start();
    }

    public boolean produce(Object obj) {
        boolean b = queue.add(obj);
        return b;
    }

    public void run() {
        while (start) {
            try {
                Object obj = queue.take();
                consumer.consume(obj);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public void stopConsumer() {
        start = false;
    }


}
