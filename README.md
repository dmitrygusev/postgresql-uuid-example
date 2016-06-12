# postgresql-uuid-example

Sample application to support GitHub issue:

https://github.com/jOOQ/jOOQ/issues/5329

## Usage
1. Create new PostgreSQL database with name `uuid_example`.
  
  ```
  createdb -h localhost -p 5432 -U $USER uuid_example
  ```

2. Clone this project

  ```
  git clone https://github.com/dmitrygusev/postgresql-uuid-example.git
  cd postgresql-uuid-example
  ```

3. Apply database migrations

  ```
  ./gradlew update
  ```

4. Run tests

  ```
  ./gradlew test
  ```
