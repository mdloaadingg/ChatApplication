#include "communication.h"
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

int main() {
    char input[1024];
    int running = 1;

    // Connect to the server
    if (!connect_to_server("127.0.0.1", 8888)) {
        fprintf(stderr, "Failed to connect to server.\n");
        return EXIT_FAILURE;
    }
    printf("Connected to chat server.\n");

    // Open the pipe
    if (!open_pipe("/tmp/chat_pipe")) {
        fprintf(stderr, "Failed to open pipe.\n");
        return EXIT_FAILURE;
    }
    printf("Pipe to display messages is open.\n");

    // Show available commands
    printf("Commands: login <username> <password>, logout, list, create <username> <password>, delete <username>, quit\n");

    // Command loop
    while (running) {
        printf("Enter command: ");
        fgets(input, sizeof(input), stdin);

        char *cmd = strtok(input, " \n");

        if (strcmp(cmd, "login") == 0) {
            char *username = strtok(NULL, " \n");
            char *password = strtok(NULL, " \n");
            if (username && password) {
                send_login(username, password);
            } else {
                printf("Usage: login <username> <password>\n");
            }
        } else if (strcmp(cmd, "logout") == 0) {
            send_logout();
        } else if (strcmp(cmd, "list") == 0) {
            send_list_request();
        } else if (strcmp(cmd, "create") == 0) {
            char *username = strtok(NULL, " \n");
            char *password = strtok(NULL, " \n");
            if (username && password) {
                send_create_account(username, password);
            } else {
                printf("Usage: create <username> <password>\n");
            }
        } else if (strcmp(cmd, "delete") == 0) {
            char *username = strtok(NULL, " \n");
            if (username) {
                send_delete_account(username);
            } else {
                printf("Usage: delete <username>\n");
            }
        } else if (strcmp(cmd, "quit") == 0) {
            running = 0;
        } else {
            printf("Unknown command.\n");
        }
    }

    // Close connections
    close_connections();
    return EXIT_SUCCESS;
}
