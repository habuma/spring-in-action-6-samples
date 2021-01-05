//tag::recents[]
package tacos.web.api;
//end::recents[]

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.Optional;
//tag::recents[]

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
//end::recents[]
import org.springframework.http.HttpStatus;
//tag::recents[]
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
//end::recents[]
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
//tag::recents[]
import org.springframework.web.bind.annotation.RequestMapping;
//end::recents[]
import org.springframework.web.bind.annotation.ResponseStatus;
//tag::recents[]
import org.springframework.web.bind.annotation.RestController;

import tacos.Taco;
import tacos.data.TacoRepository;

@RestController
@RequestMapping(path="/design",                      // <1>
                produces="application/json")
@CrossOrigin(origins="*")        // <2>
public class DesignTacoController {
  private TacoRepository tacoRepo;
  
//end::recents[]
  @Autowired
  EntityLinks entityLinks;
//tag::recents[]

  public DesignTacoController(TacoRepository tacoRepo) {
    this.tacoRepo = tacoRepo;
  }

  @GetMapping("/recent")
  public Iterable<Taco> recentTacos() {                 //<3>
    PageRequest page = PageRequest.of(
            0, 12, Sort.by("createdAt").descending());
    return tacoRepo.findAll(page).getContent();
  }
  //end::recents[]
  
  /*
  //tag::recentsHateoas1[]
  @GetMapping("/recent")
  public CollectionModel<EntityModel<Taco>> recentTacos() {
    PageRequest page = PageRequest.of(
            0, 12, Sort.by("createdAt").descending());

    List<Taco> tacos = tacoRepo.findAll(page).getContent();
    CollectionModel<EntityModel<Taco>> recentResources = 
    	CollectionModel.wrap(tacos);

    recentResources.add(
        new Link("http://localhost:8080/design/recent", "recents"));
    return recentResources;
  }
  //end::recentsHateoas1[]
  */
  
  
  /*
  @GetMapping("/recent")
  public CollectionModel<EntityModel<Taco>> recentTacos() {
    PageRequest page = PageRequest.of(
            0, 12, Sort.by("createdAt").descending());

    List<Taco> tacos = tacoRepo.findAll(page).getContent();

  	//tag::recentsHateoas2[]
    CollectionModel<EntityModel<Taco>> recentResources = CollectionModel.wrap(tacos);
    recentResources.add(
        WebMvcLinkBuilder.linkTo(DesignTacoController.class)
    					 .slash("recent")
    					 .withRel("recents"));
	//end::recentsHateoas2[]
    return recentResources;
  }
   */
  
  /*
  @GetMapping("/recent")
  public CollectionModel<EntityModel<Taco>> recentTacos() {
    PageRequest page = PageRequest.of(
            0, 12, Sort.by("createdAt").descending());

    List<Taco> tacos = tacoRepo.findAll(page).getContent();

  	//tag::recentsHateoas3[]
    CollectionModel<EntityModel<Taco>> recentResources = CollectionModel.wrap(tacos);
    recentResources.add(
        linkTo(methodOn(DesignTacoController.class).recentTacos())
			 .withRel("recents"));
	//end::recentsHateoas3[]
    return recentResources;
  }
  */
   

//  @GetMapping("/recenth")
//  public Resources<TacoResource> recentTacosH() {
//    PageRequest page = PageRequest.of(
//            0, 12, Sort.by("createdAt").descending());
//    List<Taco> tacos = tacoRepo.findAll(page).getContent();
//    
//    List<TacoResource> tacoResources = 
//        new TacoResourceAssembler().toResources(tacos);
//    Resources<TacoResource> recentResources = 
//        new Resources<TacoResource>(tacoResources);
//    recentResources.add(
//        linkTo(methodOn(DesignTacoController.class).recentTacos())
//        .withRel("recents"));
//    return recentResources;
//  }

  
  
//ControllerLinkBuilder.linkTo(DesignTacoController.class)
//.slash("recent")
//.withRel("recents"));

  
  
  
//  @GetMapping("/recenth")
//  public Resources<TacoResource> recenthTacos() {
//    PageRequest page = PageRequest.of(
//            0, 12, Sort.by("createdAt").descending());
//    List<Taco> tacos = tacoRepo.findAll(page).getContent();
//
//    List<TacoResource> tacoResources = new TacoResourceAssembler().toResources(tacos);
//    
//    Resources<TacoResource> tacosResources = new Resources<>(tacoResources);
////    Link recentsLink = ControllerLinkBuilder
////        .linkTo(DesignTacoController.class)
////        .slash("recent")
////        .withRel("recents");
//
//    Link recentsLink = 
//        linkTo(methodOn(DesignTacoController.class).recentTacos())
//        .withRel("recents");
//    tacosResources.add(recentsLink);
//    return tacosResources;
//  }
  
  //tag::postTaco[]
  @PostMapping(consumes="application/json")
  @ResponseStatus(HttpStatus.CREATED)
  public Taco postTaco(@RequestBody Taco taco) {
    return tacoRepo.save(taco);
  }
  //end::postTaco[]
  
  //tag::tacoByIdSimple[]
  @GetMapping("/{id}")
  public Taco tacoById(@PathVariable("id") Long id) {
    Optional<Taco> optTaco = tacoRepo.findById(id);
    if (optTaco.isPresent()) {
      return optTaco.get();
    }
    return null;
  }
  //end::tacoByIdSimple[]

  /*
  //tag::tacoByIdResponseEntity[]
  @GetMapping("/{id}")
  public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
    Optional<Taco> optTaco = tacoRepo.findById(id);
    if (optTaco.isPresent()) {
      return new ResponseEntity<>(optTaco.get(), HttpStatus.OK);
    }
    return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
  }
  //end::tacoByIdResponseEntity[]
  */

  
//tag::recents[]
}
//end::recents[]

