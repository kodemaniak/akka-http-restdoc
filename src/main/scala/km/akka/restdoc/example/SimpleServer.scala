package km.akka.restdoc.example

import akka.actor.ActorSystem
import akka.http.Http
import akka.http.model.headers.RawHeader
import akka.http.model.{MediaTypes, HttpEntity, HttpResponse, StatusCodes}
import akka.http.server.{Route, Directives}
import akka.stream.ActorFlowMaterializer

import scala.collection.immutable

/**
 * Created by carsten on 01.04.15.
 */
object SimpleServer extends App with Service {
  implicit val system = ActorSystem()
  implicit val executor = system.dispatcher
  implicit val materializer = ActorFlowMaterializer()

  Http().bindAndHandle(route, interface = "0.0.0.0", port = 12131)
}

trait Service extends Directives {
  def route: Route = {
    logRequestResult("test") {
      path("users") {
        get {
          complete(HttpResponse(StatusCodes.OK, immutable.Seq(RawHeader("Header-Name", "Value")), HttpEntity(MediaTypes.`application/json`, """[{"name":"User One", "age": 40}]""")))
        } ~
        post {
          complete(HttpResponse(StatusCodes.Created, immutable.Seq(RawHeader("Header-Name", "Value")), HttpEntity("OK")))
        }
      }
    }
  }
}
