# Product Service

Este microservicio forma parte del sistema de eCommerce distribuido y se encarga de gestionar los productos de la 
aplicación.

## 📦 Tecnologías utilizadas

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Validation
- MySQL
- Feign Client
- Eureka Client
- Lombok

## ⚙️ Endpoints principales

| Método | Ruta                                       | Descripción                  |
|--------|--------------------------------------------|------------------------------|
| POST   | /api/product                               | Crear un nuevo Producto      |
| GET    | /api/product/all                           | Buscar todos los Productos   |
| GET    | /api/product/find/{typeProduct}            | Buscar un Producto por Tipo  |
| PUT    | /api/product/update/{id}                   | Modificar un Producto        |
| DELETE | /api/product/delete/{id}                   | Borrar un Producto por ID    |
| GET    | /api/product/type/{id}                     | Buscar un Producto por ID    |
| GET    | /api/product/{id}/{quantity}               | Verificar Stock de Productos |
| POST   | /api/product/subQuantity/{id}/{quantity}   | Actualiza el Stock           |

## 🗃️ Base de datos

Este servicio utiliza una base de datos MySQL. Las entidades principales son:

- `Crear un nuevo Producto` (name, description, price,quantity,typeProduct).
- `Buscar todos los Productos` (todos los productos en la base de datos).
- `Buscar un Producto por Tipo` (typeProduct en la URL de Postman), los tipos de Productos estan aqui -->  `src/main/java/com/productService/productService/enums/TypeProductEnum.java` .
- `Actualizar un Producto` (name, description, price,quantity,typeProduct), modifica el que necesites.
- `Eliminar un Producto` por id (id en la Url de Postman).
- `Buscar un Producto` por id (id en la Url de Postman), Usa Feing Clients.
- `Verificar Stock de Productos` (id y quantity en la Url de Postman) Usa Feing Clients.
- `Actualiza el Stock` (id y quantity en la Url de Postman), Usa Feing Clients.


## 🔌 Comunicación con otros servicios

Este servicio es **cliente de Eureka** y se comunica con otros microservicios mediante **Feign Clients**.

## 🧪 Tests

Incluye:

- Pruebas unitarias con JUnit y Mockito.
- Validaciones de endpoints con MockMvc.
- Pruebas de integración con @SpringBootTest.
- Ejecuta los tests con Maven:
  ```bash
  mvn test
  ```

## 📫 Instrucciones para ejecutar el servicio
1. Clona el repositorio.
2. Configura el archivo `application.properties`  con los datos de conexión a la base de datos MySQL.
3. Asegúrate de que el servidor Eureka esté corriendo.
4. Asegúrate que el Gateway esté corriendo.
5. Asegúrate de que los demás micro servicios están corriendo.
5. Asegúrate de que la base de datos MySQL esté corriendo.
6. Ejecuta el servicio con tu IDE o usando Maven:
   ```bash
   mvn spring-boot:run
   ```


## 	📁 EndPoints
- Puedes ver todos los endPoints en `http://localhost:8082/api/user` una vez que el servicio esté corriendo.


## 🧪 Postman

- Usa Postman o cualquier cliente HTTP para probar los endpoints, Puedes acceder a las colección en el repositorio
  Principal de la apilcacion `.github/static/e-commerce.postman_collection.json`.
- Tienes un Json para crear productos en Postman `src/main/resources/Json/NewProducts.json`.

## 🗂️ Repositorio Principal
- 🔗 [GitHub Organization](https://github.com/IronHackProject)

## 👨‍💻 Autor
-[DevJerryX](https://github.com/planetWeb252)