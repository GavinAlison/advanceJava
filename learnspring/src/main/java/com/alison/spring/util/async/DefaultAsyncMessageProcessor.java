
package com.alison.spring.util.async;


public class DefaultAsyncMessageProcessor extends AbstractAsyncMessageProcessor {

//    private static Logger logger = LoggerFactory.getLogger(DefaultAsyncMessageProcessor.class);

    @Subscribe(Constants.EVENT_INFERENCE)
    public void processInferenceEvent(AsyncMessageEvent event) {
//        logger.info("Process inference event..");
    }

    @Subscribe(Constants.EVENT_UNARYCALL)
    public void processUnaryCallEvent(AsyncMessageEvent event) {
//        logger.info("Process unaryCall event..");
    }

    @Subscribe(Constants.EVENT_ERROR)
    public void processErrorEvent(AsyncMessageEvent event) {
//        logger.info("Process error event..");
    }

}
