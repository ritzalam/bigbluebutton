#!/bin/bash

sbt clean
sbt compile
sbt package

APPLICATION_NAME=bbbapps
APP_JAR_NAME=${APPLICATION_NAME}_2.11-0.0.1.jar
WEBAPPS_LOCATION=/usr/share/red5/webapps
JARS_ORIG_LOCATION=~/dev/bigbluebutton/incubator/bbb-red5-apps/target/webapp/WEB-INF/lib
JARS_DEST_LOCATION=/usr/share/red5/webapps/$APPLICATION_NAME/WEB-INF/lib

echo "deploying $APPLICATION_NAME to $WEBAPPS_LOCATION"

sudo chmod -R 777 $WEBAPPS_LOCATION

if [[ -d $WEBAPPS_LOCATION/$APPLICATION_NAME ]]; then
    sudo rm -r $WEBAPPS_LOCATION/$APPLICATION_NAME
fi
sudo cp -r target/webapp/ $WEBAPPS_LOCATION/$APPLICATION_NAME

# By default all jars are copied in the webapp. However, some are already in red5
# Keep only the ones needed by the webapp (which are not in red5 yet)
sudo rm -rf $JARS_DEST_LOCATION/*
sudo cp $JARS_ORIG_LOCATION/$APP_JAR_NAME \
    $JARS_ORIG_LOCATION/aopalliance-1.0.jar \
    $JARS_ORIG_LOCATION/akka-* \
    $JARS_ORIG_LOCATION/bbb-common-message-0.0.19-SNAPSHOT.jar \
    $JARS_ORIG_LOCATION/boon-0.33.jar \
    $JARS_ORIG_LOCATION/commons-pool2-2.3.jar \
    $JARS_ORIG_LOCATION/config-1.3.0.jar \
    $JARS_ORIG_LOCATION/easymock-2.4.jar \
    $JARS_ORIG_LOCATION/gson-2.5.jar \
    $JARS_ORIG_LOCATION/jedis-2.7.2.jar \
    $JARS_ORIG_LOCATION/scala-library-* \
    $JARS_DEST_LOCATION

# extract web application
sudo mkdir /usr/share/red5/webapps/$APPLICATION_NAME/WEB-INF/classes
cd /usr/share/red5/webapps/$APPLICATION_NAME/WEB-INF/classes/
sudo jar -xf ../lib/$APP_JAR_NAME
sudo rm /usr/share/red5/webapps/$APPLICATION_NAME/WEB-INF/lib/$APP_JAR_NAME

# Fix permissions and ownership
sudo chmod -R 777 $WEBAPPS_LOCATION/$APPLICATION_NAME
sudo chown -R red5:red5 $WEBAPPS_LOCATION/$APPLICATION_NAME

# Dev only
