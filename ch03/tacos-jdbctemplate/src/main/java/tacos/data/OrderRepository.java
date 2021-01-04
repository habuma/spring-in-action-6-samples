//tag::allButFindById[]
package tacos.data;

import java.util.Optional;

import tacos.TacoOrder;

public interface OrderRepository {

  TacoOrder save(TacoOrder order);
//end::allButFindById[]
  
  Optional<TacoOrder> findById(Long id);
  
//tag::allButFindById[]
  
}
//end::allButFindById[]