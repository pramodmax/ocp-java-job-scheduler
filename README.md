
# Steps to run the code 

Step1: the following env variables should be added to the Deployment / Deployment config
```
export SCHEDULE_START_DELAY=1000
export SCHEDULE_INTERVAL_DELAY=1000
export CLEANUP_SCRIPT_PATH=~/Desktop/shell-script.sh
```

Step2: Add the jar to your entrypoint along with your current/main process. Note that the scheduler is running in background.
```
java -Xmx50m -Xms50m -jar kube-job-scheduler-1.0.jar &
```


# Description 
| Environment variable      | Description | Sample content    |
| :---        |    :----:   |          ---: |
| SCHEDULE_START_DELAY      | The 1st time, when the program runs, this timer is used. It is represented in millisecond       | 1000  |
| SCHEDULE_INTERVAL_DELAY      | This is the interval time across each job. It is represented in millisecond       | 1000   |
| CLEANUP_SCRIPT_PATH      | This is env variable is used to run executable   | ~/Desktop/shell-script.sh  |
