package controllers

import javax.inject.Inject

import models.Person
import play.api.data.Form
import play.api.data.Forms._
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller}

import play.api.Play.current
import play.api.i18n.Messages.Implicits._


class PersonController @Inject() extends Controller {

  val personForm = Form(
    mapping(
      "id" -> longNumber,
      "name" -> nonEmptyText
    )(Person.apply)(Person.unapply)
  )

  def show(id: Long) = Action {
    Person.findById(id).map { person =>
      Ok(Json.toJson(person))
    }.getOrElse(NotFound)
  }

  def save = Action { implicit request =>
    val form = personForm.bindFromRequest()
    form.fold(
      hasErrors = { form =>
        Redirect(routes.PersonController.newPerson)
      },
      success = { person =>
        Person.add(person)
        Redirect(routes.PersonController.show(person.id))
      }
    )
  }

  def newPerson = Action { implicit request =>
    val form = personForm
    Ok(views.html.edit(form))
  }
}
