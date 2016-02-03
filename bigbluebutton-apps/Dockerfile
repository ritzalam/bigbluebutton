FROM ubuntu:14.04
MAINTAINER Richard Alam <ritzalam@gmail.com>
ENV REFRESHED_AT 2015-12-24

RUN apt-get -yqq update

ADD build/bigbluebutton/ /usr/share/red5/webapps/bigbluebutton/

VOLUME [ "/usr/share/red5/webapps/bigbluebutton" ]

