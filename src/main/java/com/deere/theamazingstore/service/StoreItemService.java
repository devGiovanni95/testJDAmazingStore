package com.deere.theamazingstore.service;

import com.deere.theamazingstore.data.model.StoreItemH2TableRow;
import com.deere.theamazingstore.data.repository.StoreItemH2Repository;
import com.deere.theamazingstore.model.StoreItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
  public List<StoreItem> getList() {
    return storeItemH2Repository.findAll().stream().map(this::convertToModel).collect(Collectors.toList());
  }

  public Double getOverallPriceAverage() {
    throw new RuntimeException("Not implemented");
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

    // Se não existir, cria a linha da tabela e salva o novo item
    StoreItemH2TableRow row = new StoreItemH2TableRow(storeItem.getName(), storeItem.getPrice(), storeItem.getType());
    StoreItemH2TableRow savedRow = storeItemH2Repository.save(row);

    // Converte o StoreItemH2TableRow de volta para StoreItem
    return storeItemH2Repository.save(new StoreItem(savedRow.getName(), savedRow.getPrice(), savedRow.getType()));

  }



  public StoreItem update(Integer id, StoreItem storeItem) {
    // TODO: Implement this method
    StoreItem item = getItemById(id);
    item.setName(storeItem.getName());
    item.setType(storeItem.getType());
    item.setPrice(storeItem.getPrice());
    return storeItemH2Repository.save(item);
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
      throw new RuntimeException("Elemento não encontrado para o ID: " + id);
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
    return storeItemH2Repository.findById(i).map(this::convertToModel).orElseThrow(() -> new RuntimeException("Item não encontrado com ID: " + i));
  }
/*
  public Optional<StoreItem> getItemById(Integer id) {
    return storeItemH2Repository.findById(id).map(this::convertToModel);
  }
  */

}
