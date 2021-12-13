import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class JobRunner {
    public static void main(String[] args) {
        String SCHEDULE_START_DELAY = Optional.ofNullable(System.getenv("SCHEDULE_START_DELAY")).orElseThrow(
                () -> new RuntimeException("SCHEDULE_START_DELAY is not set in the environment"));
        String SCHEDULE_INTERVAL_DELAY = Optional.ofNullable(System.getenv("SCHEDULE_INTERVAL_DELAY")).orElseThrow(
                () -> new RuntimeException("SCHEDULE_INTERVAL_DELAY is not set in the environment"));

        Timer anzTimer = new Timer();
        TimerTask anzCleanupTask = new TimerTask() {
            String CLEANUP_SCRIPT_PATH = Optional.ofNullable(System.getenv("CLEANUP_SCRIPT_PATH")).orElseThrow(
                    () -> new RuntimeException("CLEANUP_SCRIPT_PATH is not set in the environment"));

            @Override
            public void run() {
                runShell();
            }

            public void runShell(){
                ProcessBuilder builder = new ProcessBuilder();
                builder.command(CLEANUP_SCRIPT_PATH);
//                builder.command("/Users/pramodpadmanabhan/Desktop/client/my/kube-java-scheduler/demo.sh");
                try {
                    Process process = builder.start();
                    StringBuilder output = new StringBuilder();

                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(process.getInputStream()));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        output.append(line + "\n");
                    }
                    int exitVal = process.waitFor();
                    if (exitVal == 0) {
                        System.out.println(output);
                    } else {
                        System.out.println("Error: Could not finish the process successfully!!");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        };
        System.out.println("SCHEDULE_START_DELAY = " + SCHEDULE_START_DELAY);
        System.out.println("SCHEDULE_INTERVAL_DELAY = " + SCHEDULE_INTERVAL_DELAY);
        anzTimer.scheduleAtFixedRate(anzCleanupTask, Integer.parseInt(SCHEDULE_START_DELAY),Integer.parseInt(SCHEDULE_INTERVAL_DELAY));
    }



}


