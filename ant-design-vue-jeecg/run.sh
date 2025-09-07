#!/bin/bash

export NODE_OPTIONS=--openssl-legacy-provider

git pull

# yarn cache clean

yarn run serve

