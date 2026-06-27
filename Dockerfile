FROM ubuntu:latest
LABEL authors="fsegovia"

ENTRYPOINT ["top", "-b"]