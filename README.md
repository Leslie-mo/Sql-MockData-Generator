

# SQL & Mock Data Generator

  * [Project Introduction](#project-introduction)
  * [Demo](#demo)
  * [Getting Started](#getting-started)
  * [Features List](#features-list)
      - [Create Tables with an Interface](#create-tables-with-an-interface)
      - [Generate SQL Queries with One Click](#generate-sql-queries-with-one-click)
        * [SQL (Table Creation and Fata Insertion)](#sql--table-creation-and-fata-insertion-)
        * [Mock Data](#mock-data)
        * [JSON Data](#json-data)
        * [Java Code](#java-code)
        * [Frontend Code](#frontend-code)
      - [Save Table](#save-table)
      - [Quick Import of Table Creation](#quick-import-of-table-creation)
        * [Import Saved Tables](#import-saved-tables)
        * [Import Configurations](#import-configurations)
        * [Import and Create Table SQL](#import-and-create-table-sql)
        * [Importing Data Excel](#importing-data-excel)
      - [Multiple Mock Data Generation Rules](#multiple-mock-data-generation-rules)
  * [Technologies](#technologies)
  * [System Design](#system-design)
      - [Overall Architectural Design](#overall-architectural-design)
      - [Schema Definition](#schema-definition)
      - [Generator](#generator)
        * [Multiple Generation Types](#multiple-generation-types)
        * [Multiple Mock Data Generation Rules](#multiple-mock-data-generation-rules-1)
        * [Unified Generation Portal](#unified-generation-portal)
      - [Save Table Service](#save-table-service)
      - [Table Schema Builder](#table-schema-builder)

## Project Introduction

As a programmer in Japan, I have experienced firsthand the inefficiencies and repetition in Japanese software development through my work. The technical documentation dictates how the data tables should be constructed and the testing phase requires a large amount of manual data input. To meet these requirements, programmers often spend a lot of time inputting data manually. 

To address this issue, I came up with the idea of creating a SQL Query & Mock Data Generator. This project aims to simplify the process of building data tables and generating test data in Japanese development by using an SQL generator to create tables and simulate data quickly, which can then be saved to a database. The goal of my project is to eliminate the need for manual input and save time for programmers.

## Demo

http://54.199.168.104/

## Getting Started

1. Get the backend code by cloning it from https://github.com/Leslie-mo/Sql-MockData-Generator
2. To create the "table_info" table, execute the create_table.sql file located in the sql directory.
3. Change the database configuration in application.yml to match your own database.

```
# Database Configuration
datasource:
  driver-class-name: com.mysql.cj.jdbc.Driver
  url: jdbc:mysql://localhost:3306/sqlfather
  username: root
  password: 123456
```

4. Start the MainApplication.
5. Obtain the frontend code by cloning it from https://github.com/Leslie-mo/Sql-MockData-Generator-Front
6. Install the necessary dependencies.

```
npm run install
```

7. Launch the application.

```
npm run dev
```

8. The app will be available at [http://localhost:8000](http://localhost:8000/).

## Features List

#### Create Tables with an Interface

Fill out the form with information about the table you want to create. Using the information you provide about the database name, table name, field names, and field types, the tool will generate the necessary SQL statements to create the table.

![image-20230128120420352](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128120420352.png) 

You can add additional fields and field types, as well as include four default fields: id, create_time, update_time, and is_delete.

<img src="https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128121012641.png" alt="image-20230128121012641" style="zoom:50%;" />              

<img src="https://github.com/Leslie-mo/README-image-repo/blob/2f2546315e97c5d1c5f81e920b50dc22e8ef998c/Sql-MockData-Generator-image/image-20230128121034478.png" alt="image-20230128121034478" style="zoom:50%;" />

#### Generate SQL Queries with One Click

##### SQL (Table Creation and Fata Insertion)

Once you have entered the data, click the button to generate the SQL statements for creating the table and inserting the data.

![image-20230128121545167](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128121545167.png) 

![image-20230128122025633](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128122025633.png) 

##### Mock Data

The tool also allows you to generate mock data based on the fields, which can be saved as an Excel file.

![image-20230128122929860](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128122929860.png)  

##### JSON Data

![image-20230128123259141](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128123259141.png) 

##### Java Code

Additionally, the tool can generate Java entity class code and code for creating a new entity object in Java.

![image-20230128123610305](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128123610305.png) 

##### Frontend Code

![image-20230128123922951](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128123922951.png) 



#### Save Table 

The information in this table can be saved for direct import in the future.

 ![image-20230128130112995](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128130112995.png) ![image-20230128130143125](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128130143125.png) 

The table that has been saved can be seen within the database.

![image-20230128130403940](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128130403940.png) 

#### Quick Import of Table Creation

##### Import Saved Tables

<img src="https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128130702410.png" alt="image-20230128130702410" style="zoom:50%;" />   ![image-20230128130812320](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128130812320.png) ![image-20230128130926367](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128130926367.png) 

##### Import Configurations

The schema can be imported in JSON format and then parsed into fields to complete the form.

![image-20230128131102754](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128131102754.png) 

![image-20230128131406272](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128131406272.png) 



  ![image-20230128130926367](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128130926367.png) 

##### Import and Create Table SQL

![image-20230128131843930](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128131843930.png) 

![image-20230128131902172](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128131902172.png)

![image-20230128131955324](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128131955324.png) 

##### Importing Data Excel

Select a local data Excel.

![image-20230128132252266](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128132252266.png) 

The first row of the table is the database column name. And the second row is the data.

<img src="https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128132119385.png" alt="image-20230128132119385" style="zoom:30%;" />  

![image-20230128132328318](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128132328318.png) 

- #### Multiple Mock Data Generation Rules

  - Fixed values

  - Random values

  - Rule（Regular expressions）

  - Incremental values

    ![image-20230128132510975](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128132510975.png) 

## Technologies

Main Technologies:

- Spring Boot 2.7.x

- MyBatis Plus 3.5.x

- MySQL 8.x


Additional Dependencies:

- FreeMarker: template engine
- Druid: SQL parsing
- Datafaker: data simulation
- Apache Commons Lang3: utility library
- Hutool: utility library
- Gson: JSON parsing
- Easy Excel: Excel import and export

## System Design

#### Overall Architectural Design

The main idea behind this project is to bring together all forms of input into a single, well-defined Schema, and from there, produce a range of outputs based on that Schema. 

The design process can be visualized as follows: inputs are collected and standardized into the Schema, which then feeds into a generator to produce any desired output.

![image-20230128133300223](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128133300223.png) 

The system is organized into several key modules, each with a well-defined purpose:

- Schema definition: this module encompasses a Java class, represented in JSON configuration, that serves to store table and field information.
- Generator: This module is responsible for producing data and code based on the Schema definition.
- Save Table: This module saves table information into the database.
- Table Schema Builder: This module is tasked with transforming various inputs into a unified Table Schema definition.

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

##### Multiple Generation Types

Designate each generation type as a Builder.

![image-20230128001610303](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128001610303.png) 

##### Multiple Mock Data Generation Rules

Each generation rule is established as a Generator, and several instances of Generators are created and managed consistently through the use of the DataGeneratorFactory class, which employs the factory pattern.

![image-20230128001735865](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128001735865.png) 

##### Unified Generation Portal

Combine the different types of generation through the facade pattern to create a consistent method for calling and validating generation.

![image-20230128004519400](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128004519400.png) 

#### Save Table Service

The table that was generated previously can be saved for future use.

![image-20230128004005643](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128004005643.png) 

#### Table Schema Builder

The various parameters can be merged into a single TableSchema object.

![image-20230128003651900](https://github.com/Leslie-mo/README-image-repo/blob/f8bc788e7713ec931a154f8d9617e3d45071c159/Sql-MockData-Generator-image/image-20230128003651900.png) 
