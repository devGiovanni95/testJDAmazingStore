package com.deere.theamazingstore.data.repository;

import com.deere.theamazingstore.data.model.StoreItemH2TableRow;
import java.util.List;
import java.util.Optional;

import com.deere.theamazingstore.model.StoreItem;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;

public interface StoreItemH2Repository extends CrudRepository<StoreItemH2TableRow, Integer> {
  @NonNull
  List<StoreItemH2TableRow> findAll();

  @NonNull
  Optional<StoreItemH2TableRow> findByName(@NonNull String name);

  @NonNull
  Optional<StoreItemH2TableRow> findById(@NonNull Integer id);

  @Override
  @NonNull
  StoreItemH2TableRow save(@NonNull StoreItemH2TableRow item);

  @Override
  void deleteById(@NonNull Integer id);

  StoreItem save(StoreItem item);

}
