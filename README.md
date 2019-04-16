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
    - [Creating  the product list page template and the main](#creating-the-product-list-page-template-and-the-main)
    - [Adding the Route.](#adding-the-route)
    - [Checking the language localization.](#checking-the-language-localization)
    - [Details and barCode.](#details-and-barcode)
    - [Form, Form view and  Form validation](#form-form-view-and-form-validation)
        - [Form](#form)
        - [Form template](#form-template)

<!-- /TOC -->
## Introduction

This project follows the book [**Play for Scala**](https://www.manning.com/books/play-for-scala). Each branch of the project corresponds to a chapter in the book. The master branch merges all the branches. These notes are largely inspired by the book.

# Chapitre 1

## Creating and running an empty application

Instead of creating the empty application as shown in section 1.5.2, within *Play 2.6* one needs instead to run the sbt command in the terminal

``` sbt
 sbt new playframework/play-scala-seed.g8
```
after running the command, the console asks the name of the application and the name of the organization. We choose ``` Products ``` and  ``` com.products ```. The application is created. The project  directory structure is displayed below:  
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
│   ├── javascript
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

Running the application typing the command ``` sbt run ``` and opening a browser to the address  ``` //localhost:9000/ ``` should give a welcome message.

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

So far no HTTP request has been made. To solve that we create a new function in ``` app/controllers/HomeController.scala ```

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

Opening a browser to  the address  ``` //localhost:9000/hello?name=Play! ``` will display the "hello Play!"  in an HTML structured page.

# Chapter 2

## Stylesheets and custom CSS

Although it is possible to import Bootstrap as described in the book. Another way it to add the Bootstrap library directly in our ```build.sbt ``` files via [**Webjars**](https://www.webjars.org/)
``` sbt
libraryDependencies += "org.webjars" % "bootstrap" % "2.1.1"
```
Note that using this method the version used in the book (2.0.2) is not available. To limit variation between the book and this notes I picked the version 2.1.1.
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
The second line in that configuration sets the secret to come from an environment variable called APPLICATION_SECRET if such an environment variable is set, otherwise, it leaves the secret unchanged from the previous line. This secret string is used by play to generate a cryptographic signature like for the cookies session.

Then for the  language configuration, we add the line

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

The language supported by our application is English and French.We will use this configuration interface to access application user interface text which is defined in a message file for each language

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

Note that the way the product controller has been implemented in  [**Play for Scala**](https://www.manning.com/books/play-for-scala) is old. In Play 2.6 controller are classes to take advantage of Dependency Injection. In these notes we will be pragmatic and not go in details with the  Dependency Injection pattern. Briefly, this design is used when you have a component, such as the controller, and it requires some other components as dependencies. Dependency Injection helps to separate the component behaviour from its dependencies resolution. More detail about DI can be found and the [**Guice wiki**](https://github.com/google/guice/wiki/Motivation). In play, we inject component dependencies via ```@Inject``` annotation.  The ```@Inject``` annotation is used usually on constructors. for example:

```scala
import javax.inject._

class MyComponent @Inject() (cc: ControllerComponents)
extends AbstractController(cc) {
  // ...
}
```

Note that the ```@Inject```  annotation must come after the class name but before the constructor parameters, and must have parentheses. With that in mind, the way to implement the ```Product``` controller is:
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
Above our Product class dependents on the  ControllerComponents and use the trait ```play.api.i18n.I18nSupport```. This trait allows us to access to the ```conf/messages.xxx``` through the ```MessageApi``` instance. More information can be found at [Internationalization with Messages](https://www.playframework.com/documentation/2.6.x/ScalaI18N).

## Creating  the product list page template and the main

Here instead of having a ```implicit lang``` parameter we have a ```implicit Messages```.  parameter.
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

In the ```conf/routes``` we add the row ```GET     /Products                   controllers.Products.list```

As in the book, we redirect the index to the product page by modifying the index action in  ```HomeController.scala ```

```scala
def index() = Action { implicit request: Request[AnyContent] =>
        Redirect(routes.Products.list)
}
```

## Checking the language localization.

Play sets the language of the application if the language configuration in the HTTP request matches ones of the configured language of the web browser. The settings of the application are checked with the debug view in ```app/view/debug.scala.html```

```HTML
@()(implicit messages: Messages)
@import play.api.Play.current
<footer>
  lang = @messages.lang.code,
  user = @current.configuration.getString("environment.user"),
  date = @(new java.util.Date().format("dd-MM-yyyy"))
</footer>
```
As in the section [Creating  the product list page template and the main](#creating-the-product-list-page-template-and-the-main) we have an ```implicit messages: Messages``` instead of the ```implicit lang```.

## Details and barCode.

Nothing change here with respect to the books. The only thing that one needs to be careful is to replace the ```implicit lang: Lang``` with ```implicit messages:Messages```.

## Form, Form view and  Form validation

### Form

To use forms, we import the following packages:

```scala
import play.api.data._
import play.api.data.Forms._
```
We define a form structure. It consists of mapping together with two functions that the form can use to map between itself and (in our case) an instance of our ``` Product ``` case class.

```Scala
val productForm: Form[Product]=Form(
    mapping(
        "ean"-> longNumber.verifying("validation.ean.duplicate",Product.findByEan(_).isEmpty),
        "name" > nonEmptyText,
        "description"-> nonEmptyText
    )(Product.apply)(Product.unapply)
)
```
The first part of the field specifies the field and the contraint the the input need to satisfy ("For example nonEmptyText") and the second and third parts are the function that the Form instance will use to create a Product (apply) and fill the form with a given Product (unapply).

### Form template

Now that we have a form, we want to use it in our template. We do this by including the form as a parameter to the view template. For ```editProduct.scala.html```, the header at the top of the page will look like this:
```scala
@(productForm: Form[Product])(implicit messages: Messages)
```
In order to create ```<form>``` tag, we use a view helper and set the action and method tag parameters according to the reverse routes
```Scala
@helper.form(action=routes.Products.save()){
     @CSRF.formField
    <fieldset>
        <legend> @messages("products.details", messages("products.new"))</legend>
        @helper.inputText(productForm("ean"))
        @helper.inputText(productForm("name"))
        @helper.textarea(productForm("description"))
    </fieldset>
```

Note here the difference with the book. Namely the ```@CSRF``` which is used to protect against Cross-Site Request Forgery. The ```@CSRF.formField``` take an implicit request header as a parameter. Thus one needs to have the header at the top of the page to look like this:
```scala
@(productFom: Form[Product])(implicit messages: Messages,request: RequestHeader)
```

### Form validation

The goal of the form is to collect information which is given by the user of the application and convert them into an instance of ```Product```. This is possible only if the input data are valid. Imagine we want to add the ```Product``` which has been entered in the form by the user. This is done by adding to the Products controller the ``save``` function:

``` Scala
def save = Action{implicit request =>
    val newProductForm = productForm.bindFromRequest()
    newProductForm.fold(
        hasError={form => Redirect(routes.Products.newProduct())},
        success ={newProduct=> Product.add(newProduct)
            Redirect(routes.Products.show(newProduct.ean))
        }
    )
}
```

Here the ```bindFromRequest``` takes the request parameters given by the users. If the parameters are wrong,  the fold method redirects to the ```newProduct form page``` else it adds the product to the ```products list``` and then redirect to the detail page of the product we just added.
