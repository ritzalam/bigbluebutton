#!/bin/bash
sbt clean
sbt compile
sbt publish
sbt publishLocal

