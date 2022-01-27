package tacos.messaging;

import tacos.TacoOrder;

public interface OrderMessagingService {

  void sendOrder(TacoOrder order);
  
}
