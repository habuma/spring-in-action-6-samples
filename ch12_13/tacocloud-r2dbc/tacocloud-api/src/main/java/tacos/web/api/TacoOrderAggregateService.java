package tacos.web.api;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;
import tacos.Taco;
import tacos.TacoOrder;
import tacos.data.OrderRepository;
import tacos.data.TacoRepository;

@Service
@RequiredArgsConstructor
public class TacoOrderAggregateService {

  private final TacoRepository tacoRepo;
  private final OrderRepository orderRepo;

  public Mono<TacoOrder> save(TacoOrder tacoOrder) {
    return Mono.just(tacoOrder)
      .flatMap(order -> {
        List<Taco> tacos = order.getTacos();
        order.setTacos(new ArrayList<>());
        return tacoRepo.saveAll(tacos)
            .map(taco -> {
              order.addTaco(taco);
              return order;
            }).last();
      })
      .flatMap(orderRepo::save);
  }

  public Mono<TacoOrder> findById(Long id) {
    return orderRepo
      .findById(id)
      .flatMap(order -> {
        return tacoRepo.findAllById(order.getTacoIds()) // <1>
          .map(taco -> {
            order.addTaco(taco);
            return order;
          }).last();
      });
  }

}
