// afficheur_message.c
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>

#define BUFFER_SIZE 1024

int main(int argc, char *argv[]) {
    char buffer[BUFFER_SIZE];

    // Verify that the pipe file descriptor is passed as an argument
    if (argc != 2) {
        fprintf(stderr, "Usage: %s <pipe_fd>\n", argv[0]);
        exit(EXIT_FAILURE);
    }

    int pipe_fd = atoi(argv[1]);

    // Continuously read from the pipe and display the messages
    while (1) {
        ssize_t num_read = read(pipe_fd, buffer, BUFFER_SIZE - 1);
        if (num_read > 0) {
            buffer[num_read] = '\0';  // Ensure the string is null-terminated
            printf("Message Received: %s\n", buffer);
        } else if (num_read == 0) {
            // End of stream, close the pipe and exit
            close(pipe_fd);
            break;
        } else {
            // Handle read error
            perror("Read error");
            close(pipe_fd);
            exit(EXIT_FAILURE);
        }
    }

    return EXIT_SUCCESS;
}
