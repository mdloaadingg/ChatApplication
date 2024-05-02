#include "communication.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <fcntl.h>

int socket_fd;
int pipe_fd;  // File descriptor for the pipe

// Function to connect to the server
int connect_to_server(const char *server_ip, int port) {
    struct sockaddr_in server_addr;

    // Create a socket
    socket_fd = socket(AF_INET, SOCK_STREAM, 0);
    if (socket_fd < 0) {
        perror("Socket creation failed");
        return 0;
    }

    // Define the server address
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(port);
    server_addr.sin_addr.s_addr = inet_addr(server_ip);

    // Connect to the server
    if (connect(socket_fd, (struct sockaddr *)&server_addr, sizeof(server_addr)) < 0) {
        perror("Connect failed");
        return 0;
    }

    return 1;
}

// Function to open the named pipe
int open_pipe(const char *pipe_path) {
    pipe_fd = open(pipe_path, O_WRONLY);
    if (pipe_fd < 0) {
        perror("Failed to open pipe");
        return 0;
    }
    return 1;
}

// Function to send login information
void send_login(const char *username, const char *password) {
    char buffer[1024];
    sprintf(buffer, "LOGIN %s %s\n", username, password);
    send(socket_fd, buffer, strlen(buffer), 0);  // Send to server via socket
    if (write(pipe_fd, buffer, strlen(buffer)) < 0) {
        perror("Write to pipe failed");
    }
}

// Function to send logout request
void send_logout() {
    char buffer[1024] = "LOGOUT\n";
    send(socket_fd, buffer, strlen(buffer), 0);  // Send to server via socket
    if (write(pipe_fd, buffer, strlen(buffer)) < 0) {
        perror("Write to pipe failed");
    }
}

// Function to send a request for the list of users
void send_list_request() {
    char buffer[1024] = "LIST\n";
    send(socket_fd, buffer, strlen(buffer), 0);  // Send to server via socket
    if (write(pipe_fd, buffer, strlen(buffer)) < 0) {
        perror("Write to pipe failed");
    }
}

// Function to send a request to create a new account
void send_create_account(const char *username, const char *password) {
    char buffer[1024];
    sprintf(buffer, "CREATE %s %s\n", username, password);
    send(socket_fd, buffer, strlen(buffer), 0);  // Send to server via socket
    if (write(pipe_fd, buffer, strlen(buffer)) < 0) {
        perror("Write to pipe failed");
    }
}

// Function to send a request to delete an account
void send_delete_account(const char *username) {
    char buffer[1024];
    sprintf(buffer, "DELETE %s\n", username);
    send(socket_fd, buffer, strlen(buffer), 0);  // Send to server via socket
    if (write(pipe_fd, buffer, strlen(buffer)) < 0) {
        perror("Write to pipe failed");
    }
}

// Function to close the connection and the pipe
void close_connections() {
    close(socket_fd);
    close(pipe_fd);
}
