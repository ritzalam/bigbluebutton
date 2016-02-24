package org.bigbluebutton.core.api

import java.lang.Boolean

case class StatusCode(val code: Int, val text: String)
object StatusCodes {
  // Borrowed from https://dev.twitter.com/overview/api/response-codes (ralam June 18, 2015)
  val OK = new StatusCode(200, "OK")
  val NOT_MODIFIED = new StatusCode(304, "Not Modified")
  val BAD_REQUEST = new StatusCode(400, "Bad Request")
  val UNAUTHORIZED = new StatusCode(401, "Unauthorized")
  val FORBIDDEN = new StatusCode(403, "Forbidden")
  val NOT_FOUND = new StatusCode(404, "Not Found")
  val NOT_ACCEPTABLE = new StatusCode(406, "Not Acceptable")
  val INTERNAL_SERVER_ERROR = new StatusCode(500, "Internal Server Error")
  val BAD_GATEWAY = new StatusCode(502, "Bad Gateway")
  val SERVICE_UNAVAIL = new StatusCode(503, "Service Unavailable")
  val GATEWAY_TIMEOUT = new StatusCode(504, "Gateway Timeout")
}

case class ErrorCode(val code: Int, message: String)
object ErrorCodes {
  val RESOURCE_NOT_FOUND = new ErrorCode(1, "Resource not found")
  val INVALID_DATA = new ErrorCode(88, "Invalid data")
}

case class RequestResult(status: StatusCode, errors: Option[Array[ErrorCode]])

