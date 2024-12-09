package com.deere.theamazingstore.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.deere.theamazingstore.data.model.StoreItemH2TableRow;
import com.deere.theamazingstore.data.repository.StoreItemH2Repository;
import com.deere.theamazingstore.model.StoreItem;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * !! WARNING !!
 * DO NOT CHANGE THE VALIDATIONS IN THE TESTS
 */
@ExtendWith(MockitoExtension.class)
class StoreItemServiceTest {
  @Mock
  private StoreItemH2Repository storeItemH2Repository;

  @InjectMocks
  private StoreItemService storeItemService;

  @Test
  @DisplayName("Should get an item by its id")
  void shouldGetAnItemById() {
    // Given
    StoreItemH2TableRow secondRow = new StoreItemH2TableRow(2, "Service 1", 56.21, "SERVICE");
    // When
    given(storeItemH2Repository.findById(2)).willReturn(Optional.of(secondRow));

    // Then
    StoreItem actualItem = storeItemService.getItemById(2);

    // Assert expected returned Item
    StoreItem expectedItem = new StoreItem("Service 1", 56.21, "SERVICE");
    assertEquals(expectedItem, actualItem);

    // Assert findAll is called
    verify(storeItemH2Repository).findById(2);
  }

  @Test
  @DisplayName("Should return null if item does not exist")
  void shouldReturnNullIfItemDoesNotExist() {
    // Given
    // When
    given(storeItemH2Repository.findById(2)).willReturn(Optional.empty());

    // Then
    StoreItem actualItem = storeItemService.getItemById(2);

    // Assert returned item is Null
    assertNull(actualItem);

    // Assert findAll is called
    verify(storeItemH2Repository).findById(2);
  }

  @Test
  @DisplayName("Should get all Store Items")
  void shouldGetAllStoreItems() {
    // Given
    List<StoreItemH2TableRow> rowsFromDatabase = List.of(
        new StoreItemH2TableRow(1, "Product 1", 10.50, "PRODUCT"),
        new StoreItemH2TableRow(2, "Service 1", 56.21, "SERVICE"),
        new StoreItemH2TableRow(3, "Product 2", 14.50, "PRODUCT")
    );
    // When
    given(storeItemH2Repository.findAll()).willReturn(rowsFromDatabase);

    // Then
    List<StoreItem> actualList = storeItemService.getList();

    // Assert expected returned list
    List<StoreItem> expectedList = List.of(
        new StoreItem("Product 1", 10.50, "PRODUCT"),
        new StoreItem("Service 1", 56.21, "SERVICE"),
        new StoreItem("Product 2", 14.50, "PRODUCT")
    );
    assertEquals(expectedList, actualList);

    // Assert findAll is called
    verify(storeItemH2Repository).findAll();
  }

  @Test
  @DisplayName("Should filter items by partial name")
  void shouldFilterItemsByName() {
    // Given
    List<StoreItemH2TableRow> rowsFromDatabase = List.of(
        new StoreItemH2TableRow(1, "Product 1", 10.50, "PRODUCT"),
        new StoreItemH2TableRow(2, "Service 1", 56.21, "SERVICE"),
        new StoreItemH2TableRow(3, "Product 2", 14.50, "PRODUCT")
    );
    // When
    given(storeItemH2Repository.findAll()).willReturn(rowsFromDatabase);

    // Then
    String input = "vicE";
    List<StoreItem> actualList = storeItemService.getList();

    // Assert filtered list
    List<StoreItem> expectedList = List.of(
        new StoreItem("Service 1", 56.21, "SERVICE")
    );
    assertEquals(expectedList, actualList);

    // Assert findAll is called
    verify(storeItemH2Repository).findAll();
  }

  @Test
  @DisplayName("Should sort items by price in descending order")
  void shouldSortItems() {
    // Given
    List<StoreItemH2TableRow> rowsFromDatabase = List.of(
        new StoreItemH2TableRow(1, "Product 1", 10.50, "PRODUCT"),
        new StoreItemH2TableRow(2, "Service 1", 56.21, "SERVICE"),
        new StoreItemH2TableRow(3, "Product 2", 14.50, "PRODUCT")
    );
    // When
    given(storeItemH2Repository.findAll()).willReturn(rowsFromDatabase);

    // Then
    List<StoreItem> actualList = storeItemService.getList();

    // Assert sorted list
    List<StoreItem> expectedList = List.of(
        new StoreItem("Service 1", 56.21, "SERVICE"),
        new StoreItem("Product 2", 14.50, "PRODUCT"),
        new StoreItem("Product 1", 10.50, "PRODUCT")
    );
    assertEquals(expectedList, actualList);

    // Assert findAll is called
    verify(storeItemH2Repository).findAll();
  }

  @Test
  @DisplayName("Should get overall price average")
  void shouldGetOverallPriceAverage() {
    // Given
    List<StoreItemH2TableRow> rowsFromDatabase = List.of(
        new StoreItemH2TableRow(1, "Product 1", 10.50, "PRODUCT"),
        new StoreItemH2TableRow(2, "Service 1", 56.21, "SERVICE"),
        new StoreItemH2TableRow(3, "Product 2", 14.50, "PRODUCT")
    );
    // When
    given(storeItemH2Repository.findAll()).willReturn(rowsFromDatabase);

    // Then
    Double priceAverage = storeItemService.getOverallPriceAverage();

    // Assert expected price average
    assertEquals("27.07", String.format("%.2f", priceAverage));

    // Assert findAll is called
    verify(storeItemH2Repository).findAll();
  }

  @Test
  @DisplayName("Should get the item with smallest price (MIN)")
  void shouldGetMinimumPrice() {
    // Given
    List<StoreItemH2TableRow> rowsFromDatabase = List.of(
        new StoreItemH2TableRow(1, "Product 1", 10.50, "PRODUCT"),
        new StoreItemH2TableRow(2, "Service 1", 56.21, "SERVICE"),
        new StoreItemH2TableRow(3, "Product 2", 14.50, "PRODUCT")
    );
    // When
    given(storeItemH2Repository.findAll()).willReturn(rowsFromDatabase);

    // Then
    Double minPrice = storeItemService.getMinPrice();

    // Assert expected MIN
    assertEquals("10,50", String.format("%.2f", minPrice));

    // Assert findAll is called
    verify(storeItemH2Repository).findAll();
  }

  @Test
  @DisplayName("Should get the item with greatest price (MAX)")
  void shouldGetMaximumPrice() {
    // Given
    List<StoreItemH2TableRow> rowsFromDatabase = List.of(
        new StoreItemH2TableRow(1, "Product 1", 10.50, "PRODUCT"),
        new StoreItemH2TableRow(2, "Service 1", 56.21, "SERVICE"),
        new StoreItemH2TableRow(3, "Product 2", 14.50, "PRODUCT")
    );
    // When
    given(storeItemH2Repository.findAll()).willReturn(rowsFromDatabase);

    // Then
    Double maxPrice = storeItemService.getMaxPrice();

    // Assert expected MAX
    //assertEquals("56.21", String.format("%.2f", maxPrice));
    assertEquals(56.21, maxPrice);

    // Assert findAll is called
    verify(storeItemH2Repository).findAll();
  }

  @Test
  @DisplayName("Should create an item if it does not exist yet")
  void shouldCreateAnItem() {
    // Given
    given(storeItemH2Repository.findByName("Product 1")).willReturn(Optional.empty());
    given(storeItemH2Repository.save(any(StoreItemH2TableRow.class))).willReturn(
        new StoreItemH2TableRow(1, "Product 1", 10.50, "PRODUCT"));

    // When
    StoreItem storeItem = new StoreItem("Product 1", 10.50, "PRODUCT");

    // Then
    StoreItem createdItem = storeItemService.create(storeItem);

    // Assert created item
    assertEquals(storeItem, createdItem);

    // Assert findByName is called
    verify(storeItemH2Repository).findByName("Product 1");

    // Assert save is called
    verify(storeItemH2Repository).save(any(StoreItemH2TableRow.class));
  }

  @Test
  @DisplayName("Should throw IllegalArgumentException if item already exists when creating")
  void shouldThrowAnExceptionIfItemAlreadyExists() {
    // Given
    StoreItemH2TableRow existingProduct = new StoreItemH2TableRow(1, "Product 1", 10.50, "PRODUCT");
    given(storeItemH2Repository.findByName("Product 1")).willReturn(Optional.of(existingProduct));

    // When
    StoreItem storeItem = new StoreItem("Product 1", 10.50, "PRODUCT");

    // Assert exception is thrown
    assertThrows(IllegalArgumentException.class, () -> storeItemService.create(storeItem));

    // Assert findByName is called
    verify(storeItemH2Repository).findByName("Product 1");

    // Assert save is NEVER called
    verify(storeItemH2Repository, never()).save(any(StoreItemH2TableRow.class));
  }

  @ParameterizedTest
  @ValueSource(strings = {"potato", "product", "service", "PrOdUcT", "SeRvIcE"})
  @DisplayName("Should throw IllegalArgumentException if desired item type is not PRODUCT or SERVICE")
  void shouldNotCreateAnItemWhoseTypeIsNeitherProductNorService(String itemType) {
    // Given
    given(storeItemH2Repository.findByName("Product 1")).willReturn(Optional.empty());

    // When
    StoreItem storeItem = new StoreItem("Product 1", 10.50, itemType);

    // Assert exception is thrown
    assertThrows(IllegalArgumentException.class, () -> storeItemService.create(storeItem));

    // Assert findByName is called
    verify(storeItemH2Repository).findByName("Product 1");

    // Assert save is NEVER called
    verify(storeItemH2Repository, never()).save(any(StoreItemH2TableRow.class));
  }

  @Test
  @DisplayName("Should update an existing item")
  void shouldUpdateAnExistingItem() {
    // Given
    StoreItemH2TableRow existingItem = new StoreItemH2TableRow(1, "Product 1", 10.50, "PRODUCT");
    given(storeItemH2Repository.findById(1)).willReturn(Optional.of(existingItem));
    given(storeItemH2Repository.save(any(StoreItemH2TableRow.class))).willReturn(
        new StoreItemH2TableRow(1, "Product 1 - UPDATED", 12.60, "PRODUCT"));

    // When
    StoreItem storeItem = new StoreItem("Product 1 - UPDATED", 12.60, "PRODUCT");

    // Then
    StoreItem updatedItem = storeItemService.update(1, storeItem);

    // Assert created item
    assertEquals(storeItem, updatedItem);

    // Assert findById is called
    verify(storeItemH2Repository).findById(1);

    // Assert save is called
    verify(storeItemH2Repository).save(any(StoreItemH2TableRow.class));
  }

  @Test
  @DisplayName("Should throw IllegalArgumentException if item does not exist when updating")
  void shouldNotUpdateAnItemThatDoesNotExist() {
    // Given
    given(storeItemH2Repository.findById(2)).willReturn(Optional.empty());

    // When
    StoreItem storeItem = new StoreItem("Product 2 - UPDATED", 12.60, "PRODUCT");

    // Assert exception is thrown
    assertThrows(IllegalArgumentException.class, () -> storeItemService.update(2, storeItem));

    // Assert findById is called
    verify(storeItemH2Repository).findById(2);

    // Assert save is NEVER called
    verify(storeItemH2Repository, never()).save(any(StoreItemH2TableRow.class));
  }

  @Test
  @DisplayName("Should throw IllegalArgumentException if new price is double of the current price when updating")
  void shouldNotUpdateWithDoubleItsCurrentPrice() {
    // Given
    StoreItemH2TableRow existingItem = new StoreItemH2TableRow(1, "Product 1", 10.50, "PRODUCT");
    given(storeItemH2Repository.findById(1)).willReturn(Optional.of(existingItem));

    // When
    StoreItem storeItem = new StoreItem("Product 2 - UPDATED", 21.0, "PRODUCT");

    // Assert exception is thrown
    assertThrows(IllegalArgumentException.class, () -> storeItemService.update(1, storeItem));

    // Assert findById is called
    verify(storeItemH2Repository).findById(1);

    // Assert save is NEVER called
    verify(storeItemH2Repository, never()).save(any(StoreItemH2TableRow.class));
  }

  @Test
  @DisplayName("Should throw IllegalArgumentException if new price is greater than the double of the current price when updating")
  void shouldNotUpdateWithGreaterThanDoubleItsCurrentPrice() {
    // Given
    StoreItemH2TableRow existingItem = new StoreItemH2TableRow(1, "Product 1", 10.50, "PRODUCT");
    given(storeItemH2Repository.findById(1)).willReturn(Optional.of(existingItem));

    // When
    StoreItem storeItem = new StoreItem("Product 2 - UPDATED", 100.0, "PRODUCT");

    // Assert exception is thrown
    assertThrows(IllegalArgumentException.class, () -> storeItemService.update(1, storeItem));

    // Assert findById is called
    verify(storeItemH2Repository).findById(1);

    // Assert save is NEVER called
    verify(storeItemH2Repository, never()).save(any(StoreItemH2TableRow.class));
  }

  @Test
  @DisplayName("Should throw IllegalArgumentException if new price is half the current price when updating")
  void shouldNotUpdateWhenPriceIsHalfTheCurrentPrice() {
    // Given
    StoreItemH2TableRow existingItem = new StoreItemH2TableRow(1, "Product 1", 10.50, "PRODUCT");
    given(storeItemH2Repository.findById(1)).willReturn(Optional.of(existingItem));

    // When
    StoreItem storeItem = new StoreItem("Product 2 - UPDATED", 5.25, "PRODUCT");

    // Assert exception is thrown
    assertThrows(IllegalArgumentException.class, () -> storeItemService.update(1, storeItem));

    // Assert findById is called
    verify(storeItemH2Repository).findById(1);

    // Assert save is NEVER called
    verify(storeItemH2Repository, never()).save(any(StoreItemH2TableRow.class));
  }

  @Test
  @DisplayName("Should throw IllegalArgumentException if new price is less than half the current price when updating")
  void shouldNotUpdateWhenPriceIsLessThanHalfTheCurrentPrice() {
    // Given
    StoreItemH2TableRow existingItem = new StoreItemH2TableRow(1, "Product 1", 10.50, "PRODUCT");
    given(storeItemH2Repository.findById(1)).willReturn(Optional.of(existingItem));

    // When
    StoreItem storeItem = new StoreItem("Product 2 - UPDATED", 0.45, "PRODUCT");

    // Assert exception is thrown
    assertThrows(IllegalArgumentException.class, () -> storeItemService.update(1, storeItem));

    // Assert findById is called
    verify(storeItemH2Repository).findById(1);

    // Assert save is NEVER called
    verify(storeItemH2Repository, never()).save(any(StoreItemH2TableRow.class));
  }

  @Test
  @DisplayName("Should delete an existing item")
  void shouldDeleteAnExistingItem() throws Exception {
    // Given
    StoreItemH2TableRow existingItem = new StoreItemH2TableRow(1, "Product 1", 10.50, "PRODUCT");
    given(storeItemH2Repository.findById(1)).willReturn(Optional.of(existingItem));

    // When
    storeItemService.delete(1);

    // Assert findById is called
    verify(storeItemH2Repository).findById(1);

    // Assert deleteById is called
    verify(storeItemH2Repository).deleteById(1);
  }

  @Test
  @DisplayName("Should not delete an item if it does not exist")
  void shouldNotDeleteAnItemThatDoesNotExist() {
    // Given
    given(storeItemH2Repository.findById(1)).willReturn(Optional.empty());

    // Assert exception is thrown
    assertThrows(IllegalArgumentException.class, () -> storeItemService.delete(1));

    // Assert findById is called
    verify(storeItemH2Repository).findById(1);

    // Assert deleteById is NEVER called
    verify(storeItemH2Repository, never()).deleteById(1);
  }
}