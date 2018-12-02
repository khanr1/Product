# __Product__
>Play framework tutorial based on [**Play for Scala**](https://www.manning.com/books/play-for-scala) with some notes.

## Introduction

This project fallow the book [**Play for Scala**](https://www.manning.com/books/play-for-scala). Each branch of the project correspond to a chapter in the book. The master branch merge them all.

## Chapitre 1

### Creating and running an empty application

Instead of creating the empty application as show in section 1.5.2, with *Play 2.6* one needs instead to use the sbt command in the terminal

``` sbt
 sbt new playframework/play-scala-seed.g8
```
after running the command,the console asks to give the name of the application and the name of the organization. We choose ``` Products ``` and  ``` com.products ```. The application is created and one should have the directory structure displayed below:
```bash
.
├── app
│   ├── controllers  
│   └── views
├── conf
├── gradle
│   └── wrapper
├── logs
├── project
│   ├── project
│   └── target
├── public
│   ├── images
│   ├── javascripts
│   └── stylesheets
├── target
│   ├── scala-2.12
│   ├── streams
│   └── web
└── test
    └── controllers


```
This directory structure is common to all Play applications. The directories group the files as follows:
* __app:__ Application source code
* __conf: __ Configuration files and data
* __project: __ Project build scripts
* __public: __ Publicly accessible static files
* __test:__ Automated test

Running the  application typing the command ``` sbt run ``` and opening a browser to  the address  ``` //localhost:9000/ ``` should give a welcome message.
