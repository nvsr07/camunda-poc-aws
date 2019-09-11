package com.esrx.services.fulfillment.flow;

import com.esrx.services.fulfillment.messages.Message;
import com.esrx.services.fulfillment.messages.MessageSender;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.camunda.bpm.engine.impl.el.FixedValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmitEventAdapter implements JavaDelegate {

  @Autowired
  private MessageSender messageSender;

  /**
   * configured in flow model, so it can be reused at various places
   */
  private FixedValue eventName;

  @Override
  public void execute(DelegateExecution context) throws Exception {
    String traceId = context.getProcessBusinessKey();

    String eventNameString = null;
    if (eventName!=null && eventName.getValue(context)!=null) {
      eventNameString = (String) eventName.getValue(context);
    } else {
      // if not configured we use the element id from the flow definition as default
      eventNameString = context.getCurrentActivityId();
    }
    
    messageSender.send(new Message<Object>( //
        eventNameString, //
        traceId, //
        null)); // no payload at the moment
  }

}
