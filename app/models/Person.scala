package models

import play.api.libs.json.Json

case class Person(id:  Long, name: String)

object Person {
  implicit val personFormat = Json.format[Person]

  var persons = Set(Person(1, "A"))

  def findAll = persons

  def findById(id: Long) = persons.find(_.id == id)

  def add(person: Person) = persons += person


}
