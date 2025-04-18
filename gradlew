#!/bin/bash
#
# Gradle startup script for UN*X
#

##############################################################################
#
# Check for a suitable JVM and set JAVA_HOME if not already set.
#

if [ -z "$JAVA_HOME" ]; then
  # Try to find a suitable JVM on this machine
  if [ -x "/usr/libexec/java_home" ]; then
    export JAVA_HOME=`/usr/libexec/java_home`
  fi
fi

##############################################################################
#
# Resolve the paths to the gradle wrapper jars.
#

APP_HOME=$(cd "$(dirname "$0")"; pwd)
CLASSPATH="$APP_HOME/gradle/wrapper/gradle-wrapper.jar"

##############################################################################
#
# Execute the wrapper.
#

exec java -cp "$CLASSPATH" org.gradle.wrapper.GradleWrapperMain "$@"