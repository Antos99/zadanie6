#!/bin/sh
# shellcheck disable=SC2046
export $(grep -v '^#' /run/secrets/app_secret | xargs)
java "$JVM_OPTIONS" -jar "$JAVA_APP"
