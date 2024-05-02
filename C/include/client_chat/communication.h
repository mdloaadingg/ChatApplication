// communication.c
#include "communication.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>

int socket_fd;

int connect_to_server(const char *server_ip, int port) {
    struct sockaddr_in server_addr;

    socket_fd = socket(AF_INET, SOCK_STREAM, 0);
    if (socket_fd < 0) {
        perror("Socket creation failed");
        return 0;
    }

    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(port);
    server_addr.sin_addr.s_addr = inet_addr(server_ip);

    if (connect(socket_fd, (struct sockaddr *)&server_addr, sizeof(server_addr)) < 0) {
        perror("Connect failed");
        return 0;
    }

    return 1;
}

void send_login(const char *username, const char *password) {
    char buffer[1024];
    sprintf(buffer, "LOGIN %s %s\n", username, password);
    send(socket_fd, buffer, strlen(buffer), 0);
}

void send_logout() {
    char buffer[1024] = "LOGOUT\n";
    send(socket_fd, buffer, strlen(buffer), 0);
}

void send_list_request() {
    char buffer[1024] = "LIST\n";
    send(socket_fd, buffer, strlen(buffer), 0);
}

void send_create_account(const char *username, const char *password) {
    char buffer[1024];
    sprintf(buffer, "CREATE %s %s\n", username, password);
    send(socket_fd, buffer, strlen(buffer), 0);
}

void send_delete_account(const char *username) {
    char buffer[1024];
    sprintf(buffer, "DELETE %s\n", username);
    send(socket_fd, buffer, strlen(buffer), 0);
}

void close_connection() {
    close(socket_fd);
}
