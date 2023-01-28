# SQL And Mock Data Generator

## Project Introduce

  I'm a programmer working in Japan. In Japanese-style development, data tables need to be built according to documents, and a large amount of test data is required. Manual input every time is very troublesome.
  This project is aimed at streamlining the process of building data tables and generating test data in Japanese development. It utilizes an SQL generator to quickly create tables and simulate data, which can then be saved to a database. This eliminates the need for manual input and saves time for developers.



## Features List

#### Visualization of table creation

You can enter data related to table creation in the form, and generate SQL statements to create the table based on the entered database name, table name, field name and field type, etc.

![image-20230128120420352](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128001610303.png) 

New field and field types can be added, as well as four new general fields(id, create_time, update_time, is_delete).

<img src="C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128121012641.png" alt="image-20230128121012641" style="zoom:50%;" />              <img src="C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128121034478.png" alt="image-20230128121034478" style="zoom:50%;" />

#### One-Click Generation

##### SQL (table creation and data insert)

After adding the data, click the  button to generate the table creation and data insert SQL .

![image-20230128121545167](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128121545167.png) 

![image-20230128122025633](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128122025633.png) 



##### Mock Data

You can also generate the mock data based on the fields, and the data can be downloaded as an excel file.

![image-20230128122929860](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128122929860.png)  

##### JSON Data

![image-20230128123259141](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128123259141.png) 

##### Java Code

You can also generate Java entity class code and Java new entity object code.

![image-20230128123610305](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128123610305.png) 

##### Frontend code

![image-20230128123922951](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128123922951.png) 



#### Save Table 

You can save this table information and import it directly next time.

 ![image-20230128130112995](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128130112995.png) ![image-20230128130143125](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128130143125.png) 

Saved table an be viewed in the database.

![image-20230128130403940](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128130403940.png) 

#### Quick import of table creation

##### Import saved tables

<img src="C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128130702410.png" alt="image-20230128130702410" style="zoom:50%;" />   ![image-20230128130812320](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128130812320.png) ![image-20230128130926367](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128130926367.png) 

##### Import configurations

You can import the schema in JSON format and parse it as fields to fill in the form

![image-20230128131102754](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128131102754.png) 

![image-20230128131406272](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128131406272.png) 



  ![image-20230128130926367](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128130926367.png) 

##### Import create table SQL

![image-20230128131843930](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128131843930.png) 

![image-20230128131902172](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128131902172.png)

![image-20230128131955324](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128131955324.png) 

##### Importing data Excel

![image-20230128132252266](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128132252266.png) 

<img src="C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128132119385.png" alt="image-20230128132119385" style="zoom:50%;" />  

![image-20230128132328318](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128132328318.png) 

- #### Multiple simulation data generation rules

  - Fixed values

  - Random values

  - Rule（Regular expressions）

  - Incremental values

    ![image-20230128132510975](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128132510975.png) 

## Technique

Main Technologies.

- Spring Boot 2.7.x

- MyBatis Plus 3.5.x

- MySQL 8.x

  

Additional dependencies include:

- FreeMarker: template engine
- Druid: SQL parsing
- Datafaker: data simulation
- Apache Commons Lang3: utility library
- Hutool: utility library
- Gson: JSON parsing
- Easy Excel: Excel import and export



## System Design

### Overall Architecture Design

The core design concept of this project is to unify all input methods into a clear Schema, and then generate various types of content based on the Schema.

The architectural design diagram is as follows, that is, any input → unified schema→generator→ any output.

![image-20230128133300223](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128133300223.png) 

The system is divided into the following core modules, each module has clear responsibilities:

- Schema definition: it is essentially a Java class (JSON configuration) used to save table and field information
- Generator: Responsible for generating data and code according to Schema
- Save Table: Save table information into database
- Table schema builder: convert various input sources into a unified Table Schema definition



#### Schema definition

Save the table and field information in fixed format JSON with the following structure:

```
​```json
{
  "dbName": "Database Name",
  "tableName": "test_table",
  "tableComment": "Table Comment",
  "mockNum": 20,
  "fieldList": [{
    "fieldName": "username",
    "comment": "User Name",
    "fieldType": "varchar(256)",
    "mockType": "Random",
    "mockParams": "person name",
    "notNull": true,
    "primaryKey": false,
    "autoIncrement": false
  }]
}
​```
```

#### Generator

##### Multiple generation types

Define each generation type as a Builder

![image-20230128001610303](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128001610303.png) 

##### Multiple mock data generation rules

Each generation rule is defined as a Generator, and multiple Generator instances are created and managed uniformly using the DataGeneratorFactory class (factory pattern).

![image-20230128001735865](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128001735865.png) 

##### Unified generation portal

Aggregate the various generation types using the facade pattern to provide a uniform generation invocation and validation method.

![image-20230128004519400](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128004519400.png) 

#### Save Table Service

The previously generated table can be saved for next use

![image-20230128004005643](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128004005643.png) 

#### Table schema builder

![image-20230128003651900](C:\Users\leslie\AppData\Roaming\Typora\typora-user-images\image-20230128003651900.png) 
