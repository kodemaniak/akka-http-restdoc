package km.akka.restdoc.example

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.headers.RawHeader
import akka.http.scaladsl.model.{HttpEntity, HttpResponse, MediaTypes, StatusCodes}
import akka.http.scaladsl.server.{Directives, Route}
import akka.stream.ActorMaterializer

import scala.collection.immutable

/**
 * Created by carsten on 01.04.15.
 */
object SimpleServer extends App with Service {
  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorMaterializer()

  Http().bindAndHandle(route, interface = "0.0.0.0", port = 12131)
}

trait Service extends Directives {
  def route: Route = {
    logRequestResult("test") {
      path("users") {
        get {
          complete(HttpResponse(StatusCodes.OK,
            immutable.Seq(RawHeader("Header-Name", "Value")),
            HttpEntity(MediaTypes.`application/json`, """[{"name":"User One", "age": 40}]""")))
        } ~
        post {
          complete(HttpResponse(StatusCodes.Created,
            immutable.Seq(RawHeader("Header-Name", "Value")),
            HttpEntity("OK")))
        }
      }
    }
  }
}
