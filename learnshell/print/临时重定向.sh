#!/usr/bin/env bash


# testing STDERR messages


echo "this is an error ">&2
echo "this is another error"
echo "this is also an error ">&2