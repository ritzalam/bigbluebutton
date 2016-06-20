package org.bigbluebutton.core.apps

import org.bigbluebutton.common.messages2x.objects.ChatMessage

import scala.collection.mutable.ArrayBuffer
import scala.collection.immutable.HashMap

class ChatModel {
  private val messages = new ArrayBuffer[Map[String, String]]()
  private val messages2x = new ArrayBuffer[ChatMessage]()

  def getChatHistory(): Array[Map[String, String]] = {
    val history = new Array[Map[String, String]](messages.size)
    messages.copyToArray(history)

    history
  }

  def addNewChatMessage(msg: Map[String, String]) {
    messages append msg
  }

  def addNewChatMessage2x(msg: ChatMessage) {
    messages2x append msg
  }
}