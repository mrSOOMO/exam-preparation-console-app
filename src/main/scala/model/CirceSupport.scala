package model

import akka.http.scaladsl.marshalling.{Marshaller, ToEntityMarshaller}
import akka.http.scaladsl.model.MediaTypes.`application/json`
import akka.http.scaladsl.unmarshalling.{FromEntityUnmarshaller, Unmarshaller}
import io.circe.syntax._
import io.circe.{Decoder, Encoder, jawn}

object CirceSupport {
  implicit def circeJsonUnmarshaller[A: Decoder]: FromEntityUnmarshaller[A] =
    Unmarshaller.stringUnmarshaller.forContentTypes(`application/json`).map { data =>
      jawn.decode[A](data).fold(throw _, identity)
    }

  implicit def circeJsonMarshaller[A: Encoder]: ToEntityMarshaller[A] =
    Marshaller.stringMarshaller(`application/json`).compose(_.asJson.noSpaces)

  implicit def circeJsonListMarshaller[A: Encoder]: ToEntityMarshaller[List[A]] =
    Marshaller.stringMarshaller(`application/json`).compose(_.asJson.noSpaces)
}

