# Sql-MockData-Generator

- [Project Introduce](https://github.com/Leslie-mo/Sql-MockData-Generator#project-introduce)
- [Features List](https://github.com/Leslie-mo/Sql-MockData-Generator#features-list)
  - [Visualization of table creation](https://github.com/Leslie-mo/Sql-MockData-Generator#visualization-of-table-creation)
  - [One-Click Generation](https://github.com/Leslie-mo/Sql-MockData-Generator#one-click-generation)
    - [SQL (table creation and data insert)](https://github.com/Leslie-mo/Sql-MockData-Generator#sql-table-creation-and-data-insert)
    - [Mock Data](https://github.com/Leslie-mo/Sql-MockData-Generator#mock-data)
    - [JSON Data](https://github.com/Leslie-mo/Sql-MockData-Generator#json-data)
    - [Java Code](https://github.com/Leslie-mo/Sql-MockData-Generator#java-code)
    - [Frontend code](https://github.com/Leslie-mo/Sql-MockData-Generator#frontend-code)
  - [Save Table](https://github.com/Leslie-mo/Sql-MockData-Generator#save-table)
  - [Quick Import Of Table Creation](https://github.com/Leslie-mo/Sql-MockData-Generator#quick-import-of-table-creation)
    - [Import Saved Tables](https://github.com/Leslie-mo/Sql-MockData-Generator#import-saved-tables)
    - [Import Configurations](https://github.com/Leslie-mo/Sql-MockData-Generator#import-configurations)
    - [Import Create Table SQL](https://github.com/Leslie-mo/Sql-MockData-Generator#import-create-table-sql)
    - [Importing Data Excel](https://github.com/Leslie-mo/Sql-MockData-Generator#importing-data-excel)
- [Technique](https://github.com/Leslie-mo/Sql-MockData-Generator#technique)
- [System Design](https://github.com/Leslie-mo/Sql-MockData-Generator#system-design)
  - [Overall Architecture Design](https://github.com/Leslie-mo/Sql-MockData-Generator#overall-architecture-design)
  - [Schema Definition](https://github.com/Leslie-mo/Sql-MockData-Generator#schema-definition)
  - [Generator](https://github.com/Leslie-mo/Sql-MockData-Generator#generator)
    - [Multiple generation types](https://github.com/Leslie-mo/Sql-MockData-Generator#multiple-generation-types)
    - [Multiple mock data generation rules](https://github.com/Leslie-mo/Sql-MockData-Generator#multiple-mock-data-generation-rules)
    - [Unified generation portal](https://github.com/Leslie-mo/Sql-MockData-Generator#unified-generation-portal)
  - [Save Table Service](https://github.com/Leslie-mo/Sql-MockData-Generator#save-table-service)
  - [Table Schema Builder](https://github.com/Leslie-mo/Sql-MockData-Generator#table-schema-builder)

## Project Introduce

  I'm a programmer working in Japan. In Japanese-style development, data tables need to be built according to documents, and a large amount of test data is required. Manual input every time is very troublesome.
  This project is aimed at streamlining the process of building data tables and generating test data in Japanese development. It utilizes an SQL generator to quickly create tables and simulate data, which can then be saved to a database. This eliminates the need for manual input and saves time for developers.



## Features List

#### Visualization of table creation

You can enter data related to table creation in the form, and generate SQL statements to create the table based on the entered database name, table name, field name and field type, etc.

![image-20230128120420352](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128120420352.png) 

New field and field types can be added, as well as four new general fields(id, create_time, update_time, is_delete).

<img src="https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128121012641.png" alt="image-20230128121012641" style="zoom:50%;" />              

<img src="https://github.com/Leslie-mo/README-image-repo/blob/2f2546315e97c5d1c5f81e920b50dc22e8ef998c/Sql-MockData-Generator-image/image-20230128121034478.png" alt="image-20230128121034478" style="zoom:50%;" />

#### One-Click Generation

##### SQL (table creation and data insert)

After adding the data, click the  button to generate the table creation and data insert SQL .

![image-20230128121545167](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128121545167.png) 

![image-20230128122025633](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128122025633.png) 



##### Mock Data

You can also generate the mock data based on the fields, and the data can be downloaded as an excel file.

![image-20230128122929860](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128122929860.png)  

##### JSON Data

![image-20230128123259141](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128123259141.png) 

##### Java Code

You can also generate Java entity class code and Java new entity object code.

![image-20230128123610305](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128123610305.png) 

##### Frontend Code

![image-20230128123922951](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128123922951.png) 



#### Save Table 

You can save this table information and import it directly next time.

 ![image-20230128130112995](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128130112995.png) ![image-20230128130143125](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128130143125.png) 

Saved table an be viewed in the database.

![image-20230128130403940](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128130403940.png) 

#### Quick Import Of Table Creation

##### Import Saved Tables

<img src="https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128130702410.png" alt="image-20230128130702410" style="zoom:50%;" />   ![image-20230128130812320](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128130812320.png) ![image-20230128130926367](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128130926367.png) 

##### Import Configurations

You can import the schema in JSON format and parse it as fields to fill in the form

![image-20230128131102754](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128131102754.png) 

![image-20230128131406272](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128131406272.png) 



  ![image-20230128130926367](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128130926367.png) 

##### Import Create Table SQL

![image-20230128131843930](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128131843930.png) 

![image-20230128131902172](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128131902172.png)

![image-20230128131955324](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128131955324.png) 

##### Importing Data Excel

![image-20230128132252266](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128132252266.png) 

<img src="https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128132119385.png" alt="image-20230128132119385" style="zoom:50%;" />  

![image-20230128132328318](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128132328318.png) 

- #### Multiple Mock Data Generation Rules

  - Fixed values

  - Random values

  - Rule（Regular expressions）

  - Incremental values

    ![image-20230128132510975](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128132510975.png) 

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

#### Overall Architecture Design

The core design concept of this project is to unify all input methods into a clear Schema, and then generate various types of content based on the Schema.

The architectural design diagram is as follows, that is, any input → unified schema→generator→ any output.

![image-20230128133300223](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128133300223.png) 

The system is divided into the following core modules, each module has clear responsibilities:

- Schema definition: it is essentially a Java class (JSON configuration) used to save table and field information
- Generator: Responsible for generating data and code according to Schema
- Save Table: Save table information into database
- Table schema builder: convert various input sources into a unified Table Schema definition



#### Schema Definition

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

##### Multiple Generation types

Define each generation type as a Builder

![image-20230128001610303](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128001610303.png) 

##### Multiple Mock Data Generation Rules

Each generation rule is defined as a Generator, and multiple Generator instances are created and managed uniformly using the DataGeneratorFactory class (factory pattern).

![image-20230128001735865](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128001735865.png) 

##### Unified Generation Portal

Aggregate the various generation types using the facade pattern to provide a uniform generation invocation and validation method.

![image-20230128004519400](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128004519400.png) 

#### Save Table Service

The previously generated table can be saved for next use

![image-20230128004005643](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128004005643.png) 

#### Table Schema Builder

![image-20230128003651900](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128003651900.png) 
