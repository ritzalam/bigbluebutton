package org.bigbluebutton.core2x.apps.presentation

import org.bigbluebutton.core2x.apps.presentation.domain._
import org.bigbluebutton.core2x.domain.IntUserId

import com.softwaremill.quicklens._

object PresentationModel {
  private var lastInstanceIndex = 0

  private def newInstanceIndex(): Int = { lastInstanceIndex += 1; lastInstanceIndex }

  def create(): PresentationInstance = {
    create(Set.empty, None)
  }

  def create(admin: Set[IntUserId]): PresentationInstance = {
    new PresentationInstance(newInstanceIndex(), admin, None, Set.empty)
  }

  def create(admin: Set[IntUserId], curPresenter: Option[CurrentPresenter]): PresentationInstance = {
    new PresentationInstance(newInstanceIndex(), admin, curPresenter, Set.empty)
  }
}

class PresentationModel {
  private var presentationIntances = new scala.collection.immutable.HashMap[Int, PresentationInstance]

  def save(presInstance: PresentationInstance): Unit = {
    presentationIntances += presInstance.instance -> presInstance
  }

  def remove(instance: Int): Option[PresentationInstance] = {
    val pres = presentationIntances.get(instance)
    pres foreach (p => presentationIntances -= instance)
    pres
  }

  def toVector: Vector[PresentationInstance] = presentationIntances.values.toVector
}

object PresentationInstance {
  def addAdmin(admin: IntUserId, instance: PresentationInstance): PresentationInstance = {
    modify(instance)(_.admins).setTo(instance.admins + admin)
  }

  def removeAdmin(admin: IntUserId, instance: PresentationInstance): PresentationInstance = {
    modify(instance)(_.admins).setTo(instance.admins - admin)
  }

  def setCurrentPresenter(curPresenter: Option[CurrentPresenter],
    instance: PresentationInstance): PresentationInstance = {
    modify(instance)(_.curPresenter).setTo(curPresenter)
  }

  def save(presentation: Presentation, instance: PresentationInstance): PresentationInstance = {
    val preses = instance.presentations filterNot (p => p.id.value == presentation.id.value)
    modify(instance)(_.presentations).setTo(preses + presentation)
  }
}

case class PresentationInstance(val instance: Int, val admins: Set[IntUserId] = Set.empty,
  val curPresenter: Option[CurrentPresenter] = None,
  val presentations: Set[Presentation] = Set.empty)

object Presentation {
  def setCurrent(current: Boolean, pres: Presentation): Presentation = {
    modify(pres)(_.current).setTo(current)
  }

  def detDefault(default: Boolean, pres: Presentation): Presentation = {
    modify(pres)(_.default).setTo(default)
  }

  def save(page: Page, pres: Presentation): Presentation = {
    val pages = pres.pages filterNot (p => p.id == page.id)
    modify(pres)(_.pages).setTo(pages + page)
  }
}

case class Presentation(val id: PresentationId, val name: String, current: Boolean = false,
  val pages: Set[Page], default: Boolean)

case class PreuploadedPresentation(id: PresentationId, name: String, default: Boolean)

object Page {
  def setCoordinate(coordinate: Coordinate, page: Page): Page = {
    modify(page)(_.coordinate).setTo(coordinate)
  }

  def setDimension(dimension: Dimension, page: Page): Page = {
    modify(page)(_.dimension).setTo(dimension)
  }
}

case class Page(val id: String, val num: Int, val thumbUrl: ThumbUrl, val swfUrl: SwfUrl, val textUrl: TextUrl,
  val svgUrl: SvgUrl, current: Boolean = false, coordinate: Coordinate = new Coordinate(),
  dimension: Dimension = new Dimension())

