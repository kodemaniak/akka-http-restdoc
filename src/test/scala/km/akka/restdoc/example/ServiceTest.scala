package km.akka.restdoc.example

import akka.http.model.headers.RawHeader
import akka.http.model._
import akka.http.model.StatusCodes._
import akka.http.server.Route
import akka.http.testkit.{RouteTest, ScalatestRouteTest}
import org.apache.commons.io.FileUtils
import org.scalatest.{Matchers, WordSpec}
import spray.json._
import akka.http.marshallers.sprayjson.SprayJsonSupport._
import DefaultJsonProtocol._

import scala.concurrent.Await
import java.io.File

/**
 * Created by carsten on 02.04.15.
 */
class ServiceTest extends WordSpec with ScalatestRouteTest with AkkaHttpRestdoc with Matchers with Service {
  "The Service" should {
    "return all users" in {
      perform(Get("/users")
        .withHeaders(RawHeader("Accept", "application/json")))
        .checkAndDocument("list-users") {
          status should be (OK)
        }
    }
    "create a user" in {
      val json = "{ \"name\": \"Carsten\" }".parseJson.asJsObject
        perform(Post("/users", json)
          .withHeaders(RawHeader("Accept", "application/json"), RawHeader("Content-Type", "application/json")))
          .checkAndDocument("create-user") {
            status should be (Created)
          }
    }
  }
}

trait AkkaHttpRestdoc {
  self: RouteTest =>

  def route: Route

  import scala.concurrent.duration._

  val dir = new File("src/asciidoctor/restdoc")

  def perform(req: HttpRequest) = RequestAvailable(route, req)

  case class RequestAvailable(route: Route, req: HttpRequest) {
    def checkAndDocument[T](name: String)(body: => T): T = {
      req ~> Route.seal(route) ~> check {
        val baseDir = new File(dir, name)
        baseDir.mkdirs()
        val curlFile = new File(baseDir, "request.adoc")
        val responseFile = new File(baseDir, "response.adoc")
        val result = body

        val reqString = formatRequest(req)
        FileUtils.write(curlFile, reqString)
        println(reqString)
        val respString = formatResponse(response)
        FileUtils.write(responseFile, respString)
        println(respString)

        result
      }
    }
  }

  private def formatRequest(req: HttpRequest): String = {
    val url = req.uri.toEffectiveHttpRequestUri(Uri.Host("localhost"), 12131)
    val headers = req.headers.map(formatHeaderCurl).mkString(" ")
    val body = formatBody(req.entity)

    s"""[source,bash]
       |----
       |curl $url -i $headers $body
       |----
    """.stripMargin
  }

  private def formatHeaderCurl(header: HttpHeader): String = {
    val name = header.name()
    val value = header.value()
    s"-H '$name: $value'"
  }

  private def formatBody(entity: RequestEntity): String = {
    val bodyString = Await.result(entity.toStrict(1.second).map { s => s.data.decodeString("utf-8") }, 1.second)
    if (bodyString.nonEmpty)
      s"-d '${bodyString.parseJson.prettyPrint}'"
    else
      bodyString
  }

  private def formatResponse(response: HttpResponse): String = {
    val headers = response.headers.map(formatHeaderResponse).mkString("\n")
    val body = formatEntity(response.entity)
    s"""----
       |$headers
       |
       |$body
       |----
     """.stripMargin
  }

  private def formatHeaderResponse(header: HttpHeader): String = {
    val name = header.name()
    val value = header.value()
    s"$name: $value"
  }

  private def formatContentType(ct: ContentType): String = {
    val ctString = ct.toString()
    s"Content-Type: $ctString"
  }

  private def formatEntity(entity: HttpEntity): String = {
    Await.result(entity.toStrict(1.second).map { strict =>
      strict.contentType.mediaType.value match {
        case "text/plain" =>
          strict.data.decodeString("utf-8")
        case "application/json" =>
          strict.data.decodeString("utf-8").parseJson.prettyPrint
        case _ =>
          "[BINARY DATA]"
      }
    }, 1.second)
  }
}

