package com.esrx.services.order.flow;



import com.esrx.services.order.messages.Message;
import com.esrx.services.order.messages.MessageSender;
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
    String correlationId = (String)context.getVariable("correlationId");

    String eventNameString = null;
    if (eventName!=null && eventName.getValue(context)!=null) {
      eventNameString = (String) eventName.getValue(context);
    } else {
      // if not configured we use the element id from the flow definition as default
      eventNameString = context.getCurrentActivityId();
    }

    String payload = (String) context.getVariableLocal("PAYLOAD_"+ eventNameString);
    //"PAYLOAD_" + message.getMessageType(),

    messageSender.send(new Message<Object>( //
        eventNameString, //
        traceId, //
        payload).setCorrelationId(correlationId)); // no payload at the moment
  }

}
