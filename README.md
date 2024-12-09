# CRUD - Product and Services List 🛹🚲🛼👩‍🌾🧑‍🔧 🪠

Create an application using Java or ABAP that implements the functionalities to manage the products and services a store sells.
The product data is present on this project, the table name is STORE_ITEM and the database name is theamazingstore. 
You can find the product [table schema](schema.png) in the root directory of this project.

# Instructions
- Clone this repository to your local machine, you can find the step-by-step [**here**](./CLONING_THE_REPO.md).
- Follow the instructions on [running the project](./RUNNING_THE_PROJECT.md) file to set up the project and run it locally.
- The features below have to be implemented in the [StoreItemService](./src/main/java/com/deere/theamazingstore/service/StoreItemService.java) class.
  Note there's one method for each feature. You are free to change the signature of all the methods and create private methods as well.
- To make sure your code is attending to the requirements, the tests in the [StoreItemServiceTest](./src/test/java/com/deere/theamazingstore/service/StoreItemServiceTest.java) class _**should pass**_ ❗❗.
- **Make sure to follow the [DOS AND DON'TS](./DOS_AND_DONTS.md) instructions**.
- Done? **Make sure to follow the [SUBMITTING MY CODE](./SUBMITTING_MY_CODE.md) instructions so we can evaluate your code**.

# Features
## Item List
Given the data provided in the database, return a product list with the product/service name, it's current price and it's type.
### Example:
| NAME                        | PRICE  | TYPE   |
| --------------              | ------ |--------|
| Tire                        | 245.00 |PRODUCT |
| Steering wheel              | 600.30 |PRODUCT |
| Air conditioner maintenance | 204.68 |SERVICE |
| Engine rectification        | 998.02 |SERVICE |
| Rubber hose                 | 32.00  |PRODUCT |
| Car painting                | 250.00 |SERVICE |
| Motor oil                   | 47.00  |PRODUCT |

## Filtering 🔍
Once the product list is returned the users need a filter capability by the name so they can find their desired product/service easily. So, given a string find the item whose name matches the string.
### Example
#### Input:```tenAnce```
### Returns:
| NAME                        | PRICE  |
| --------------------------- | ------ |
| Air conditioner maintenance | 204.68 |

## Sorting ⬆️⬇️
When the user doesn't want to filter the list a sorting functionality is desirable, 
showing the items from the **highest price** to the **lowest price**.
### Example:
| NAME                        | PRICE  | TYPE   |
| --------------              | ------ |--------|
| Engine rectification        | 998.02 |SERVICE |
| Steering wheel              | 600.30 |PRODUCT |
| Car painting                | 250.00 |SERVICE |
| Tire                        | 245.00 |PRODUCT |
| Air conditioner maintenance | 204.68 |SERVICE |
| Motor oil                   | 47.00  |PRODUCT |
| Rubber hose                 | 32.00  |PRODUCT |

## Get an item by its ID ⚾
Given an id from a product/service return the item with the corresponding id.
### Example:
#### Input:```1```
### Returns: ```Tire | 245.00 | PRODUCT```
When the item doesn't exist, return null
### Example:
#### Input:```9999```
### Returns: ```null```

## Price Average 💵
The prices tend to fluctuate during the year and the store owner is very transparent when it comes to its business. 
So, he wants the customers to be aware of the overall average of the prices in the store so they can compare during the year.
Create a code that returns the **overall price average** of all the products and services from the store.

## Min Price 📉
Create a code that returns the **minimum price** from all the products and services from the store.

## Max Price 📈
Implement a feature that returns the **maximum price** from all the products and services from the store.

## Create item 🆕
This feature is very important for the store owner. After all, how brand-new products are going to be launched in the market, right?!
So, create a code where the store owner can input a new product **with all the required fields**:

- Name
- Price
- Type (SERVICE or PRODUCT)

If a product with the same name already exists, then throw an _IllegalArgumentException_ indicating the item already exists.

## Update item 🪄
Sometimes the store owner may want to change an item name or its price. 
Given an existing item id, send an object with the **new price** and the **new name** to update the current data.
If the product doesn't exist, then **throw an error** informing the situation.
In case the new price is double the current price or less than 50% the current price, throw an _IllegalArgumentException_ ```"Price rule not met"```.

## Delete item 🗑️
Sometimes a product or a service life comes to an end and has to be removed from the market.
Given an existing item id, elaborate a method that deletes the item for the incoming id.
If the item doesn't exist, throw an _IllegalArgumentException_: ```"Product or service does not exist"```.

## REST Endpoints 🌐
The features above are not required to be a separate endpoint each **from the start**, but it is desirable that this system gets deployed as a REST API.
<br>Given the functionalities you developed, make them available through REST endpoints.<br>
**Remember to use the correct HTTP method for each operation**.
### Calling the endpoints locally
Feel free to install and use any tool you like to test the endpoints, like:
- [Postman](https://www.postman.com/)
- [Insomnia](https://insomnia.rest/)
- [Curl](https://curl.se/)
- [Bruno](https://www.usebruno.com/downloads)
- Or even the browser console itself
