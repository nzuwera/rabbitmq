#!/bin/bash

# Variables
DOCKER_USERNAME="nzuwera"  # Replace with your Docker Hub username
VERSION="1.0.1"                            # Replace with your application version

# Function to build and push a single app
build_and_push_app() {
  local APP_NAME=$1
  local DOCKER_REPO="$DOCKER_USERNAME/$APP_NAME"

  echo "Building the Spring Boot application: $APP_NAME..."
  cd $APP_NAME/
  gradle clean build

  # Check if the build was successful
  if [ $? -ne 0 ]; then
    echo "Gradle build for $APP_NAME failed. Exiting..."
    exit 1
  fi

  echo "Building Docker image for $APP_NAME..."
  docker build -t $APP_NAME -f Dockerfile .

  # Check if the Docker build was successful
  if [ $? -ne 0 ]; then
    echo "Docker build for $APP_NAME failed. Exiting..."
    exit 1
  fi

  echo "Tagging Docker image for $APP_NAME..."
  docker tag $APP_NAME $DOCKER_REPO:$VERSION

  echo "Pushing Docker image for $APP_NAME to Docker Hub..."
  docker push $DOCKER_REPO:$VERSION

  # Check if the Docker push was successful
  if [ $? -ne 0 ]; then
    echo "Docker push for $APP_NAME failed. Exiting..."
    exit 1
  fi

  echo "Docker image for $APP_NAME successfully built, tagged, and pushed to Docker Hub!"
}

# Check if an app name is passed as an argument
if [ $# -eq 1 ]; then
  APP_NAME=$1
  if [[ $APP_NAME == "rabbitmq-producer" || $APP_NAME == "rabbitmq-consumer" ]]; then
    build_and_push_app $APP_NAME
  else
    echo "Invalid app name. Please specify 'rabbitmq-producer' or 'rabbitmq-consumer'."
    exit 1
  fi
else
  # If no argument is passed, build both apps
  echo "No app name specified. Building both 'rabbitmq-producer' and 'rabbitmq-consumer'."
  build_and_push_app "rabbitmq-producer"
  cd ../
  build_and_push_app "rabbitmq-consumer"
fi