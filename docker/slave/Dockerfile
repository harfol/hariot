FROM gcc:latest

WORKDIR /home/slave
COPY bin .
COPY script .
CMD ["bash", "slave-start.sh"]
