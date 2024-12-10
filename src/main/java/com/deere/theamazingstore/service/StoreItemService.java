package com.deere.theamazingstore.service;

import com.deere.theamazingstore.data.model.StoreItemH2TableRow;
import com.deere.theamazingstore.data.repository.StoreItemH2Repository;
import com.deere.theamazingstore.model.StoreItem;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implement the methods to make StoreItemServiceTest pass.
 * Feel free to change the methods signature if you think it's necessary.
 */
@Service
public class StoreItemService {

  private final StoreItemH2Repository storeItemH2Repository;

  @Autowired
  public StoreItemService(StoreItemH2Repository storeItemH2Repository) {
    this.storeItemH2Repository = storeItemH2Repository;
  }

  private StoreItem convertToModel(StoreItemH2TableRow row) {
    return new StoreItem(row.getName(), row.getPrice(), row.getType());
  }
  public List<StoreItem> getListSorted() {
    return storeItemH2Repository.findAll().stream()
            .map(this::convertToModel)
            .sorted(Comparator.comparing(StoreItem::getPrice).reversed())
            .collect(Collectors.toList());
  }

    public List<StoreItem> getList() {
        return storeItemH2Repository.findAll().stream()
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }

    public List<StoreItem> getListFilter(String type) {
        return storeItemH2Repository.findAll().stream()
                .filter(item -> { return  item.getType().toLowerCase().contains(type.toLowerCase());})
                .map(this::convertToModel)
                .collect(Collectors.toList());
    }

  //rever
  private  StoreItem getItemById(Integer id){
    StoreItemH2TableRow existingItem = storeItemH2Repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Item not found"));

     StoreItem item = new StoreItem(
             existingItem.getName(),
             existingItem.getPrice(),
             existingItem.getType()
    );
    return null;
  }

  public Double getOverallPriceAverage() {
      return storeItemH2Repository.findAll().stream()
              .map(this::convertToModel) // Convert the data to the model
              .mapToDouble(StoreItem::getPrice) // Map each item to its price
              .average() // Calculate the average of the prices
              .orElse(0.0); // If the list is empty, return 0.0 as the default value
  }
/*
  public StoreItem create(StoreItem storeItem) {
    // TODO: Implement this method
      StoreItemH2TableRow row =  new StoreItemH2TableRow(storeItem.getName(), storeItem.getPrice(), storeItem.getType() );
    StoreItem store = new StoreItem(row.getName(), row.getPrice(), row.getType());
//       return storeItemH2Repository.save(store);

    Optional<StoreItem> existingItem = storeItemH2Repository.findAll().stream()
            .filter(item -> item.getName().equals(store.getName())) // Supondo que 'nome' seja o campo único
            .findFirst().map(this::convertToModel);

    if (existingItem.isPresent()) {
        try {
            throw new Exception("Um item com o mesmo nome já existe."); // Ou lance uma exceção personalizada
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Se não existir, salva o novo item
    return storeItemH2Repository.save(store);
  }
*/

  public StoreItem create(StoreItem storeItem) {
    // Verifica se já existe um item com o mesmo nome
    Optional<StoreItemH2TableRow> existingItem = storeItemH2Repository.findByName(storeItem.getName());

    if (existingItem.isPresent()) {
      throw new IllegalArgumentException("Um item com o mesmo nome já existe.");
    }
    if (storeItem.getType().equals("PRODUCT") || storeItem.getType().equals("SERVICE")){
        // Se não existir, cria a linha da tabela e salva o novo item
        StoreItemH2TableRow row = new StoreItemH2TableRow(storeItem.getName(), storeItem.getPrice(), storeItem.getType());
        StoreItemH2TableRow savedRow = storeItemH2Repository.save(row);

        // Converte o StoreItemH2TableRow de volta para StoreItem
        return new StoreItem(savedRow.getName(), savedRow.getPrice(), savedRow.getType());

    }else {
      throw new IllegalArgumentException("type is not PRODUCT or SERVICE");

    }


  }

  public StoreItem update(Integer id, StoreItem storeItem) {

    // Retrieve the existing item from the repository
    StoreItemH2TableRow existingItem = storeItemH2Repository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Item not found"));

    // TODO: Implement this method
    double aux = existingItem.getPrice() / 2;

    if(storeItem.getPrice() <= aux ){
      throw new IllegalArgumentException("The new price is half the current price when updating");
    }

    if((existingItem.getPrice() * 2) <= storeItem.getPrice()  ){
        throw new IllegalArgumentException("The new price is double of the current price when updating or greater than the double of the current price when updating");
    }

  //StoreItem item = getItemById(id);
    existingItem.setName(storeItem.getName());
    existingItem.setType(storeItem.getType());
    existingItem.setPrice(storeItem.getPrice());


      StoreItemH2TableRow updatedItem = storeItemH2Repository.save(existingItem);

    return new StoreItem(
            updatedItem.getName(),
            updatedItem.getPrice(),
            updatedItem.getType()
    );
  }

  public void delete(Integer id) throws Exception {
    if (id == null) {
      throw new IllegalArgumentException("ID não pode ser nulo.");
    }

    // Tenta encontrar o item antes de deletá-lo
    Optional<StoreItemH2TableRow> existingItem = storeItemH2Repository.findById(id);

    if (existingItem.isPresent()) {
      // Se o item existe, chama o método deleteById
      storeItemH2Repository.deleteById(id);
    } else {
      // Caso o item não exista, você pode optar por lançar uma exceção ou apenas retornar
      throw new IllegalArgumentException("Elemento não encontrado para o ID: " + id);
    }
  }

  public Double getMinPrice() {
    List<StoreItemH2TableRow> allItems = storeItemH2Repository.findAll();
    return allItems.stream().map(StoreItemH2TableRow::getPrice).min(Double::compareTo).orElse(0.0);
  }

  public Double getMaxPrice() {
    List<StoreItemH2TableRow> allItems = storeItemH2Repository.findAll();
    return allItems.stream().map(StoreItemH2TableRow::getPrice).max(Double::compareTo).orElse(0.0);
  }

  public StoreItem getItemById(int i) {
  return storeItemH2Repository.findById(i).map(this::convertToModel).orElse(null);
  }
/*
  public Optional<StoreItem> getItemById(Integer id) {
    return storeItemH2Repository.findById(id).map(this::convertToModel);
  }
  */

}
