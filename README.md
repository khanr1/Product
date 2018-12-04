# __Product__
>Play framework tutorial based on [**Play for Scala**](https://www.manning.com/books/play-for-scala) with some notes.

## Introduction

This project fallow the book [**Play for Scala**](https://www.manning.com/books/play-for-scala). Each branch of the project correspond to a chapter in the book. The master branch merge them all.

# Chapitre 1

## Creating and running an empty application

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

## Hello the World

The application is working but not giving the proper "hello the world!" message. To fix that we have to edit the file ``` app/controllers/HomeController.scala ``` and replace :

``` Scala
def index() = Action { implicit request: Request[AnyContent] =>
  Ok(views.html.index())
}
```
with
``` Scala
def index() = Action { implicit request: Request[AnyContent] =>
  Ok("Hello World!")
}
```

Opening a browser to  the address  ``` //localhost:9000/ ``` will display  the hello world message

## Creating an HTTP request parameter

So far no HTTP request have been made. To change that we create a new funtion in ``` app/controllers/HomeController.scala ```

``` Scala
def hello(name: String) = Action { implicit request: Request[AnyContent] =>
  Ok("Hello "+name)
}
```
Then we add a new line in our  ``` conf/routes ```/
``` HTTP
GET /hello
controllers.Application.hello(name: String)
```

Opening a browser to  the address  ``` //localhost:9000/hello?name=Play! ``` will display  the "hello Play!" "message.

## Creating an HTML page template.

So far we just displayed a plain text. The next step is to create a proper web page. Let create a file ``` app/views/hello.scala.html ```with the content

``` Scala
@(name:String)
<!doctype html>
<html>
<head>
<meta charset="UTF-8">
<title>Hello</title>
</head>
<body>
<h1>Hello <em>@name</em></h1>
</body>
</html>
```

In order to render the page we then modified our previous  hello funtion to:

``` Scala
def hello(name: String) = Action {
Ok(views.html.hello(name))
}
```

Opening a browser to  the address  ``` //localhost:9000/hello?name=Play! ``` will display  the "hello Play!"  in a  HTML page.
