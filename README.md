# __Product__
>Play framework tutorial based on [**Play for Scala**](https://www.manning.com/books/play-for-scala) with some notes.

## Introduction

This project follow the book [**Play for Scala**](https://www.manning.com/books/play-for-scala). Each branches of the project correspond to a chapter in the book. The master branch merge theall the branches.

# Chapitre 1

## Creating and running an empty application

Instead of creating the empty application as shown in section 1.5.2, within *Play 2.6* one needs instead to run the sbt command in the terminal

``` sbt
 sbt new playframework/play-scala-seed.g8
```
after running the command,the console asks the name of the application and the name of the organization. We choose ``` Products ``` and  ``` com.products ```. The application is created. The project  directory structure is displayed below:  
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
This directory structure is common to all **Play** applications. The directories group the files as follow:
* __app:__ Application source code
* __conf:__ Configuration files and data
* __project:__ Project build scripts
* __public:__ Publicly accessible static files
* __test:__ Automated test

Running the  application typing the command ``` sbt run ``` and opening a browser to  the address  ``` //localhost:9000/ ``` should give a welcome message.

## Hello the World

The application is working but is not giving the proper "hello the world!" message. To fix that we have to edit the file ``` app/controllers/HomeController.scala ``` and replace :

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

Opening a browser to  the address  ``` //localhost:9000/ ```  displays the hello world message

## Creating an HTTP request parameter

So far no HTTP request have been made. To solve that we create a new function in ``` app/controllers/HomeController.scala ```

``` Scala
def hello(name: String) = Action { implicit request: Request[AnyContent] =>
  Ok("Hello "+name)
}
```
Then we add a new line in our  ``` conf/routes ```/
``` Scala
GET /hello
controllers.Application.hello(name: String)
```

Opening a browser to  the address  ``` //localhost:9000/hello?name=Play! ``` will display  the "hello Play!" "message.

## Creating an HTML page template.

So far we have just displayed a plain text. The next step is to create a proper web page. Let create a file ``` app/views/hello.scala.html ``` with the content

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

In order to render the page we then modify our previous  hello function to:

``` Scala
def hello(name: String) = Action {
Ok(views.html.hello(name))
}
```

Opening a browser to  the address  ``` //localhost:9000/hello?name=Play! ``` will display  the "hello Play!"  in a  HTML structured page.

# Chapter 2

## Stylesheets and custom CSS

Although it is possible to import Bootstrap as described in the book. Another way it to add the Bootstrap library directly in our  ```build.sbt ``` files via [**Webjars**](https://www.webjars.org/)
``` sbt
libraryDependencies += "org.webjars" % "bootstrap" % "2.1.1"
```
Note that using this method the version used in the book (2.0.2) is not availables. To limit variation between the book and this notes I picked the version 2.1.1.
[**Webjars**](https://www.webjars.org/). Play automatically extracts the WebJar contents and makes them available via the Assets controller. Thus since our  route is :
```http
GET         /assets/*file           controllers.Assets.at(path="/public", file)
```

Then a WebJar file like bootstrap.css is available at:
```
/assets/lib/bootstrap/css/bootstrap.css
```
Then one can call the Bootstrap style in the htlm view by adding in the header
``` Html
<link rel="stylesheet" media="screen" href="@routes.Assets.versioned("lib/bootstrap/css/bootstrap.css")">
```
