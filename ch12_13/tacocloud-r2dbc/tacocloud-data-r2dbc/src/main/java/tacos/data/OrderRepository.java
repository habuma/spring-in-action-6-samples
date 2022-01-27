package tacos.data;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import tacos.TacoOrder;

public interface OrderRepository 
       extends ReactiveCrudRepository<TacoOrder, Long> {
}
