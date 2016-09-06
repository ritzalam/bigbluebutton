#!/bin/bash

set -x

BUILD_DIR=build
CLIENT_DIR=client

rm -rf $BUILD_DIR/*
gradle build

if [ -d "$CLIENT_DIR" ]; then
rm -rf $CLIENT_DIR
fi

mkdir $CLIENT_DIR
mv $BUILD_DIR/* $CLIENT_DIR/
cp bbb-template/config.xml $CLIENT_DIR/


