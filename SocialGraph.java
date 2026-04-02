import java.util.*;
import java.io.*;

public class SocialGraph {

    private ArrayList<ArrayList<Integer>> graph;
    private int numNodes;

    public SocialGraph() {
        graph = new ArrayList<>();
    }

    public void loadGraph(String filePath) {
        graph.clear();

        try {
            Scanner file = new Scanner(new File(filePath));

            numNodes = file.nextInt();
            int edges = file.nextInt();

            for (int i = 0; i < numNodes; i++) {
                graph.add(new ArrayList<>());
            }

            for (int i = 0; i < edges; i++) {
                int u = file.nextInt();
                int v = file.nextInt();

                graph.get(u).add(v);
                graph.get(v).add(u);
            }

            file.close();
            System.out.println("Graph loaded!");

        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } catch (Exception e) {
            System.out.println("Invalid file format.");
        }
    }

    public void displayFriendList(int id) {
        if (id < 0 || id >= numNodes) {
            System.out.println("Invalid ID.");
            return;
        }

        ArrayList<Integer> friends = graph.get(id);

        System.out.println("Person " + id + " has " + friends.size() + " friends!");
        System.out.print("List of friends: ");

        for (int f : friends) {
            System.out.print(f + " ");
        }
        System.out.println();
    }

    public void displayConnection(int start, int end) {
        if (start < 0 || start >= numNodes || end < 0 || end >= numNodes) {
            System.out.println("Invalid ID.");
            return;
        }

        if (start == end) {
            System.out.println("Same person.");
            return;
        }

        boolean[] visited = new boolean[numNodes];
        int[] parent = new int[numNodes];

        Arrays.fill(parent, -1);

        Queue<Integer> queue = new LinkedList<>();
        queue.add(start);
        visited[start] = true;

        boolean found = false;

        while (!queue.isEmpty()) {
            int current = queue.poll();

            if (current == end) {
                found = true;
                break;
            }

            for (int neighbor : graph.get(current)) {
                if (!visited[neighbor]) {
                    visited[neighbor] = true;
                    parent[neighbor] = current;
                    queue.add(neighbor);
                }
            }
        }

        if (!found) {
            System.out.println("Cannot find a connection between " + start + " and " + end);
            return;
        }

        System.out.println("There is a connection from " + start + " to " + end + "!");

        ArrayList<Integer> path = new ArrayList<>();
        int current = end;

        while (current != -1) {
            path.add(current);
            current = parent[current];
        }

        Collections.reverse(path);

        for (int i = 0; i < path.size() - 1; i++) {
            System.out.println(path.get(i) + " is friends with " + path.get(i + 1));
        }
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        SocialGraph sg = new SocialGraph();

        System.out.print("Input file path: ");
        String path = input.nextLine();

        sg.loadGraph(path);

        while (true) {
            System.out.println("\nMAIN MENU");
            System.out.println("[1] Get friend list");
            System.out.println("[2] Get connection");
            System.out.println("[3] Exit");

            System.out.print("Enter your choice: ");

            if (!input.hasNextInt()) {
                System.out.println("Invalid input.");
                input.next();
                continue;
            }

            int choice = input.nextInt();

            if (choice == 1) {
                System.out.print("Enter ID of person: ");

                if (!input.hasNextInt()) {
                    System.out.println("Invalid input.");
                    input.next();
                    continue;
                }

                int id = input.nextInt();
                sg.displayFriendList(id);

            } else if (choice == 2) {
                System.out.print("Enter ID of first person: ");

                if (!input.hasNextInt()) {
                    System.out.println("Invalid input.");
                    input.next();
                    continue;
                }

                int a = input.nextInt();

                System.out.print("Enter ID of second person: ");

                if (!input.hasNextInt()) {
                    System.out.println("Invalid input.");
                    input.next();
                    continue;
                }

                int b = input.nextInt();
                sg.displayConnection(a, b);

            } else if (choice == 3) {
                break;

            } else {
                System.out.println("Invalid choice.");
            }
        }

        input.close();
    }
}
