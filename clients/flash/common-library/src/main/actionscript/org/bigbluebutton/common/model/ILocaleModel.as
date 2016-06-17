package org.bigbluebutton.common.model
{
  public interface ILocaleModel
  {
    function setLocales(localeCodes:Array, localNames:Array):void;
    function setPreferredLocale(locale:String):void;
    function getString(resourceName:String, parameters:Array = null, locale:String = null):String;
    function getCurrentLanguageCode():String;
    function getLocaleCodeForIndex(index:int):String;
  }
}