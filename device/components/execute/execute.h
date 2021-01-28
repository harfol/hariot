#ifndef _EXECUTE_H_
#define _EXECUTE_H_

#define SIG_POWER_LOW SIGUSR1 SIG
#define SIG_SWITCH SIGUSR2 SIG

#include <signal.h>
#include <unistd.h>
#include <stdlib.h>

typedef struct
{
    char *title;
    int power;
    int status;
}device_t;

#endif
