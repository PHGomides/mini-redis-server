package io.github.phgomides.miniredis;

import io.github.phgomides.miniredis.server.RedisServer;

public class Main {

    void main() {
        int port = 6379;

        RedisServer server = new RedisServer(port);

        server.start();
    }
}
