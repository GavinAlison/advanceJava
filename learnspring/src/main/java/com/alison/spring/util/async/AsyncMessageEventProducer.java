package com.alison.spring.util.async;

import com.lmax.disruptor.EventTranslatorVararg;
import com.lmax.disruptor.RingBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Producer
 */
public class AsyncMessageEventProducer {

    public static final EventTranslatorVararg<AsyncMessageEvent> TRANSLATOR =
            (event, sequence, args) -> {
                if (args[0] instanceof AsyncMessageEvent) {
                    AsyncMessageEvent argEvent = (AsyncMessageEvent) args[0];
                    event.setName(argEvent.getName());
                    event.setAction(argEvent.getAction());
                    event.setData(argEvent.getData());
                    event.setTimestamp(argEvent.getTimestamp());
//                    event.setContext(argEvent.getContext());
                    if (event.getTimestamp() == 0) {
                        event.setTimestamp(System.currentTimeMillis());
                    }
                }
                event.setTimestamp(System.currentTimeMillis());
            };
    private static Logger logger = LoggerFactory.getLogger(AsyncMessageEventProducer.class);
    private final RingBuffer<AsyncMessageEvent> ringBuffer;

    public AsyncMessageEventProducer(RingBuffer<AsyncMessageEvent> ringBuffer) {
        this.ringBuffer = ringBuffer;
    }

    public void publishEvent(Object... args) {
        if (args != null && args.length > 0) {
            ringBuffer.tryPublishEvent(TRANSLATOR, args);
        }
    }
}
