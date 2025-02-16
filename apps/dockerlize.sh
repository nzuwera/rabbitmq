#!/bin/bash

# Variables
DOCKER_USERNAME="nzuwera"

# Function to display usage
usage() {
  echo "Usage: $0 -a <app_name>"
  echo "  -a  Application name: rabbitmq-producer, rabbitmq-consumer, or all"
  exit 1
}

# Function to extract version from build.gradle
extract_version() {
  local APP_NAME=$1
   local VERSION=$(grep -E "^version\s*=\s*['\"]([0-9]+\.[0-9]+\.[0-9]+)['\"]" "$APP_NAME/build.gradle" | awk -F'[="]' '{print $2}' | tr -d "[:space:]'")

  if [[ -z "$VERSION" ]]; then
    echo "Error: Could not extract version from $APP_NAME/build.gradle"
    exit 1
  fi

  echo "$VERSION"
}

# Function to build and push a single app
build_and_push_app() {
  local APP_NAME=$1
  local VERSION=$(extract_version "$APP_NAME")
  local DOCKER_REPO="$DOCKER_USERNAME/$APP_NAME"

  echo "Building the Spring Boot application: $APP_NAME (Version: $VERSION)..."
  cd "$APP_NAME" || exit 1
  gradle clean build

  if [ $? -ne 0 ]; then
    echo "Gradle build for $APP_NAME failed. Exiting..."
    exit 1
  fi

  echo "Building Docker image for $APP_NAME..."
  docker build -t "$APP_NAME" -f Dockerfile .

  if [ $? -ne 0 ]; then
    echo "Docker build for $APP_NAME failed. Exiting..."
    exit 1
  fi

  echo "Tagging Docker image for $APP_NAME..."
  docker tag "$APP_NAME" "$DOCKER_REPO:$VERSION"

  echo "Pushing Docker image for $DOCKER_REPO to Docker Hub..."
  docker push "$DOCKER_REPO:$VERSION"

  if [ $? -ne 0 ]; then
    echo "Docker push for $APP_NAME failed. Exiting..."
    exit 1
  fi

  echo "Docker image for $APP_NAME successfully built, tagged, and pushed to Docker Hub!"
  cd ../
}

# Parse command-line arguments
while getopts "a:" opt; do
  case $opt in
    a) APP_NAME=$OPTARG ;;
    *) usage ;;
  esac
done

# Ensure required arguments are provided
if [[ -z "$APP_NAME" ]]; then
  echo "Error: Missing required arguments."
  usage
fi

# Process app name argument
if [[ "$APP_NAME" == "rabbitmq-producer" || "$APP_NAME" == "rabbitmq-consumer" ]]; then
  build_and_push_app "$APP_NAME"
elif [[ "$APP_NAME" == "all" ]]; then
  build_and_push_app "rabbitmq-producer" &
  build_and_push_app "rabbitmq-consumer" &
  wait
else
  echo "Error: Invalid app name. Must be 'rabbitmq-producer', 'rabbitmq-consumer', or 'all'."
  usage
fi
