# __Product__
>Play framework tutorial based on [**Play for Scala**](https://www.manning.com/books/play-for-scala) with some notes.
<!-- TOC depthFrom:1 depthTo:6 withLinks:1 updateOnSave:1 orderedList:0 -->

- [__Product__](#product)
	- [Introduction](#introduction)
- [Chapitre 1](#chapitre-1)
	- [Creating and running an empty application](#creating-and-running-an-empty-application)
	- [Hello the World](#hello-the-world)
	- [Creating an HTTP request parameter](#creating-an-http-request-parameter)
	- [Creating an HTML page template.](#creating-an-html-page-template)
- [Chapter 2](#chapter-2)
	- [Stylesheets and custom CSS](#stylesheets-and-custom-css)
	- [Language Localization and Security Configuration](#language-localization-and-security-configuration)
	- [Model Creation](#model-creation)
	- [Controller action method.](#controller-action-method)

<!-- /TOC -->
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

## Language Localization and Security Configuration

In Play 2.6, the ```application.langs``` and ```application.secret``` have been replaced by  ```play.i18n.langs``` and ```play.crypto.secret``` respectively. First  we open the ```conf/application.conf``` file and add

```sbt
play.http.secret.key = "changeme"
play.http.secret.key=${?APPLICATION_SECRET}
```
The second line in that configuration sets the secret to come from an environment variable called APPLICATION_SECRET if such an environment variable is set, otherwise, it leaves the secret unchanged from the previous line. This secret string is used by play to generate cryptographic signatue like for the cookies session.

Then for the  language configuration we add the line

``` conf
play.i18n {
  # The application languages
  langs = [ "en","fr" ]

  # Whether the language cookie should be secure or not
  #langCookieSecure = true

  # Whether the HTTP only attribute of the cookie should be set to true
  #langCookieHttpOnly = true
}
```

The language supported by our application are english and french.We will use this configation interface to access application user interface text which are define in a message file for each languages

* ```conf/messages``` Default messages for all languages, for messages not localized for a particular language
* ``` conf/messages.fr``` for messages in French

we create the files mentioned above and add the lines

* ```application.name= Product catalog``` in ```conf/messages```
* ```application.name= Catalogue de produits``` in ```conf/messages```

## Model Creation

We now create a model ```Product```  which encapsulates our application data. We follow the book  and include three things in this application example:

* *A model class*: The definition of the product and its attributes
* *A data access object*: Code that provides access to the product data
* *Test data*: A set of product object.

We create the product model in the folder ``` app/models```. The code is similar to the one in the book.

## Controller action method.

The controller is responsible for handling incoming HTTP requests and generate responses using the model and the view.

Note that the way the product controller has been implemented in  [**Play for Scala**](https://www.manning.com/books/play-for-scala) is old. In Play 2.6 controller are classes to take advantage of Dependency Injection. In this notes we will be pragmatic and not  go in details with the  Dependency Injection pattern. Briefly, this design is used when you have a component, such as the controller, and it requires some other components as dependencies.Dependency Injection helps to separate the component behavior from it dependencies resolution. More detail about DI can be found and the [**Guice wiki**](https://github.com/google/guice/wiki/Motivation). In play  we inject a component dependencies via ```@Inject``` annotation.  The ```@Inject``` annotation is used usually  on constructors. for example:

```scala
import javax.inject._

class MyComponent @Inject() (cc: ControllerComponents)
extends AbstractController(cc) {
  // ...
}
```

Note that the ```@Inject```  annotation must come after the class name but before the constructor parameters, and must have parentheses. With that in mind the way to implement the ```Product``` controller is:
```scala
package controllers

import play.api.mvc._
import models.Product
import javax.inject._
import play.api.i18n._



class Products @Inject()(val controllerComponents: ControllerComponents)  extends BaseController with I18nSupport {


  def list = Action{ implicit request =>

    val products=Product.findAll
    Ok(views.html.products.list(products))
  }
}

```
Above our Product class dependents on the  ControllerComponents and use the trait ```play.api.i18n.I18nSupport```. This trait allows us to access to the ```conf/messages.xxx``` through the ```MessageApi``` instance. More information can be found at:  [Internationalization with Messages](https://www.playframework.com/documentation/2.6.x/ScalaI18N).

## Creating  the product list page template and the main

Here instead of having an  ```implicit lang``` parameter we have a ```implicit Messages```.  parameter.
```scala
@(products: List[Product])(implicit messages:Messages)
@main(messages("application.name")){
  <dl class="products">
    @for(product <- products ){
      <dt> @product.name </dt>
      <dm> @product.description<dm>
    }
  </dl>
}
```
Similary for the main we replace the ```implicit Lang``` parameter with a ```implicit Messages```

## Adding the Route.

In the ```conf/routes``` we add the row ```GET     /Products                   controllers.Products.list
```
