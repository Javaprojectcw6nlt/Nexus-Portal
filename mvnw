#!/usr/bin/env sh
# Project-local Maven bootstrapper for macOS/Linux. Requires Java, curl, and unzip.
set -eu
MAVEN_VERSION="3.9.11"
MAVEN_CACHE="${MAVEN_USER_HOME:-$HOME/.m2}/wrapper/dists"
MAVEN_HOME="$MAVEN_CACHE/apache-maven-$MAVEN_VERSION"
MAVEN_CMD="$MAVEN_HOME/bin/mvn"
if [ ! -x "$MAVEN_CMD" ]; then
  echo "[Maven Wrapper] Downloading Maven $MAVEN_VERSION into user cache (no system-wide installation)."
  mkdir -p "$MAVEN_CACHE"
  ZIP="$MAVEN_CACHE/apache-maven-$MAVEN_VERSION-bin.zip"
  curl -fL "https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/$MAVEN_VERSION/apache-maven-$MAVEN_VERSION-bin.zip" -o "$ZIP"
  unzip -q -o "$ZIP" -d "$MAVEN_CACHE"
  rm -f "$ZIP"
fi
exec "$MAVEN_CMD" "$@"
