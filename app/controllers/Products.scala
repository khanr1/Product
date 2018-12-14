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

  def show(ean:Long) =Action{ implicit request =>

    Product.findByEan(ean).map(product=> Ok(views.html.products.details(product))).getOrElse(NotFound)
  }
}
