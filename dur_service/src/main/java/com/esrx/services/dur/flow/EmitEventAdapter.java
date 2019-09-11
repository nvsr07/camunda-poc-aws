package com.esrx.services.dur.flow;

import com.esrx.services.dur.messages.Message;
import com.esrx.services.dur.messages.MessageSender;

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
    
    messageSender.send(new Message<>( //
        eventNameString, //
        traceId, //
        null)); // no payload at the moment
  }

}
