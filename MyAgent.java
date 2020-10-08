import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.logging.Logger;

public class MyAgent extends za.ac.wits.snake.MyAgent {

    public static void main(String args[]) throws IOException {
        MyAgent agent = new MyAgent();
        MyAgent.start(agent, args);
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String initString = br.readLine();
            String[] temp = initString.split(" ");
            int nSnakes = Integer.parseInt(temp[0]);
            int timeStep = 0;
            int prevAppleXCord = 0, prevAppleYCord = 0;
            double valueOfApple = 40;

            while (true) {
                timeStep++;

                int[][] playArea = new int[50][50];
                for (int i = 0; i < 50; i++) {
                    for (int j = 0; j < 50; j++) {
                        playArea[j][i] = 8;
                    }
                }

                String line = br.readLine();
                if (line.contains("Game Over")) {
                    break;
                }

                // do stuff with apples
                String apple1 = line;
                String[] coordinates = initString.split(" ");
                String[] AppleCoordinates = line.split(" ");
                int AppleXCord = Integer.parseInt(AppleCoordinates[0]);
                int AppleYCord = Integer.parseInt(AppleCoordinates[1]);

                playArea[AppleXCord][AppleYCord]=4;

                if (timeStep == 1) {
                    prevAppleXCord = AppleXCord;
                    prevAppleYCord = AppleYCord;

                } else {
                    if (prevAppleXCord == AppleXCord && prevAppleYCord == AppleYCord) {
                        valueOfApple = valueOfApple - 1;
                    } else {
                        prevAppleXCord = AppleXCord;
                        prevAppleYCord = AppleYCord;
                        valueOfApple = 40;
                    }
                }

                // System.err.println(valueOfApple);
                if (valueOfApple < 0) {
                    AppleXCord = 25;
                    AppleYCord = 25;
                }

                // read in obstacles and do something with them!
                int nObstacles = 3;
                for (int obstacle = 0; obstacle < nObstacles; obstacle++) {
                    String obs = br.readLine();
                    /// do something with obs
                    String[] points = obs.split(" ");
                    ArrayList<String> obstaclePoints = new ArrayList<String>();
                    for (int i = 0; i < points.length; i++) {
                        String[] XYPair = points[i].split(",");
                        String ObstacleX = XYPair[0];
                        String ObstacleY = XYPair[1];
                        obstaclePoints.add(ObstacleX);
                        obstaclePoints.add(ObstacleY);
                    }

                    for (int k = 3; k < obstaclePoints.size(); k = k + 2) {
                        String x1 = obstaclePoints.get(k - 3);
                        String y1 = obstaclePoints.get(k - 2);
                        String x2 = obstaclePoints.get(k - 1);
                        String y2 = obstaclePoints.get(k);

                        if (x1.equals(x2)) {
                            int startPoint = Integer.parseInt(y1);
                            int endPoint = Integer.parseInt(y2);
                            int min = Math.min(startPoint, endPoint);
                            int max = Math.max(startPoint, endPoint);
                            // System.err.println(startPoint + " " + endPoint + " X1=X2" );
                            for (int j = min; j < max + 1; j++) {
                                playArea[Integer.parseInt(x1)][j] = 5;
                            }
                        }
                        if (y1.equals(y2)) {

                            int startPoint = Integer.parseInt(x1);
                            int endPoint = Integer.parseInt(x2);
                            int min = Math.min(startPoint, endPoint);
                            int max = Math.max(startPoint, endPoint);
                            // System.err.println(startPoint + " " + endPoint + " Y1=Y2");
                            for (int j = min; j < max + 1; j++) {
                                playArea[j][Integer.parseInt(y1)] = 5;

                            }
                        }
                    }
                }

                int move = 2;
                int mySnakeNum = Integer.parseInt(br.readLine());
                int SnakeHeadXCord = 0, SnakeHeadYCord = 0, BodyXCord = 0, BodyYCord = 0;
                for (int i = 0; i < nSnakes; i++) {
                    String snakeLine = br.readLine();
                    if (i == mySnakeNum) {
                        // hey! That's me :)
                        String[] input = snakeLine.split(" ");
                        String[] SnakeCoordinates = input[3].split(",");
                        String[] BodyCoordinates = input[4].split(",");
                        SnakeHeadXCord = Integer.parseInt(SnakeCoordinates[0]);
                        SnakeHeadYCord = Integer.parseInt(SnakeCoordinates[1]);
                        BodyXCord = Integer.parseInt(BodyCoordinates[0]);
                        BodyYCord = Integer.parseInt(BodyCoordinates[1]);
                    }
                    // do stuff with other snakes
                    ArrayList<String> cords = new ArrayList<>();
                    cords.clear();
                    String temp1[];
                    String[] input = snakeLine.split(" ");
                    if (input[0].equals("dead")) {
                    } else {

                        String sLine = "";
                        for (int k = 3; k < input.length; k++) {
                            sLine = sLine + input[k] + " ";
                            temp1 = input[k].split(",");
                            cords.add(temp1[0]);
                            cords.add(temp1[1]);
                        }

                        playArea[SnakeHeadXCord][SnakeHeadYCord] = 7;
                        for (int k = 3; k < cords.size(); k = k + 2) {
                            String x1 = cords.get(k - 3);
                            String y1 = cords.get(k - 2);
                            String x2 = cords.get(k - 1);
                            String y2 = cords.get(k);

                            if (x1.equals(x2)) {
                                int startPoint = Integer.parseInt(y1);
                                int endPoint = Integer.parseInt(y2);
                                int min = Math.min(startPoint, endPoint);
                                int max = Math.max(startPoint, endPoint);
                                for (int j = min; j < max + 1; j++) {
                                    playArea[Integer.parseInt(x1)][j] = i;
                                }
                            }
                            if (y1.equals(y2)) {

                                int startPoint = Integer.parseInt(x1);
                                int endPoint = Integer.parseInt(x2);
                                int min = Math.min(startPoint, endPoint);
                                int max = Math.max(startPoint, endPoint);
                                for (int j = min; j < max + 1; j++) {
                                    playArea[j][Integer.parseInt(y1)] = i;

                                }
                            }
                            if (k == 3) {
                                playArea[Integer.parseInt(x1)][Integer.parseInt(y1)] = 7;
                            }
                        }
                    }
                }

                // finished reading, calculate move:

                boolean visited[][] = new boolean[50][50];
                int r, c, sr, sc, rr, cc, S, k = 0;

                Queue<Integer> rq = new LinkedList<Integer>();
                Queue<Integer> cq = new LinkedList<Integer>();
                ArrayList<Integer> prevX = new ArrayList<Integer>();
                ArrayList<Integer> prevY = new ArrayList<Integer>();

                int move_count = 0, nodes_left_in_layer = 1, nodes_in_next_layer=0;

                int[][] prevMatrix;

                boolean end_reached = false;

                int[] dr = { -1, 1, 0, 0 };
                int[] dc = { 0, 0, 1, -1 };

                sr = SnakeHeadXCord;
                sc = SnakeHeadYCord;
                S = 50;

                prevMatrix = new int[S][S];

                rq.add(sr);
                cq.add(sc);
                visited[sr][sc] = true;

                while (rq.size() > 0) {
                    r = rq.poll();
                    c = cq.poll();


                    if (playArea[r][c] == 4) {

                        end_reached = true;
                        break;
                    }

                    if (end_reached) {
                        break;
                    }
                    ////////////////////////////////////////////////////////////////// EXPLORING
                    ////////////////////////////////////////////////////////////////// NEIGHBOURS
                    for (int i = 0; i < 4; i++) {
                        rr = r + dr[i];
                        cc = c + dc[i];

                        if (rr >= 0 && cc >= 0) {

                            if (rr < S && cc < S) {

                                if (visited[rr][cc]) {

                                } else {

                                    if (playArea[rr][cc] == 8 || playArea[rr][cc]==4) {
                                        prevMatrix[rr][cc] = k;
                                        rq.add(rr);
                                        cq.add(cc);

                                        prevX.add(r);
                                        prevY.add(c);
                                        k++;
                                        visited[rr][cc] = true;
                                        nodes_in_next_layer++;

                                        if (playArea[rr][cc] == 4) {
                                            end_reached = true;
                                        }

                                    }

                                }
                            }
                        }
                    }

                    nodes_left_in_layer--;
                    if (nodes_left_in_layer == 0) {
                        nodes_left_in_layer = nodes_in_next_layer;
                        nodes_in_next_layer = 0;
                        move_count++;
                        // System.out.println(move_count);
                    }
                }


                if (end_reached) {

                } else {
                }
                int prevStep = 8;
                if(prevX.size()>0 && prevY.size()>0) {
                    int finalX = prevX.get(prevX.size() - 1);
                    int finalY = prevY.get(prevX.size() - 1);
                    if (playArea[finalX][finalY] == 0 || playArea[finalX][finalY] == 7) {
                    } else {
                        playArea[finalX][finalY] = 6;
                    }

                    System.err.println(k);
                    while (prevStep > 0) {
                        prevStep = prevMatrix[finalX][finalY];
                        finalX = prevX.get(prevStep);
                        finalY = prevY.get(prevStep);


                        if (finalX > 0) {
                            int left = playArea[finalX - 1][finalY];
                            if (left == 7) {
                                playArea[finalX][finalY] = 4;
                            }
                        }

                        if (finalX < 49) {
                            int right = playArea[finalX + 1][finalY];
                            if (right == 7) {
                                playArea[finalX][finalY] = 4;
                            }
                        }

                        if (finalY > 0) {
                            int above = playArea[finalX][finalY - 1];
                            if (above == 7) {
                                playArea[finalX][finalY] = 4;
                            }
                        }

                        if (finalY < 49) {
                            int below = playArea[finalX][finalY + 1];
                            if (below == 7) {
                                playArea[finalX][finalY] = 4;
                            }
                        }
                        if (playArea[finalX][finalY] == 0 || playArea[finalX][finalY] == 7 || playArea[finalX][finalY] == 4) {
                        } else {
                            playArea[finalX][finalY] = 6;
                        }
                    }
                }
                ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //APPLE MARKING

                if(AppleXCord>0){
                    int left = playArea[AppleXCord-1][AppleYCord];
                    if(left==6){
                        playArea[AppleXCord-1][AppleYCord] = 4;
                    }
                }

                if(AppleXCord<49){
                    int right = playArea[AppleXCord+1][AppleYCord];
                    if(right==6){
                        playArea[AppleXCord+1][AppleYCord] = 4 ;
                    }
                }

                if(AppleYCord>0){
                    int above = playArea[AppleXCord][AppleYCord-1];
                    if(above==6){
                        playArea[AppleXCord][AppleYCord-1] = 4;
                    }
                }

                if(AppleYCord<49){
                    int below = playArea[AppleXCord][AppleYCord+1];
                    if(below==6){
                        playArea[AppleXCord][AppleYCord+1] = 4;
                    }
                }

                /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                //CHECK WHERE THE 4 IS TO DETERMINE MOVE ALONG PATH

                if(SnakeHeadXCord>0){
                    int left = playArea[SnakeHeadXCord-1][SnakeHeadYCord];
                    if(left==4){
                        move = 2;
                    }
                }

                if(SnakeHeadXCord<49){
                    int right = playArea[SnakeHeadXCord+1][SnakeHeadYCord];
                    if(right==
                    4){
                        move = 3;
                    }
                }

                if(SnakeHeadYCord>0){
                    int above = playArea[SnakeHeadXCord][SnakeHeadYCord-1];
                    if(above==4){
                        move = 0;
                    }
                }

                if(SnakeHeadYCord<49){
                    int below = playArea[SnakeHeadXCord][SnakeHeadYCord+1];
                    if(below==4){
                        move = 1;
                    }
                }






                int width = playArea.length;
                int height = playArea[0].length;
                for (int i = 0; i < height; i++) {
                    line = "";
                    for (int j = 0; j < width; j++) {
                        if (j != width - 1) {
                            line = line + playArea[j][i] + " ";
                        } else {
                            line = line + playArea[j][i];
                        }
                    }
                    System.err.println(line);
                }
                System.err.println(" ");


                // END OF MOVE MAKING, DANGER CHECKING SEGMENT

                /*

                int left, right, above, below;

                if (SnakeHeadXCord != 49) {
                    right = playArea[SnakeHeadXCord + 1][SnakeHeadYCord];
                } else {
                    right = 5;
                }

                if (SnakeHeadXCord != 0) {
                    left = playArea[SnakeHeadXCord - 1][SnakeHeadYCord];
                } else {
                    left = 5;
                }

                if (SnakeHeadYCord != 0) {
                    above = playArea[SnakeHeadXCord][SnakeHeadYCord - 1];
                } else {
                    above = 5;
                }

                if (SnakeHeadYCord != 49) {
                    below = playArea[SnakeHeadXCord][SnakeHeadYCord + 1];
                } else {
                    below = 5;
                }

                if (move == 0) { // move above
                    if (above != 8) { // danger above
                        if (SnakeHeadXCord >= AppleXCord) { // move left
                            if (left == 8) { // check for danger left
                                move = 2;
                            } else {
                                if (right == 8) { // check for danger right
                                    move = 3;
                                } else {
                                    move = 1; // no other choice but to move down
                                }
                            }
                        } else { // move right
                            if (right == 8) { // check for danger right
                                move = 3;
                            } else {
                                if (left == 8) { // check for danger left
                                    move = 2;
                                } else {
                                    move = 1; // no other choice but to move down
                                }
                            }
                        }
                    }
                } else if (move == 1) { // move down
                    if (below != 8) { // danger down
                        if (SnakeHeadXCord >= AppleXCord) { // move left
                            if (left == 8) { // check for danger left
                                move = 2;
                            } else {
                                if (right == 8) { // check for danger right
                                    move = 3;
                                } else {
                                    move = 0; // no other choice but to move up
                                }
                            }
                        } else { // move right
                            if (right == 8) { // check for danger right
                                move = 3;
                            } else {
                                if (left == 8) { // check for danger left
                                    move = 2;
                                } else {
                                    move = 0; // no other choice but to move up
                                }
                            }
                        }
                    }
                } else if (move == 2) { // move left
                    if (left != 8) { // danger left
                        if (SnakeHeadYCord >= AppleYCord) { // move down
                            if (below == 8) { // check for danger below
                                move = 1;
                            } else {
                                if (above == 8) { // check for danger above
                                    move = 0;
                                } else {
                                    move = 3; // no other choice but to move right
                                }
                            }
                        } else { // move above
                            if (above == 8) { // check for danger above
                                move = 0;
                            } else {
                                if (below == 8) { // check for danger down
                                    move = 1;
                                } else {
                                    move = 3; // no other choice but to move right
                                }
                            }
                        }
                    }
                } else if (move == 3) { // move is right
                    if (right != 8) { // danger right
                        if (SnakeHeadYCord >= AppleYCord) { // move down
                            if (below == 8) { // check for danger below
                                move = 1;
                            } else {
                                if (above == 8) { // check for danger above
                                    move = 0;
                                } else {
                                    move = 2; // no other choice but to move left
                                }
                            }
                        } else { // move above
                            if (above == 8) { // check for danger above
                                move = 0;
                            } else {
                                if (below == 8) { // check for danger down
                                    move = 1;
                                } else {
                                    move = 2; // no other choice but to move left
                                }
                            }
                        }
                    }
                }

                */

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // CELL CHECKING COLLISION


                int left, right, above, below;

                if (SnakeHeadXCord != 49) {
                    right = playArea[SnakeHeadXCord + 1][SnakeHeadYCord];
                } else {
                    right = 5;
                }

                if (SnakeHeadXCord != 0) {
                    left = playArea[SnakeHeadXCord - 1][SnakeHeadYCord];
                } else {
                    left = 5;
                }

                if (SnakeHeadYCord != 0) {
                    above = playArea[SnakeHeadXCord][SnakeHeadYCord - 1];
                } else {
                    above = 5;
                }

                if (SnakeHeadYCord != 49) {
                    below = playArea[SnakeHeadXCord][SnakeHeadYCord + 1];
                } else {
                    below = 5;
                }
                ////////////////////////////////////////////////////////////////////
                /////////// SNAKE IS MOVING RIGHT
                if (move == 3) {
                    if (SnakeHeadXCord < 47) {
                        if (playArea[SnakeHeadXCord + 2][SnakeHeadYCord] == 7) { // 2 POSITIONS RIGHT OF SNAKE
                            if (AppleYCord > SnakeHeadYCord) {
                                if (below == 8) {
                                    move = 1;
                                } else {
                                    if (above == 8) {
                                        move = 0;
                                    } else {
                                        move = 3;
                                    }
                                }
                            } else {
                                if (AppleYCord < SnakeHeadYCord) {
                                    if (above == 8) {
                                        move = 0;
                                    } else {
                                        if (below == 8) {
                                            move = 1;
                                        } else {
                                            move = 3;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (SnakeHeadYCord < 49 && SnakeHeadXCord < 49) {
                        if (playArea[SnakeHeadXCord + 1][SnakeHeadYCord + 1] == 7) { // SNAKE COMING FROM BELOW
                                if (above == 8) {
                                    move = 0;
                                } else {
                                    if (below == 8) {
                                        move = 1;
                                    } else {
                                        move = 2;
                                    }
                                }
                            }
                        }


                    if (SnakeHeadYCord > 0 && SnakeHeadXCord < 49 && SnakeHeadYCord < 49) {
                        if (playArea[SnakeHeadXCord + 1][SnakeHeadYCord - 1] == 7) { // SNAKE COMING FROM ABOVE
                                if (above == 8) {
                                    move = 0;
                                } else {
                                    if (below == 8) {
                                        move = 1;
                                    } else {
                                        move = 2;
                                    }
                                }
                            }
                        }

                }

                ///////////////////////////////////////////////////////////////////////////////////////////////////
                ///////////// SNAKE IS MOVING LEFT
                if (move == 2) {
                    if (SnakeHeadXCord > 1) {
                        if (playArea[SnakeHeadXCord - 2][SnakeHeadYCord] == 7) { // 2 POSITIONS LEFT OF SNAKE
                            if (AppleYCord > SnakeHeadYCord) {
                                if (below == 8) {
                                    move = 1;
                                } else {
                                    if (above == 8) {
                                        move = 0;
                                    } else {
                                        move = 3;
                                    }
                                }
                            } else {
                                if (AppleYCord < SnakeHeadYCord) {
                                    if (above == 8) {
                                        move = 0;
                                    } else {
                                        if (below == 8) {
                                            move = 1;
                                        } else {
                                            move = 3;
                                        }
                                    }
                                }
                            }
                        }
                    }

                    if (SnakeHeadYCord < 49 && SnakeHeadXCord > 0) {
                        if (playArea[SnakeHeadXCord - 1][SnakeHeadYCord + 1] == 7 && move == 2) { // SOMEONE BELOW
                                if (above == 8) {
                                    move = 0;
                                } else {
                                    if (below == 8) {
                                        move = 1;
                                    } else {
                                        move = 3;
                                    }
                                }

                        }
                    }

                    if (SnakeHeadYCord > 0 && SnakeHeadXCord > 0 && SnakeHeadYCord < 49) {
                        if (playArea[SnakeHeadXCord - 1][SnakeHeadYCord - 1] == 7 && move == 2) { // SOMEONE ABOVE
                                if (above == 8) {
                                    move = 0;
                                } else {
                                    if (below == 8) {
                                        move = 1;
                                    } else {
                                        move = 3;

                                }
                            }
                        }
                    }
                }
                //////////////////////////////////////////////////////////////////////////////////
                // SNAKE IS MOVING UP

                if (move == 0) {
                    if (SnakeHeadYCord > 1) {
                        if (playArea[SnakeHeadXCord][SnakeHeadYCord - 2] == 7 || playArea[SnakeHeadXCord][SnakeHeadYCord-2] == 5) { // SOMEONE IS ABOVE DANGER !
                            if (AppleYCord > SnakeHeadYCord - 2) {
                                if (left == 8) {
                                    move = 2;
                                } else {
                                    if (right == 8) {
                                        move = 3;
                                    } else {
                                        move = 0;
                                    }
                                }
                            }
                        }
                    }

                    if (SnakeHeadXCord > 0 && SnakeHeadYCord > 0) {
                        if (playArea[SnakeHeadXCord - 1][SnakeHeadYCord - 1] == 7) { // SOMETHING IS ON THE LEFT
                                if (left == 8) {
                                    move = 2;
                                } else {
                                    if (right == 8) {
                                        move = 3;
                                    } else {
                                        move = 1;
                                    }
                                }

                        }
                    }

                    if (SnakeHeadXCord < 49 && SnakeHeadYCord > 0) {
                        if (playArea[SnakeHeadXCord + 1][SnakeHeadYCord - 1] == 7 ) { // SOMETHING IS ON THE RIGHT
                                if (left == 8) {
                                    move = 2;
                                } else {
                                    if (right == 8) {
                                        move = 3;
                                    } else {
                                        move = 1;
                                    }
                                }
                            }

                    }
                }
                //////////////////////////////////////////////////////////////////
                // SNAKE IS MOVING DOWN

                if (move == 1) {

                    if (SnakeHeadXCord > 0 && SnakeHeadYCord < 49) {
                        if (playArea[SnakeHeadXCord - 1][SnakeHeadYCord + 1] == 7) { // SOMETHING IS ON THE LEFT
                                if (left == 8) {
                                    move = 2;
                                } else {
                                    if (right == 8) {
                                        move = 3;
                                    } else {
                                        move = 0;
                                    }
                                }

                        }
                    }

                    if (SnakeHeadXCord < 49 && SnakeHeadYCord < 49) {
                        if (playArea[SnakeHeadXCord + 1][SnakeHeadYCord + 1] == 7) { // SOMETHING IS ON THE RIGHT
                                if (left == 8) {
                                    move = 2;
                                } else {
                                    if (right == 8) {
                                        move = 3;
                                    } else {
                                        move = 0;
                                    }
                                }

                        }
                    }
                }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
                // APPLE COLLISION CHECKING

                if (SnakeHeadXCord == AppleXCord - 1 && SnakeHeadYCord == AppleYCord) {
                    if (AppleXCord != 49) {
                        if (playArea[AppleXCord + 1][AppleYCord] != 8) {
                            if (above == 8) {
                                move = 0;
                            } else {
                                move = 1;
                            }
                        }
                    }

                    if (AppleYCord != 49) {
                        if (playArea[AppleXCord][AppleYCord + 1] != 8) {
                            if (above == 8) {
                                move = 0;
                            } else {
                                move = 1;
                            }
                        }
                    }

                    if (AppleYCord != 0) {
                        if (playArea[AppleXCord][AppleYCord - 1] != 8) {
                            if (above == 8) {
                                move = 0;
                            } else {
                                move = 1;
                            }
                        }
                    }

                }

                if (SnakeHeadXCord == AppleXCord + 1 && SnakeHeadYCord == AppleYCord) {

                    if (AppleXCord != 0) {
                        if (playArea[AppleXCord - 1][AppleYCord] != 8) {
                            if (above == 8) {
                                move = 0;
                            } else {
                                move = 1;
                            }
                        }
                    }

                    if (AppleYCord != 49) {
                        if (playArea[AppleXCord][AppleYCord + 1] != 8) {
                            if (above == 8) {
                                move = 0;
                            } else {
                                move = 1;
                            }
                        }
                    }

                    if (AppleYCord != 0) {
                        if (playArea[AppleXCord][AppleYCord - 1] != 8) {
                            if (above == 8) {
                                move = 0;
                            } else {
                                move = 1;
                            }
                        }
                    }

                }

                if (SnakeHeadYCord == AppleYCord + 1 && SnakeHeadXCord == AppleXCord) {

                    if (AppleYCord != 0) {
                        if (playArea[AppleXCord][AppleYCord - 1] != 8) {
                            if (left == 8) {
                                move = 2;
                            } else {
                                move = 3;
                            }
                        }
                    }

                    if (AppleXCord != 49) {
                        if (playArea[AppleXCord + 1][AppleYCord] != 8) {
                            if (left == 8) {
                                move = 2;
                            } else {
                                move = 3;
                            }
                        }
                    }

                    if (AppleXCord != 0) {
                        if (playArea[AppleXCord - 1][AppleYCord] != 8) {
                            if (left == 8) {
                                move = 2;
                            } else {
                                move = 3;
                            }
                        }
                    }
                }

                if (SnakeHeadYCord == AppleYCord - 1 && SnakeHeadXCord == AppleXCord) {

                    if (AppleXCord != 0) {
                        if (playArea[AppleXCord - 1][AppleYCord] != 8) {
                            if (left == 8) {
                                move = 2;
                            } else {
                                move = 3;
                            }
                        }
                    }

                    if (AppleXCord != 49) {
                        if (playArea[AppleXCord + 1][AppleYCord] != 8) {
                            if (left == 8) {
                                move = 2;
                            } else {
                                move = 3;
                            }
                        }
                    }

                    if (AppleYCord != 49) {
                        if (playArea[AppleXCord][AppleYCord + 1] != 8) {
                            if (left == 8) {
                                move = 2;
                            } else {
                                move = 3;
                            }
                        }
                    }
                }


                System.err.println(move);
                System.out.println(move);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}