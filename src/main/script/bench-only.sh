#!/usr/bin/env bash
#
# Simple benchmark script using hey
#
# Author:	Matteo Franci <mttfranci@gmail.com>
#
# NOTE: this script is based on <https://github.com/alina-yur/native-spring-boot/blob/main/bench-jit-c2.sh>
set -e

function print() {
    printf "\033[1;34m$1\033[0m\n"
}

export TEST_URL=http://localhost:8080/photobook-demo/api/photobook/view/images/springio23/language/it/current_page/1/page_size/5
export LOOP_COUNT=250000
export BASE_DIR=target

print "Executing warmup load"
hey -n=50000 -c=8 ${TEST_URL}

print "Executing benchmark load"
hey -n=${LOOP_COUNT} -c=8 ${TEST_URL}

print "benchmark run done!🎉"

