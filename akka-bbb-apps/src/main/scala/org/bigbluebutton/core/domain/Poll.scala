package org.bigbluebutton.core.domain

case class QuestionResponsesVO(val questionID: String, val responseIDs: Array[String])
case class PollResponseVO(val pollID: String, val responses: Array[QuestionResponsesVO])
case class ResponderVO(responseID: String, user: Responder)

case class AnswerVO(val id: Int, val key: String, val text: Option[String], val responders: Option[Array[Responder]])
case class QuestionVO(val id: Int, val questionType: String, val multiResponse: Boolean, val questionText: Option[String], val answers: Option[Array[AnswerVO]])
case class PollVO(val id: String, val questions: Array[QuestionVO], val title: Option[String], val started: Boolean, val stopped: Boolean, val showResult: Boolean)

case class Responder(val userId: IntUserId, name: Name)

case class ResponseOutVO(id: String, text: String, responders: Array[Responder] = Array[Responder]())
case class QuestionOutVO(id: String, multiResponse: Boolean, question: String, responses: Array[ResponseOutVO])

case class SimpleAnswerOutVO(id: Int, key: String)
case class SimplePollOutVO(id: String, answers: Array[SimpleAnswerOutVO])

case class SimpleVoteOutVO(id: Int, key: String, numVotes: Int)
case class SimplePollResultOutVO(id: String, answers: Array[SimpleVoteOutVO], numRespondents: Int, numResponders: Int)
