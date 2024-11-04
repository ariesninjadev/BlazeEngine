public class Main {
    public static void main(String[] args) {
        // Existing code...

        // Create and show the DepthMapVisualizer window
        DepthMapVisualizer depthMapVisualizer = new DepthMapVisualizer(1024, 1024);
        depthMapVisualizer.setVisible(true);

        // Start a new thread to update the depth map visualization
        new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000); // Update every second
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                double[][] depthMap = Shadows.createDepthMap(client, light);
                depthMapVisualizer.updateDepthMap(depthMap);
            }
        }).start();
    }
}